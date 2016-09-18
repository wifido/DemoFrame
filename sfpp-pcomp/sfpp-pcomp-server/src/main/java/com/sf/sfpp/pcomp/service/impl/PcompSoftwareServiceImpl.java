package com.sf.sfpp.pcomp.service.impl;

import com.github.pagehelper.PageInfo;
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
    public String getResourceUrl(String softwareId) {
        return pcompSoftwareManager.getResourceUrl(softwareId);
    }

    @Override
    public PageInfo<PcompSoftware> fetchAllSoftwaresSeparatelyByKind(String kindId, int pageNumber) throws PcompException {
        try {
            return pcompSoftwareManager.getAllAvailablePcompSoftwaresByPcompKindId(kindId, pageNumber).toPageInfo();
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
    public PageInfo<PcompSoftware> fetchRecommendedSoftwares() throws PcompException {
        try {
            return pcompSoftwareManager.getRecommendedPcompSoftwares().toPageInfo();
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public PageInfo<PcompSoftware> fetchLatestSoftwares() throws PcompException {
        try {
            return pcompSoftwareManager.getLatestPcompSoftwares().toPageInfo();
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public PcompSoftware fetchSoftware(String kindId, String softwareName) throws PcompException {
        try {
            return pcompSoftwareManager.getPcompSoftwareByPcompKindIdAndPcompSoftwareName(kindId, softwareName);
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public boolean existsSoftware(String kindId, String softwareId) throws PcompException {
        try {
            return pcompSoftwareManager.existsPcompSoftware(kindId, softwareId);
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
    public boolean addSoftware(PcompSoftware software) throws PcompException {
        try {
            return pcompSoftwareManager.addPcompSoftwareOnly(software);
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
    public boolean removeSoftware(String softwareId, int userId) throws PcompException {
        try {
            return pcompSoftwareManager.deletePcompSoftwareByPcompSoftwareIdLogically(softwareId, userId);
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }
}
