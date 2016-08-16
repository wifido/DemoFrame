package com.sf.sfpp.pcomp.manager;

import com.sf.sfpp.pcomp.common.model.PcompKind;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.dao.PcompKindMapper;
import com.sf.sfpp.pcomp.dao.PcompSoftwareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/15
 */
@Component
public class PcompKindManager {
    @Autowired
    private PcompKindMapper pcompKindMapper;
    @Autowired
    private PcompSoftwareMapper pcompSoftwareMapper;

    public List<PcompKind> getKindsByTitle(String titleId) {
        return pcompKindMapper.selectAvailabeleKindsByTitleID(titleId);
    }

    public List<PcompKind> getAllKinds() {
        return pcompKindMapper.selectAllAvailabeleKinds();
    }

    public PcompKind getPcompKindByPcompKindId(String pcompKindId) {
        return pcompKindMapper.selectByPrimaryKey(pcompKindId);
    }

    public PcompKind getPcompKindByPcompSoftwareId(String pcompSoftwareId) {
        PcompSoftware pcompSoftware = pcompSoftwareMapper.selectByPrimaryKey(pcompSoftwareId);
        return pcompKindMapper.selectByPrimaryKey(pcompSoftware.getPcompKindId());
    }

}
