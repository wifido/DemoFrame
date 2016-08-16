package com.sf.sfpp.pcomp.manager;

import com.sf.sfpp.pcomp.common.model.PcompKind;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.common.model.PcompTitle;
import com.sf.sfpp.pcomp.dao.PcompKindMapper;
import com.sf.sfpp.pcomp.dao.PcompSoftwareMapper;
import com.sf.sfpp.pcomp.dao.PcompTitleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/15
 */
@Component
public class PcompTitleManager {
    @Autowired
    private PcompTitleMapper pcompTitleMapper;
    @Autowired
    private PcompKindMapper pcompKindMapper;
    @Autowired
    private PcompSoftwareMapper pcompSoftwareMapper;

    public List<PcompTitle> getAllTitles() {
        return pcompTitleMapper.selectAllAvailable();
    }

    public PcompTitle getPcompTitleByPcompKindId(String pcompKindId){
        PcompKind pcompKind = pcompKindMapper.selectByPrimaryKey(pcompKindId);
        return pcompTitleMapper.selectByPrimaryKey(pcompKind.getPcompTitleId());
    }

    public PcompTitle getPcompTitleByPcompSoftwareId(String pcompSoftwareId){
        PcompSoftware pcompSoftware = pcompSoftwareMapper.selectByPrimaryKey(pcompSoftwareId);
        return getPcompTitleByPcompKindId(pcompSoftware.getPcompKindId());
    }
}
