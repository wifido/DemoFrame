package com.sf.sfpp.web.controller.main;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.domain.WebCache;
import com.sf.sfpp.web.common.PagePathConstants;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/12
 */
@Controller
public class IndexPageController {
    @RequestMapping(PagePathConstants.HOMEPAGE_PATH)
    public ModelAndView mainPage(HttpServletRequest request, ModelMap model) {
        WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
        if(webCache==null){
            webCache = new WebCache();
        }
        webCache.setTitle(Constants.MAIN_SYSTEM_SHORT);
        webCache.setPathTree(new LinkedHashMap<String, String>());
        model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
        return new ModelAndView("home/index");
    }
}
