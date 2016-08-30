package com.sf.sfpp.web.controller.main;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.domain.WebCache;
import com.sf.sfpp.pcomp.common.domain.PcompCacheObject;
import com.sf.sfpp.pcomp.service.PcompKindService;
import com.sf.sfpp.pcomp.service.PcompSoftwareService;
import com.sf.sfpp.web.common.PathConstants;
import com.sf.sfpp.web.controller.common.AbstractCachedController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/12
 */
@Controller
public class IndexPageController extends AbstractCachedController {
    @Autowired
    private PcompKindService pcompKindService;
    @Autowired
    private PcompSoftwareService pcompSoftwareService;

    @RequestMapping(value = PathConstants.INDEX_PATH, method = RequestMethod.GET)
    public String mainPage(HttpServletRequest request, ModelMap model) {
        WebCache webCache = getWebCache(request);
        webCache.setTitle(Constants.MAIN_SYSTEM_SHORT);
        webCache.setPathTree(new LinkedHashMap<String, String>());
        model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
        try {
            PcompCacheObject pcompCacheObject = new PcompCacheObject();
            webCache.setCacheObject(pcompCacheObject);
            pcompCacheObject.setPcompKinds(pcompKindService.fetchRecommendedKinds());
            pcompCacheObject.setPcompSoftwares(pcompSoftwareService.fetchLatestSoftwares());
        } catch (Exception e) {
            handleException(e, webCache);
        }
        return PathConstants.HOMEPAGE_JSP_PATH;
    }
}
