package com.sf.sfpp.pcomp.service.impl;

import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.dao.PcompSoftwareMapper;
import com.sf.sfpp.pcomp.service.PcompSoftwareService;

import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/11
 */
public class PcompSoftwareServiceImpl implements PcompSoftwareService {

    @Override
    public List<PcompSoftware> fetchAllSoftwaresSeparatelyByKind(String kind, int pageNumber) throws PcompException {
        return null;
    }

    @Override
    public PcompSoftware fetchSoftware(String software) throws PcompException {
        return null;
    }

    @Override
    public int existsSoftware(String kind, String softwareName) throws PcompException {
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
