package com.sf.sfpp.pcomp.dao;

import com.sf.sfpp.pcomp.common.model.PcompVersion;

import java.util.List;

public interface PcompVersionMapper {
    int deleteByPrimaryKey(String id);

    int insert(PcompVersion record);

    int insertSelective(PcompVersion record);

    PcompVersion selectByPrimaryKey(String id);

    List<PcompVersion> selectAvailableBySoftwareId(String softwareId);

    int updateByPrimaryKeySelective(PcompVersion record);

    int updateByPrimaryKeyWithBLOBs(PcompVersion record);

    int updateByPrimaryKey(PcompVersion record);
}