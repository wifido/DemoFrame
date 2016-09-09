package com.sf.sfpp.pcomp.service.impl;

import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompTitle;
import com.sf.sfpp.pcomp.manager.PcompTitleManager;
import com.sf.sfpp.pcomp.service.PcompTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/11
 */
@Service
public class PcompTitleServiceImpl implements PcompTitleService {

    @Autowired
    private PcompTitleManager pcompTitleManager;

    @Override
    public List<PcompTitle> fetchAllTitles() throws PcompException {
        try {
            return pcompTitleManager.getAllTitles();
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public boolean existsTitle(String titleName) throws PcompException {
        try {
            return pcompTitleManager.existsPcompTitleName(titleName);
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public PcompTitle fetchTitleByTitleId(String titleId) throws PcompException {
        try {
            return pcompTitleManager.getPcompTitleByPcompTitleId(titleId);
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public PcompTitle fetchTitleByTitleName(String titleName) throws PcompException {
        try {
            return pcompTitleManager.getPcompTitleByPcompTitleName(titleName);
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public PcompTitle fetchTitleByKindId(String kindId) throws PcompException {
        try {
            return null;
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public PcompTitle fetchTitleBySoftwareId(String softwareId) throws PcompException {
        try {
            return null;
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public boolean addNewTitle(PcompTitle pcompTitle) throws PcompException {
        try {
            return pcompTitleManager.addPcompTitle(pcompTitle);
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }


    @Override
    public boolean modifyTitle(PcompTitle pcompTitle) throws PcompException {
        try {
            return pcompTitleManager.updatePcompTitle(pcompTitle);
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public boolean removeTitle(String titleId, int userId) throws PcompException {
        try {
            return pcompTitleManager.deletePcompTitleLogically(titleId, userId);
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }


}
