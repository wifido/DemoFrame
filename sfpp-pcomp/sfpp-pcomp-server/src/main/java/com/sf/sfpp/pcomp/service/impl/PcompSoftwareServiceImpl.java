package com.sf.sfpp.pcomp.service.impl;

import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.dao.PcompSoftwareMapper;
import com.sf.sfpp.pcomp.manager.PcompSoftwareManager;
import com.sf.sfpp.pcomp.service.PcompSoftwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/11
 */
@Service
public class PcompSoftwareServiceImpl implements PcompSoftwareService {

    @Autowired
    private PcompSoftwareManager pcompSoftwareManager;

    @Override
    public List<PcompSoftware> fetchAllSoftwaresSeparatelyByKind(String kindId, int pageNumber) throws PcompException {
        return pcompSoftwareManager.getAllAvailablePcompSoftwaresByPcompKindId(kindId);
    }

    @Override
    public PcompSoftware fetchSoftware(String softwareId) throws PcompException {
        return pcompSoftwareManager.getPcompSoftwareByPcompSoftwareId(softwareId);
    }

    @Override
    public int existsSoftware(String kindId, String softwareId) throws PcompException {
        return 0;
    }

    @Override
    public List<PcompSoftware> getSimilarSofware(String softwareName) throws PcompException {
        return null;
    }

    @Override
    public boolean addSoftware(String kind, PcompSoftware software) throws PcompException {
        return false;
    }

    @Override
    public boolean updateSoftware(PcompSoftware software) throws PcompException {
        return false;
    }

    @Override
    public boolean removeSoftware(PcompSoftware software) throws PcompException {
        return false;
    }
}
