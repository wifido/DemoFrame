package com.sf.sfpp.pcomp.service;

import com.github.pagehelper.PageInfo;
import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompKind;

import java.util.Map;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/11
 */
public interface PcompKindService {
    final static int NUM_PER_PAGE = 1;
    final static String ALL_TITLE = "-1";

    /**
     * 根据title查询title对应的所有Kinds，包括分页
     * @param title “-1"代表获取所有分类
     * @param pageNumber
     * @return
     * @throws PcompException
     */
    PageInfo<PcompKind> fetchAllKindsSeparatelyByTitle(String title, int pageNumber) throws PcompException;

    /**
     * 根据kindID获取对应的kind
     * @param kindID
     * @return
     * @throws PcompException
     */
    PcompKind fetchKindByKindId(String kindID) throws PcompException;

    /**
     * 根据softwareId获取对应的kind
     * @param softwareId
     * @return
     * @throws PcompException
     */
    PcompKind fetchKindBySoftwareId(String softwareId) throws PcompException;

    /**
     * 判断某一kind是否存在
     * @param titleName
     * @param kind 名称
     * @return
     * @throws PcompException
     */
    boolean existsKind(String titleName,String kind) throws PcompException;

    /**
     * 利用唯一键返回某一kind
     * @param titleName
     * @param kind 名称
     * @return 对应的PcompKind
     * @throws PcompException
     */
    PcompKind fetchKind(String titleName,String kind) throws PcompException;

    /**
     * 添加kind，注意，这里首先要先判断某一kind是否存在
     * @param kind
     * @return 失败返回false
     * @throws PcompException
     */
    boolean addKind(PcompKind kind) throws PcompException;

    /**
     * 批量添加kind，map的key为kind名称
     * @param stringPcompKindMap
     * @return 失败返回false
     * @throws PcompException
     */
    boolean batchAddKind(Map<String,PcompKind> stringPcompKindMap) throws PcompException;

    /**
     * 更新kind信息
     * @param kind
     * @return 失败返回false
     * @throws PcompException
     */
    boolean updateKind(PcompKind kind) throws PcompException;

    /**
     * 删除kind
     * @param pcompKindId
     * @return 失败返回false
     * @throws PcompException
     */
    boolean removeKind(String pcompKindId, int userId) throws PcompException;
}
