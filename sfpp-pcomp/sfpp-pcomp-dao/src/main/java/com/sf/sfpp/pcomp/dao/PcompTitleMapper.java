package com.sf.sfpp.pcomp.dao;

import com.sf.sfpp.pcomp.common.model.PcompTitle;

public interface PcompTitleMapper {
    int deleteByPrimaryKey(String id);

    int insert(PcompTitle record);

    int insertSelective(PcompTitle record);

    PcompTitle selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PcompTitle record);

    int updateByPrimaryKey(PcompTitle record);
}