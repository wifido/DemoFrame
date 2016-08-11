package com.sf.sfpp.pcomp.dao;

import com.sf.sfpp.pcomp.common.model.PcompKind;

public interface PcompKindMapper {
    int deleteByPrimaryKey(String id);

    int insert(PcompKind record);

    int insertSelective(PcompKind record);

    PcompKind selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PcompKind record);

    int updateByPrimaryKey(PcompKind record);
}