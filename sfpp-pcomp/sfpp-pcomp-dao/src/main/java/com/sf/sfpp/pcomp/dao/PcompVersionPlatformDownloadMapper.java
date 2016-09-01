package com.sf.sfpp.pcomp.dao;

import com.sf.sfpp.pcomp.common.model.PcompVersionPlatformDownload;

import java.util.List;

public interface PcompVersionPlatformDownloadMapper {
    int deleteByPrimaryKey(String id);

    int insert(PcompVersionPlatformDownload record);

    int insertSelective(PcompVersionPlatformDownload record);

    PcompVersionPlatformDownload selectByPrimaryKey(String id);

    List<PcompVersionPlatformDownload> selectByVersionId(String versionId);

    int updateByPrimaryKeySelective(PcompVersionPlatformDownload record);

    int updateByPrimaryKey(PcompVersionPlatformDownload record);
}