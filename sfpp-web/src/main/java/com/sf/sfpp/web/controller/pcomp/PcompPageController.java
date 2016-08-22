package com.sf.sfpp.web.controller.pcomp;

import com.github.pagehelper.PageInfo;
import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.domain.WebCache;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.domain.PcompCacheObject;
import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompKind;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.common.model.PcompTitle;
import com.sf.sfpp.pcomp.common.model.extend.PcompSoftwareExtend;
import com.sf.sfpp.pcomp.service.PcompKindService;
import com.sf.sfpp.pcomp.service.PcompSoftwareService;
import com.sf.sfpp.pcomp.service.PcompTitleService;
import com.sf.sfpp.web.common.PathConstants;
import com.sf.sfpp.web.common.utils.PathUtils;
import com.sf.sfpp.web.controller.common.AbstractCachedController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
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

    @RequestMapping(value = PathConstants.PCOMP_HOMEPAGE_PATH, method = RequestMethod.GET)
    public String mainPage(HttpServletRequest request, ModelMap model) {
        WebCache webCache = getWebCache(request);
        Map<String, String> pathTree = new LinkedHashMap<>();
        pathTree.put(Constants.PUBLIC_COMPONENT_SYSTEM, PathUtils.makePath(PathConstants.PCOMP_HOMEPAGE_PATH));
        webCache.setPathTree(pathTree);
        PcompCacheObject pcompCacheObject = new PcompCacheObject();
        try {
            final List<PcompTitle> pcompTitles = pcompTitleService.fetchAllTitles();
            pcompCacheObject.setPcompTitles(pcompTitles);
            String titleId = request.getParameter(PcompConstants.PCOMP_TITLE);
            String pageNumber = request.getParameter(Constants.PAGE_NUMBER);
            if (pageNumber == null)
                pageNumber = "1";
            if (titleId == null) {
                PageInfo<PcompKind> pcompKinds = pcompKindService.fetchAllKindsSeparatelyByTitle(PcompKindService.ALL_TITLE, Integer.parseInt(pageNumber));
                pcompCacheObject.setPcompKinds(pcompKinds);

            } else {

                PageInfo<PcompKind> pcompKinds = pcompKindService.fetchAllKindsSeparatelyByTitle(titleId, Integer.parseInt(pageNumber));
                pcompCacheObject.setPcompKinds(pcompKinds);
                PcompTitle pcompTitle = new PcompTitle();
                pcompTitle.setId(titleId);
                pcompCacheObject.setPcompTitle(pcompTitle);
            }

        } catch (PcompException e) {
            model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
            return handleException(e, webCache);
        }
        webCache.setCacheObject(pcompCacheObject);
        model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
        return PathConstants.PCOMP_HOMEPAGE_JSP_PATH;
    }

    @RequestMapping(value = PathConstants.PCOMP_KIND_PATH, method = RequestMethod.GET)
    public String kindPage(HttpServletRequest request, ModelMap model) {
        WebCache webCache = getWebCache(request);
        Map<String, String> pathTree = new LinkedHashMap<>();
        String pcompKindId = request.getParameter(PcompConstants.PCOMP_KIND);
        PcompCacheObject pcompCacheObject = new PcompCacheObject();
        String pageNumber = request.getParameter(Constants.PAGE_NUMBER);
        if (pageNumber == null)
            pageNumber = "1";
        try {
            PcompKind pcompKind = pcompKindService.fetchKindByKindId(pcompKindId);
            PageInfo<PcompSoftware> pcompSoftwares = pcompSoftwareService.fetchAllSoftwaresSeparatelyByKind(pcompKindId,  Integer.parseInt(pageNumber));
            pcompCacheObject.setPcompKind(pcompKind);
            pcompCacheObject.setPcompSoftwares(pcompSoftwares);

            pathTree.put(Constants.PUBLIC_COMPONENT_SYSTEM, PathUtils.makePath(PathConstants.PCOMP_HOMEPAGE_PATH));
            pathTree.put(pcompKind.getName(), PathUtils.makeKindPath(pcompKind.getId()));
            webCache.setPathTree(pathTree);
        } catch (PcompException e) {
            model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
            return handleException(e, webCache);
        }
        webCache.setCacheObject(pcompCacheObject);
        model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
        return PathConstants.PCOMP_KIND_JSP_PATH;
    }


    @RequestMapping(value = PathConstants.PCOMP_SOFTWARE_PATH, method = RequestMethod.GET)
    public String softwarePage(HttpServletRequest request, ModelMap model) {
        WebCache webCache = getWebCache(request);
        Map<String, String> pathTree = new LinkedHashMap<>();
        String pcompSoftwareId = request.getParameter(PcompConstants.PCOMP_SOFTWARE);
        PcompCacheObject pcompCacheObject = new PcompCacheObject();
        try {
            PcompSoftwareExtend pcompSoftware = (PcompSoftwareExtend) pcompSoftwareService.fetchSoftware(pcompSoftwareId);
            PcompKind pcompKind = pcompKindService.fetchKindBySoftwareId(pcompSoftwareId);
            pathTree.put(Constants.PUBLIC_COMPONENT_SYSTEM, PathUtils.makePath(PathConstants.PCOMP_HOMEPAGE_PATH));
            pathTree.put(pcompKind.getName(), PathUtils.makeKindPath(pcompKind.getId()));
            pathTree.put(pcompSoftware.getName(), PathUtils.makeSoftwarePath(pcompSoftware.getId()));
            pcompCacheObject.setPcompSoftware(pcompSoftware);
        } catch (PcompException e) {
            model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
            return handleException(e, webCache);
        }
        webCache.setCacheObject(pcompCacheObject);
        webCache.setPathTree(pathTree);
        model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
        model.addAttribute(PcompConstants.SOFTWARE_PAGE_NAVIGATION, request.getParameter(PcompConstants.SOFTWARE_PAGE_NAVIGATION));
        model.addAttribute(PcompConstants.PCOMP_VERSION, request.getParameter(PcompConstants.PCOMP_VERSION));
        return PathConstants.PCOMP_SOFTWARE_JSP_PATH;
    }

    @RequestMapping(value = PathConstants.PCOMP_TITLE_KIND_ADD_PAGE, method = RequestMethod.GET)
    public String addTitleOrKindPage(HttpServletRequest request, ModelMap model) {
        WebCache webCache = getWebCache(request);
        PcompCacheObject pcompCacheObject = new PcompCacheObject();
        try {
            List<PcompTitle> pcompTitles = pcompTitleService.fetchAllTitles();
            pcompCacheObject.setPcompTitles(pcompTitles);
            webCache.setCacheObject(pcompCacheObject);
        } catch (PcompException e) {
            model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
            return handleException(e, webCache);
        }
        model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
        return PathConstants.PCOMP_TITLE_KIND_ADD_PAGE_JSP_PATH;
    }


}
