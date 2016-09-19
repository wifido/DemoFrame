package com.sf.sfpp.pcomp.manager;

import com.sf.sfpp.common.utils.StrUtils;
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
    public abstract void modifyResource(Resource resource,String id);
    public void addInitialResource(String id,int userId){
        User user = userMapper.selectByPrimaryKey(userId);
        Role role = roleMapper.selectByPrimaryRoleName(user.getUserNo());
        String resourceUrl = getResourceUrl(id);
        Resource resource = resourceMapper.selectResourceByUrl(resourceUrl);
        if (resource == null) {
            resource = new Resource();
            resource.setResourceUrl(resourceUrl);
            modifyResource(resource,id);
            resourceMapper.insertSelective(resource);
            resource = resourceMapper.selectResourceByUrl(resourceUrl);
        }
        List<Integer> resourceIds = new LinkedList<>();
        resourceIds.add(resource.getResourceId());
        resourceMapper.insertRoleResourceList(StrUtils.makeString(role.getRoleId()), resourceIds);
    }


}
