package com.sf.sfpp.pcomp.manager;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.model.PcompKind;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.dao.PcompKindMapper;
import com.sf.sfpp.pcomp.dao.PcompSoftwareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public Page<PcompKind> getKindsByTitle(String titleId, int pageNumber) {
        PageHelper.startPage(pageNumber, PcompConstants.numberPerPage);
        return (Page<PcompKind>) pcompKindMapper.selectAvailabeleKindsByTitleID(titleId);
    }

    public Page<PcompKind> getAllKinds(int pageNumber) {
        PageHelper.startPage(pageNumber, PcompConstants.numberPerPage);
        Page<PcompKind> pcompKinds = (Page<PcompKind>) pcompKindMapper.selectAllAvailabeleKinds();
        pcompKinds.getPages();
        return pcompKinds;
    }

    public PcompKind getPcompKindByPcompKindId(String pcompKindId) {
        return pcompKindMapper.selectByPrimaryKey(pcompKindId);
    }

    public PcompKind getPcompKindByPcompSoftwareId(String pcompSoftwareId) {
        PcompSoftware pcompSoftware = pcompSoftwareMapper.selectByPrimaryKey(pcompSoftwareId);
        return pcompKindMapper.selectByPrimaryKey(pcompSoftware.getPcompKindId());
    }

}
