package com.sf.sfpp.web.user.shiro.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.sfpp.user.dao.domain.User;
import com.sf.sfpp.user.service.UserService;
import com.sf.sfpp.web.user.shiro.LdapAuthentication;


/**
 * Created by Michael on 5/13/16.
 */
@Service
public class UserRealm extends AuthorizingRealm {
    
    private static Logger log = LoggerFactory.getLogger(UserRealm.class);
    
    @Autowired
    UserService userService;

    @Autowired
    LdapAuthentication ldapAuthentication;
    
    /**
     * 权限验证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

//        if(!SecurityUtils.getSubject().isAuthenticated()){
//            SecurityUtils.getSubject().logout();
//        }
//        return null;
        
      //根据自己系统规则的需要编写获取授权信息，这里为了快速入门只获取了用户对应角色的资源url信息  
    	User user = (User) principalCollection.getPrimaryPrincipal(); 
        if (user != null) {  
            List<String> pers;
			try {
				pers = userService.getPermissionsByUserName(user.getUserNo());
				 if (pers != null && !pers.isEmpty()) {  
		                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();  
		                for (String each : pers) {  
		                    //将权限资源添加到用户信息中  
		                    info.addStringPermission(each);  
		                }  
		                return info;  
		            }  
			} catch (Exception e) {
				log.error("获取用户权限失败", e);
			}  
           
        }  
        return null;  
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        log.info("登录认证");
        // 通过表单接收的用户名
        String username = token.getUsername();
        String password = "";
        if (token.getPassword() != null) {
            password = new String(token.getPassword());
        }
        if (username != null && !"".equals(username)) {
            // 需要验证用户是否在本环境中存在
			User user = null;
			try {
				user = userService.getUserByUserNo(username);
			} catch (Exception e) {
				log.error("通过工号获取用户信息出错:", e);
			}
			if (user == null) {
				throw new UnknownAccountException("未找到此用户，请联系管理员。");// 没找到帐号
			}
			if (user.getIsDeleted()) {
				throw new UnknownAccountException("用户被锁定，请联系管理员。");// 账号锁定
			}
			// 是否通过 域认证
			boolean flag = ldapAuthentication.authentication(username, password);

			if (true == flag) {

				// 如果身份认证验证成功，返回一个AuthenticationInfo实现；
				SimpleAuthenticationInfo info = null;
				try {
					info = new SimpleAuthenticationInfo(user, password.toCharArray(), user.getUserNo());
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return info;
			} else {
				log.info(username + "域认证失败。");
				throw new UnknownAccountException("域认证失败");
			}
        }
        return null;
    }
}
