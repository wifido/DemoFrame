package com.sf.sfpp.pcomp.service.impl;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.idgen.IDGenerator;
import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompTitle;
import com.sf.sfpp.pcomp.service.PcompTitleService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/11
 */
@Service
public class PcompTitleServiceImpl implements PcompTitleService {
    static List<PcompTitle> strings = new LinkedList<>();
    static{
        PcompTitle pcompTitle = new PcompTitle();
        pcompTitle.setId(IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM));
        pcompTitle.setName("基础类");
        strings.add(pcompTitle);
        pcompTitle = new PcompTitle();
        pcompTitle.setId(IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM));
        pcompTitle.setName("web应用前端");
        strings.add(pcompTitle);
        pcompTitle = new PcompTitle();
        pcompTitle.setId(IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM));
        pcompTitle.setName("web应用后端");
        strings.add(pcompTitle);
        pcompTitle = new PcompTitle();
        pcompTitle.setId(IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM));
        pcompTitle.setName("数据库相关");
        strings.add(pcompTitle);
        pcompTitle = new PcompTitle();
        pcompTitle.setId(IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM));
        pcompTitle.setName("服务器相关");
        strings.add(pcompTitle);
        pcompTitle = new PcompTitle();
        pcompTitle.setId(IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM));
        pcompTitle.setName("管理监控");
        strings.add(pcompTitle);
        pcompTitle = new PcompTitle();
        pcompTitle.setId(IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM));
        pcompTitle.setName("其他");
        strings.add(pcompTitle);
    }
    @Override
    public List<PcompTitle> fetchAllTitles() throws PcompException {
        return strings;
    }

    @Override
    public boolean existsTitle(String titleId) throws PcompException {
        return true;
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
