package com.sf.sfpp.pcomp.manager;

import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.user.dao.domain.Resource;
import com.sf.sfpp.user.dao.domain.Role;
import com.sf.sfpp.user.dao.domain.User;
import com.sf.sfpp.user.dao.mapper.ResourceMapper;
import com.sf.sfpp.user.dao.mapper.RoleMapper;
import com.sf.sfpp.user.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/15
 */
public abstract class EventManager {
    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected RoleMapper roleMapper;
    @Autowired
    protected ResourceMapper resourceMapper;

    public abstract String getResourceUrl(String id);

    public void addInitialResource(String id, int userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        Role role = roleMapper.selectByPrimaryRoleName(user.getUserNo());
        String resourceUrl = getResourceUrl(id);
        Resource resource = resourceMapper.selectResourceByUrl(resourceUrl);
        Resource lastResource = null;
        List<Integer> resourceIds = new LinkedList<>();
        if (resource == null) {
            String[] resourceElements = resourceUrl.split(":");
            for (int i = 1; i < resourceElements.length; i++) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 1; j <= i; j++) {
                    stringBuilder.append(":").append(resourceElements[j]);
                }
                resource = resourceMapper.selectResourceByUrl(stringBuilder.toString());
                if (resource == null) {
                    resource = new Resource();
                    resource.setResourceUrl(stringBuilder.toString());
                    if (lastResource != null) {
                        resource.setParentId(lastResource.getResourceId());
                    }
                    switch (i) {
                    case 3:
                        resource.setResourceType(PcompConstants.PCOMP_TITLE);
                        resource.setResourceName(resourceElements[i]);
                        resource.setRemark("全部权限");
                        break;
                    case 4:
                        resource.setResourceType(PcompConstants.PCOMP_KIND);
                        resource.setResourceName(resourceElements[i]);
                        resource.setRemark("全部权限");
                        break;
                    case 5:
                        resource.setResourceType(PcompConstants.PCOMP_SOFTWARE);
                        resource.setResourceName(resourceElements[i]);
                        resource.setRemark("全部权限");
                        break;
                    case 6:
                        resource.setResourceType(PcompConstants.PCOMP_VERSION);
                        resource.setResourceName(resourceElements[i]);
                        resource.setRemark("全部权限");
                        break;
                    }
                    resourceMapper.insertSelective(resource);
                    resource = resourceMapper.selectResourceByUrl(resource.getResourceUrl());
                    resourceIds.add(resource.getResourceId());
                }
                lastResource = resource;
            }
        }
        resourceMapper.insertRoleResourceList(StrUtils.makeString(role.getRoleId()), resourceIds);
    }

}
