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
        return pcompTitleManager.getAllTitles();
    }

    @Override
    public boolean existsTitle(String titleName) throws PcompException {
        return false;
    }

    @Override
    public boolean addNewTitle(String newName) throws PcompException {
        return false;
    }

    @Override
    public boolean batchAddTitles(List<String> newNames) throws PcompException {
        return false;
    }

    @Override
    public boolean modifyTitleName(String oldName, String newName) throws PcompException {
        return false;
    }

    @Override
    public boolean removeTitle(String titleName) throws PcompException {
        return false;
    }


}
