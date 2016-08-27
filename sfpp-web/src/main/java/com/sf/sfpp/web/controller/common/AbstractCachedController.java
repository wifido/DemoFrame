package com.sf.sfpp.web.controller.common;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.domain.WebCache;
import com.sf.sfpp.common.utils.ExceptionUtils;
import com.sf.sfpp.user.dao.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/15
 */
public class AbstractCachedController {
    protected final static Logger log = LoggerFactory.getLogger(AbstractCachedController.class);

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
        String stackTrace = ExceptionUtils.getStackTrace(e);
        log.warn(stackTrace);
        webCache.setMessage(stackTrace);
        return "/error/500";
    }
}
