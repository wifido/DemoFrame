package com.sf.sfpp.pcomp.common.model.extend;

import com.sf.sfpp.pcomp.common.model.PcompVersion;
import com.sf.sfpp.pcomp.common.model.PcompVersionDoucumentDownload;
import com.sf.sfpp.pcomp.common.model.PcompVersionPlatformDownload;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/12
 */
public class PcompVersionExtend extends PcompVersion {
    private List<PcompVersionDoucumentDownload> pcompVersionDoucumentDownloads = new LinkedList<>();

    public List<PcompVersionPlatformDownload> getPcompVersionPlatformDownloads() {
        return pcompVersionPlatformDownloads;
    }

    public List<PcompVersionDoucumentDownload> getPcompVersionDoucumentDownloads() {
        return pcompVersionDoucumentDownloads;
    }


    private List<PcompVersionPlatformDownload> pcompVersionPlatformDownloads = new LinkedList<>();

    public void setPcompVersionDoucumentDownloads(List<PcompVersionDoucumentDownload> pcompVersionDoucumentDownloads) {
        this.pcompVersionDoucumentDownloads = pcompVersionDoucumentDownloads;
    }

    public void setPcompVersionPlatformDownloads(List<PcompVersionPlatformDownload> pcompVersionPlatformDownloads) {
        this.pcompVersionPlatformDownloads = pcompVersionPlatformDownloads;
    }

    private PcompVersionExtend() {

    }

    public static PcompVersionExtend fromPcompVersion(PcompVersion pcompVersion) {
        PcompVersionExtend pcompVersionExtend = new PcompVersionExtend();
        pcompVersionExtend.setCreatedBy(pcompVersion.getCreatedBy());
        pcompVersionExtend.setCreatedTime(pcompVersion.getCreatedTime());
        pcompVersionExtend.setId(pcompVersion.getId());
        pcompVersionExtend.setIntroduction(pcompVersion.getIntroduction());
        pcompVersionExtend.setIsDeleted(pcompVersion.getIsDeleted());
        pcompVersionExtend.setModifiedBy(pcompVersion.getModifiedBy());
        pcompVersionExtend.setModifiedTime(pcompVersion.getModifiedTime());
        pcompVersionExtend.setPcompSoftwareId(pcompVersion.getPcompSoftwareId());
        pcompVersionExtend.setQuickStart(pcompVersion.getQuickStart());
        pcompVersionExtend.setVersionNumber(pcompVersion.getVersionNumber());
        return pcompVersionExtend;
    }
}
