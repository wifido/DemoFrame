package com.sf.sfpp.pcomp.common.model.extend;

import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.common.model.PcompVersion;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/12
 */
public class PcompSoftwareExtend extends PcompSoftware {
    private Set<PcompVersion> pcompVersions = new HashSet<>();

    public Set<PcompVersion> getPcompVersions() {
        return pcompVersions;
    }

}
