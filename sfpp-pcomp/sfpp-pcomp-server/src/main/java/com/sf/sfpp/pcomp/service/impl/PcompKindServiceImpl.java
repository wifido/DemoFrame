package com.sf.sfpp.pcomp.service.impl;

import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompKind;
import com.sf.sfpp.pcomp.manager.PcompKindManager;
import com.sf.sfpp.pcomp.service.PcompKindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/11
 */
@Service
public class PcompKindServiceImpl implements PcompKindService {

    @Autowired
    private PcompKindManager pcompKindManager;

    @Override
    public List<PcompKind> fetchAllKindsSeparatelyByTitle(String title, int pageNumber) throws PcompException {
        if (PcompKindService.ALL_TITLE.equals(title))
            return pcompKindManager.getAllKinds();
        else
            return pcompKindManager.getKindsByTitle(title);
    }

    @Override
    public PcompKind fetchKindByKindId(String kindID) throws PcompException {
        return pcompKindManager.getPcompKindById(kindID);
    }

    @Override
    public String existsKind(String titleID, String kind) throws PcompException {
        return null;
    }

    @Override
    public boolean addKind(String titleID, PcompKind kind) throws PcompException {
        return false;
    }

    @Override
    public boolean batchAddKind(Map<String, PcompKind> stringPcompKindMap) throws PcompException {
        return false;
    }

    @Override
    public boolean updateKind(PcompKind kind) throws PcompException {
        return false;
    }

    @Override
    public boolean removeKind(String pcompKind) throws PcompException {
        return false;
    }


}
