package com.sf.sfpp.pcomp.dao;

import com.sf.sfpp.pcomp.common.model.PcompVersionDoucumentDownload;

import java.util.List;

public interface PcompVersionDoucumentDownloadMapper {
    int deleteByPrimaryKey(String id);

    int insert(PcompVersionDoucumentDownload record);

    int insertSelective(PcompVersionDoucumentDownload record);

    PcompVersionDoucumentDownload selectByPrimaryKey(String id);

    List<PcompVersionDoucumentDownload> selectByVersionId(String versionId);

    int updateByPrimaryKeySelective(PcompVersionDoucumentDownload record);

    int updateByPrimaryKey(PcompVersionDoucumentDownload record);
}