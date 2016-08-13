package com.sf.sfpp.web.controller.pcomp;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.domain.WebCache;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.domain.PcompCacheObject;
import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompTitle;
import com.sf.sfpp.pcomp.service.PcompTitleService;
import com.sf.sfpp.web.common.PagePathConstants;
import com.sf.sfpp.web.common.utils.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/12
 */
@Controller
public class PcompPageController {

    @Autowired
    private PcompTitleService pcompTitleService;

    @RequestMapping(PagePathConstants.PCOMP_HOMEPAGE_PATH)
    public ModelAndView mainPage(HttpServletRequest request, ModelMap model) {
        WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
        if (webCache == null) {
            webCache = new WebCache();
        }
        webCache.setTitle(Constants.MAIN_SYSTEM_SHORT);
        Map<String, String> pathTree = new LinkedHashMap<>();
        pathTree.put(Constants.PUBLIC_COMPONENT_SYSTEM, PathUtils.makePath(PagePathConstants.PCOMP_HOMEPAGE_PATH));
        webCache.setPathTree(pathTree);
        PcompCacheObject pcompCacheObject = new PcompCacheObject();
        try {
            final List<PcompTitle> pcompTitles = pcompTitleService.fetchAllTitles();
            pcompCacheObject.setPcompTitles(pcompTitles);
            String titleId = request.getParameter(PcompConstants.PCOMP_TITLE);
            if (titleId != null && pcompTitleService.existsTitle(titleId)) {
                PcompTitle pcompTitle = new PcompTitle();
                pcompTitle.setId(titleId);
                pcompCacheObject.setPcompTitle(pcompTitle);
            }
        } catch (PcompException e) {
            e.printStackTrace();
        }
        webCache.setCacheObject(pcompCacheObject);
        model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
        return new ModelAndView("pcomp/index");
    }
}
