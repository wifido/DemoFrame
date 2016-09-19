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
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.common.model.PcompVersion;
import com.sf.sfpp.pcomp.common.model.extend.PcompSoftwareExtend;
import com.sf.sfpp.pcomp.common.model.extend.PcompVersionExtend;
import com.sf.sfpp.pcomp.dao.PcompSoftwareMapper;
import com.sf.sfpp.pcomp.dao.PcompVersionDoucumentDownloadMapper;
import com.sf.sfpp.pcomp.dao.PcompVersionMapper;
import com.sf.sfpp.pcomp.dao.PcompVersionPlatformDownloadMapper;
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
public class PcompSoftwareManager extends EventManager {
    //// TODO: 2016/8/25 AOP改写麻烦的方法
    private final static Logger log = LoggerFactory.getLogger(PcompSoftwareManager.class);

    @Value("${pcomp.connection.key}")
    private String kafkaConnectionKey;
    @Autowired
    private KafkaConnectionPool kafkaConnectionPool;
    @Autowired
    private PcompSoftwareMapper pcompSoftwareMapper;
    @Autowired
    private PcompVersionMapper pcompVersionMapper;
    @Autowired
    private PcompVersionDoucumentDownloadMapper pcompVersionDoucumentDownloadMapper;
    @Autowired
    private PcompVersionPlatformDownloadMapper pcompVersionPlatformDownloadMapper;

    @Autowired
    private PcompVersionManager pcompVersionManager;
    @Autowired
    private PcompKindManager pcompKindManager;

    private final ExecutorService executorService = Executors
            .newFixedThreadPool(PcompConstants.HEAVY_WORK_THREAD_POOL_SIZE);

    public Page<PcompSoftware> getAllAvailablePcompSoftwaresByPcompKindId(String kindId, int pageNumber) {
        if (pageNumber != Constants.ALL_PAGE_NUMBER) {
            PageHelper.startPage(pageNumber, PcompConstants.NUMBER_PER_PAGE);
        } else {
            PageHelper.startPage(1, Integer.MAX_VALUE);
        }
        return (Page<PcompSoftware>) pcompSoftwareMapper.selectAllAvailableByKindId(kindId);
    }

    public Page<PcompSoftware> getRecommendedPcompSoftwares() {
        PageHelper.startPage(1, PcompConstants.NUMBER_PER_PAGE);
        return (Page<PcompSoftware>) pcompSoftwareMapper.selectLatest();
    }

    public Page<PcompSoftware> getLatestPcompSoftwares() {
        PageHelper.startPage(1, PcompConstants.NUMBER_PER_PAGE);
        return (Page<PcompSoftware>) pcompSoftwareMapper.selectLatest();
    }

    public PcompSoftware getPcompSoftwareByPcompSoftwareId(String pcompSoftwareId) {
        PcompSoftwareExtend pcompSoftware = PcompSoftwareExtend
                .fromPcompSoftware(pcompSoftwareMapper.selectByPrimaryKey(pcompSoftwareId));
        List<PcompVersion> pcompVersions = pcompVersionMapper.selectAvailableBySoftwareId(pcompSoftwareId);
        for (PcompVersion pcompVersion : pcompVersions) {
            PcompVersionExtend pcompVersionExtend = PcompVersionExtend.fromPcompVersion(pcompVersion);
            pcompVersionExtend.setPcompVersionDoucumentDownloads(
                    pcompVersionDoucumentDownloadMapper.selectByVersionId(pcompVersion.getId()));
            pcompVersionExtend.setPcompVersionPlatformDownloads(
                    pcompVersionPlatformDownloadMapper.selectByVersionId(pcompVersion.getId()));
            pcompSoftware.getPcompVersions().add(pcompVersionExtend);
        }
        return pcompSoftware;
    }

    public boolean existsPcompSoftware(String kindId, String pcompsoftwareName) {
        return pcompSoftwareMapper.selectByUniqueKey(kindId, pcompsoftwareName) != null;
    }

