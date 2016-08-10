/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.service.permission;

import java.util.List;
import com.sf.sfpp.dao.domain.permission.Resource;
import com.sf.sfpp.dao.dto.permission.ResourceTreeNode;

/**
 * 
 * @date     2016年6月12日
 * @author   591791
 */
public interface ResourceService {
    
    Boolean existSubResource(String parentResUrl);
    
    List<Resource> getSubResource(String parentResUrl);
    
    List<ResourceTreeNode> getResourceList();
    
    List<ResourceTreeNode> getResourceByRoleId(String roleId);

    int updateRoleResource(String roleId, List<Integer> resourceIdList);

    List<Resource> getMainMenuByUserNo(String userNo);

    int deleteResource(String id);

    int updateResource(Resource resource);

    int addResource(Resource resource);
    
    /** 根据用户名和主菜单路径获取子菜单 */
    List<Resource> getSubResByUserNoAndParent(String userNo, String parentResUrl);
}
