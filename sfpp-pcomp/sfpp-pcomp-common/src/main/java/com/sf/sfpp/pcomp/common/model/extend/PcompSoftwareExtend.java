package com.sf.sfpp.pcomp.common.model.extend;

import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.common.model.PcompVersion;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/12
 */
public class PcompSoftwareExtend extends PcompSoftware {
    private PcompSoftwareExtend() {
    }

    private List<PcompVersion> pcompVersions = new LinkedList<>();

    public List<PcompVersion> getPcompVersions() {
        return pcompVersions;
    }

    public static PcompSoftwareExtend fromPcompSoftware(PcompSoftware pcompSoftware) {
        PcompSoftwareExtend pcompSoftwareExtend = new PcompSoftwareExtend();
        pcompSoftwareExtend.setAvatar(pcompSoftware.getAvatar());
        pcompSoftwareExtend.setCreatedBy(pcompSoftware.getCreatedBy());
        pcompSoftwareExtend.setCreatedTime(pcompSoftware.getCreatedTime());
        pcompSoftwareExtend.setId(pcompSoftware.getId());
        pcompSoftwareExtend.setIntroduction(pcompSoftware.getIntroduction());
        pcompSoftwareExtend.setIntroductionShort(pcompSoftware.getIntroductionShort());
        pcompSoftwareExtend.setIsDeleted(pcompSoftware.getIsDeleted());
        pcompSoftwareExtend.setModifiedBy(pcompSoftware.getModifiedBy());
        pcompSoftwareExtend.setModifiedTime(pcompSoftware.getModifiedTime());
        pcompSoftwareExtend.setName(pcompSoftware.getName());
        pcompSoftwareExtend.setPcompKindId(pcompSoftware.getPcompKindId());
        return pcompSoftwareExtend;
    }
}
