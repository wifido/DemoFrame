package com.sf.sfpp.web.controller.pcomp;

import com.github.pagehelper.PageInfo;
import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.domain.WebCache;
import com.sf.sfpp.common.dto.JsonResult;
import com.sf.sfpp.common.idgen.IDGenerator;
import com.sf.sfpp.common.utils.ExceptionUtils;
import com.sf.sfpp.common.utils.ImageKind;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompKind;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.common.model.extend.PcompSoftwareExtend;
import com.sf.sfpp.pcomp.service.PcompKindService;
import com.sf.sfpp.pcomp.service.PcompSoftwareService;
import com.sf.sfpp.pcomp.service.PcompTitleService;
import com.sf.sfpp.user.dao.domain.User;
import com.sf.sfpp.web.common.PathConstants;
import com.sf.sfpp.web.controller.common.AbstractCachedController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @date 2016/8/16
 */
@Controller
public class PcompSoftwareController extends AbstractCachedController {
    private final static Logger log = LoggerFactory.getLogger(PcompSoftwareController.class);

    @Autowired
    private UserRightController userRightController;
    @Autowired
    private PcompTitleService pcompTitleService;

    @Autowired
    private PcompKindService pcompKindService;

    @Autowired
    private PcompSoftwareService pcompSoftwareService;

