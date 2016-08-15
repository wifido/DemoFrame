package com.sf.sfpp.web.controller.common;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.domain.WebCache;

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
        return webCache;
    }

    protected void handleException(Exception e, WebCache webCache) {
        e.printStackTrace();
        webCache.setMessage(e.getMessage());
    }
}
