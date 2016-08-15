package com.sf.sfpp.pcomp.dao;

import com.sf.sfpp.pcomp.common.model.PcompSoftware;

import java.util.List;

public interface PcompSoftwareMapper {
    int deleteByPrimaryKey(String id);

    int insert(PcompSoftware record);

    int insertSelective(PcompSoftware record);

    PcompSoftware selectByPrimaryKey(String id);

    List<PcompSoftware> selectAllAcailableByKindId(String pcomp_kind_id);

    int updateByPrimaryKeySelective(PcompSoftware record);

    int updateByPrimaryKeyWithBLOBs(PcompSoftware record);

    int updateByPrimaryKey(PcompSoftware record);
}