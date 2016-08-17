package com.sf.sfpp.pcomp.manager;

import com.sf.sfpp.pcomp.common.model.PcompVersion;
import com.sf.sfpp.pcomp.common.model.extend.PcompVersionExtend;
import com.sf.sfpp.pcomp.dao.PcompVersionDoucumentDownloadMapper;
import com.sf.sfpp.pcomp.dao.PcompVersionMapper;
import com.sf.sfpp.pcomp.dao.PcompVersionPlatformDownloadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/17
 */
@Component
public class PcompVersionManager {
    @Autowired
    private PcompVersionMapper pcompVersionMapper;
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

    public boolean updatePcompVersionOnly(PcompVersion pcompVersion) {
        pcompVersion.setModifiedTime(new Date());
        return pcompVersionMapper.updateByPrimaryKeyWithBLOBs(pcompVersion) >= 0;
    }
}
