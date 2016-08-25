package com.sf.sfpp.pcomp.manager;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
public class PcompKindManager {
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
    private PcompSoftwareManager pcompSoftwareManager;
    @Autowired
    private PcompTitleManager pcompTitleManager;

    private final ExecutorService executorService = Executors.newFixedThreadPool(PcompConstants.HEAVY_WORK_THREAD_POOL_SIZE);

    public Page<PcompKind> getKindsByTitle(String titleId, int pageNumber) {
        if (pageNumber != Constants.ALL_PAGE_NUMBER) {
            PageHelper.startPage(pageNumber, PcompConstants.NUMBER_PER_PAGE);
        } else {
            PageHelper.startPage(1, Integer.MAX_VALUE);
        }
        return (Page<PcompKind>) pcompKindMapper.selectAvailabeleKindsByTitleID(titleId);
    }

    public Page<PcompKind> getAllKinds(int pageNumber) {
        if (pageNumber != Constants.ALL_PAGE_NUMBER) {
            PageHelper.startPage(pageNumber, PcompConstants.NUMBER_PER_PAGE);
        } else {
            PageHelper.startPage(1, Integer.MAX_VALUE);
        }
        Page<PcompKind> pcompKinds = (Page<PcompKind>) pcompKindMapper.selectAllAvailabeleKinds();
        pcompKinds.getPages();
        return pcompKinds;
    }

    public PcompKind getPcompKindByPcompKindId(String pcompKindId) {
        return pcompKindMapper.selectByPrimaryKey(pcompKindId);
    }

    public PcompKind getPcompKindByPcompSoftwareId(String pcompSoftwareId) {
        PcompSoftware pcompSoftware = pcompSoftwareMapper.selectByPrimaryKey(pcompSoftwareId);
        return pcompKindMapper.selectByPrimaryKey(pcompSoftware.getPcompKindId());
    }

    public PcompKind getPcompKindByPcompTitleNameAndPcompKindName(String pcompTitleName, String pcompKindName) {
        PcompTitle pcompTitle = pcompTitleMapper.selectByUniqueKey(pcompTitleName);
        return pcompKindMapper.selectByUniqueKey(pcompTitle.getId(), pcompKindName);
    }

    public boolean existsPcompKind(String pcompTitleName, String pcompKindName) {
        String pcompTitleId = pcompTitleMapper.selectByUniqueKey(pcompTitleName).getId();
        return pcompKindMapper.selectByUniqueKey(pcompTitleId, pcompKindName) != null;
    }

    public boolean addPcompKind(PcompKind pcompKind) throws KafkaException {
        boolean b = pcompKindMapper.insertSelective(pcompKind) > 0;
        pcompKind = pcompKindMapper.selectByPrimaryKey(pcompKind.getId());
        if (b) {
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey)
                    .send(StrUtils.makeString(PcompConstants.PCOMP_KIND, Constants.KAFKA_TYPE_SEPARATOR, JSON.toJSONString(pcompKind)));
            executorService.submit(new UpdatePcompTitleModifiedTimeWork(pcompKind.getPcompTitleId()));
        }
        return b;
    }

    public boolean deletePcompKindLogically(String pcompKindId, int userId) throws KafkaException {
        PcompKind pcompKind = pcompKindMapper.selectByPrimaryKey(pcompKindId);
        if (pcompKind == null) {
            return false;
        }
        pcompKind.setIsDeleted(true);
        pcompKind.setModifiedBy(userId);
        pcompKind.setModifiedTime(new Date());
        boolean b = pcompKindMapper.updateByPrimaryKey(pcompKind) >= 0;
        if (b) {
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey)
                    .send(StrUtils.makeString(PcompConstants.PCOMP_KIND, Constants.KAFKA_TYPE_SEPARATOR, JSON.toJSONString(pcompKind)));
            executorService.submit(new UpdatePcompTitleModifiedTimeWork(pcompKind.getPcompTitleId()));
        }
        executorService.submit(new DeletePcompSoftwareLogicallyWork(pcompKindId, userId));
        return b;
    }

    private class DeletePcompSoftwareLogicallyWork implements Runnable {
        private final String pcompKindId;
        private final int userId;

        private DeletePcompSoftwareLogicallyWork(String pcompKindId, int userId) {
            this.pcompKindId = pcompKindId;
            this.userId = userId;
        }

        @Override
        public void run() {
            List<String> pcompSoftwaresIds = pcompSoftwareMapper.selectAllAvailableIdByKindId(pcompKindId);
            for (String pcompSoftwaresId : pcompSoftwaresIds) {
                try {
                    pcompSoftwareManager.deletePcompSoftwareByPcompSoftwareIdLogically(pcompSoftwaresId, userId);
                } catch (Exception e) {
                    log.warn(ExceptionUtils.getStackTrace(e));
                }
            }
        }
    }

    public boolean updateModifiedTime(String pcompKindId) throws KafkaException {
        PcompKind pcompKind = pcompKindMapper.selectByPrimaryKey(pcompKindId);
        if (pcompKind == null) {
            return false;
        }
        pcompKind.setModifiedTime(new Date());
        boolean b = pcompKindMapper.updateByPrimaryKey(pcompKind) >= 0;
        if (b) {
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey)
                    .send(StrUtils.makeString(PcompConstants.PCOMP_KIND, Constants.KAFKA_TYPE_SEPARATOR, JSON.toJSONString(pcompKind)));
            executorService.submit(new UpdatePcompTitleModifiedTimeWork(pcompKind.getPcompTitleId()));
        }
        return b;
    }

    private class UpdatePcompTitleModifiedTimeWork implements Runnable {
        private final String pcompTitleId;

        private UpdatePcompTitleModifiedTimeWork(String pcompTitleId) {
            this.pcompTitleId = pcompTitleId;
        }

        @Override
        public void run() {
            try {
                pcompTitleManager.updateModifiedTime(pcompTitleId);
            } catch (Exception e) {
                log.warn(ExceptionUtils.getStackTrace(e));
            }
        }
    }
}
