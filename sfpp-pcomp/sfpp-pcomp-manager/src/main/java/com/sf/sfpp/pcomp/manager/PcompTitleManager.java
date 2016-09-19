package com.sf.sfpp.pcomp.manager;

import com.alibaba.fastjson.JSON;
import com.sf.kafka.exception.KafkaException;
import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.utils.ExceptionUtils;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.kafka.KafkaConnectionPool;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.model.PcompKind;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.common.model.PcompTitle;
import com.sf.sfpp.pcomp.dao.PcompKindMapper;
import com.sf.sfpp.pcomp.dao.PcompSoftwareMapper;
import com.sf.sfpp.pcomp.dao.PcompTitleMapper;
import com.sf.sfpp.user.dao.domain.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/15
 */
@Component
public class PcompTitleManager extends EventManager {
    //// TODO: 2016/8/25 AOP改写麻烦的方法
    private final static Logger log = LoggerFactory.getLogger(PcompKindManager.class);
    @Value("${pcomp.connection.key}")
    private String kafkaConnectionKey;
    @Autowired
    private KafkaConnectionPool kafkaConnectionPool;
    @Autowired
    private PcompTitleMapper pcompTitleMapper;
    @Autowired
    private PcompKindMapper pcompKindMapper;
    @Autowired
    private PcompSoftwareMapper pcompSoftwareMapper;

    @Autowired
    private PcompKindManager pcompKindManager;

    private final ExecutorService executorService = Executors.newFixedThreadPool(PcompConstants.HEAVY_WORK_THREAD_POOL_SIZE);


    public List<PcompTitle> getAllTitles() {
        return pcompTitleMapper.selectAllAvailable();
    }

    public PcompTitle getPcompTitleByPcompKindId(String pcompKindId) {
        PcompKind pcompKind = pcompKindMapper.selectByPrimaryKey(pcompKindId);
        return pcompTitleMapper.selectByPrimaryKey(pcompKind.getPcompTitleId());
    }

    public PcompTitle getPcompTitleByPcompSoftwareId(String pcompSoftwareId) {
        PcompSoftware pcompSoftware = pcompSoftwareMapper.selectByPrimaryKey(pcompSoftwareId);
        return getPcompTitleByPcompKindId(pcompSoftware.getPcompKindId());
    }

    public PcompTitle getPcompTitleByPcompTitleName(String pcompTitleName) {
        return pcompTitleMapper.selectByUniqueKey(pcompTitleName);
    }

    public boolean existsPcompTitleName(String pcompTitleName) {
        return pcompTitleMapper.selectByUniqueKey(pcompTitleName) != null;
    }

    public String getResourceUrl(String pcompTitleId){
        return StrUtils.makeString(":sfpp:pcomp:",pcompTitleId);
    }

    public boolean addPcompTitle(PcompTitle pcompTitle) throws KafkaException {
        boolean b = pcompTitleMapper.insertSelective(pcompTitle) > 0;
        pcompTitle = pcompTitleMapper.selectByPrimaryKey(pcompTitle.getId());
        if (b) {
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey)
                    .send(StrUtils.makeString(PcompConstants.PCOMP_TITLE, Constants.KAFKA_TYPE_SEPARATOR, JSON.toJSONString(pcompTitle)));
            addInitialResource(pcompTitle.getId(),pcompTitle.getCreatedBy());
        }
        return b;
    }

    public boolean deletePcompTitleLogically(String pcompTitleId, int userId) throws KafkaException {
        PcompTitle pcompTitle = pcompTitleMapper.selectByPrimaryKey(pcompTitleId);
        if (pcompTitle == null) {
            return false;
        }
        pcompTitle.setIsDeleted(true);
        pcompTitle.setModifiedBy(userId);
        pcompTitle.setModifiedTime(new Date());
        boolean b = pcompTitleMapper.updateByPrimaryKey(pcompTitle) >= 0;
        Resource resource = resourceMapper.selectResourceByUrl(getResourceUrl(pcompTitleId));
        if (resource != null) {
            resource.setIsDeleted(true);
            resourceMapper.updateByPrimaryKey(resource);
        }
        if (b) {
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey)
                    .send(StrUtils.makeString(PcompConstants.PCOMP_TITLE, Constants.KAFKA_TYPE_SEPARATOR, JSON.toJSONString(pcompTitle)));
        }
        DeletePcompKindLogicallyWork deletePcompKindLogicallyWork = new DeletePcompKindLogicallyWork(pcompTitleId, userId);
        executorService.submit(deletePcompKindLogicallyWork);
        return b;
    }

    public boolean updatePcompTitle(PcompTitle pcompTitle) throws KafkaException {
        pcompTitle.setModifiedTime(new Date());
        boolean b = pcompTitleMapper.updateByPrimaryKeySelective(pcompTitle) >= 0;
        pcompTitle = pcompTitleMapper.selectByPrimaryKey(pcompTitle.getId());
        if (b) {
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey)
                    .send(StrUtils.makeString(PcompConstants.PCOMP_TITLE, Constants.KAFKA_TYPE_SEPARATOR, JSON.toJSONString(pcompTitle)));
        }
        return b;
    }

    public PcompTitle getPcompTitleByPcompTitleId(String titleId) {
        return pcompTitleMapper.selectByPrimaryKey(titleId);
    }

    private class DeletePcompKindLogicallyWork implements Runnable {
        private final String pcompTileId;
        private final int userId;

        private DeletePcompKindLogicallyWork(String pcompTileId, int userId) {
            this.pcompTileId = pcompTileId;
            this.userId = userId;
        }

        @Override
        public void run() {
            List<String> pcompKinds = pcompKindMapper.selectAvailabeleKindsIDByTitleID(pcompTileId);
            for (String pcompKind : pcompKinds) {
                try {
                    Resource resource = resourceMapper.selectResourceByUrl(pcompKindManager.getResourceUrl(pcompKind));
                    if (resource != null) {
                        resource.setIsDeleted(true);
                        resourceMapper.updateByPrimaryKey(resource);
                    }
                    pcompKindManager.deletePcompKindLogically(pcompKind, userId);
                } catch (Exception e) {
                    log.warn(ExceptionUtils.getStackTrace(e));
                }
            }
        }
    }

    public boolean updateModifiedTime(String pcompTitleId, int userId) throws KafkaException {
        PcompTitle pcompTitle = pcompTitleMapper.selectByPrimaryKey(pcompTitleId);
        if (pcompTitle == null) {
            return false;
        }
        pcompTitle.setModifiedTime(new Date());
        pcompTitle.setModifiedBy(userId);
        boolean b = pcompTitleMapper.updateByPrimaryKey(pcompTitle) >= 0;
        if (b) {
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey)
                    .send(StrUtils.makeString(PcompConstants.PCOMP_TITLE, Constants.KAFKA_TYPE_SEPARATOR, JSON.toJSONString(pcompTitle)));
        }
        return b;
    }
}
