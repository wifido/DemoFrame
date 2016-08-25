package com.sf.sfpp.web.controller.common;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.domain.WebCache;
import com.sf.sfpp.common.utils.ExceptionUtils;
import com.sf.sfpp.user.dao.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/15
 */
public class AbstractCachedController {
    protected WebCache getWebCache(HttpServletRequest request) {
        WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
        if (webCache == null) {
            webCache = new WebCache();
        }
        Subject currentUser = SecurityUtils.getSubject();
        User user = (User) currentUser.getPrincipal();
        webCache.setUser(user);
        return webCache;
    }

    protected String handleException(Exception e, WebCache webCache) {
//        e.printStackTrace();
        webCache.setMessage(ExceptionUtils.getStackTrace(e));
        return "/jsp/common/page/ErrorMessage";
    }
}
