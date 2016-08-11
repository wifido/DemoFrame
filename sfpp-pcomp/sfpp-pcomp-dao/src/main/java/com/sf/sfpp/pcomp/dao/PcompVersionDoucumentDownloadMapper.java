package com.sf.sfpp.pcomp.dao;

import com.sf.sfpp.pcomp.common.model.PcompVersionDoucumentDownload;

public interface PcompVersionDoucumentDownloadMapper {
    int deleteByPrimaryKey(String id);

    int insert(PcompVersionDoucumentDownload record);

    int insertSelective(PcompVersionDoucumentDownload record);

    PcompVersionDoucumentDownload selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PcompVersionDoucumentDownload record);

    int updateByPrimaryKey(PcompVersionDoucumentDownload record);
}