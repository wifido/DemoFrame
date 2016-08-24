package com.sf.sfpp.pcomp.manager;

import com.alibaba.fastjson.JSON;
import com.sf.kafka.exception.KafkaException;
import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.kafka.KafkaConnectionPool;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.common.model.PcompVersion;
import com.sf.sfpp.pcomp.common.model.PcompVersionDoucumentDownload;
import com.sf.sfpp.pcomp.common.model.PcompVersionPlatformDownload;
import com.sf.sfpp.pcomp.common.model.extend.PcompVersionExtend;
import com.sf.sfpp.pcomp.dao.PcompSoftwareMapper;
import com.sf.sfpp.pcomp.dao.PcompVersionDoucumentDownloadMapper;
import com.sf.sfpp.pcomp.dao.PcompVersionMapper;
import com.sf.sfpp.pcomp.dao.PcompVersionPlatformDownloadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/17
 */
@Component
public class PcompVersionManager {
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
        PcompSoftware pcompSoftware = pcompSoftwareMapper.selectByPrimaryKey(pcompVersion.getPcompSoftwareId());
        pcompSoftware.setModifiedTime(pcompVersion.getModifiedTime());
        pcompSoftware.setModifiedBy(pcompVersion.getModifiedBy());
        pcompSoftwareMapper.updateByPrimaryKey(pcompSoftware);
        if (b) {
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey)
                    .send(StrUtils.makeString(PcompConstants.PCOMP_VERSION, Constants.KAFKA_TYPE_SEPARATOR, JSON.toJSONString(PcompVersionExtend.toPcompVersion((PcompVersionExtend) pcompVersion))));
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey)
                    .send(StrUtils.makeString(PcompConstants.PCOMP_SOFTWARE, Constants.KAFKA_TYPE_SEPARATOR, JSON.toJSONString(pcompSoftware)));
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
            PcompSoftware pcompSoftware = pcompSoftwareMapper.selectByPrimaryKey(pcompVersionExtend.getPcompSoftwareId());
            pcompSoftware.setModifiedTime(pcompVersionExtend.getModifiedTime());
            pcompSoftware.setModifiedBy(pcompVersionExtend.getModifiedBy());
            pcompSoftwareMapper.updateByPrimaryKey(pcompSoftware);
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey)
                    .send(StrUtils.makeString(PcompConstants.PCOMP_VERSION, Constants.KAFKA_TYPE_SEPARATOR, JSON.toJSONString(PcompVersionExtend.toPcompVersion(pcompVersionExtend))));
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey)
                    .send(StrUtils.makeString(PcompConstants.PCOMP_SOFTWARE, Constants.KAFKA_TYPE_SEPARATOR, JSON.toJSONString(pcompSoftware)));
        }
        return b;
    }

    public boolean existsPcompVersion(String softwareId, String version) {
        return pcompVersionMapper.selectByUniqueKey(softwareId, version) != null;
    }
}
