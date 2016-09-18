package com.sf.sfpp.web.controller.pcomp;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.dto.JsonResult;
import com.sf.sfpp.common.utils.ExceptionUtils;
import com.sf.sfpp.common.utils.ImageKind;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.model.PcompKind;
import com.sf.sfpp.pcomp.service.PcompKindService;
import com.sf.sfpp.pcomp.service.PcompTitleService;
import com.sf.sfpp.user.dao.domain.User;
import com.sf.sfpp.web.common.PathConstants;
import com.sf.sfpp.web.controller.common.AbstractCachedController;
import com.sf.sfpp.web.controller.user.UserRightController;
import org.apache.shiro.SecurityUtils;
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
    private UserRightController userRightController;
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
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
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
            result.setData(pcompKindService.fetchAllKindsSeparatelyByTitle(titleId, Constants.ALL_PAGE_NUMBER).getList());
            return result;
        } catch (Exception e) {
            result.setMessage(e.getMessage());
        }
        List<PcompKind> pcompKinds = new LinkedList<>();
        result.setData(pcompKinds);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "pcomp/kind/recommended", method = RequestMethod.GET)
    public JsonResult<List<PcompKind>> getRecommendedSoftware() {
        JsonResult<List<PcompKind>> result = new JsonResult<>();
        try {
            result.setData(pcompKindService.fetchRecommendedKinds().getList());
        } catch (Exception e) {
            String stackTrace = ExceptionUtils.getStackTrace(e);
            log.warn(stackTrace);
            result.setMessage(stackTrace);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "pcomp/kind/getById", method = RequestMethod.GET)
    public JsonResult<PcompKind> getKindById(HttpServletRequest request) {
        JsonResult<PcompKind> result = new JsonResult<>();
        try {
            result.setData(pcompKindService.fetchKindByKindId(request.getParameter("pcompKindId")));
        } catch (Exception e) {
            String stackTrace = ExceptionUtils.getStackTrace(e);
            log.warn(stackTrace);
            result.setMessage(stackTrace);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_KIND_CREATE_PATH, method = RequestMethod.POST)
    public JsonResult<Boolean> createKind(@RequestParam(value = PathConstants.PCOMP_KIND_BANNER_IMAGE, required = false) MultipartFile bannerImage, @RequestParam(value = PathConstants.PCOMP_KIND_TOP_PHOTO, required = false) MultipartFile topPhoto, HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
        JsonResult<Boolean> result = new JsonResult<>();
        String titleName = request.getParameter(PathConstants.PCOMP_TITLE_NAME);
        String kindName = request.getParameter(PathConstants.PCOMP_KIND_NAME);
        String kindIntroduction = request.getParameter(PathConstants.PCOMP_KIND_INTRODUCTION);
        try {
            String titleId = pcompTitleService.fetchTitleByTitleName(titleName).getId();
            JsonResult<Boolean> hasRight = userRightController.getHasAddPcompKindRight(titleId);
            if (hasRight.getData()) {
                PcompKind pcompKind = new PcompKind();
                pcompKind.setName(kindName);
                pcompKind.setIntroduction(kindIntroduction);

                pcompKind.setPcompTitleId(titleId);
                if (bannerImage != null) {
                    pcompKind.setBannerImage(bannerImage.getSize() > 0 ? imageController.uploadImage(bannerImage, ImageKind.BANNER_IMAGE) : "");
                }
                if (topPhoto != null) {
                    pcompKind.setTopPhoto(topPhoto.getSize() > 0 ? imageController.uploadImage(topPhoto, ImageKind.TOP_PHOTO) : "");
                }
                if (!pcompKindService.existsKind(titleName, kindName)) {
                    pcompKind.setCreatedBy(((User) SecurityUtils.getSubject().getPrincipal()).getId());
                    pcompKind.setModifiedBy(((User) SecurityUtils.getSubject().getPrincipal()).getId());
                    pcompKindService.addKind(pcompKind);
                }
                result.setData(true);
            } else {
                result.setData(false);
                result.setMessage("Permission Denied!");
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/pcomp_kind/update", method = RequestMethod.POST)
    public JsonResult<Boolean> updateKind(@RequestParam(value = PathConstants.PCOMP_KIND_BANNER_IMAGE, required = false) MultipartFile bannerImage, @RequestParam(value = PathConstants.PCOMP_KIND_TOP_PHOTO, required = false) MultipartFile topPhoto, HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
        JsonResult<Boolean> result = new JsonResult<>();
        String kindId = request.getParameter(PcompConstants.PCOMP_KIND);
        String kindName = request.getParameter(PathConstants.PCOMP_KIND_NAME);
        String kindIntroduction = request.getParameter(PathConstants.PCOMP_KIND_INTRODUCTION);
        try {
            PcompKind pcompKind = pcompKindService.fetchKindByKindId(kindId);
            JsonResult<Boolean> hasRight = userRightController.getHasAddPcompKindRight(pcompKind.getPcompTitleId());
            if (hasRight.getData()) {
                pcompKind.setName(kindName);
                pcompKind.setIntroduction(kindIntroduction);
                if (bannerImage != null && bannerImage.getSize() > 0) {
                    pcompKind.setBannerImage(imageController.uploadImage(bannerImage, ImageKind.BANNER_IMAGE));
                }
                if (topPhoto != null && topPhoto.getSize() > 0) {
                    pcompKind.setTopPhoto(imageController.uploadImage(topPhoto, ImageKind.TOP_PHOTO));
                }
                pcompKind.setModifiedBy(((User) SecurityUtils.getSubject().getPrincipal()).getId());
                pcompKindService.updateKind(pcompKind);
                result.setData(true);
            } else {
                result.setData(false);
                result.setMessage("Permission Denied!");
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_KIND_REMOVE_PATH, method = RequestMethod.GET)
    public JsonResult<Boolean> removeKind(HttpServletRequest request) {
        JsonResult<Boolean> result = new JsonResult<>();
        String kindId = request.getParameter(PcompConstants.PCOMP_KIND);
        try {
            PcompKind pcompKind = pcompKindService.fetchKindByKindId(kindId);
            JsonResult<Boolean> hasRight = userRightController.getHasModifyPcompKindRight(pcompKind.getId());
            if (hasRight.getData()) {
                pcompKindService.removeKind(kindId, ((User) SecurityUtils.getSubject().getPrincipal()).getId());
                result.setData(true);
            } else {
                result.setData(false);
                result.setMessage("Permission Denied!");
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }
}
