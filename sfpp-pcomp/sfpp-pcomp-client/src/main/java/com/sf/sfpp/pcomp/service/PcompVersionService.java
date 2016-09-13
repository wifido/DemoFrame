package com.sf.sfpp.pcomp.service;

import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompVersion;
import com.sf.sfpp.pcomp.common.model.PcompVersionDoucumentDownload;
import com.sf.sfpp.pcomp.common.model.PcompVersionPlatformDownload;
import com.sf.sfpp.pcomp.common.model.extend.PcompVersionExtend;

import java.util.List;

/**
 * 注意，和其他服务不同的是，这个服务所有对象参数为PcompVersionExtend，而不是PcompVersion
 *
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/12
 */
public interface PcompVersionService {
    /**
     * 根据software寻找所有versions信息
     *
     * @param softwareId
     * @return
     */
    List<PcompVersion> fetchAllVersionsSeparatelyBySoftware(String softwareId) throws PcompException;

    /**
     * 根据id查找version信息
     *
     * @param versionId
     * @return
     */
    PcompVersion fetchVersionById(String versionId) throws PcompException;

    /**
     * 根据软件id和版本号查找version信息
     * @param softwareId
     * @param versionNumber
     * @return
     * @throws PcompException
     */
    PcompVersion fetchVersionBySoftwareIdAndVersionNumber(String softwareId, String versionNumber) throws PcompException;

    /**
     * 根据software id判断version是否已经存在
     *
     * @param softwareId
     * @param version
     * @return
     */
    boolean existsVersion(String softwareId, String version) throws PcompException;

    /**
     * 添加软件版本以及版本相关所有信息
     *
     * @param version
     * @return
     */
    boolean addVersion(PcompVersionExtend version) throws PcompException;

    /**
     * 更新软件版本以及版本相关所有信息
     *
     * @param version
     * @return
     */
    boolean updateVersion(PcompVersionExtend version) throws PcompException;

    /**
     * 移除对应版本id的版本
     *
     * @param versionId
     * @return
     */
    boolean removeVersion(String versionId, int userId) throws PcompException;

    boolean addVersionDownload(PcompVersionPlatformDownload pcompVersionPlatformDownload, int userId) throws PcompException;

    boolean updateVersionDownload(PcompVersionPlatformDownload pcompVersionPlatformDownload, int userId) throws PcompException;

    boolean removeVersionDownload(String versionDownloadId, int userId) throws PcompException;

    PcompVersionPlatformDownload fetchVersionDownload(String versionDownloadId) throws PcompException;

    boolean addVersionDocument(PcompVersionDoucumentDownload pcompVersionDoucumentDownload, int userId) throws PcompException;

    boolean updateVersionDocument(PcompVersionDoucumentDownload pcompVersionDoucumentDownload, int userId) throws PcompException;

    boolean removeVersionDocument(String versionDoucumentId, int userId) throws PcompException;

    PcompVersionDoucumentDownload fetchVersionDocument(String versionDoucumentId) throws PcompException;
}
