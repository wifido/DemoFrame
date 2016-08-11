/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.web.user.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义的登出过滤器
 * @date     2016年6月15日
 * @author   591791
 */
public class CoustomLogoutFilter extends LogoutFilter {

    private static final Logger log = LoggerFactory.getLogger(CoustomLogoutFilter.class);
    /**
     * 
     */
    public CoustomLogoutFilter() {}

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        String redirectUrl = getRedirectUrl(request, response, subject);
        //try/catch added for SHIRO-298:
        try {
            subject.logout();
        } catch (SessionException ise) {
            log.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
        }
        
        // 原本是在这个位置向前台加入登录状态的cookie，已废弃这种方法
        
//        log.info("-------logout and reset cookies");
//        HttpServletRequest req = WebUtils.toHttp(request);
//        String contextPath = req.getContextPath();
//        Cookie[] cookies = req.getCookies();
//        for (Cookie cookie : cookies) {
//            System.out.println(cookie.getName()+"=====" + cookie.getPath());
//        }
        
//        HttpServletResponse resp = WebUtils.toHttp(response);
//        Cookie cookie = new Cookie("loginFlag", "false");
//        cookie.setPath(contextPath);
//        resp.addCookie(cookie);
//        response = resp;
        
        issueRedirect(request, response, redirectUrl);
        return false;
    }
}
