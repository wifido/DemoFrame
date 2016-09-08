package com.sf.sfpp.web.controller.pcomp;

import com.sf.sfpp.ambry.HTTPUpload;
import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.domain.WebCache;
import com.sf.sfpp.common.dto.JsonResult;
import com.sf.sfpp.common.idgen.IDGenerator;
import com.sf.sfpp.common.utils.ExceptionUtils;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.*;
import com.sf.sfpp.pcomp.common.model.extend.PcompVersionExtend;
import com.sf.sfpp.pcomp.service.PcompKindService;
import com.sf.sfpp.pcomp.service.PcompSoftwareService;
import com.sf.sfpp.pcomp.service.PcompVersionService;
import com.sf.sfpp.user.dao.domain.User;
import com.sf.sfpp.web.common.PathConstants;
import com.sf.sfpp.web.controller.common.AbstractCachedController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
    private HTTPUpload httpUpload;

    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_VERSION_VALIDATE_PATH, method = RequestMethod.GET)
    public JsonResult<Boolean> validateVersionNumber(HttpServletRequest request) {
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
        } catch (Exception e) {
            result.setMessage(e.getMessage());
        }
        result.setData(true);
        return result;
    }

    @RequestMapping(value = PathConstants.PCOMP_VERSION_EXTEND_CREATE_PATH, method = RequestMethod.POST)
    public String createVersion(@RequestParam(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DOWNLOAD) MultipartFile[] documents
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
                pcompVersionPlatformDownload.setDownload(httpUpload.uploadFile(softwares[i].getInputStream(), softwares[i].getSize(), Constants.PUBLIC_COMPONENT_SYSTEM_ENG, pcompVersionPlatformDownload.getId(), "file", ""));
                pcompVersionExtend.getPcompVersionPlatformDownloads().add(pcompVersionPlatformDownload);
            }
            for (int i = 0; i < documents.length; i++) {
                PcompVersionDoucumentDownload pcompVersionDoucumentDownload = new PcompVersionDoucumentDownload();
                pcompVersionDoucumentDownload.setId(IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM));
                pcompVersionDoucumentDownload.setPcompVersionId(pcompVersion.getId());
                pcompVersionDoucumentDownload.setDescription(descriptions[i]);
                pcompVersionDoucumentDownload.setDownload(httpUpload.uploadFile(documents[i].getInputStream(), documents[i].getSize(), Constants.PUBLIC_COMPONENT_SYSTEM_ENG, pcompVersionDoucumentDownload.getId(), "file", ""));
                pcompVersionExtend.getPcompVersionDoucumentDownloads().add(pcompVersionDoucumentDownload);
            }
            User user = null;
            if (webCache.getUser() != null) {
                user = (User) webCache.getUser();
            }
            if (user != null) {
                pcompVersionExtend.setCreatedBy(user.getId());
                pcompVersionExtend.setModifiedBy(user.getId());
            } else {
                pcompVersionExtend.setCreatedBy(-1);
                pcompVersionExtend.setModifiedBy(-1);
            }
            pcompVersionService.addVersion(pcompVersionExtend);
        } catch (Exception e) {
            handleException(e, webCache);
        }
        redirectAttributes.addAttribute(PcompConstants.PCOMP_SOFTWARE, pcompSoftware.getId());
        return "redirect:" + PathConstants.PCOMP_SOFTWARE_PATH;
    }

    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_VERSION_MODIFICATION_PATH, method = RequestMethod.POST)
    public JsonResult<Boolean> updateVersion(HttpServletRequest request) {
        JsonResult<Boolean> result = new JsonResult<>();
        boolean modified = false;
        String pcomp_version_id = request.getParameter(PathConstants.PCOMP_VERSION_ID);
        String pcomp_version_number = request.getParameter(PathConstants.PCOMP_VERSION_NUMBER);
        String pcomp_version_introduction = request.getParameter(PathConstants.PCOMP_VERSION_INTRODUCTION);
        String pcomp_version_quickstart = request.getParameter(PathConstants.PCOMP_VERSION_QUICKSTART);
        try {
            PcompVersionExtend pcompVersion = (PcompVersionExtend) pcompVersionService.fetchVersionById(pcomp_version_id);
            PcompSoftware pcompSoftware = pcompSoftwareService.fetchSoftware(pcompVersion.getPcompSoftwareId());
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
                Subject currentUser = SecurityUtils.getSubject();
                User user = (User) currentUser.getPrincipal();
                if (!user.getId().equals(pcompSoftware.getCreatedBy()) && !user.getId().equals(pcompVersion.getCreatedBy())) {
                    throw new PcompException(Constants.PERMISSION_DENIED);
                }
                pcompVersion.setModifiedBy(user.getId());
            }
            pcompVersionService.updateVersion(pcompVersion);
            result.setData(true);
        } catch (Exception e) {
            result.setMessage(ExceptionUtils.getStackTrace(e));
            result.setData(false);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_VERSION_REMOVE_PATH, method = RequestMethod.GET)
    public JsonResult<Boolean> removeVersion(HttpServletRequest request) {
        JsonResult<Boolean> result = new JsonResult<>();
        String versionId = request.getParameter(PcompConstants.PCOMP_VERSION);
        try {
            PcompVersion pcompVersion = pcompVersionService.fetchVersionById(versionId);
            PcompSoftware pcompSoftware = pcompSoftwareService.fetchSoftware(pcompVersion.getPcompSoftwareId());
            Subject currentUser = SecurityUtils.getSubject();
            User user = (User) currentUser.getPrincipal();
            if (!user.getId().equals(pcompSoftware.getCreatedBy()) && !user.getId().equals(pcompVersion.getCreatedBy())) {
                throw new PcompException(Constants.PERMISSION_DENIED);
            }
            pcompVersionService.removeVersion(versionId, user.getId());
            result.setData(true);
        } catch (Exception e) {
            result.setMessage(ExceptionUtils.getStackTrace(e));
            result.setData(false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_REMOVE_PATH, method = RequestMethod.GET)
    public JsonResult<Boolean> removeVersionDownload(HttpServletRequest request) {
        JsonResult<Boolean> result = new JsonResult<>();
        String versionId = request.getParameter(PcompConstants.PCOMP_VERSION);
        String versionDownloadId = request.getParameter(PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD);
        try {
            PcompVersion pcompVersion = pcompVersionService.fetchVersionById(versionId);
            PcompSoftware pcompSoftware = pcompSoftwareService.fetchSoftware(pcompVersion.getPcompSoftwareId());
            Subject currentUser = SecurityUtils.getSubject();
            User user = (User) currentUser.getPrincipal();
            if (!user.getId().equals(pcompSoftware.getCreatedBy()) && !user.getId().equals(pcompVersion.getCreatedBy())) {
                throw new PcompException(Constants.PERMISSION_DENIED);
            }
            pcompVersionService.removeVersionDownload(versionDownloadId, user.getId());
            result.setData(true);
        } catch (Exception e) {
            result.setMessage(ExceptionUtils.getStackTrace(e));
            result.setData(false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_MODIFICATION_PATH, method = RequestMethod.POST)
    public JsonResult<Boolean> updateVersionDownload(@RequestParam(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_DOWNLOAD) MultipartFile software,
                                                     @RequestParam(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_PLATFORM) String platform,
                                                     HttpServletRequest request) {
        JsonResult<Boolean> result = new JsonResult<>();
        String versionId = request.getParameter(PcompConstants.PCOMP_VERSION);
        String versionDownloadId = request.getParameter(PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD);
        try {
            PcompVersion pcompVersion = pcompVersionService.fetchVersionById(versionId);
            PcompSoftware pcompSoftware = pcompSoftwareService.fetchSoftware(pcompVersion.getPcompSoftwareId());
            Subject currentUser = SecurityUtils.getSubject();
            User user = (User) currentUser.getPrincipal();
            if (!user.getId().equals(pcompSoftware.getCreatedBy()) && !user.getId().equals(pcompVersion.getCreatedBy())) {
                throw new PcompException(Constants.PERMISSION_DENIED);
            }
            if (!StrUtils.isNull(platform)) {
                PcompVersionPlatformDownload pcompVersionPlatformDownload = pcompVersionService.fetchVersionDownload(versionDownloadId);
                pcompVersionPlatformDownload.setPlatform(platform);
                if (software != null && software.getSize() > 0) {
                    pcompVersionPlatformDownload.setDownload(httpUpload.uploadFile(software.getInputStream(), software.getSize(), Constants.PUBLIC_COMPONENT_SYSTEM_ENG, pcompVersionPlatformDownload.getId(), "file", ""));
                }
                pcompVersionService.updateVersionDownload(pcompVersionPlatformDownload, user.getId());
                result.setData(true);
            }
        } catch (Exception e) {
            result.setMessage(ExceptionUtils.getStackTrace(e));
            result.setData(false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_CREATE_PATH, method = RequestMethod.POST)
    public JsonResult<Boolean> addVersionDownload(@RequestParam(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_DOWNLOAD) MultipartFile software,
                                                  @RequestParam(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_PLATFORM) String platform,
                                                  HttpServletRequest request) {
        JsonResult<Boolean> result = new JsonResult<>();
        String versionId = request.getParameter(PcompConstants.PCOMP_VERSION);
        try {
            PcompVersion pcompVersion = pcompVersionService.fetchVersionById(versionId);
            PcompSoftware pcompSoftware = pcompSoftwareService.fetchSoftware(pcompVersion.getPcompSoftwareId());
            Subject currentUser = SecurityUtils.getSubject();
            User user = (User) currentUser.getPrincipal();
            if (!user.getId().equals(pcompSoftware.getCreatedBy()) && !user.getId().equals(pcompVersion.getCreatedBy())) {
                throw new PcompException(Constants.PERMISSION_DENIED);
            }
            if (!StrUtils.isNull(platform)) {
                PcompVersionPlatformDownload pcompVersionPlatformDownload = new PcompVersionPlatformDownload();
                pcompVersionPlatformDownload.setPlatform(platform);
                pcompVersionPlatformDownload.setPcompVersionId(pcompVersion.getId());
                pcompVersionPlatformDownload.setId(IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM));
                if (software != null && software.getSize() > 0) {
                    pcompVersionPlatformDownload.setDownload(httpUpload.uploadFile(software.getInputStream(), software.getSize(), Constants.PUBLIC_COMPONENT_SYSTEM_ENG, pcompVersionPlatformDownload.getId(), "file", ""));
                }
                pcompVersionService.addVersionDownload(pcompVersionPlatformDownload, user.getId());
                result.setData(true);
            }
        } catch (Exception e) {
            result.setMessage(ExceptionUtils.getStackTrace(e));
            result.setData(false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_REMOVE_PATH, method = RequestMethod.GET)
    public JsonResult<Boolean> removeVersionDocument(HttpServletRequest request) {
        JsonResult<Boolean> result = new JsonResult<>();
        String versionId = request.getParameter(PcompConstants.PCOMP_VERSION);
        String versionDocumentId = request.getParameter(PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT);
        try {
            PcompVersion pcompVersion = pcompVersionService.fetchVersionById(versionId);
            PcompSoftware pcompSoftware = pcompSoftwareService.fetchSoftware(pcompVersion.getPcompSoftwareId());
            Subject currentUser = SecurityUtils.getSubject();
            User user = (User) currentUser.getPrincipal();
            if (!user.getId().equals(pcompSoftware.getCreatedBy()) && !user.getId().equals(pcompVersion.getCreatedBy())) {
                throw new PcompException(Constants.PERMISSION_DENIED);
            }
            pcompVersionService.removeVersionDocument(versionDocumentId, user.getId());
            result.setData(true);
        } catch (Exception e) {
            result.setMessage(ExceptionUtils.getStackTrace(e));
            result.setData(false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_MODIFICATION_PATH, method = RequestMethod.POST)
    public JsonResult<Boolean> updateVersionDocument(@RequestParam(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DOWNLOAD) MultipartFile document,
                                                     @RequestParam(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DESCRIPTION) String description,
                                                     HttpServletRequest request) {
        JsonResult<Boolean> result = new JsonResult<>();
        String versionId = request.getParameter(PcompConstants.PCOMP_VERSION);
        String versionDocumentId = request.getParameter(PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT);
        try {
            PcompVersion pcompVersion = pcompVersionService.fetchVersionById(versionId);
            PcompSoftware pcompSoftware = pcompSoftwareService.fetchSoftware(pcompVersion.getPcompSoftwareId());
            Subject currentUser = SecurityUtils.getSubject();
            User user = (User) currentUser.getPrincipal();
            if (!user.getId().equals(pcompSoftware.getCreatedBy()) && !user.getId().equals(pcompVersion.getCreatedBy())) {
                throw new PcompException(Constants.PERMISSION_DENIED);
            }
            if (!StrUtils.isNull(description)) {
                PcompVersionDoucumentDownload pcompVersionDoucumentDownload = pcompVersionService.fetchVersionDocument(versionDocumentId);
                pcompVersionDoucumentDownload.setDescription(description);
                if (document != null && document.getSize() > 0) {
                    pcompVersionDoucumentDownload.setDownload(httpUpload.uploadFile(document.getInputStream(), document.getSize(), Constants.PUBLIC_COMPONENT_SYSTEM_ENG, pcompVersionDoucumentDownload.getId(), "file", ""));
                }
                pcompVersionService.updateVersionDocument(pcompVersionDoucumentDownload, user.getId());
                result.setData(true);
            }
        } catch (Exception e) {
            result.setData(false);
            result.setMessage(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_CREATE_PATH, method = RequestMethod.POST)
    public JsonResult<Boolean> addVersionDocument(@RequestParam(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DOWNLOAD) MultipartFile document,
                                                  @RequestParam(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DESCRIPTION) String description,
                                                  HttpServletRequest request) {
        JsonResult<Boolean> result = new JsonResult<>();
        String versionId = request.getParameter(PcompConstants.PCOMP_VERSION);
        try {
            PcompVersion pcompVersion = pcompVersionService.fetchVersionById(versionId);
            PcompSoftware pcompSoftware = pcompSoftwareService.fetchSoftware(pcompVersion.getPcompSoftwareId());
            Subject currentUser = SecurityUtils.getSubject();
            User user = (User) currentUser.getPrincipal();
            if (!user.getId().equals(pcompSoftware.getCreatedBy()) && !user.getId().equals(pcompVersion.getCreatedBy())) {
                throw new PcompException(Constants.PERMISSION_DENIED);
            }
            if (!StrUtils.isNull(description)) {
                PcompVersionDoucumentDownload pcompVersionDoucumentDownload = new PcompVersionDoucumentDownload();
                pcompVersionDoucumentDownload.setDescription(description);
                pcompVersionDoucumentDownload.setId(IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM));
                pcompVersionDoucumentDownload.setPcompVersionId(pcompVersion.getId());
                if (document != null && document.getSize() > 0) {
                    pcompVersionDoucumentDownload.setDownload(httpUpload.uploadFile(document.getInputStream(), document.getSize(), Constants.PUBLIC_COMPONENT_SYSTEM_ENG, pcompVersionDoucumentDownload.getId(), "file", ""));
                }
                pcompVersionService.addVersionDocument(pcompVersionDoucumentDownload, user.getId());
                result.setData(true);
            }
        } catch (Exception e) {
            result.setData(false);
            result.setMessage(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }
}
