package com.sf.sfpp.web.user.shiro.filter;


import com.sf.sfpp.web.common.utils.AjaxUtils;
import com.sf.sfpp.web.user.shiro.CaptchaUsernamePasswordToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 基于几点修改：
 * 1、onLoginFailure 时 把异常添加到request attribute中 而不是异常类名
 * 2、登录成功时：成功页面重定向：
 * 2.1、如果前一个页面是登录页面，-->2.3
 * 2.2、如果有SavedRequest 则返回到SavedRequest
 * 2.3、否则根据当前登录的用户决定返回到管理员首页/前台首页
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {



    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

    private String captchaParam = DEFAULT_CAPTCHA_PARAM;

    public String getCaptchaParam() {
        return captchaParam;
    }

    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    // 创建 Token
    protected CaptchaUsernamePasswordToken createToken(
            ServletRequest request, ServletResponse response) {

        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
        return new CaptchaUsernamePasswordToken(username, password.toCharArray(), rememberMe, captcha);
    }



    // 认证
    protected boolean executeLogin(ServletRequest request,
                                   ServletResponse response) throws Exception {
        CaptchaUsernamePasswordToken token = createToken(request, response);

        try {
           /* doCaptchaValidate((HttpServletRequest) request, token);*/

            Subject subject = getSubject(request, response);
            subject.login(token);

            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
    }



    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if(this.isLoginRequest(request, response)) {
            if(this.isLoginSubmission(request, response)) {
                return this.executeLogin(request, response);
            } else {
                return true;
            }
        } else {

            if(AjaxUtils.isAjaxRequest(WebUtils.toHttp(request))){

                HttpServletResponse res = WebUtils.toHttp(response);
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }else{
                this.saveRequestAndRedirectToLogin(request, response);
            }


            return false;
        }
    }





    /**
     * 默认的成功地址
     */
    private String defaultSuccessUrl;


    public void setDefaultSuccessUrl(String defaultSuccessUrl) {
        this.defaultSuccessUrl = defaultSuccessUrl;
    }


    public String getDefaultSuccessUrl() {
        return defaultSuccessUrl;
    }



    /**
     * 根据用户选择成功地址
     *
     * @return
     */
    @Override
    public String getSuccessUrl() {
    	return super.getSuccessUrl();
    }


    @Override
    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        request.setAttribute(getFailureKeyAttribute(), ae);
    }

}
