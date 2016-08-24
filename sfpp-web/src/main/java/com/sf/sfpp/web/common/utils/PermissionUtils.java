package com.sf.sfpp.web.common.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.sf.sfpp.user.dao.domain.User;

public class PermissionUtils {

	public static boolean isCurrentUser(int userId){
		boolean flag = false;
		Subject currentUser = SecurityUtils.getSubject();
        User user = (User) currentUser.getPrincipal();
        if(user != null 
        		&& user.getId() == userId){
        	flag = true;
        }
		return flag;
	}
}
