/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.user.manager;

import java.util.List;

import com.sf.sfpp.user.dao.domain.Resource;
import com.sf.sfpp.user.dao.dto.RoleResource;


/**
 * 
 * @date     2016年6月12日
 * @author   591791
 */
public interface ResourceManager {
    
    Boolean existSubResource(String parentResUrl);
    
    List<Resource> getSubResource(String parentResUrl);

	List<Resource> getResourceByUserNo(String userNo);

    List<Resource> getResourceList();
    
    List<RoleResource> getResourceByRoleId(String roleId);

    int updateRoleResource(String roleId, List<Integer> resourceIdList);

    int deleteResource(String id);

    int updateResource(Resource resource);

    int addResource(Resource resource);
}