    @Autowired
    private ImageController imageController;

    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_SOFTWARE_VALIDATE_PATH, method = RequestMethod.GET)
    public JsonResult<Boolean> validateSoftwareName(HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
        JsonResult<Boolean> result = new JsonResult<>();
        String titleName = request.getParameter(PathConstants.PCOMP_TITLE_NAME);
        String kindName = request.getParameter(PathConstants.PCOMP_KIND_NAME);
        String softwareName = request.getParameter(PathConstants.PCOMP_SOFTWARE_NAME);
        try {
            PcompKind pcompKind = pcompKindService.fetchKind(titleName, kindName);
            result.setData(pcompSoftwareService.existsSoftware(pcompKind.getId(), softwareName));
            return result;
        } catch (Exception e) {
            result.setMessage(e.getMessage());
        }
        result.setData(true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_SOFTWARE_FETCH_PATH, method = RequestMethod.GET)
    public JsonResult<List<PcompSoftware>> getSoftwareList(HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
        JsonResult<List<PcompSoftware>> result = new JsonResult<>();
        List<PcompSoftware> pcompSoftwares = new LinkedList<>();
        String titleName = request.getParameter(PathConstants.PCOMP_TITLE_NAME);
        String kindName = request.getParameter(PathConstants.PCOMP_KIND_NAME);
        String softwareName = request.getParameter(PathConstants.PCOMP_SOFTWARE_NAME);
        try {
            PcompKind pcompKind = pcompKindService.fetchKind(titleName, kindName);
            result.setData((List<PcompSoftware>) pcompSoftwareService.fetchAllSoftwaresSeparatelyByKind(pcompKind.getId(), Constants.ALL_PAGE_NUMBER).getList());
            return result;
        } catch (Exception e) {
            result.setMessage(e.getMessage());
        }
        result.setData(pcompSoftwares);
        return result;
    }

    //todo 各种推荐实现
    @ResponseBody
    @RequestMapping(value = "pcomp/software/recommended", method = RequestMethod.GET)
    public JsonResult<List<PcompSoftware>> getRecommendedSoftware(HttpServletRequest request) {
        JsonResult<List<PcompSoftware>> result = new JsonResult<>();
        try {
            String pcompKindId = request.getParameter("pcompKindId");
            if (pcompKindId == null) {
                result.setData(pcompSoftwareService.fetchRecommendedSoftwares().getList());
            } else {
                result.setData(pcompSoftwareService.fetchAllSoftwaresSeparatelyByKind(pcompKindId, 1).getList());
            }
        } catch (Exception e) {
            String stackTrace = ExceptionUtils.getStackTrace(e);
            log.warn(stackTrace);
            result.setMessage(stackTrace);
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "pcomp/software/getAllByKind", method = RequestMethod.GET)
    public JsonResult<PageInfo<PcompSoftware>> getAllSoftwareByKind(HttpServletRequest request) {
        JsonResult<PageInfo<PcompSoftware>> result = new JsonResult<>();
        try {
            String pcompKindId = request.getParameter("pcompKindId");
            String pageNumber = request.getParameter("pageNumber");
                result.setData(pcompSoftwareService.fetchAllSoftwaresSeparatelyByKind(pcompKindId, Integer.parseInt(pageNumber)));
        } catch (Exception e) {
            String stackTrace = ExceptionUtils.getStackTrace(e);
            log.warn(stackTrace);
            result.setMessage(stackTrace);
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "pcomp/software/getById", method = RequestMethod.GET)
    public JsonResult<PcompSoftware> getSoftwareById(HttpServletRequest request) {
        JsonResult<PcompSoftware> result = new JsonResult<>();
        try {
            String pcompSoftwareId = request.getParameter("pcompSoftwareId");
            result.setData(pcompSoftwareService.fetchSoftware(pcompSoftwareId));
        } catch (Exception e) {
            String stackTrace = ExceptionUtils.getStackTrace(e);
            log.warn(stackTrace);
            result.setMessage(stackTrace);
        }
        return result;
    }

    @RequestMapping(value = PathConstants.PCOMP_SOFTWARE_CREATE_PATH, method = RequestMethod.POST)
    public String createSoftware(@RequestParam(PathConstants.PCOMP_SOFTWARE_AVATAR) MultipartFile avatar, HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
        WebCache webCache = getWebCache(request);
        String titleName = request.getParameter(PathConstants.PCOMP_TITLE_NAME);
        String kindName = request.getParameter(PathConstants.PCOMP_KIND_NAME);
        String pcomp_software_name = request.getParameter(PathConstants.PCOMP_SOFTWARE_NAME);
        String pcomp_software_short_introduction = request.getParameter(PathConstants.PCOMP_SOFTWARE_SHORT_INTRODUCTION);
        String pcomp_software_introduction = request.getParameter(PathConstants.PCOMP_SOFTWARE_INTRODUCTION);
        PcompSoftware pcompSoftware = null;
        try {
            pcompSoftware = new PcompSoftware();
            PcompKind pcompKind = pcompKindService.fetchKind(titleName, kindName);
            if (pcompSoftwareService.existsSoftware(pcompKind.getId(), pcomp_software_name)) {
                return "redirect:" + PathConstants.PCOMP_HOMEPAGE_PATH;
            }
            pcompSoftware.setId(IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM));
            pcompSoftware.setName(pcomp_software_name);
            pcompSoftware.setPcompKindId(pcompKind.getId());
            pcompSoftware.setIntroduction(pcomp_software_introduction);
            pcompSoftware.setIntroductionShort(pcomp_software_short_introduction);
            pcompSoftware.setAvatar(avatar.getSize() > 0 ? imageController.uploadImage(avatar, ImageKind.AVATAR) : "");
            User user = (User) webCache.getUser();
            if (user != null) {
                pcompSoftware.setCreatedBy(user.getId());
                pcompSoftware.setCreatedBy(user.getId());
            } else {
                throw new PcompException(Constants.PERMISSION_DENIED);
            }
            pcompSoftwareService.addSoftware(pcompSoftware);
        } catch (Exception e) {
            model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
            return handleException(e, webCache);
        }
        redirectAttributes.addAttribute(PcompConstants.PCOMP_SOFTWARE, pcompSoftware.getId());
        return "redirect:" + PathConstants.PCOMP_SOFTWARE_PATH;
    }

    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_SOFTWARE_MODIFICATION_PATH, method = RequestMethod.POST)
    public JsonResult<Boolean> updateSoftware(@RequestParam(value = PathConstants.PCOMP_SOFTWARE_AVATAR, required = false) MultipartFile avatar, HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
        WebCache webCache = getWebCache(request);
        JsonResult<Boolean> booleanJsonResult = new JsonResult<>();
        boolean modified = false;
        String pcomp_software_id = request.getParameter(PathConstants.PCOMP_SOFTWARE_ID);
        String pcomp_software_name = request.getParameter(PathConstants.PCOMP_SOFTWARE_NAME);
        String pcomp_software_introduction = request.getParameter(PathConstants.PCOMP_SOFTWARE_INTRODUCTION);
        try {
            PcompSoftwareExtend pcompSoftware = (PcompSoftwareExtend) pcompSoftwareService.fetchSoftware(pcomp_software_id);
            if (pcompSoftware == null) {
                throw new PcompException(new StringBuilder().append("Software:").append(pcomp_software_id).append("不存在").toString(), new Exception());
            }

            if (avatar != null && avatar.getSize() != 0) {
                pcompSoftware.setAvatar(imageController.uploadImage(avatar, ImageKind.AVATAR));
                modified = true;
            }

            if (!StrUtils.isNull(pcomp_software_name)) {
                pcompSoftware.setName(pcomp_software_name);
                modified = true;
            }
            if (!StrUtils.isNull(pcomp_software_introduction)) {
                pcompSoftware.setIntroduction(pcomp_software_introduction);
                modified = true;
            }
            if (modified) {
                User user = (User) webCache.getUser();
                if (!user.getId().equals(pcompSoftware.getCreatedBy())) {
                    throw new PcompException(Constants.PERMISSION_DENIED);
                } else {
                    pcompSoftware.setModifiedBy(user.getId());
                }
            }
            pcompSoftwareService.updateSoftware(pcompSoftware);
            booleanJsonResult.setData(true);
        } catch (Exception e) {
            booleanJsonResult.setMessage(ExceptionUtils.getStackTrace(e));
            booleanJsonResult.setData(false);
        }
        return booleanJsonResult;
    }
    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_SOFTWARE_REMOVE_PATH, method = RequestMethod.GET)
    public JsonResult<Boolean> removeSoftware(HttpServletRequest request) {
        WebCache webCache = getWebCache(request);
        JsonResult<Boolean> result = new JsonResult<>();
        String softwareID = request.getParameter(PcompConstants.PCOMP_SOFTWARE);
        try {
            PcompSoftware pcompSoftware = pcompSoftwareService.fetchSoftware(softwareID);
            User user = (User) webCache.getUser();
            if (!user.getId().equals(pcompSoftware.getCreatedBy())) {
                throw new PcompException(Constants.PERMISSION_DENIED);
            }
            pcompSoftwareService.removeSoftware(softwareID, user.getId());
            result.setData(true);
        } catch (Exception e) {
            result.setMessage(ExceptionUtils.getStackTrace(e));
            result.setData(false);
        }
        return result;
    }
}
