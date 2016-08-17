package com.sf.sfpp.web.controller.pcomp;

import com.sf.sfpp.common.domain.WebCache;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompVersion;
import com.sf.sfpp.pcomp.common.model.extend.PcompVersionExtend;
import com.sf.sfpp.pcomp.service.PcompVersionService;
import com.sf.sfpp.user.dao.domain.User;
import com.sf.sfpp.web.common.PathConstants;
import com.sf.sfpp.web.controller.common.AbstractCachedController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
            if(modified) {
                User user = null;
                if (webCache.getUser() != null) {
                    user = (User) webCache.getUser();
                }
                if(user != null){
                    pcompVersion.setModifiedBy(user.getId());
                } else {
                    pcompVersion.setModifiedBy(-1);
                }
            }
            pcompVersionService.updateVersion(pcompVersion);
        } catch (PcompException e) {
            handleException(e,webCache);
        }
        redirectAttributes.addAttribute(PcompConstants.PCOMP_SOFTWARE,pcomp_software_id);
        redirectAttributes.addAttribute(PcompConstants.SOFTWARE_PAGE_NAVIGATION,page_navi);
        redirectAttributes.addAttribute(PcompConstants.PCOMP_VERSION,pcomp_version_id);
        return "redirect:"+ PathConstants.PCOMP_SOFTWARE_PATH;
    }
}
