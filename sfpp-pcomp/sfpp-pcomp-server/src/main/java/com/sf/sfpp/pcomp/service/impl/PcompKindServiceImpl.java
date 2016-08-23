package com.sf.sfpp.pcomp.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.idgen.IDGenerator;
import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompKind;
import com.sf.sfpp.pcomp.manager.PcompKindManager;
import com.sf.sfpp.pcomp.service.PcompKindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public PageInfo<PcompKind> fetchAllKindsSeparatelyByTitle(String title, int pageNumber) throws PcompException {
        try {
            Page<PcompKind> allKinds = null;
            if (PcompKindService.ALL_TITLE.equals(title)) {
                allKinds = pcompKindManager.getAllKinds(pageNumber);
            } else {
                allKinds = pcompKindManager.getKindsByTitle(title, pageNumber);
            }
            return allKinds.toPageInfo();
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public PcompKind fetchKindByKindId(String kindID) throws PcompException {
        try {
            return pcompKindManager.getPcompKindByPcompKindId(kindID);
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public PcompKind fetchKindBySoftwareId(String softwareId) throws PcompException {
        try {
            return pcompKindManager.getPcompKindByPcompSoftwareId(softwareId);
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public boolean existsKind(String titleName, String kind) throws PcompException {
        try {
            return pcompKindManager.existsPcompKind(titleName, kind);
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public PcompKind fetchKind(String titleName, String kind) throws PcompException {
        try {
            return pcompKindManager.getPcompKindByPcompTitleNameAndPcompKindName(titleName,kind);
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public boolean addKind(PcompKind pcompKind) throws PcompException {
        try {
            pcompKind.setId(IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM));
            return pcompKindManager.addPcompKind(pcompKind);
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public boolean batchAddKind(Map<String, PcompKind> stringPcompKindMap) throws PcompException {
        try {
            return false;
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public boolean updateKind(PcompKind kind) throws PcompException {
        try {
            return false;
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public boolean removeKind(String pcompKind) throws PcompException {
        try {
            return false;
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

}
