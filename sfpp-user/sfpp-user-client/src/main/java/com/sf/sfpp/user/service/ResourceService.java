/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.user.service;

import java.util.List;

import com.sf.sfpp.user.dao.domain.Resource;
import com.sf.sfpp.user.dao.dto.ResourceTreeNode;



/**
 * 资源dubbo服务对外接口
 * @date     2016年8月10日
 * @author   lingjie.wu
 */
public interface ResourceService {
    
    Boolean existSubResource(String parentResUrl) throws Exception;
    
    List<Resource> getSubResource(String parentResUrl) throws Exception;
    
    List<ResourceTreeNode> getResourceList() throws Exception;
    
    List<ResourceTreeNode> getResourceByRoleId(String roleId) throws Exception;

    int updateRoleResource(String roleId, List<Integer> resourceIdList) throws Exception;

    List<Resource> getMainMenuByUserNo(String userNo) throws Exception;

    int deleteResource(String id) throws Exception;

    int updateResource(Resource resource) throws Exception;

    int addResource(Resource resource) throws Exception;
    
    /** 根据用户名和主菜单路径获取子菜单 */
    List<Resource> getSubResByUserNoAndParent(String userNo, String parentResUrl) throws Exception;
}
