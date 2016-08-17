package com.sf.sfpp.pcomp.manager;

import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.common.model.PcompVersion;
import com.sf.sfpp.pcomp.common.model.extend.PcompSoftwareExtend;
import com.sf.sfpp.pcomp.common.model.extend.PcompVersionExtend;
import com.sf.sfpp.pcomp.dao.PcompSoftwareMapper;
import com.sf.sfpp.pcomp.dao.PcompVersionDoucumentDownloadMapper;
import com.sf.sfpp.pcomp.dao.PcompVersionMapper;
import com.sf.sfpp.pcomp.dao.PcompVersionPlatformDownloadMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private PcompSoftwareMapper pcompSoftwareMapper;
    @Autowired
    private PcompVersionMapper pcompVersionMapper;
    @Autowired
    private PcompVersionDoucumentDownloadMapper pcompVersionDoucumentDownloadMapper;
    @Autowired
    private PcompVersionPlatformDownloadMapper pcompVersionPlatformDownloadMapper;


    public List<PcompSoftware> getAllAvailablePcompSoftwaresByPcompKindId(String kindId) {
        return pcompSoftwareMapper.selectAllAcailableByKindId(kindId);
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

    public boolean updatePcompSoftwareOnly(PcompSoftware pcompSoftware) {
        pcompSoftware.setModifiedTime(new Date());
        return pcompSoftwareMapper.updateByPrimaryKeyWithBLOBs(pcompSoftware) >= 0;
    }
}
