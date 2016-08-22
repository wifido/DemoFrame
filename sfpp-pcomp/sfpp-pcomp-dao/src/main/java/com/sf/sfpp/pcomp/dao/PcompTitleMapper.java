package com.sf.sfpp.pcomp.dao;

import com.sf.sfpp.pcomp.common.model.PcompTitle;

import java.util.List;

public interface PcompTitleMapper {
    int deleteByPrimaryKey(String id);

    int insert(PcompTitle record);

    int insertSelective(PcompTitle record);

    PcompTitle selectByPrimaryKey(String id);

    PcompTitle selectByUniqueKey(String name);

    List<PcompTitle> selectAllAvailable();

    int updateByPrimaryKeySelective(PcompTitle record);

    int updateByPrimaryKey(PcompTitle record);
}