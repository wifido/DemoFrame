package com.sf.sfpp.web.controller.pcomp;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.domain.WebCache;
import com.sf.sfpp.common.dto.JsonResult;
import com.sf.sfpp.pcomp.common.exception.PcompException;
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
    public JsonResult<Boolean> validateTitleName(HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
        JsonResult<Boolean> result = new JsonResult<>();
        String titleName = request.getParameter(PathConstants.PCOMP_TITLE_NAME);
        try {
            result.setData(pcompTitleService.existsTitle(titleName));
            return result;
        } catch (PcompException e) {
            result.setMessage(e.getMessage());
        }
        result.setData(true);
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
        } catch (PcompException e) {
            model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
            return handleException(e, webCache);
        }
        return "redirect:"+ PathConstants.PCOMP_HOMEPAGE_PATH;
    }
}
