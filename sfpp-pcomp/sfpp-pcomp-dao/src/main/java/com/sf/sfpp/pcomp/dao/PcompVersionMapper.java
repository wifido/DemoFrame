package com.sf.sfpp.pcomp.dao;

import com.sf.sfpp.pcomp.common.model.PcompVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PcompVersionMapper {
    int deleteByPrimaryKey(String id);

    int insert(PcompVersion record);

    int insertSelective(PcompVersion record);

    PcompVersion selectByPrimaryKey(String id);

    PcompVersion selectByUniqueKey(@Param("pcomp_kind_id")String pcomp_software_id,@Param("version_number") String version_number);

    List<PcompVersion> selectAvailableBySoftwareId(String softwareId);

    int updateByPrimaryKeySelective(PcompVersion record);

    int updateByPrimaryKeyWithBLOBs(PcompVersion record);

    int updateByPrimaryKey(PcompVersion record);
}