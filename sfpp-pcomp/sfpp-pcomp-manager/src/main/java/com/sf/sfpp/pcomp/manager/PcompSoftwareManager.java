package com.sf.sfpp.pcomp.manager;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sf.kafka.exception.KafkaException;
import com.sf.sfpp.common.Constants;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/15
 */
@Component
public class PcompSoftwareManager {
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


    public Page<PcompSoftware> getAllAvailablePcompSoftwaresByPcompKindId(String kindId, int pageNumber) {
        if (pageNumber != Constants.ALL_PAGE_NUMBER) {
            PageHelper.startPage(pageNumber, PcompConstants.numberPerPage);
        }else{
            PageHelper.startPage(1, Integer.MAX_VALUE);
        }
        return (Page<PcompSoftware>) pcompSoftwareMapper.selectAllAcailableByKindId(kindId);
    }

    public PcompSoftware getPcompSoftwareByPcompSoftwareId(String pcompSoftwareId) {
        PcompSoftwareExtend pcompSoftware = PcompSoftwareExtend.fromPcompSoftware(pcompSoftwareMapper.selectByPrimaryKey(pcompSoftwareId));
        List<PcompVersion> pcompVersions = pcompVersionMapper.selectAvailableBySoftwareId(pcompSoftwareId);
        for (PcompVersion pcompVersion : pcompVersions) {
            PcompVersionExtend pcompVersionExtend = PcompVersionExtend.fromPcompVersion(pcompVersion);
            pcompVersionExtend.setPcompVersionDoucumentDownloads(pcompVersionDoucumentDownloadMapper.selectByVersionId(pcompVersion.getId()));
            pcompVersionExtend.setPcompVersionPlatformDownloads(pcompVersionPlatformDownloadMapper.selectByVersionId(pcompVersion.getId()));
            pcompSoftware.getPcompVersions().add(pcompVersionExtend);
        }
        return pcompSoftware;
    }

    //// TODO: 2016/8/17 kafka连接失败处理
    public boolean updatePcompSoftwareOnly(PcompSoftware pcompSoftware) throws KafkaException {
        pcompSoftware.setModifiedTime(new Date());
        //两个线程同时修改同一个pcompSoftware，可能导致kafka内和数据库内数据不一致，但几率很小，不考虑。
        boolean b = pcompSoftwareMapper.updateByPrimaryKeyWithBLOBs(pcompSoftware) >= 0;
        if (b) {
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey)
                    .send(StrUtils.makeString(PcompConstants.PCOMP_SOFTWARE, Constants.KAFKA_TYPE_SEPARATOR, JSON.toJSONString(PcompSoftwareExtend.toPcompSoftware((PcompSoftwareExtend) pcompSoftware))));
        }
        return b;
    }
}
