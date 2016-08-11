package com.sf.sfpp.web.user.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by rqzheng on 2016/3/8.
 */
public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {

    private static final long serialVersionUID = -2793965333840597519L;

    private String captcha;

    public CaptchaUsernamePasswordToken(String username, char[] password, boolean rememberMe, String captcha) {
        super(username, password, rememberMe);
        this.captcha = captcha;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
