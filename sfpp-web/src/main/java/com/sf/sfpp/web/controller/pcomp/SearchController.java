package com.sf.sfpp.web.controller.pcomp;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.domain.WebCache;
import com.sf.sfpp.common.dto.JsonResult;
import com.sf.sfpp.common.utils.ExceptionUtils;
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
import com.sf.sfpp.web.controller.common.AbstractCachedController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

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
    @ResponseBody
    @RequestMapping(value = "/pcomp/search", method = RequestMethod.GET)
    public JsonResult<List<SearchResult>> search(HttpServletRequest request) {
        String keyword = StrUtils.isNull(request.getParameter(PathConstants.PCOMP_SEARCH_KEYWORD)) ? "" : request.getParameter(PathConstants.PCOMP_SEARCH_KEYWORD);
        String category = StrUtils.isNull(request.getParameter(PathConstants.PCOMP_SEARCH_CATEGORY_PARA)) ? "" : request.getParameter(PathConstants.PCOMP_SEARCH_CATEGORY_PARA);
        String sortedBy = StrUtils.isNull(request.getParameter(PathConstants.PCOMP_SEARCH_SORTED_BY_PARA)) ? SortRule.BY_CORRELATION : request.getParameter(PathConstants.PCOMP_SEARCH_SORTED_BY_PARA);
        JsonResult<List<SearchResult>> jsonResult = new JsonResult<>();
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
            jsonResult.setData(searchResults);
            return jsonResult;
        } catch (Exception e) {
            jsonResult.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return jsonResult;
    }

    private ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        }
    };

    public SearchResult getResult(Object object) throws PcompException {
        SearchResult searchResult = new SearchResult();
        SimpleDateFormat simpleDateFormat = simpleDateFormatThreadLocal.get();
        if (object instanceof PcompSoftware) {
            PcompSoftware pcompSoftware = (PcompSoftware) object;
            searchResult.setTitle(pcompSoftware.getName());
            searchResult.setLink(StrUtils.makeString("pcompSoftware.html?pcompSoftwareId=",pcompSoftware.getId()));
            searchResult.setIntroduction(IntroductionUtils.getShortIntroduction(pcompSoftware.getIntroduction()));
            searchResult.setAvatar(pcompSoftware.getAvatar());
            searchResult.setModifiedTime(simpleDateFormat.format(pcompSoftware.getModifiedTime()));
        } else if (object instanceof PcompVersion) {
            PcompVersion pcompVersion = (PcompVersion) object;
            searchResult.setTitle(StrUtils.makeString(pcompSoftwareService.fetchSoftware(pcompVersion.getPcompSoftwareId()).getName(),
                    Constants.NAME_SEPARATOR,
                    pcompVersion.getVersionNumber()));
            searchResult.setIntroduction(IntroductionUtils.getShortIntroduction(pcompVersion.getIntroduction()));
            searchResult.setModifiedTime(simpleDateFormat.format(pcompVersion.getModifiedTime()));
            PcompSoftware pcompSoftware = pcompSoftwareService.fetchSoftware(pcompVersion.getPcompSoftwareId());
            searchResult.setAvatar(pcompSoftware.getAvatar());
            searchResult.setLink(StrUtils.makeString("pcompSoftware.html?pcompSoftwareId=",pcompSoftware.getId()));
        } else {
            searchResult = null;
        }
        return searchResult;
    }
}
