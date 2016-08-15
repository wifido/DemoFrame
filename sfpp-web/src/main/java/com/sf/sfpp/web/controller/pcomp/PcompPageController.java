package com.sf.sfpp.web.controller.pcomp;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.domain.WebCache;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.domain.PcompCacheObject;
import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompKind;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.common.model.PcompTitle;
import com.sf.sfpp.pcomp.service.PcompKindService;
import com.sf.sfpp.pcomp.service.PcompSoftwareService;
import com.sf.sfpp.pcomp.service.PcompTitleService;
import com.sf.sfpp.web.common.PagePathConstants;
import com.sf.sfpp.web.common.utils.PathUtils;
import com.sf.sfpp.web.controller.common.AbstractCachedController;
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
public class PcompPageController extends AbstractCachedController {

    @Autowired
    private PcompTitleService pcompTitleService;
    @Autowired
    private PcompKindService pcompKindService;
    @Autowired
    private PcompSoftwareService pcompSoftwareService;

    @Override
    protected WebCache getWebCache(HttpServletRequest request) {
        WebCache webCache = super.getWebCache(request);
        webCache.setTitle(StrUtils.makeString(Constants.MAIN_SYSTEM_SHORT, Constants.NAME_SEPARATOR, Constants.PUBLIC_COMPONENT_SYSTEM));
        return webCache;
    }

    @RequestMapping(PagePathConstants.PCOMP_HOMEPAGE_PATH)
    public ModelAndView mainPage(HttpServletRequest request, ModelMap model) {
        WebCache webCache = getWebCache(request);
        Map<String, String> pathTree = new LinkedHashMap<>();
        pathTree.put(Constants.PUBLIC_COMPONENT_SYSTEM, PathUtils.makePath(PagePathConstants.PCOMP_HOMEPAGE_PATH));
        webCache.setPathTree(pathTree);
        PcompCacheObject pcompCacheObject = new PcompCacheObject();
        try {
            final List<PcompTitle> pcompTitles = pcompTitleService.fetchAllTitles();
            pcompCacheObject.setPcompTitles(pcompTitles);
            String titleId = request.getParameter(PcompConstants.PCOMP_TITLE);
            if (titleId == null) {
                List<PcompKind> pcompKinds = pcompKindService.fetchAllKindsSeparatelyByTitle(PcompKindService.ALL_TITLE, 0);
                pcompCacheObject.setPcompKinds(pcompKinds);

            } else {
                //todo 分页
                List<PcompKind> pcompKinds = pcompKindService.fetchAllKindsSeparatelyByTitle(titleId, 0);
                pcompCacheObject.setPcompKinds(pcompKinds);
                PcompTitle pcompTitle = new PcompTitle();
                pcompTitle.setId(titleId);
                pcompCacheObject.setPcompTitle(pcompTitle);
            }

        } catch (PcompException e) {
            handleException(e, webCache);
        }
        webCache.setCacheObject(pcompCacheObject);
        model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
        return new ModelAndView("pcomp/index");
    }

    @RequestMapping(PagePathConstants.PCOMP_KIND_PATH)
    public ModelAndView kindPage(HttpServletRequest request, ModelMap model) {
        WebCache webCache = getWebCache(request);
        Map<String, String> pathTree = new LinkedHashMap<>();
        String pcompKindId = request.getParameter(PcompConstants.PCOMP_KIND);
        PcompCacheObject pcompCacheObject = new PcompCacheObject();
        try {
            PcompKind pcompKind = pcompKindService.fetchKindByKindId(pcompKindId);
            //todo 分页
            List<PcompSoftware> pcompSoftwares = pcompSoftwareService.fetchAllSoftwaresSeparatelyByKind(pcompKindId, 0);
            pcompCacheObject.setPcompKind(pcompKind);
            pcompCacheObject.setPcompSoftwares(pcompSoftwares);

            pathTree.put(Constants.PUBLIC_COMPONENT_SYSTEM, PathUtils.makePath(PagePathConstants.PCOMP_HOMEPAGE_PATH));
            pathTree.put(pcompKind.getName(), PathUtils.makeKindPath(pcompKind.getId()));
            webCache.setPathTree(pathTree);
        } catch (PcompException e) {
            handleException(e, webCache);
        }
        webCache.setCacheObject(pcompCacheObject);
        model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
        return new ModelAndView("pcomp/kindIndex");
    }


}
