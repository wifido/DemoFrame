package com.sf.sfpp.web.controller.pcomp;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.domain.WebCache;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.elasticsearch.pcomp.PcompSearchService;
import com.sf.sfpp.elasticsearch.pcomp.SortRule;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.common.model.PcompVersion;
import com.sf.sfpp.pcomp.service.PcompSoftwareService;
import com.sf.sfpp.web.common.PathConstants;
import com.sf.sfpp.web.common.search.domain.SearchResult;
import com.sf.sfpp.web.common.utils.IntroductionUtils;
import com.sf.sfpp.web.common.utils.PathUtils;
import com.sf.sfpp.web.controller.common.AbstractCachedController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/18
 */
@Controller
public class SearchController extends AbstractCachedController {
    @Autowired
    private PcompSearchService pcompSearchService;
    @Autowired
    private PcompSoftwareService pcompSoftwareService;

    @Override
    protected WebCache getWebCache(HttpServletRequest request) {
        WebCache webCache = super.getWebCache(request);
        webCache.setTitle(StrUtils.makeString(Constants.MAIN_SYSTEM_SHORT, Constants.NAME_SEPARATOR, Constants.PUBLIC_COMPONENT_SYSTEM));
        return webCache;
    }

    @RequestMapping(value = PathConstants.PCOMP_SEARCH_PATH, method = RequestMethod.GET)
    public String search(HttpServletRequest request, ModelMap model) {
        WebCache webCache = getWebCache(request);
        Map<String, String> pathTree = new LinkedHashMap<>();
        pathTree.put(Constants.PUBLIC_COMPONENT_SYSTEM, PathUtils.makePath(PathConstants.PCOMP_HOMEPAGE_PATH));
        pathTree.put(Constants.PUBLIC_SEARCH, PathUtils.makePath(PathConstants.PCOMP_SEARCH_PATH));
        String keyword = StrUtils.isNull(request.getParameter(PathConstants.PCOMP_SEARCH_KEYWORD)) ? "" : request.getParameter(PathConstants.PCOMP_SEARCH_KEYWORD);
        String category = StrUtils.isNull(request.getParameter(PathConstants.PCOMP_SEARCH_CATEGORY_PARA)) ? "" : request.getParameter(PathConstants.PCOMP_SEARCH_CATEGORY_PARA);
        String sortedBy = StrUtils.isNull(request.getParameter(PathConstants.PCOMP_SEARCH_SORTED_BY_PARA)) ? SortRule.BY_CORRELATION : request.getParameter(PathConstants.PCOMP_SEARCH_SORTED_BY_PARA);
        List results = null;
        try {
            switch (category) {
                case PcompConstants.PCOMP_SOFTWARE:
                    results = pcompSearchService.getSoftwareRelated(keyword, sortedBy);
                    break;
                case PcompConstants.PCOMP_VERSION:
                    results = pcompSearchService.getVersionRelated(keyword, sortedBy);
                    break;
                default:
                    results = pcompSearchService.getAllRelated(keyword, sortedBy);
            }
            List<SearchResult> searchResults = new LinkedList<>();
            if (results != null) {
                for (Object o : results) {
                    SearchResult result = getResult(o);
                    if (result != null) {
                        searchResults.add(result);
                    }
                }
            }
            webCache.setPathTree(pathTree);
            model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
            model.addAttribute(PathConstants.PCOMP_SEARCH_RESULT, searchResults);
            model.addAttribute(PathConstants.PCOMP_SEARCH_KEYWORD, keyword);
            model.addAttribute(PathConstants.PCOMP_SEARCH_CATEGORY_PARA, category);
            model.addAttribute(PathConstants.PCOMP_SEARCH_SORTED_BY_PARA, sortedBy);
            return PathConstants.PCOMP_SEARCH_JSP_PATH;
        } catch (PcompException e) {
            model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
            return handleException(e, webCache);
        }
    }

    public SearchResult getResult(Object object) throws PcompException {
        SearchResult searchResult = new SearchResult();
        if (object instanceof PcompSoftware) {
            PcompSoftware pcompSoftware = (PcompSoftware) object;
            searchResult.setTitle(pcompSoftware.getName());
            searchResult.setLink(PathUtils.makeSoftwarePath(pcompSoftware.getId()));
            searchResult.setIntroduction(IntroductionUtils.getShortIntroduction(pcompSoftware.getIntroduction()));
        } else if (object instanceof PcompVersion) {
            PcompVersion pcompVersion = (PcompVersion) object;
            searchResult.setTitle(StrUtils.makeString(pcompSoftwareService.fetchSoftware(pcompVersion.getPcompSoftwareId()).getName(),
                    Constants.NAME_SEPARATOR,
                    pcompVersion.getVersionNumber()));
            searchResult.setLink(PathUtils.makeVersionPath(pcompVersion.getPcompSoftwareId(), PcompConstants.HISTORY, pcompVersion.getId()));
            searchResult.setIntroduction(IntroductionUtils.getShortIntroduction(pcompVersion.getIntroduction()));
        } else {
            searchResult = null;
        }
        return searchResult;
    }
}
