package com.sf.sfpp.web.controller.pcomp;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.domain.WebCache;
import com.sf.sfpp.common.dto.JsonResult;
import com.sf.sfpp.common.utils.ImageKind;
import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompKind;
import com.sf.sfpp.pcomp.service.PcompKindService;
import com.sf.sfpp.pcomp.service.PcompTitleService;
import com.sf.sfpp.web.common.PathConstants;
import com.sf.sfpp.web.controller.common.AbstractCachedController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/22
 */
@Controller
public class PcompKindController extends AbstractCachedController {
    @Autowired
    private PcompKindService pcompKindService;

    @Autowired
    private PcompTitleService pcompTitleService;

    @Autowired
    private ImageController imageController;

    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_KIND_VALIDATE_PATH, method = RequestMethod.GET)
    public JsonResult<Boolean> validateKindName(HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
        JsonResult<Boolean> result = new JsonResult<>();
        String titleName = request.getParameter(PathConstants.PCOMP_TITLE_NAME);
        String kindName = request.getParameter(PathConstants.PCOMP_KIND_NAME);
        try {
            result.setData(pcompKindService.existsKind(titleName, kindName));
            return result;
        } catch (PcompException e) {
            result.setMessage(e.getMessage());
        }
        result.setData(true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_KIND_FETCH_PATH, method = RequestMethod.GET)
    public JsonResult<List<PcompKind>> fetchKinds(HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
        JsonResult<List<PcompKind>> result = new JsonResult<>();
        String titleName = request.getParameter(PathConstants.PCOMP_TITLE_NAME);

        try {
            String titleId = pcompTitleService.fetchTitleByTitleName(titleName).getId();
            result.setData(pcompKindService.fetchAllKindsSeparatelyByTitle(titleId,Constants.ALL_PAGE_NUMBER).getList());
            return result;
        } catch (PcompException e) {
            result.setMessage(e.getMessage());
        }
        List<PcompKind> pcompKinds = new LinkedList<>();
        result.setData(pcompKinds);
        return result;
    }

    @RequestMapping(value = PathConstants.PCOMP_KIND_CREATE_PATH, method = RequestMethod.POST)
    public String createKind(@RequestParam(PathConstants.PCOMP_KIND_BANNER_IMAGE) MultipartFile bannerImage, @RequestParam(PathConstants.PCOMP_KIND_TOP_PHOTO) MultipartFile topPhoto, HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
        WebCache webCache = getWebCache(request);
        model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
        String titleName = request.getParameter(PathConstants.PCOMP_TITLE_NAME);
        String kindName = request.getParameter(PathConstants.PCOMP_KIND_NAME);
        String kindIntroduction = request.getParameter(PathConstants.PCOMP_KIND_INTRODUCTION);
        try {
            PcompKind pcompKind = new PcompKind();
            pcompKind.setName(kindName);
            pcompKind.setIntroduction(kindIntroduction);

            String titleId = pcompTitleService.fetchTitleByTitleName(titleName).getId();
            pcompKind.setPcompTitleId(titleId);

            pcompKind.setBannerImage(imageController.uploadImage(bannerImage, ImageKind.BANNER_IMAGE));
            pcompKind.setTopPhoto(imageController.uploadImage(topPhoto, ImageKind.TOP_PHOTO));
            if (!pcompKindService.existsKind(titleName, kindName)) {
                pcompKindService.addKind(pcompKind);
            }
        } catch (PcompException | IOException e) {
            model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
            return handleException(e, webCache);
        }
        return "redirect:" + PathConstants.PCOMP_HOMEPAGE_PATH;
    }


}
