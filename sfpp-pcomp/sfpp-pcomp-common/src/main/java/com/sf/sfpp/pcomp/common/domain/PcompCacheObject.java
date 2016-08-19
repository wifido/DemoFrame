package com.sf.sfpp.pcomp.common.domain;

import com.github.pagehelper.PageInfo;
import com.sf.sfpp.pcomp.common.model.PcompKind;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.common.model.PcompTitle;
import com.sf.sfpp.pcomp.common.model.PcompVersion;

import java.util.LinkedList;
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

    private List<PcompTitle> pcompTitles = new LinkedList<>();


    public PageInfo<PcompKind> getPcompKinds() {
        return pcompKinds;
    }

    public void setPcompKinds(PageInfo<PcompKind> pcompKinds) {
        this.pcompKinds = pcompKinds;
    }

    public PageInfo<PcompSoftware> getPcompSoftwares() {
        return pcompSoftwares;
    }

    public void setPcompSoftwares(PageInfo<PcompSoftware> pcompSoftwares) {
        this.pcompSoftwares = pcompSoftwares;
    }

    private PageInfo<PcompKind> pcompKinds = new PageInfo<>();
    private PageInfo<PcompSoftware> pcompSoftwares = new PageInfo<>();
    private List<PcompVersion> pcompVersions = new LinkedList<>();

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

    public List<PcompVersion> getPcompVersions() {
        return pcompVersions;
    }

    public void setPcompVersions(List<PcompVersion> pcompVersions) {
        this.pcompVersions = pcompVersions;
    }
}
