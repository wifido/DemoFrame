package com.sf.sfpp.web.controller.pcomp;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.domain.WebCache;
import com.sf.sfpp.common.dto.JsonResult;
import com.sf.sfpp.common.idgen.IDGenerator;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.*;
import com.sf.sfpp.pcomp.common.model.extend.PcompVersionExtend;
import com.sf.sfpp.pcomp.service.PcompKindService;
import com.sf.sfpp.pcomp.service.PcompSoftwareService;
import com.sf.sfpp.pcomp.service.PcompVersionService;
import com.sf.sfpp.resource.client.file.FileService;
import com.sf.sfpp.user.dao.domain.User;
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

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/17
 */
@Controller
public class PcompVersionController extends AbstractCachedController {
    @Autowired
    private PcompVersionService pcompVersionService;

    @Autowired
    private PcompKindService pcompKindService;

    @Autowired
    private PcompSoftwareService pcompSoftwareService;

    @Autowired
    private FileService fileService;

    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_VERSION_VALIDATE_PATH, method = RequestMethod.GET)
    public JsonResult<Boolean> validateSoftwareName(HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
        JsonResult<Boolean> result = new JsonResult<>();
        String titleName = request.getParameter(PathConstants.PCOMP_TITLE_NAME);
        String kindName = request.getParameter(PathConstants.PCOMP_KIND_NAME);
        String softwareName = request.getParameter(PathConstants.PCOMP_SOFTWARE_NAME);
        String versionNumber = request.getParameter(PathConstants.PCOMP_VERSION_NUMBER);
        try {
            PcompKind pcompKind = pcompKindService.fetchKind(titleName, kindName);
            PcompSoftware pcompSoftware = pcompSoftwareService.fetchSoftware(pcompKind.getId(), softwareName);
            result.setData(pcompVersionService.existsVersion(pcompSoftware.getId(), versionNumber));
            return result;
        } catch (PcompException e) {
            result.setMessage(e.getMessage());
        }
        result.setData(true);
        return result;
    }

    @RequestMapping(value = PathConstants.PCOMP_VERSION_EXTEND_CREATE_PATH, method = RequestMethod.POST)
    public String createSoftware(@RequestParam(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DOWNLOAD) MultipartFile[] documents
            , @RequestParam(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_DOWNLOAD) MultipartFile[] softwares
            , @RequestParam(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DESCRIPTION) String[] descriptions
            , @RequestParam(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_PLATFORM) String[] platforms
            , HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
        WebCache webCache = getWebCache(request);
        model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
        String titleName = request.getParameter(PathConstants.PCOMP_TITLE_NAME);
        String kindName = request.getParameter(PathConstants.PCOMP_KIND_NAME);
        String pcomp_software_name = request.getParameter(PathConstants.PCOMP_SOFTWARE_NAME);
        String pcomp_version_number = request.getParameter(PathConstants.PCOMP_VERSION_NUMBER);
        String pcomp_version_introduction = request.getParameter(PathConstants.PCOMP_VERSION_INTRODUCTION);
        String pcomp_version_quickstart = request.getParameter(PathConstants.PCOMP_VERSION_QUICKSTART);
        PcompVersion pcompVersion = new PcompVersion();
        PcompSoftware pcompSoftware = null;
        try {
            PcompKind pcompKind = pcompKindService.fetchKind(titleName, kindName);
            pcompSoftware = pcompSoftwareService.fetchSoftware(pcompKind.getId(), pcomp_software_name);
            if (pcompVersionService.existsVersion(pcompSoftware.getId(), pcomp_version_number)) {
                return PathConstants.PCOMP_HOMEPAGE_JSP_PATH;
            }
            pcompVersion.setId(IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM));
            pcompVersion.setVersionNumber(pcomp_version_number);
            pcompVersion.setIntroduction(pcomp_version_introduction);
            pcompVersion.setPcompSoftwareId(pcompSoftware.getId());
            pcompVersion.setQuickStart(pcomp_version_quickstart);
            PcompVersionExtend pcompVersionExtend = PcompVersionExtend.fromPcompVersion(pcompVersion);
            for (int i = 0; i < softwares.length; i++) {
                PcompVersionPlatformDownload pcompVersionPlatformDownload = new PcompVersionPlatformDownload();
                pcompVersionPlatformDownload.setId(IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM));
                pcompVersionPlatformDownload.setPcompVersionId(pcompVersion.getId());
                pcompVersionPlatformDownload.setPlatform(platforms[i]);
                pcompVersionPlatformDownload.setDownload(fileService.saveFile(softwares[i].getOriginalFilename(), softwares[i].getInputStream()));
                pcompVersionExtend.getPcompVersionPlatformDownloads().add(pcompVersionPlatformDownload);
            }
            for (int i = 0; i < documents.length; i++) {
                PcompVersionDoucumentDownload pcompVersionDoucumentDownload = new PcompVersionDoucumentDownload();
                pcompVersionDoucumentDownload.setId(IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM));
                pcompVersionDoucumentDownload.setPcompVersionId(pcompVersion.getId());
                pcompVersionDoucumentDownload.setDescription(descriptions[i]);
                pcompVersionDoucumentDownload.setDownload(fileService.saveFile(softwares[i].getOriginalFilename(), softwares[i].getInputStream()));
                pcompVersionExtend.getPcompVersionDoucumentDownloads().add(pcompVersionDoucumentDownload);
            }
            User user = null;
            if (webCache.getUser() != null) {
                user = (User) webCache.getUser();
            }
            if (user != null) {
                pcompVersionExtend.setCreatedBy(user.getId());
                pcompVersionExtend.setCreatedBy(user.getId());
            } else {
                pcompVersionExtend.setModifiedBy(-1);
                pcompVersionExtend.setModifiedBy(-1);
            }
            pcompVersionService.addVersion(pcompVersionExtend);
        } catch (Exception e) {
            handleException(e, webCache);
        }
        redirectAttributes.addAttribute(PcompConstants.PCOMP_SOFTWARE, pcompSoftware.getId());
        return "redirect:" + PathConstants.PCOMP_SOFTWARE_PATH;
    }

    @RequestMapping(value = PathConstants.PCOMP_VERSION_MODIFICATION_PATH, method = RequestMethod.POST)
    public String updateVersion(HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
        WebCache webCache = getWebCache(request);
        boolean modified = false;
        String pcomp_software_id = request.getParameter(PathConstants.PCOMP_SOFTWARE_ID);
        String page_navi = request.getParameter(PcompConstants.SOFTWARE_PAGE_NAVIGATION);
        String pcomp_version_id = request.getParameter(PathConstants.PCOMP_VERSION_ID);
        String pcomp_version_number = request.getParameter(PathConstants.PCOMP_VERSION_NUMBER);
        String pcomp_version_introduction = request.getParameter(PathConstants.PCOMP_VERSION_INTRODUCTION);
        String pcomp_version_quickstart = request.getParameter(PathConstants.PCOMP_VERSION_QUICKSTART);
        try {
            PcompVersionExtend pcompVersion = (PcompVersionExtend) pcompVersionService.fetchVersionById(pcomp_version_id);
            if (pcompVersion == null) {
                throw new PcompException(new StringBuilder().append("Software:").append(pcomp_version_id).append("不存在").toString(), new Exception());
            }
            if (!StrUtils.isNull(pcomp_version_number)) {
                pcompVersion.setVersionNumber(pcomp_version_number);
                modified = true;
            }
            if (!StrUtils.isNull(pcomp_version_introduction)) {
                pcompVersion.setIntroduction(pcomp_version_introduction);
                modified = true;
            }
            if (!StrUtils.isNull(pcomp_version_quickstart)) {
                pcompVersion.setQuickStart(pcomp_version_quickstart);
                modified = true;
            }
            if (modified) {
                User user = null;
                if (webCache.getUser() != null) {
                    user = (User) webCache.getUser();
                }
                if (user != null) {
                    pcompVersion.setModifiedBy(user.getId());
                } else {
                    pcompVersion.setModifiedBy(-1);
                }
            }
            pcompVersionService.updateVersion(pcompVersion);
        } catch (PcompException e) {
            handleException(e, webCache);
        }
        redirectAttributes.addAttribute(PcompConstants.PCOMP_SOFTWARE, pcomp_software_id);
        redirectAttributes.addAttribute(PcompConstants.SOFTWARE_PAGE_NAVIGATION, page_navi);
        redirectAttributes.addAttribute(PcompConstants.PCOMP_VERSION, pcomp_version_id);
        return "redirect:" + PathConstants.PCOMP_SOFTWARE_PATH;
    }
}
