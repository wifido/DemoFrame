package com.sf.sfpp.pcomp.service;

import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompKind;

import java.util.List;
import java.util.Map;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/11
 */
public interface PcompKindService {
    final static int NUM_PER_PAGE = 16;
    final static String ALL_TITLE = "-1";

    /**
     * 根据title查询title对应的所有Kinds，包括分页
     * @param title “-1"代表获取所有分类
     * @param pageNumber
     * @return
     * @throws PcompException
     */
    List<PcompKind> fetchAllKindsSeparatelyByTitle(String title,int pageNumber) throws PcompException;

    /**
     * 判断某一kind是否存在，顺便返回title的id
     * @param title
     * @param kind 名称
     * @return title id | title不存在则为-1
     * @throws PcompException
     */
    int existsKind(String title,String kind) throws PcompException;

    /**
     * 添加kind，注意，这里首先要先判断某一kind是否存在
     * @param title
     * @param kind
     * @return 失败返回false
     * @throws PcompException
     */
    boolean addKind(String title,PcompKind kind) throws PcompException;

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
     * @param pcompKind
     * @return 失败返回false
     * @throws PcompException
     */
    boolean removeKind(String pcompKind) throws PcompException;
}
