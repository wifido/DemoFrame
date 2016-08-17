package com.sf.sfpp.pcomp.service.impl;

import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
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
        try {
            return pcompSoftwareManager.getAllAvailablePcompSoftwaresByPcompKindId(kindId);
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public PcompSoftware fetchSoftware(String softwareId) throws PcompException {
        try {
            return pcompSoftwareManager.getPcompSoftwareByPcompSoftwareId(softwareId);
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public int existsSoftware(String kindId, String softwareId) throws PcompException {
        try {
            return 0;
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public List<PcompSoftware> getSimilarSofware(String softwareName) throws PcompException {
        try {
            return null;
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public boolean addSoftware(String kind, PcompSoftware software) throws PcompException {
        try {
            return false;
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public boolean updateSoftware(PcompSoftware software) throws PcompException {
        try {
            return pcompSoftwareManager.updatePcompSoftwareOnly(software);
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public boolean removeSoftware(PcompSoftware software) throws PcompException {
        try {
            return false;
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }
}
