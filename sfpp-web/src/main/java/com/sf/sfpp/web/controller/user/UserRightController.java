package com.sf.sfpp.web.controller.user;

import com.sf.sfpp.common.dto.JsonResult;
import com.sf.sfpp.common.utils.ExceptionUtils;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.service.PcompKindService;
import com.sf.sfpp.pcomp.service.PcompSoftwareService;
import com.sf.sfpp.pcomp.service.PcompTitleService;
import com.sf.sfpp.pcomp.service.PcompVersionService;
import com.sf.sfpp.user.dao.domain.Resource;
import com.sf.sfpp.user.service.ResourceService;
import com.sf.sfpp.user.service.UserService;
import com.sf.sfpp.web.user.shiro.realm.UserRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/9/8
 */
@Controller
public class UserRightController {
    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private PcompTitleService pcompTitleService;
    @Autowired
    private PcompKindService pcompKindService;
    @Autowired
    private PcompSoftwareService pcompSoftwareService;
    @Autowired
    private PcompVersionService pcompVersionService;

    private final static Logger log = LoggerFactory.getLogger(UserRightController.class);

    @ResponseBody
    @RequestMapping(value = "/isGlobalAdmin", method = RequestMethod.GET)
    public JsonResult<Boolean> getIsGlobalAdmin() {
        Subject currentUser = SecurityUtils.getSubject();
        JsonResult<Boolean> result = new JsonResult<>();
        try {
            if (currentUser.isPermitted(":sfpp")) {
                result.setData(true);
            } else {
                result.setMessage("Permission Denied");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/addGlobalAdmin", method = RequestMethod.GET)
    public JsonResult<Boolean> addGlobalAdmin(@RequestParam(value = "userId") String userId) {
        Subject currentUser = SecurityUtils.getSubject();
        JsonResult<Boolean> result = new JsonResult<>();
        try {
            if (currentUser.isPermitted(":sfpp")) {
                userService.bindUserWithResource(userId, ":sfpp", null, null, null);
                result.setData(true);
            } else {
                result.setMessage("Permission Denied");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/isAdmin", method = RequestMethod.GET)
    public JsonResult<Boolean> getIsPcompAdmin() {
        Subject currentUser = SecurityUtils.getSubject();
        JsonResult<Boolean> result = new JsonResult<>();
        try {
            if (currentUser.isPermitted(StrUtils.makeString(":sfpp", ":pcomp"))) {
                result.setData(true);
            } else {
                result.setMessage("Permission Denied");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/addAdmin", method = RequestMethod.GET)
    public JsonResult<Boolean> addPcompAdmin(@RequestParam(value = "userId") String userId) {
        Subject currentUser = SecurityUtils.getSubject();
        JsonResult<Boolean> result = new JsonResult<>();
        try {
            if (currentUser.isPermitted(StrUtils.makeString(":sfpp", ":pcomp"))) {
                userService.bindUserWithResource(userId, StrUtils.makeString(":sfpp", ":pcomp"), null, null, null);
                result.setData(true);
            } else {
                result.setMessage("Permission Denied");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/title/addKind/hasRight", method = RequestMethod.GET)
    public JsonResult<Boolean> getHasAddPcompKindRight(
            @RequestParam(value = PcompConstants.PCOMP_TITLE) String titleId) {
        Subject currentUser = SecurityUtils.getSubject();
        JsonResult<Boolean> result = new JsonResult<>();
        try {
            if (currentUser.isPermitted(StrUtils.makeString(pcompTitleService.getResourceUrl(titleId), ":addChild"))) {
                result.setData(true);
            } else {
                result.setMessage("Permission Denied");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/title/addKind/addRight", method = RequestMethod.GET)
    public JsonResult<Boolean> addAddPcompKindRight(@RequestParam(value = PcompConstants.PCOMP_TITLE) String titleId,
            @RequestParam(value = "userId") String userId) {
        Subject currentUser = SecurityUtils.getSubject();
        JsonResult<Boolean> result = new JsonResult<>();
        try {
            String resourceUrl = StrUtils.makeString(pcompTitleService.getResourceUrl(titleId), ":addChild");
            if (currentUser.isPermitted(resourceUrl)) {
                userService.bindUserWithResource(userId, resourceUrl, PcompConstants.PCOMP_TITLE, titleId, "添加类别");
                result.setData(true);
            } else {
                result.setMessage("Permission Denied");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/title/modify/hasRight",
            method = RequestMethod.GET)
    public JsonResult<Boolean> getHasModifyPcompTitleRight(
            @RequestParam(value = PcompConstants.PCOMP_TITLE) String titleId) {
        Subject currentUser = SecurityUtils.getSubject();
        JsonResult<Boolean> result = new JsonResult<>();
        try {
            if (currentUser.isPermitted(pcompTitleService.getResourceUrl(titleId))) {
                result.setData(true);
            } else {
                result.setMessage("Permission Denied");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/title/modify/addRight",
            method = RequestMethod.GET)
    public JsonResult<Boolean> addModifyPcompTitleRight(
            @RequestParam(value = PcompConstants.PCOMP_TITLE) String titleId,
            @RequestParam(value = "userId") String userId) {
        Subject currentUser = SecurityUtils.getSubject();
        JsonResult<Boolean> result = new JsonResult<>();
        try {
            String resourceUrl = pcompTitleService.getResourceUrl(titleId);
            if (currentUser.isPermitted(resourceUrl)) {
                userService.bindUserWithResource(userId, resourceUrl, PcompConstants.PCOMP_TITLE, titleId, "全部权限");
                result.setData(true);
            } else {
                result.setMessage("Permission Denied");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/kind/addSoftware/hasRight",
            method = RequestMethod.GET)
    public JsonResult<Boolean> getHasAddPcompSoftwareRight(
            @RequestParam(value = PcompConstants.PCOMP_KIND) String pcompKindId) {
        JsonResult<Boolean> result = new JsonResult<>();
        Subject currentUser = SecurityUtils.getSubject();
        try {
            if (pcompKindService.fetchKindByKindId(pcompKindId).getPcompTitleId()
                    .equals("0000000000000000000000000000000000000000")) {
                if (currentUser
                        .isPermitted(StrUtils.makeString(pcompKindService.getResourceUrl(pcompKindId), ":addChild"))) {
                    result.setData(true);
                } else {
                    result.setMessage("Permission Denied");
                    result.setData(false);
                }
            } else {
                result.setData(true);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/kind/addSoftware/addRight",
            method = RequestMethod.GET)
    public JsonResult<Boolean> addAddPcompSoftwareRight(
            @RequestParam(value = PcompConstants.PCOMP_KIND) String pcompKindId,
            @RequestParam(value = "userId") String userId) {
        JsonResult<Boolean> result = new JsonResult<>();
        Subject currentUser = SecurityUtils.getSubject();
        try {
            if (pcompKindService.fetchKindByKindId(pcompKindId).getPcompTitleId()
                    .equals("0000000000000000000000000000000000000000")) {
                String resourceUrl = StrUtils.makeString(pcompKindService.getResourceUrl(pcompKindId), ":addChild");
                if (currentUser.isPermitted(resourceUrl)) {
                    userService
                            .bindUserWithResource(userId, resourceUrl, PcompConstants.PCOMP_KIND, pcompKindId, "添加软件");
                    result.setData(true);
                } else {
                    result.setMessage("Permission Denied");
                    result.setData(false);
                }
            } else {
                result.setData(true);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/kind/modify/hasRight",
            method = RequestMethod.GET)
    public JsonResult<Boolean> getHasModifyPcompKindRight(
            @RequestParam(value = PcompConstants.PCOMP_KIND) String pcompKindId) {
        JsonResult<Boolean> result = new JsonResult<>();
        Subject currentUser = SecurityUtils.getSubject();
        try {
            String resourceUrl = pcompKindService.getResourceUrl(pcompKindId);
            if (currentUser.isPermitted(resourceUrl)) {
                result.setData(true);
            } else {
                result.setMessage("Permission Denied");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/kind/modify/addRight",
            method = RequestMethod.GET)
    public JsonResult<Boolean> addModifyPcompKindRight(
            @RequestParam(value = PcompConstants.PCOMP_KIND) String pcompKindId,
            @RequestParam(value = "userId") String userId) {
        JsonResult<Boolean> result = new JsonResult<>();
        Subject currentUser = SecurityUtils.getSubject();
        try {
            String resourceUrl = pcompKindService.getResourceUrl(pcompKindId);
            if (currentUser.isPermitted(resourceUrl)) {
                userService.bindUserWithResource(userId, resourceUrl,PcompConstants.PCOMP_KIND,pcompKindId,"全部权限");
                result.setData(true);
            } else {
                result.setMessage("Permission Denied");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/software/view/hasRight",
            method = RequestMethod.GET)
    public JsonResult<Boolean> getHasViewPcompSoftwareRight(
            @RequestParam(value = PcompConstants.PCOMP_SOFTWARE) String softwareId) {
        JsonResult<Boolean> result = new JsonResult<>();
        Subject currentUser = SecurityUtils.getSubject();
        try {
            String resourceUrl = StrUtils.makeString(pcompSoftwareService.getResourceUrl(softwareId), ":view");
            Resource resource = resourceService.selectResourceByUrl(resourceUrl);
            if (resource != null) {
                if (currentUser.isPermitted(resourceUrl)) {
                    result.setData(true);
                } else {
                    result.setMessage("Permission Denied");
                    result.setData(false);
                }
            } else {
                result.setData(true);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/software/view/addRight",
            method = RequestMethod.GET)
    public JsonResult<Boolean> addViewPcompSoftwareRight(
            @RequestParam(value = PcompConstants.PCOMP_SOFTWARE) String softwareId,
            @RequestParam(value = "userId") String userId) {
        JsonResult<Boolean> result = new JsonResult<>();
        Subject currentUser = SecurityUtils.getSubject();
        try {
            String resourceUrl = StrUtils.makeString(pcompSoftwareService.getResourceUrl(softwareId), ":view");
            Resource resource = resourceService.selectResourceByUrl(resourceUrl);
            if (resource != null) {
                if (currentUser.isPermitted(pcompSoftwareService.getResourceUrl(softwareId))) {
                    userService.bindUserWithResource(userId, resourceUrl,PcompConstants.PCOMP_SOFTWARE,softwareId,"(本软件私有)可查看");
                    result.setData(true);
                } else {
                    result.setMessage("Permission Denied");
                    result.setData(false);
                }
            } else {
                result.setData(true);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/software/transferToPrivate",
            method = RequestMethod.GET)
    public JsonResult<Boolean> transferPcompSoftwareToPrivate(
            @RequestParam(value = PcompConstants.PCOMP_SOFTWARE) String softwareId) {
        JsonResult<Boolean> result = new JsonResult<>();
        Subject currentUser = SecurityUtils.getSubject();
        try {
            String resourceUrl = StrUtils.makeString(pcompSoftwareService.getResourceUrl(softwareId), ":view");
            if (currentUser.isPermitted(resourceUrl)) {
                Resource resource = resourceService.selectResourceByUrl(resourceUrl);
                if (resource == null) {
                    resource = new Resource();
                    resource.setResourceUrl(resourceUrl);
                    resource.setResourceType(PcompConstants.PCOMP_SOFTWARE);
                    resource.setResourceName(softwareId);
                    resource.setRemark("(本软件私有)可查看");
                    resourceService.addResource(resource);
                }
                result.setData(true);
            } else {
                result.setMessage("Permission Denied");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/software/transferToPublic",
            method = RequestMethod.GET)
    public JsonResult<Boolean> transferPcompSoftwareToPublic(
            @RequestParam(value = PcompConstants.PCOMP_SOFTWARE) String softwareId) {
        JsonResult<Boolean> result = new JsonResult<>();
        Subject currentUser = SecurityUtils.getSubject();
        try {
            String resourceUrl = StrUtils.makeString(pcompSoftwareService.getResourceUrl(softwareId), ":view");
            if (currentUser.isPermitted(resourceUrl)) {
                Resource resource = resourceService.selectResourceByUrl(resourceUrl);
                if (resource != null) {
                    resourceService.deleteResource(StrUtils.makeString(resource.getResourceId()));
                }
                result.setData(true);
            } else {
                result.setMessage("Permission Denied");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/software/modify/hasRight",
            method = RequestMethod.GET)
    public JsonResult<Boolean> getHasModifyPcompSoftwareRight(
            @RequestParam(value = PcompConstants.PCOMP_SOFTWARE) String softwareId) {
        JsonResult<Boolean> result = new JsonResult<>();
        Subject currentUser = SecurityUtils.getSubject();
        try {
            String resourceUrl = pcompSoftwareService.getResourceUrl(softwareId);
            if (currentUser.isPermitted(resourceUrl)) {
                result.setData(true);
            } else {
                result.setMessage("Permission Denied");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/software/modify/addRight",
            method = RequestMethod.GET)
    public JsonResult<Boolean> addModifyPcompSoftwareRight(
            @RequestParam(value = PcompConstants.PCOMP_SOFTWARE) String softwareId,
            @RequestParam(value = "userId") String userId) {
        JsonResult<Boolean> result = new JsonResult<>();
        Subject currentUser = SecurityUtils.getSubject();
        try {
            String resourceUrl = pcompSoftwareService.getResourceUrl(softwareId);
            if (currentUser.isPermitted(resourceUrl)) {
                userService.bindUserWithResource(userId, resourceUrl,PcompConstants.PCOMP_SOFTWARE,softwareId,"全部权限");
                result.setData(true);
            } else {
                result.setMessage("Permission Denied");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/software/addVersion/hasRight",
            method = RequestMethod.GET)
    public JsonResult<Boolean> getHasAddPcompVersionRight(
            @RequestParam(value = PcompConstants.PCOMP_SOFTWARE) String softwareId) {
        JsonResult<Boolean> result = new JsonResult<>();
        Subject currentUser = SecurityUtils.getSubject();
        try {
            String resourceUrl = StrUtils.makeString(pcompSoftwareService.getResourceUrl(softwareId), ":addChild");
            if (currentUser.isPermitted(resourceUrl)) {
                result.setData(true);
            } else {
                result.setMessage("Permission Denied");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/software/addVersion/addRight",
            method = RequestMethod.GET)
    public JsonResult<Boolean> addAddPcompVersionRight(
            @RequestParam(value = PcompConstants.PCOMP_SOFTWARE) String softwareId,
            @RequestParam(value = "userId") String userId) {
        JsonResult<Boolean> result = new JsonResult<>();
        Subject currentUser = SecurityUtils.getSubject();
        try {
            String resourceUrl = StrUtils.makeString(pcompSoftwareService.getResourceUrl(softwareId), ":addChild");
            if (currentUser.isPermitted(resourceUrl)) {
                userService.bindUserWithResource(userId, resourceUrl,PcompConstants.PCOMP_SOFTWARE,softwareId,"添加版本");
                result.setData(true);
            } else {
                result.setMessage("Permission Denied");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/version/modify/hasRight",
            method = RequestMethod.GET)
    public JsonResult<Boolean> getHasModifyPcompVersionRight(
            @RequestParam(value = PcompConstants.PCOMP_VERSION) String versionId) {
        JsonResult<Boolean> result = new JsonResult<>();
        Subject currentUser = SecurityUtils.getSubject();
        try {
            String resourceUrl = pcompVersionService.getResourceUrl(versionId);
            if (currentUser.isPermitted(resourceUrl)) {
                result.setData(true);
            } else {
                result.setMessage("Permission Denied");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/version/modify/addRight",
            method = RequestMethod.GET)
    public JsonResult<Boolean> addModifyPcompVersionRight(
            @RequestParam(value = PcompConstants.PCOMP_VERSION) String versionId,
            @RequestParam(value = "userId") String userId) {
        JsonResult<Boolean> result = new JsonResult<>();
        Subject currentUser = SecurityUtils.getSubject();
        try {
            String resourceUrl = pcompVersionService.getResourceUrl(versionId);
            if (currentUser.isPermitted(resourceUrl)) {
                userService.bindUserWithResource(userId, resourceUrl,PcompConstants.PCOMP_VERSION,versionId,"全部权限");
                result.setData(true);
            } else {
                result.setMessage("Permission Denied");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    public void refreshUserRight(PrincipalCollection principalCollection) {
        RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm userRealm = (UserRealm) securityManager.getRealms().iterator().next();
        userRealm.refreshAuthorizationInfo(principalCollection);
    }
}
