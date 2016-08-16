package com.sf.sfpp.pcomp.service;

import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompTitle;

import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/11
 */
public interface PcompTitleService {
    /**
     * Title中只包含一个name为String类型
     * @return
     * @throws PcompException
     */
    List<PcompTitle> fetchAllTitles() throws PcompException;

    /**
     * 验证title是否存在
     * @param titleName
     * @return true 已存在 false 不存在
     * @throws PcompException
     */
    boolean existsTitle(String titleName) throws PcompException;

    /**
     * 通过Id获取对应的Title信息
     * @param titleId
     * @return
     * @throws PcompException
     */
    PcompTitle fetchTitleByTitleId(String titleId) throws PcompException;

    /**
     * 通过KindId获取对应的Title信息
     * @param kindId
     * @return
     * @throws PcompException
     */
    PcompTitle fetchTitleByKindId(String kindId) throws PcompException;

    /**
     * 通过Software获取对应的Title信息
     * @param softwareId
     * @return
     * @throws PcompException
     */
    PcompTitle fetchTitleBySoftwareId(String softwareId) throws PcompException;
    /**
     * 新建title
     * @param newName
     * @return 失败返回false
     * @throws PcompException
     */
    boolean addNewTitle(String newName) throws PcompException;

    /**
     * 批量添加，一个失败，都失败
     * @param newNames
     * @return 失败返回false
     * @throws PcompException
     */
    boolean batchAddTitles(List<String> newNames) throws PcompException;

    /**
     * 修改名称
     * @param oldName
     * @param newName
     * @return 失败返回false
     * @throws PcompException
     */
    boolean modifyTitleName(String oldName,String newName) throws PcompException;

    /**
     * 删除
     * @param titleName
     * @return 失败返回false
     * @throws PcompException
     */
    boolean removeTitle(String titleName) throws PcompException;

}
