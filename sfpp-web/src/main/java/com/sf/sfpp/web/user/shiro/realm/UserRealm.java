package com.sf.sfpp.web.user.shiro.realm;

import com.sf.sfpp.user.dao.domain.User;
import com.sf.sfpp.user.service.RoleService;
import com.sf.sfpp.user.service.UserService;
import com.sf.sfpp.web.user.shiro.LdapAuthentication;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by Michael on 5/13/16.
 */
@Service
public class UserRealm extends AuthorizingRealm {

    private static Logger log = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

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
                pers = userService.getPermissionsByUserNo(user.getUserNo());
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
     * 修改用户权限后，必须调用此方法，重新载入用户权限，更新权限缓存
     * @param principals
     */
    public void refreshAuthorizationInfo(PrincipalCollection principals) {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        Object key = this.getAuthorizationCacheKey(principals);
        AuthorizationInfo info = doGetAuthorizationInfo(principals);
        cache.put(key, info);
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        SimpleAuthenticationInfo info = null;
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        log.info("登录认证");
        // 通过表单接收的用户名
        String userNo = token.getUsername();
        String password = "";
        if (token.getPassword() != null) {
            password = new String(token.getPassword());
        }
        if (userNo == null
                || "".equals(userNo)) {
            throw new UnknownAccountException("用户不能为空");// 没找到帐号
        }
        if (userNo != null
                && !"".equals(userNo)) {
            // 是否通过 域认证
            boolean flag = true;
//			boolean flag = ldapAuthentication.authentication(userNo, password);
            if (!flag) {
                log.info(userNo + "域认证失败。");
                throw new UnknownAccountException("域认证失败");
            }
            // 需要验证用户是否在本环境中存在
            User user = null;
            try {
                user = userService.getUserByUserNo(userNo);
            } catch (Exception e) {
                log.error("通过工号获取用户信息出错:", e);
            }
            if (user == null) {
                user = new User();
                user.setUserNo(userNo);
                try {
                    int i = userService.addUser(user);
                    if (i > 0) {
                        user = userService.getUserByUserNo(userNo);
                    }
                } catch (Exception e) {
                    log.error("添加用户出错", e);
                    throw new UnknownAccountException("添加用户出错，请重试。");// 账号锁定
                }
            } else {
                if (user.getIsDeleted()) {
                    throw new UnknownAccountException("用户被锁定，请联系管理员。");// 账号锁定
                }
            }
            // 如果身份认证验证成功，返回一个AuthenticationInfo实现；
            try {
                info = new SimpleAuthenticationInfo(user, password.toCharArray(), user.getUserNo());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return info;
    }
}
