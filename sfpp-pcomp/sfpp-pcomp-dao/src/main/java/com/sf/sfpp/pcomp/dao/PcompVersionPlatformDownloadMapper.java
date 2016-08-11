package com.sf.sfpp.pcomp.dao;

import com.sf.sfpp.pcomp.common.model.PcompVersionPlatformDownload;

public interface PcompVersionPlatformDownloadMapper {
    int deleteByPrimaryKey(String id);

    int insert(PcompVersionPlatformDownload record);

    int insertSelective(PcompVersionPlatformDownload record);

    PcompVersionPlatformDownload selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PcompVersionPlatformDownload record);

    int updateByPrimaryKey(PcompVersionPlatformDownload record);
}