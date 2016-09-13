/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.web.user.controller;

import com.sf.sfpp.web.common.PathConstants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;



/**
 * 登录
 * @date     2016年5月18日
 * @author   591791
 */
@Controller
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);
    
    /**
     * 访问需要登录的页面时被拦截后，需要跳转到到login页面
     * 结合shiro配置查看
     * @throws IOException 
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        log.info("shiro拦截到需要登录的请求: "+ httpServletRequest.getRequestURI());
        
        Subject subject = SecurityUtils.getSubject();
        boolean loginStatus = subject.isAuthenticated();
        if (loginStatus) {
            log.info("已登录状态，返回到主页");
            Cookie coo = new Cookie("loginMessage", null);
            httpServletResponse.addCookie(coo);
            return "redirect:" + PathConstants.HOMEPAGE_PATH;
        } else {
            log.info("未登录状态，返回登录页面");
            Cookie cook = new Cookie("loginMessage", null);
            httpServletResponse.addCookie(cook);
            return "/login";
        }
    }

    @RequestMapping(value = "unauthorized", method = RequestMethod.GET)
    public String toUnauthorized() {
        log.info("没有权限");
        return "/error/403";
    }

    /**
     * 登录失败会调用此接口，返回登录界面
     * @throws IOException 
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String loginFail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException{
        Subject subject = SecurityUtils.getSubject();
        boolean loginStatus = subject.isAuthenticated();
        if (loginStatus) {
            log.info("已登录状态，返回到主页");
            Cookie coo = new Cookie("loginMessage", null);
            httpServletResponse.addCookie(coo);
            return "redirect:" + PathConstants.INDEX_PATH;
        } else {
            //登录失败了 提取错误消息
            Exception shiroLoginFailureEx = (Exception) httpServletRequest.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
            String errorMsg = shiroLoginFailureEx.getMessage();
            log.info("登录失败后返回login页面");
            Cookie coo = new Cookie("loginMessage", URLEncoder.encode(errorMsg,"UTF-8"));
            httpServletResponse.addCookie(coo);
            return "/login";
        }
    }
}
