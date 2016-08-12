package com.sf.sfpp.pcomp.common.domain;

import com.sf.sfpp.pcomp.common.model.PcompKind;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.common.model.PcompTitle;
import com.sf.sfpp.pcomp.common.model.PcompVersion;

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
}
