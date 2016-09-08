package com.sf.sfpp.web.controller.pcomp;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.domain.WebCache;
import com.sf.sfpp.common.dto.JsonResult;
import com.sf.sfpp.pcomp.common.model.PcompTitle;
import com.sf.sfpp.pcomp.service.PcompTitleService;
import com.sf.sfpp.web.common.PathConstants;
import com.sf.sfpp.web.controller.common.AbstractCachedController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/22
 */
@Controller
public class PcompTitleController extends AbstractCachedController {
    @Autowired
    private PcompTitleService pcompTitleService;


    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_TITLE_VALIDATE_PATH, method = RequestMethod.GET)
    public JsonResult<Boolean> validateTitleName(HttpServletRequest request) {
        JsonResult<Boolean> result = new JsonResult<>();
        String titleName = request.getParameter(PathConstants.PCOMP_TITLE_NAME);
        try {
            result.setData(pcompTitleService.existsTitle(titleName));
            return result;
        } catch (Exception e) {
            result.setMessage(e.getMessage());
        }
        result.setData(true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/pcomp_title/get", method = RequestMethod.GET)
    public JsonResult<List<PcompTitle>> getAllTitle() {
        JsonResult<List<PcompTitle>> result = new JsonResult<>();
        try {
            result.setData(pcompTitleService.fetchAllTitles());
            return result;
        } catch (Exception e) {
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = PathConstants.PCOMP_TITLE_CREATE_PATH, method = RequestMethod.GET)
    public String createTitle(HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
        WebCache webCache = getWebCache(request);
        model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
        String pcomp_title_name = request.getParameter(PathConstants.PCOMP_TITLE_NAME);
        try {
            if(!pcompTitleService.existsTitle(pcomp_title_name)) {
                pcompTitleService.addNewTitle(pcomp_title_name);
            }
        } catch (Exception e) {
            model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
            return handleException(e, webCache);
        }
        return "redirect:"+ PathConstants.PCOMP_HOMEPAGE_PATH;
    }
}
