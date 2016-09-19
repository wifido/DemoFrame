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
import com.sf.sfpp.user.dao.domain.Resource;
import com.sf.sfpp.user.dao.mapper.ResourceMapper;
import com.sf.sfpp.user.dao.mapper.RoleMapper;
import com.sf.sfpp.user.dao.mapper.UserMapper;
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
public class PcompKindManager extends EventManager {
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
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private PcompSoftwareManager pcompSoftwareManager;
    @Autowired
    private PcompTitleManager pcompTitleManager;

    private final ExecutorService executorService = Executors
            .newFixedThreadPool(PcompConstants.HEAVY_WORK_THREAD_POOL_SIZE);

    public Page<PcompKind> getKindsByTitle(String titleId, int pageNumber) {
        if (pageNumber != Constants.ALL_PAGE_NUMBER) {
            PageHelper.startPage(pageNumber, PcompConstants.NUMBER_PER_PAGE);
        } else {
            PageHelper.startPage(1, Integer.MAX_VALUE);
        }
        return (Page<PcompKind>) pcompKindMapper.selectAvailabeleKindsByTitleID(titleId);
    }

    public Page<PcompKind> getRecommendedPcompKinds() {
        PageHelper.startPage(1, PcompConstants.NUMBER_PER_PAGE);
        return (Page<PcompKind>) pcompKindMapper.selectLatest();
    }

    public Page<PcompKind> getLatestPcompKinds() {
        PageHelper.startPage(1, PcompConstants.NUMBER_PER_PAGE);
        return (Page<PcompKind>) pcompKindMapper.selectLatest();
    }

    public Page<PcompKind> getAllKinds(int pageNumber) {
        if (pageNumber != Constants.ALL_PAGE_NUMBER) {
            PageHelper.startPage(pageNumber, PcompConstants.NUMBER_PER_PAGE);
        } else {
            PageHelper.startPage(1, Integer.MAX_VALUE);
        }
        Page<PcompKind> pcompKinds = (Page<PcompKind>) pcompKindMapper.selectAllAvailabeleKinds();
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
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey).send(StrUtils
                    .makeString(PcompConstants.PCOMP_KIND, Constants.KAFKA_TYPE_SEPARATOR,
                            JSON.toJSONString(pcompKind)));
            addInitialResource(pcompKind.getId(), pcompKind.getCreatedBy());
            executorService.submit(new UpdatePcompTitleModifiedTimeWork(pcompKind.getPcompTitleId(),
                    pcompKind.getModifiedBy()));
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
        Resource resource = resourceMapper.selectResourceByUrl(getResourceUrl(pcompKindId));
        if (resource != null) {
            resource.setIsDeleted(true);
            resourceMapper.updateByPrimaryKey(resource);
        }
        if (b) {
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey).send(StrUtils
                    .makeString(PcompConstants.PCOMP_KIND, Constants.KAFKA_TYPE_SEPARATOR,
                            JSON.toJSONString(pcompKind)));
            executorService.submit(new UpdatePcompTitleModifiedTimeWork(pcompKind.getPcompTitleId(), userId));
        }
        executorService.submit(new DeletePcompSoftwareLogicallyWork(pcompKindId, userId));
        return b;
    }

    public boolean updatePcompKind(PcompKind pcompKind) throws KafkaException {
        pcompKind.setModifiedTime(new Date());
        boolean b = pcompKindMapper.updateByPrimaryKey(pcompKind) >= 0;
        if (b) {
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey).send(StrUtils
                    .makeString(PcompConstants.PCOMP_KIND, Constants.KAFKA_TYPE_SEPARATOR,
                            JSON.toJSONString(pcompKind)));
            executorService.submit(new UpdatePcompTitleModifiedTimeWork(pcompKind.getPcompTitleId(),
                    pcompKind.getModifiedBy()));
        }
        return b;
    }

    public String getResourceUrl(String kindId) {
        return StrUtils
                .makeString(pcompTitleManager.getResourceUrl(getPcompKindByPcompKindId(kindId).getPcompTitleId()), ":",
                        kindId);
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
                    Resource resource = resourceMapper
                            .selectResourceByUrl(pcompSoftwareManager.getResourceUrl(pcompSoftwaresId));
                    if (resource != null) {
                        resource.setIsDeleted(true);
                        resourceMapper.updateByPrimaryKey(resource);
                    }
                    pcompSoftwareManager.deletePcompSoftwareByPcompSoftwareIdLogically(pcompSoftwaresId, userId);
                } catch (Exception e) {
                    log.warn(ExceptionUtils.getStackTrace(e));
                }
            }
        }
    }

    public boolean updateModifiedTime(String pcompKindId, int userId) throws KafkaException {
        PcompKind pcompKind = pcompKindMapper.selectByPrimaryKey(pcompKindId);
        if (pcompKind == null) {
            return false;
        }
        pcompKind.setModifiedTime(new Date());
        pcompKind.setModifiedBy(userId);
        boolean b = pcompKindMapper.updateByPrimaryKey(pcompKind) >= 0;
        if (b) {
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey).send(StrUtils
                    .makeString(PcompConstants.PCOMP_KIND, Constants.KAFKA_TYPE_SEPARATOR,
                            JSON.toJSONString(pcompKind)));
            executorService.submit(new UpdatePcompTitleModifiedTimeWork(pcompKind.getPcompTitleId(), userId));
        }
        return b;
    }

    private class UpdatePcompTitleModifiedTimeWork implements Runnable {
        private final String pcompTitleId;
        private final int userId;

        private UpdatePcompTitleModifiedTimeWork(String pcompTitleId, int userId) {
            this.pcompTitleId = pcompTitleId;
            this.userId = userId;
        }

        @Override
        public void run() {
            try {
                pcompTitleManager.updateModifiedTime(pcompTitleId, userId);
            } catch (Exception e) {
                log.warn(ExceptionUtils.getStackTrace(e));
            }
        }
    }
}
