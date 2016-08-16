package com.sf.sfpp.web.controller.main;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.domain.WebCache;
import com.sf.sfpp.web.common.PathConstants;
import com.sf.sfpp.web.controller.common.AbstractCachedController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/12
 */
@Controller
public class IndexPageController extends AbstractCachedController {
    @RequestMapping(value = PathConstants.INDEX_PATH, method = RequestMethod.GET)
    public String mainPage(HttpServletRequest request, ModelMap model) {
        WebCache webCache = getWebCache(request);
        webCache.setTitle(Constants.MAIN_SYSTEM_SHORT);
        webCache.setPathTree(new LinkedHashMap<String, String>());
        model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
        return PathConstants.HOMEPAGE_JSP_PATH;
    }
}
