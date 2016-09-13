package com.sf.sfpp.web.controller.pcomp;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.dto.JsonResult;
import com.sf.sfpp.common.utils.ExceptionUtils;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.user.dao.domain.Resource;
import com.sf.sfpp.user.dao.domain.Role;
import com.sf.sfpp.user.dao.domain.User;
import com.sf.sfpp.user.service.ResourceService;
import com.sf.sfpp.user.service.RoleService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

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
    private RoleService roleService;
    @Autowired
    private ResourceService resourceService;

    private final static Logger log = LoggerFactory.getLogger(UserRightController.class);

    @ResponseBody
    @RequestMapping(value = "/pcomp/title/addKind/hasRight", method = RequestMethod.GET)
    public JsonResult<Boolean> getHasAddPcompKindRight() {
        Subject currentUser = SecurityUtils.getSubject();
        JsonResult<Boolean> result = new JsonResult<>();
        try {
            if (currentUser.hasRole("系统管理员") || currentUser.hasRole("公共组件管理员")) {
                result.setData(true);
            } else {
                result.setMessage("你没有权限");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/title/modify/hasRight", method = RequestMethod.GET)
    public JsonResult<Boolean> getHasModifyPcompTitleRight() {
        return getHasAddPcompKindRight();
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/kind/addSoftware/hasRight", method = RequestMethod.GET)
    public JsonResult<Boolean> getHasAddPcompSoftwareRight(String pcompKindId) {
        JsonResult<Boolean> result = new JsonResult<>();
        result.setData(true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/kind/modify/hasRight", method = RequestMethod.GET)
    public JsonResult<Boolean> getHasModifyPcompKindRight() {
        return getHasAddPcompKindRight();
    }

    private String getSoftwareRights(String softwareId, String right) {
        return StrUtils.makeString(Constants.FOLDER_PATH_SEPARATOR,
                PcompConstants.PCOMP_SOFTWARE,
                Constants.FOLDER_PATH_SEPARATOR,
                softwareId, right);
    }

    private String getVersionRights(String softwareId, String versionId, String right) {
        return StrUtils.makeString(Constants.FOLDER_PATH_SEPARATOR,
                PcompConstants.PCOMP_SOFTWARE,
                Constants.FOLDER_PATH_SEPARATOR,
                softwareId,
                Constants.FOLDER_PATH_SEPARATOR,
                versionId,
                right);
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/software/modify/hasRight", method = RequestMethod.GET)
    public JsonResult<Boolean> getHasModifyPcompSoftwareRight(@RequestParam(value = PcompConstants.PCOMP_SOFTWARE)String softwareId ) {
        Subject currentUser = SecurityUtils.getSubject();
        JsonResult<Boolean> result = new JsonResult<>();
        try {
            if (currentUser.isPermitted(getSoftwareRights(softwareId, "/modify"))) {
                result.setData(true);
            } else if (currentUser.hasRole("系统管理员") || currentUser.hasRole("公共组件管理员")) {
                result.setData(true);
            } else {
                result.setMessage("你没有权限");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/software/addVersion/hasRight", method = RequestMethod.GET)
    public JsonResult<Boolean> getHasAddPcompVersionRight(@RequestParam(value = PcompConstants.PCOMP_SOFTWARE)String softwareId) {
        Subject currentUser = SecurityUtils.getSubject();
        JsonResult<Boolean> result = new JsonResult<>();
        try {
            if (currentUser.isPermitted(getSoftwareRights(softwareId, "/addChild"))) {
                result.setData(true);
            } else if (currentUser.hasRole("系统管理员") || currentUser.hasRole("公共组件管理员")) {
                result.setData(true);
            } else {
                result.setMessage("你没有权限");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/version/modify/hasRight", method = RequestMethod.GET)
    public JsonResult<Boolean> getHasModifyPcompVersionRight(@RequestParam(value = PcompConstants.PCOMP_SOFTWARE) String softwareId,@RequestParam(value = PcompConstants.PCOMP_VERSION)String versionId) {
        Subject currentUser = SecurityUtils.getSubject();
        JsonResult<Boolean> result = new JsonResult<>();
        try {
            if (currentUser.isPermitted(getVersionRights(softwareId, versionId, "/modify"))) {
                result.setData(true);
            } else if (currentUser.hasRole("系统管理员") || currentUser.hasRole("公共组件管理员")) {
                result.setData(true);
            } else {
                result.setMessage("你没有权限");
                result.setData(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/software/modify/addRight", method = RequestMethod.GET)
    public JsonResult<Boolean> addModifyPcompSoftwareRight(@RequestParam(value = PcompConstants.PCOMP_SOFTWARE) String softwareId) {
        Subject currentUser = SecurityUtils.getSubject();
        JsonResult<Boolean> result = new JsonResult<>();
        try {
            String resourceUrl = getSoftwareRights(softwareId, "/modify");
            Resource resource = resourceService.selectResourceByUrl(resourceUrl);
            if (resource != null) {
                result.setData(false);
                result.setMessage("已存在权限");
            } else {
                User user = (User) currentUser.getPrincipal();
                resource = new Resource();
                resource.setResourceName(StrUtils.makeString("软件", softwareId, "修改权限"));
                resource.setResourceUrl(resourceUrl);
                resourceService.addResource(resource);
                Role role = roleService.getRoleByRoleName(user.getUserNo());
                resource = resourceService.selectResourceByUrl(resource.getResourceUrl());
                List<Integer> resourceIdList = new LinkedList<>();
                resourceIdList.add(resource.getResourceId());
                resource = new Resource();
                resource.setResourceName(StrUtils.makeString("软件", softwareId, "增加版本权限"));
                resource.setResourceUrl(getSoftwareRights(softwareId, "/addChild"));
                resourceService.addResource(resource);
                resource = resourceService.selectResourceByUrl(resource.getResourceUrl());
                resourceIdList.add(resource.getResourceId());
                resourceService.updateRoleResource(StrUtils.makeString(role.getRoleId()), resourceIdList);
                refreshUserRight(currentUser.getPrincipals());
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/software/modify/addRightOthers", method = RequestMethod.GET)
    public JsonResult<Boolean> addModifyOtherPcompSoftwareRight(HttpServletRequest request) throws Exception {
        String softwareId = request.getParameter(PcompConstants.PCOMP_SOFTWARE);
        String userIds = request.getParameter("userIds");
        String[] userId = userIds.split(",");
        String resourceUrl = getSoftwareRights(softwareId, "/modify");
        Subject currentUser = SecurityUtils.getSubject();
        JsonResult<Boolean> result = new JsonResult<>();
        try {
            if (currentUser.isPermitted(resourceUrl) || currentUser.hasRole("系统管理员") || currentUser.hasRole("公共组件管理员")) {
                for (int i = 0; i < userId.length; i++) {
                    User user = userService.getUserByUserId(Integer.parseInt(userId[i]));
                    Role role = roleService.getRoleByRoleName(user.getUserNo());
                    Resource resource = resourceService.selectResourceByUrl(resourceUrl);
                    List<Integer> resourceIdList = new LinkedList<>();
                    resourceIdList.add(resource.getResourceId());
                    resource = resourceService.selectResourceByUrl(getSoftwareRights(softwareId, "/addChild"));
                    resourceIdList.add(resource.getResourceId());
                    resourceService.updateRoleResource(StrUtils.makeString(role.getRoleId()), resourceIdList);
                }
                result.setData(true);
            } else {
                result.setData(false);
                result.setMessage("你没有权限");
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/software/addVersion/addRightsOthers", method = RequestMethod.GET)
    public JsonResult<Boolean> addAddPcompVersionRight(HttpServletRequest request) throws Exception {
        String softwareId = request.getParameter(PcompConstants.PCOMP_SOFTWARE);
        String resourceUrl = getSoftwareRights(softwareId, "/addChild");
        String userIds = request.getParameter("userIds");
        String[] userId = userIds.split(",");
        Subject currentUser = SecurityUtils.getSubject();
        JsonResult<Boolean> result = new JsonResult<>();
        try {
            if (currentUser.isPermitted(resourceUrl) || currentUser.hasRole("系统管理员") || currentUser.hasRole("公共组件管理员")) {
                for (int i = 0; i < userId.length; i++) {
                    User user = userService.getUserByUserId(Integer.parseInt(userId[i]));
                    Role role = roleService.getRoleByRoleName(user.getUserNo());
                    List<Integer> resourceIdList = new LinkedList<>();
                    Resource resource = resourceService.selectResourceByUrl(resourceUrl);
                    resourceIdList.add(resource.getResourceId());
                    resourceService.updateRoleResource(StrUtils.makeString(role.getRoleId()), resourceIdList);
                }
                result.setData(true);
            } else {
                result.setData(false);
                result.setMessage("你没有权限");
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/version/modify/addRight", method = RequestMethod.GET)
    public JsonResult<Boolean> addModifyPcompVersionRight(String softwareId, String versionId) throws Exception {
        Subject currentUser = SecurityUtils.getSubject();
        JsonResult<Boolean> result = new JsonResult<>();
        try {
            String resourceUrl = getVersionRights(softwareId, versionId, "/modify");
            Resource resource = resourceService.selectResourceByUrl(resourceUrl);
            if (resource != null) {
                result.setData(false);
                result.setMessage("已存在权限");
            } else {
                User user = (User) currentUser.getPrincipal();
                resource = new Resource();
                resource.setResourceName(StrUtils.makeString("版本", softwareId, "修改权限"));
                resource.setResourceUrl(resourceUrl);
                resourceService.addResource(resource);
                Role role = roleService.getRoleByRoleName(user.getUserNo());
                resource = resourceService.selectResourceByUrl(resource.getResourceUrl());
                List<Integer> resourceIdList = new LinkedList<>();
                resourceIdList.add(resource.getResourceId());
                resourceService.updateRoleResource(StrUtils.makeString(role.getRoleId()), resourceIdList);
                refreshUserRight(currentUser.getPrincipals());
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }
    private void refreshUserRight(PrincipalCollection principalCollection){
        RealmSecurityManager securityManager =
                (RealmSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm userRealm = (UserRealm) securityManager.getRealms().iterator().next();
        userRealm.refreshAuthorizationInfo(principalCollection);
    }
}
