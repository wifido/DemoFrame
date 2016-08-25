package com.sf.sfpp.pcomp.manager;

import com.alibaba.fastjson.JSON;
import com.sf.kafka.exception.KafkaException;
import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.utils.ExceptionUtils;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.kafka.KafkaConnectionPool;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.model.PcompVersion;
import com.sf.sfpp.pcomp.common.model.PcompVersionDoucumentDownload;
import com.sf.sfpp.pcomp.common.model.PcompVersionPlatformDownload;
import com.sf.sfpp.pcomp.common.model.extend.PcompVersionExtend;
import com.sf.sfpp.pcomp.dao.PcompSoftwareMapper;
import com.sf.sfpp.pcomp.dao.PcompVersionDoucumentDownloadMapper;
import com.sf.sfpp.pcomp.dao.PcompVersionMapper;
import com.sf.sfpp.pcomp.dao.PcompVersionPlatformDownloadMapper;
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
 * @date 2016/8/17
 */
@Component
public class PcompVersionManager {
    //// TODO: 2016/8/25 AOP改写麻烦的方法
    private final static Logger log = LoggerFactory.getLogger(PcompSoftwareManager.class);

    @Value("${pcomp.connection.key}")
    private String kafkaConnectionKey;
    @Autowired
    private KafkaConnectionPool kafkaConnectionPool;
    @Autowired
    private PcompVersionMapper pcompVersionMapper;
    @Autowired
    private PcompSoftwareMapper pcompSoftwareMapper;
    @Autowired
    private PcompVersionDoucumentDownloadMapper pcompVersionDoucumentDownloadMapper;
    @Autowired
    private PcompVersionPlatformDownloadMapper pcompVersionPlatformDownloadMapper;

    @Autowired
    private PcompSoftwareManager pcompSoftwareManager;

    private final ExecutorService executorService = Executors.newFixedThreadPool(PcompConstants.HEAVY_WORK_THREAD_POOL_SIZE);


    public PcompVersion getPcompVersionByPcompVersionId(String pcompVersionId) {
        PcompVersionExtend pcompVersion = PcompVersionExtend.fromPcompVersion(pcompVersionMapper.selectByPrimaryKey(pcompVersionId));
        pcompVersion.setPcompVersionDoucumentDownloads(pcompVersionDoucumentDownloadMapper.selectByVersionId(pcompVersion.getId()));
        pcompVersion.setPcompVersionPlatformDownloads(pcompVersionPlatformDownloadMapper.selectByVersionId(pcompVersion.getId()));
        return pcompVersion;
    }

    //// TODO: 2016/8/17 kafka连接失败处理
    public boolean updatePcompVersionOnly(PcompVersion pcompVersion) throws KafkaException {
        pcompVersion.setModifiedTime(new Date());
        boolean b = pcompVersionMapper.updateByPrimaryKeyWithBLOBs(pcompVersion) >= 0;
        if (b) {
            executorService.submit(new UpdatePcompSoftwareModifiedTimeWork(pcompVersion.getPcompSoftwareId()));
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey)
                    .send(StrUtils.makeString(PcompConstants.PCOMP_VERSION, Constants.KAFKA_TYPE_SEPARATOR, JSON.toJSONString(PcompVersionExtend.toPcompVersion((PcompVersionExtend) pcompVersion))));
        }
        return b;
    }

    public boolean addPcompVersionExtend(PcompVersionExtend pcompVersionExtend) throws KafkaException {
        for (PcompVersionDoucumentDownload pcompVersionDoucumentDownload : pcompVersionExtend.getPcompVersionDoucumentDownloads()) {
            boolean b = pcompVersionDoucumentDownloadMapper.insertSelective(pcompVersionDoucumentDownload) >= 0;
            if (!b) {
                return false;
            }
        }
        for (PcompVersionPlatformDownload pcompVersionPlatformDownload : pcompVersionExtend.getPcompVersionPlatformDownloads()) {
            boolean b = pcompVersionPlatformDownloadMapper.insertSelective(pcompVersionPlatformDownload) >= 0;
            if (!b) {
                return false;
            }
        }
        boolean b = pcompVersionMapper.insertSelective(pcompVersionExtend) >= 0;
        if (b) {
            executorService.submit(new UpdatePcompSoftwareModifiedTimeWork(pcompVersionExtend.getPcompSoftwareId()));
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey)
                    .send(StrUtils.makeString(PcompConstants.PCOMP_VERSION, Constants.KAFKA_TYPE_SEPARATOR, JSON.toJSONString(PcompVersionExtend.toPcompVersion(pcompVersionExtend))));
        }
        return b;
    }

    public boolean existsPcompVersion(String softwareId, String version) {
        return pcompVersionMapper.selectByUniqueKey(softwareId, version) != null;
    }

    /**
     * 逻辑删除一个版本，版本包含的软件文档为同步删除，会同步更新版本所属的软件
     * @param pcompVersionId
     * @param userId
     * @return
     * @throws KafkaException
     */
    public boolean deletePcompVersionLogically(String pcompVersionId, int userId) throws KafkaException {
        List<PcompVersionDoucumentDownload> pcompVersionDoucumentDownloads = pcompVersionDoucumentDownloadMapper.selectByVersionId(pcompVersionId);
        if (pcompVersionDoucumentDownloads != null) {
            for (PcompVersionDoucumentDownload pcompVersionDoucumentDownload : pcompVersionDoucumentDownloads) {
                pcompVersionDoucumentDownload.setIsDeleted(true);
                pcompVersionDoucumentDownloadMapper.updateByPrimaryKey(pcompVersionDoucumentDownload);
            }
        }
        List<PcompVersionPlatformDownload> pcompVersionPlatformDownloads = pcompVersionPlatformDownloadMapper.selectByVersionId(pcompVersionId);
        if (pcompVersionPlatformDownloads != null) {
            for (PcompVersionPlatformDownload pcompVersionPlatformDownload : pcompVersionPlatformDownloads) {
                pcompVersionPlatformDownload.setIsDeleted(true);
                pcompVersionPlatformDownloadMapper.updateByPrimaryKey(pcompVersionPlatformDownload);
            }
        }
        PcompVersion pcompVersion = pcompVersionMapper.selectByPrimaryKey(pcompVersionId);
        if (pcompVersion == null) {
            return false;
        }
        pcompVersion.setIsDeleted(true);
        pcompVersion.setModifiedTime(new Date());
        pcompVersion.setModifiedBy(userId);
        boolean b = pcompVersionMapper.updateByPrimaryKey(pcompVersion) >= 0;
        if (b) {
            executorService.submit(new UpdatePcompSoftwareModifiedTimeWork(pcompVersion.getPcompSoftwareId()));
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey)
                    .send(StrUtils.makeString(PcompConstants.PCOMP_VERSION, Constants.KAFKA_TYPE_SEPARATOR, JSON.toJSONString(pcompVersion)));
        }
        return b;
    }

    private class UpdatePcompSoftwareModifiedTimeWork implements Runnable {
        private final String pcompSoftwareId;

        private UpdatePcompSoftwareModifiedTimeWork(String pcompSoftwareId) {
            this.pcompSoftwareId = pcompSoftwareId;
        }

        @Override
        public void run() {
            try {
                pcompSoftwareManager.updateModifiedTime(pcompSoftwareId);
            } catch (Exception e) {
                log.warn(ExceptionUtils.getStackTrace(e));
            }
        }
    }
}
