package com.sf.sfpp.pcomp.service.impl;

import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompKind;
import com.sf.sfpp.pcomp.service.PcompKindService;

import java.util.List;
import java.util.Map;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/11
 */
public class PcompKindServiceImpl implements PcompKindService {

    @Override
    public List<PcompKind> fetchAllKindsSeparatelyByTitle(String title, int pageNumber) throws PcompException {
        return null;
    }

    @Override
    public int existsKind(String title, String kind) throws PcompException {
        return 0;
    }

    @Override
    public boolean addKind(String title, PcompKind kind) throws PcompException {
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
