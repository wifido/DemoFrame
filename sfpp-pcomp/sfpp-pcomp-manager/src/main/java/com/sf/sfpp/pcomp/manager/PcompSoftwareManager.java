package com.sf.sfpp.pcomp.manager;

import com.sf.sfpp.pcomp.common.model.PcompSoftware;
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
public class PcompSoftwareManager {
    @Autowired
    private PcompSoftwareMapper pcompSoftwareMapper;

    public List<PcompSoftware> getAllAvailablePcompSoftwaresByPcompKindId(String kindId) {
        return pcompSoftwareMapper.selectAllAcailableByKindId(kindId);
    }
}
