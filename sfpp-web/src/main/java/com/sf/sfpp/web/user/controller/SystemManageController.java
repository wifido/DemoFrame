/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.web.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.sfpp.common.dto.JsonResult;
import com.sf.sfpp.common.dto.SystemConstants;
import com.sf.sfpp.user.dao.domain.Resource;
import com.sf.sfpp.user.dao.domain.Role;
import com.sf.sfpp.user.dao.domain.User;
import com.sf.sfpp.user.dao.dto.ResourceTreeNode;
import com.sf.sfpp.user.dao.dto.UserRole;
import com.sf.sfpp.user.service.ResourceService;
import com.sf.sfpp.user.service.RoleService;
import com.sf.sfpp.user.service.UserService;



/**
 * 系统设置
 * @date     2016年7月26日
 * @author   591791
 */
@Controller
@RequestMapping("/system")
public class SystemManageController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private UserService userSerice;
    
    @Autowired
    private RoleService roleSerice;
    
    @Autowired
    private ResourceService resourceService;
    
    @RequestMapping("/user/getAllUser")
    @ResponseBody
    public List<User> getAllUser() {
        log.info("get All User...");
        List<User> resultList = new ArrayList<User>();
        try {
			resultList = userSerice.getAllUsers();
		} catch (Exception e) {
			log.error("获取所有用户出错:", e);
		}
        return resultList;
    }
    
    @RequestMapping("/user/addUser")
    @ResponseBody
    public JsonResult<Boolean> addUser(User user) {
        JsonResult<Boolean> result = new JsonResult<Boolean>();
        Integer state = null;
		try {
			state = userSerice.addUser(user);
		} catch (Exception e) {
			log.error("新增用户出错:", e);
			result.setCode(SystemConstants.RESPONSE_MESSAGE_FAILURE);
	        result.setData(false);
	        result.setMessage("调用新增用户服务出错");
	        return result;
		}
        if(state == 1) {
            result.setCode(SystemConstants.RESPONSE_STATUS_SUCCESS);
            result.setData(true);
        } else if (state == -1){
            result.setCode(SystemConstants.RESPONSE_STATUS_FAILURE);
            result.setData(false);
            result.setMessage("添加失败，已存在工号为：" + user.getUserNo() + "的用户");
        }
        return result;
    }
    
    @RequestMapping("/user/updateUser")
    @ResponseBody
    public JsonResult<Boolean> updateUser(User user) {
        JsonResult<Boolean> result= new JsonResult<Boolean>();
        Integer state;
		try {
			state = userSerice.updateUser(user);
		} catch (Exception e) {
			log.error("更新用户出错:", e);
			result.setCode(SystemConstants.RESPONSE_MESSAGE_FAILURE);
	        result.setData(false);
	        result.setMessage("调用更新用户服务出错");
	        return result;
		}
        if(state == 1) {
            result.setCode(SystemConstants.RESPONSE_STATUS_SUCCESS);
            result.setData(true);
        } else {
            result.setCode(SystemConstants.RESPONSE_STATUS_FAILURE);
            result.setData(false);
            result.setMessage("修改失败");
        }
        return result;
    }
    
    @RequestMapping("/user/getUsersByUserNo")
    @ResponseBody
    public User getUsersByUserNo(String userNo) {
    	User user = null;
        try {
        	user =  userSerice.getUserByUserNo(userNo);
		} catch (Exception e) {
			log.error("根据账号查询用户出错:", e);
		}
        return user;
    }
    
    @RequestMapping("/user/changeUserState")
    @ResponseBody
    public int changeUserState(String userNo, Boolean lock) {
        User user = new User();
        user.setUserNo(userNo);
        user.setIsDeleted(lock);
        int state = -1;
        try {
        	state =  userSerice.updateUser(user);
		} catch (Exception e) {
			log.error("改变用户状态出错:", e);
		}
        return state;
    }
    
    // 角色相关
    @RequestMapping("/role/getAllRole")
    @ResponseBody
    public List<Role> getAllRole() {
        //log.info("get All Role...");
        List<Role> resultList = new ArrayList<Role>();
        try {
			resultList = roleSerice.getAllRole();
		} catch (Exception e) {
			log.error("获取角色列表出错:", e);
		}
        return resultList;
    }
    
    @RequestMapping("/role/addRole")
    @ResponseBody
    public JsonResult<Boolean> addRole(Role role) {
        JsonResult<Boolean> result = new JsonResult<Boolean>();
        Integer state;
		try {
			state = roleSerice.addRole(role);
		} catch (Exception e) {
			log.error("新增角色出错:", e);
			result.setCode(SystemConstants.RESPONSE_MESSAGE_FAILURE);
	        result.setData(false);
	        result.setMessage("调用新增角色服务出错");
	        return result;
		}
        if(state == 1) {
            result.setCode(SystemConstants.RESPONSE_STATUS_SUCCESS);
            result.setData(true);
        } else if (state == -1){
            result.setCode(SystemConstants.RESPONSE_STATUS_FAILURE);
            result.setData(false);
            result.setMessage("添加失败，已存在：" + role.getRoleName() + "的角色");
        }
        return result;
    }
    
    @RequestMapping("/role/updateRole")
    @ResponseBody
    public JsonResult<Boolean> updateRole(Role role) {
        JsonResult<Boolean> result= new JsonResult<Boolean>();
        Integer state;
		try {
			state = roleSerice.updateRole(role);
		} catch (Exception e) {
			log.error("调用更新角色服务出错:", e);
			result.setCode(SystemConstants.RESPONSE_MESSAGE_FAILURE);
	        result.setData(false);
	        result.setMessage("调用更新角色服务出错");
	        return result;
		}
        if(state == 1) {
            result.setCode(SystemConstants.RESPONSE_STATUS_SUCCESS);
            result.setData(true);
        } else {
            result.setCode(SystemConstants.RESPONSE_STATUS_FAILURE);
            result.setData(false);
            result.setMessage("修改失败");
        }
        return result;
    }
    
    @RequestMapping("/role/getRoleByRoleId")
    @ResponseBody
    public Role getRoleByRoleId(String roleId) {
    	Role role = null;
        try {
        	role =  roleSerice.getRoleByRoleId(roleId);
		} catch (Exception e) {
			log.error("根据id查询角色信息出错:", e);
		}
        return role;
    }
    
    @RequestMapping("/role/changeRoleState")
    @ResponseBody
    public int changeRoleState(String roleId, Boolean lock) {
        Role role = new Role();
        Integer Id = Integer.parseInt(roleId);
        role.setRoleId(Id);
        role.setIsDeleted(lock);
        int state = -1;
        try {
        	state =  roleSerice.updateRole(role);
		} catch (Exception e) {
			log.error("更新角色信息出错:", e);
		}
        return state;
    }
    
    @RequestMapping("/userRole/getAllRoleByUserNo")
    @ResponseBody
    public List<UserRole> getAllRoleByUserNo(String userNo) {
    	List<UserRole>  userRoles = new ArrayList<UserRole>();
        try {
        	userRoles =  roleSerice.getUserRoleList(userNo);
		} catch (Exception e) {
			log.error("根据用户账号查询角色出错:", e);
		}
        return userRoles;
    }
    
    @RequestMapping("/userRole/changeUserRole")
    @ResponseBody
    public int changeUserRole(String roleId, Boolean state, String userNo) {
    	int flag = -1;
        try {
        	flag = roleSerice.changeUserRole(roleId, state, userNo);
		} catch (Exception e) {
			log.error("更新用户角色出错:", e);
		}
        return flag;
    }
    
 /*   @RequestMapping("/resource/getResourceList")
    @ResponseBody
    public LinkedList<Resource> getResourceList() {
        return resourceService.getResourceList();
    }*/
    
    @RequestMapping("/resource/getRoleResource")
    @ResponseBody
    public List<ResourceTreeNode> getRoleResource(String roleId) {
    	List<ResourceTreeNode>  nodes = new ArrayList<>();
        try {
        	nodes =  resourceService.getResourceByRoleId(roleId);
		} catch (Exception e) {
			log.error("根据角色查询资源出错:", e);
		}
        return nodes;
    }
    
    @RequestMapping("/resource/updateRoleResource")
    @ResponseBody
    public int updateRoleResource(String roleId, String resList) {
        List<Integer> resourceIdList = new ArrayList<Integer>();
        
        String[] resStrList = resList.split(",");
        for (String string : resStrList) {
            resourceIdList.add(Integer.parseInt(string));
        }
        int state = -1;
        try {
        	state =  resourceService.updateRoleResource(roleId, resourceIdList);
		} catch (Exception e) {
			log.error("更新角色查询资源出错:", e);
		}
        return state;
    }
    
    @RequestMapping("/resource/getResourceList")
    @ResponseBody
    public List<ResourceTreeNode> getResourceList() {
    	List<ResourceTreeNode> nodes = new ArrayList<>();
        try {
        	nodes = resourceService.getResourceList();
		} catch (Exception e) {
			log.error("查询资源出错:", e);
		}
        return nodes;
    }
    
    @RequestMapping("/resource/deleteResource")
    @ResponseBody
    public boolean deleteResource(String id) {
        int result = -1;
		try {
			result = resourceService.deleteResource(id);
		} catch (Exception e) {
			log.error("删除资源出错:", e);
		}
        return result>0;
    }
    
    @RequestMapping("/resource/updateResource")
    @ResponseBody
    public boolean deleteResource(Resource resource) {
        int result = -1;
		try {
			result = resourceService.updateResource(resource);
		} catch (Exception e) {
			log.error("删除资源出错:", e);
		}
        return result>0;
    }
    
    @RequestMapping("/resource/addResource")
    @ResponseBody
    public JsonResult<Boolean> addResource(Resource resource) {
        JsonResult<Boolean> result = new JsonResult<Boolean>();
        String selfType = resource.getRemark();
        String newType = resource.getResourceType();
        if (newType.equals("M")) {  // 添加主菜单
            resource.setParentId(-1);
        } else if(newType.equals("S")){ // 添加子菜单
            if (selfType.equals("S")) {  //如果是子菜单添加子菜单，则不能添加
                result.setCode(SystemConstants.RESPONSE_STATUS_FAILURE);
                result.setData(false);
                result.setMessage("暂时只支持2级菜单，所以不支持在子菜单下添加子菜单。");
                return result;
            }
        }
        resource.setRemark(null);
        int count;
		try {
			count = resourceService.addResource(resource);
		} catch (Exception e) {
			log.error("新增资源出错", e);
			result.setCode(SystemConstants.RESPONSE_STATUS_FAILURE);
            result.setData(false);
            result.setMessage("新增资源出错");
            return result;
		}
        result.setCode(SystemConstants.RESPONSE_STATUS_SUCCESS);
        result.setData(count>0);
        return result ;
    }

}