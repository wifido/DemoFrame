package com.sf.sfpp.pcomp.common.model.extend;

import com.sf.sfpp.pcomp.common.model.PcompVersion;
import com.sf.sfpp.pcomp.common.model.PcompVersionDoucumentDownload;
import com.sf.sfpp.pcomp.common.model.PcompVersionPlatformDownload;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/12
 */
public class PcompVersionExtend extends PcompVersion {
    private Set<PcompVersionDoucumentDownload> pcompVersionDoucumentDownloads = new HashSet<>();
    private Set<PcompVersionPlatformDownload> pcompVersionPlatformDownloads = new HashSet<>();

    public Set<PcompVersionDoucumentDownload> getPcompVersionDoucumentDownloads() {
        return pcompVersionDoucumentDownloads;
    }

    public Set<PcompVersionPlatformDownload> getPcompVersionPlatformDownloads() {
        return pcompVersionPlatformDownloads;
    }

}
