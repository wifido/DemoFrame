package com.sf.sfpp.pcomp.service;

import com.github.pagehelper.PageInfo;
import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;

import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/11
 */
public interface PcompSoftwareService {
    final static int NUM_PER_PAGE = 40;

    /**
     * 根据title还有kind查找所有符合的software，具有分页
     * @param kindId
     * @param pageNumber
     * @return
     * @throws PcompException
     */
    PageInfo<PcompSoftware> fetchAllSoftwaresSeparatelyByKind(String kindId, int pageNumber) throws PcompException;

    /**
     * 根据software的id获取这个software的详细信息
     * @param softwareId
     * @return
     * @throws PcompException
     */
    PcompSoftware fetchSoftware(String softwareId) throws PcompException;

    /**
     * 判断对应title还有对应kind下是否存在同名software
     * @param kindId
     * @param softwareId
     * @return 存在为true
     * @throws PcompException
     */
    boolean existsSoftware(String kindId, String softwareId) throws PcompException;

    /**
     * 获取相似的software名称
     * @param softwareName
     * @return
     * @throws PcompException
     */
    List<PcompSoftware> getSimilarSofware(String softwareName) throws PcompException;

    /**
     * 添加software
     * @param software
     * @return
     * @throws PcompException
     */
    boolean addSoftware(PcompSoftware software) throws PcompException;

    /**
     * 更新对应的software信息，只有和version不相关的信息调用这个方法更新
     * @param software
     * @return
     * @throws PcompException
     */
    boolean updateSoftware(PcompSoftware software) throws PcompException;

    /**
     * 移除对应的software
     * @param software
     * @return
     * @throws PcompException
     */
    boolean removeSoftware(PcompSoftware software) throws PcompException;
}
