package com.sf.sfpp.pcomp.common.domain;

import com.sf.sfpp.pcomp.common.model.PcompKind;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.common.model.PcompTitle;
import com.sf.sfpp.pcomp.common.model.PcompVersion;

import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/12
 */
public class PcompCacheObject {
    private PcompTitle pcompTitle;
    private PcompKind pcompKind;
    private PcompSoftware pcompSoftware;
    private PcompVersion pcompVersion;

    private List<PcompTitle> pcompTitles;
    private List<PcompKind> pcompKinds;
    private List<PcompSoftware> pcompSoftwares;
    private List<PcompVersion> pcompVersions;

    public PcompTitle getPcompTitle() {
        return pcompTitle;
    }

    public void setPcompTitle(PcompTitle pcompTitle) {
        this.pcompTitle = pcompTitle;
    }

    public PcompKind getPcompKind() {
        return pcompKind;
    }

    public void setPcompKind(PcompKind pcompKind) {
        this.pcompKind = pcompKind;
    }

    public PcompSoftware getPcompSoftware() {
        return pcompSoftware;
    }

    public void setPcompSoftware(PcompSoftware pcompSoftware) {
        this.pcompSoftware = pcompSoftware;
    }

    public PcompVersion getPcompVersion() {
        return pcompVersion;
    }

    public void setPcompVersion(PcompVersion pcompVersion) {
        this.pcompVersion = pcompVersion;
    }

    public List<PcompTitle> getPcompTitles() {
        return pcompTitles;
    }

    public void setPcompTitles(List<PcompTitle> pcompTitles) {
        this.pcompTitles = pcompTitles;
    }

    public List<PcompKind> getPcompKinds() {
        return pcompKinds;
    }

    public void setPcompKinds(List<PcompKind> pcompKinds) {
        this.pcompKinds = pcompKinds;
    }

    public List<PcompSoftware> getPcompSoftwares() {
        return pcompSoftwares;
    }

    public void setPcompSoftwares(List<PcompSoftware> pcompSoftwares) {
        this.pcompSoftwares = pcompSoftwares;
    }

    public List<PcompVersion> getPcompVersions() {
        return pcompVersions;
    }

    public void setPcompVersions(List<PcompVersion> pcompVersions) {
        this.pcompVersions = pcompVersions;
    }
}
