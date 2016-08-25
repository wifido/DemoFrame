package com.sf.sfpp.pcomp.dao;

import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PcompSoftwareMapper {
    int deleteByPrimaryKey(String id);

    int insert(PcompSoftware record);

    int insertSelective(PcompSoftware record);

    PcompSoftware selectByPrimaryKey(String id);

    PcompSoftware selectByUniqueKey(@Param("pcomp_kind_id")String pcomp_kind_id, @Param("name")String name);

    List<PcompSoftware> selectAllAvailableByKindId(String pcomp_kind_id);

    List<String> selectAllAvailableIdByKindId(String pcomp_kind_id);

    int updateByPrimaryKeySelective(PcompSoftware record);

    int updateByPrimaryKeyWithBLOBs(PcompSoftware record);

    int updateByPrimaryKey(PcompSoftware record);
}