    public boolean addPcompSoftwareOnly(PcompSoftware pcompSoftware) throws KafkaException {
        boolean b = pcompSoftwareMapper.insertSelective(pcompSoftware) > 0;
        pcompSoftware = pcompSoftwareMapper.selectByPrimaryKey(pcompSoftware.getId());
        if (b) {
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey).send(StrUtils
                    .makeString(PcompConstants.PCOMP_SOFTWARE, Constants.KAFKA_TYPE_SEPARATOR,
                            JSON.toJSONString(pcompSoftware)));
            addInitialResource(pcompSoftware.getId(), pcompSoftware.getCreatedBy());
            executorService.submit(new UpdatePcompKindModifiedTimeWork(pcompSoftware.getPcompKindId(),
                    pcompSoftware.getModifiedBy()));
        }
        return b;
    }

    //// TODO: 2016/8/17 kafka连接失败处理
    public boolean updatePcompSoftwareOnly(PcompSoftware pcompSoftware) throws KafkaException {
        pcompSoftware.setModifiedTime(new Date());
        //两个线程同时修改同一个pcompSoftware，可能导致kafka内和数据库内数据不一致，但几率很小，不考虑。
        boolean b = pcompSoftwareMapper.updateByPrimaryKeyWithBLOBs(pcompSoftware) >= 0;
        if (b) {
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey).send(StrUtils
                    .makeString(PcompConstants.PCOMP_SOFTWARE, Constants.KAFKA_TYPE_SEPARATOR,
                            JSON.toJSONString(pcompSoftware)));
            executorService.submit(new UpdatePcompKindModifiedTimeWork(pcompSoftware.getPcompKindId(),
                    pcompSoftware.getModifiedBy()));
        }
        return b;
    }

    public PcompSoftware getPcompSoftwareByPcompKindIdAndPcompSoftwareName(String kindId, String softwareName) {
        return pcompSoftwareMapper.selectByUniqueKey(kindId, softwareName);
    }

    /**
     * 同步删除软件，异步删除包含的版本
     *
     * @param pcompSoftwareId
     * @param userId
     * @return
     * @throws KafkaException
     */
    public boolean deletePcompSoftwareByPcompSoftwareIdLogically(String pcompSoftwareId, int userId)
            throws KafkaException {
        PcompSoftware pcompSoftware = pcompSoftwareMapper.selectByPrimaryKey(pcompSoftwareId);
        if (pcompSoftware == null) {
            return false;
        }
        pcompSoftware.setIsDeleted(true);
        pcompSoftware.setModifiedBy(userId);
        pcompSoftware.setModifiedTime(new Date());
        boolean b = pcompSoftwareMapper.updateByPrimaryKey(pcompSoftware) >= 0;
        Resource resource = resourceMapper.selectResourceByUrl(getResourceUrl(pcompSoftwareId));
        resource.setIsDeleted(true);
        resourceMapper.updateByPrimaryKey(resource);
        if (b) {
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey).send(StrUtils
                    .makeString(PcompConstants.PCOMP_SOFTWARE, Constants.KAFKA_TYPE_SEPARATOR,
                            JSON.toJSONString(pcompSoftware)));
            executorService.submit(new UpdatePcompKindModifiedTimeWork(pcompSoftware.getPcompKindId(), userId));
        }
        executorService.submit(new DeletePcompVersionLogicallyWork(pcompSoftwareId, userId));
        return b;
    }

    @Override
    public String getResourceUrl(String id) {
        return StrUtils
                .makeString(pcompKindManager.getResourceUrl(getPcompSoftwareByPcompSoftwareId(id).getPcompKindId()),
                        ":", id);
    }


    public Page getAllAvailableInternalSoftwaresOrderByCreatedTime(int pageNumber) {
        if (pageNumber != Constants.ALL_PAGE_NUMBER) {
            PageHelper.startPage(pageNumber, PcompConstants.NUMBER_PER_PAGE);
        } else {
            PageHelper.startPage(1, Integer.MAX_VALUE);
        }
        return (Page<PcompSoftware>) pcompSoftwareMapper.selectAllAcailableInternalOrderByCreatedTime();
    }

    public Page getAllAvailableOpenSoftwaresOrderByCreatedTime(int pageNumber) {
        if (pageNumber != Constants.ALL_PAGE_NUMBER) {
            PageHelper.startPage(pageNumber, PcompConstants.NUMBER_PER_PAGE);
        } else {
            PageHelper.startPage(1, Integer.MAX_VALUE);
        }
        return (Page<PcompSoftware>) pcompSoftwareMapper.selectAllAcailableOpenOrderByCreatedTime();
    }

    private class DeletePcompVersionLogicallyWork implements Runnable {
        private final String pcompSoftwareId;
        private final int userId;

        private DeletePcompVersionLogicallyWork(String pcompSoftwareId, int userId) {
            this.pcompSoftwareId = pcompSoftwareId;
            this.userId = userId;
        }

        @Override
        public void run() {
            List<String> pcompVersionIds = pcompVersionMapper.selectAvailableIDBySoftwareId(pcompSoftwareId);
            for (String pcompVersionId : pcompVersionIds) {
                try {
                    Resource resource = resourceMapper.selectResourceByUrl(pcompVersionManager.getResourceUrl(pcompVersionId));
                    resource.setIsDeleted(true);
                    resourceMapper.updateByPrimaryKey(resource);
                    pcompVersionManager.deletePcompVersionLogically(pcompVersionId, userId);
                } catch (Exception e) {
                    log.warn(ExceptionUtils.getStackTrace(e));
                }
            }
        }
    }

    public boolean updateModifiedTime(String pcompSoftwareId, int userId) throws KafkaException {
        PcompSoftware pcompSoftware = pcompSoftwareMapper.selectByPrimaryKey(pcompSoftwareId);
        if (pcompSoftware == null) {
            return false;
        }
        pcompSoftware.setModifiedTime(new Date());
        boolean b = pcompSoftwareMapper.updateByPrimaryKey(pcompSoftware) >= 0;
        if (b) {
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey).send(StrUtils
                    .makeString(PcompConstants.PCOMP_SOFTWARE, Constants.KAFKA_TYPE_SEPARATOR,
                            JSON.toJSONString(pcompSoftware)));
            executorService.submit(new UpdatePcompKindModifiedTimeWork(pcompSoftware.getPcompKindId(), userId));
        }
        return b;
    }

    private class UpdatePcompKindModifiedTimeWork implements Runnable {
        private final String pcompKindId;
        private final int userId;

        private UpdatePcompKindModifiedTimeWork(String pcompKindId, int userId) {
            this.pcompKindId = pcompKindId;
            this.userId = userId;
        }

        @Override
        public void run() {
            try {
                pcompKindManager.updateModifiedTime(pcompKindId, userId);
            } catch (Exception e) {
                log.warn(ExceptionUtils.getStackTrace(e));
            }
        }
    }
}
