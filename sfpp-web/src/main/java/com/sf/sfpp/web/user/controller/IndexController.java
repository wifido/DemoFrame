package com.sf.sfpp.web.user.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.sfpp.common.dto.JsonResult;
import com.sf.sfpp.common.dto.SystemConstants;
import com.sf.sfpp.user.dao.domain.Resource;
import com.sf.sfpp.user.dao.domain.User;
import com.sf.sfpp.user.service.ResourceService;


/**
 * Created by Michael on 5/11/16.
 */

@Controller
@RequestMapping(value = "/index")
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private ResourceService resourceService;
    
    @RequestMapping(value = "/{type}/{operation}")
    public String loadMainContent(@PathVariable(value = "type")String type, @PathVariable(value = "operation") String operation){
        String path = "/pages/" +type +"/"+operation;
        logger.info("加载页面:"+path);
        return path;
    }
    
    @RequestMapping(value = "/loadSideMenu")
    @ResponseBody
    public LinkedHashMap<String, String> loadSideMenu(String path){
        String userNo = getCurrentUserNo();
        
//        if (CacheManager.existValidCache(userNo+path)) {
//            Object obj = CacheManager.getValue(userNo+path);
//            return (LinkedHashMap<String, String>) obj;
//        }
        
        LinkedHashMap<String, String> sideMenuList = new LinkedHashMap<>();
        List<Resource> resourceList;
		try {
			resourceList = resourceService.getSubResByUserNoAndParent(userNo, path);
	        for (Resource resource : resourceList) {
	            sideMenuList.put(resource.getResourceName(), resource.getResourceUrl());
	        }
		} catch (Exception e) {
			logger.error("获取子菜单出错:", e);
		}

        
        //CacheManager.setCache(userNo+path, sideMenuList, 10);
        return sideMenuList;
    }

    /**
     * 判断是否有子菜单
     * @param
     * @return
     */
    @RequestMapping(value = "/getSideMenuFlag")
    @ResponseBody
    public Boolean getSideMenuFlag(String path){
    	boolean flag = false;
        try {
        	flag =  resourceService.existSubResource(path);
		} catch (Exception e) {
			logger.error("判断是否有子菜单出错:", e);
		}
        return flag;
    }

    @RequestMapping("/loadMainMenu")
    @ResponseBody
    public JsonResult<List<Resource>> getMainMenu() {
        JsonResult<List<Resource>> resultJson = new JsonResult<List<Resource>>();
        List<Resource> result = new ArrayList<Resource>();
        String userNo = getCurrentUserNo();
        try {
			result = resourceService.getMainMenuByUserNo(userNo);
		} catch (Exception e) {
			logger.error("获取主菜单出错:", e);
		}
        if (result.size() == 0) {
            resultJson.setMessage("当前用户无可以访问的资源，请联系管理员");
            resultJson.setCode(SystemConstants.RESPONSE_STATUS_FAILURE);
        } else {
            resultJson.setCode(SystemConstants.RESPONSE_STATUS_SUCCESS);
            resultJson.setData(result);
        }
        return resultJson;
    }
    
    private String getCurrentUserNo() {
        Subject currentUser = SecurityUtils.getSubject();
        User users = new User();
        users = (User) currentUser.getPrincipal();
        String userNo = users.getUserNo();
        return userNo;
    }
}
