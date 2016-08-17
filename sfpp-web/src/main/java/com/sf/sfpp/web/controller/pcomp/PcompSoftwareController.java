package com.sf.sfpp.web.controller.pcomp;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.domain.WebCache;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.extend.PcompSoftwareExtend;
import com.sf.sfpp.pcomp.service.PcompSoftwareService;
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
 * @date 2016/8/16
 */
@Controller
public class PcompSoftwareController extends AbstractCachedController {
    @Autowired
    private PcompSoftwareService pcompSoftwareService;

    @RequestMapping(value = PathConstants.PCOMP_SOFTWARE_MODIFICATION_PATH, method = RequestMethod.POST)
    public String updateSoftware(HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
        WebCache webCache = getWebCache(request);
        boolean modified = false;
        String pcomp_software_id = request.getParameter(PathConstants.PCOMP_SOFTWARE_ID);
        String pcomp_software_avatar = request.getParameter(PathConstants.PCOMP_SOFTWARE_AVATAR);
        String pcomp_software_name = request.getParameter(PathConstants.PCOMP_SOFTWARE_NAME);
        String pcomp_software_introduction = request.getParameter(PathConstants.PCOMP_SOFTWARE_INTRODUCTION);
        try {
            PcompSoftwareExtend pcompSoftware = (PcompSoftwareExtend) pcompSoftwareService.fetchSoftware(pcomp_software_id);
            if (pcompSoftware == null) {
                throw new PcompException(new StringBuilder().append("Software:").append(pcomp_software_id).append("不存在").toString(), new Exception());
            }
            if (!StrUtils.isNull(pcomp_software_avatar)) {
                pcompSoftware.setAvatar(pcomp_software_avatar);
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
            if(modified) {
                User user = null;
                if (webCache.getUser() != null) {
                    user = (User) webCache.getUser();
                }
                if(user != null){
                    pcompSoftware.setModifiedBy(user.getId());
                } else {
                    pcompSoftware.setModifiedBy(-1);
                }
            }
            pcompSoftwareService.updateSoftware(pcompSoftware);
        } catch (PcompException e) {
            model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
            return handleException(e, webCache);
        }
        redirectAttributes.addAttribute(PcompConstants.PCOMP_SOFTWARE,pcomp_software_id);
        return "redirect:"+ PathConstants.PCOMP_SOFTWARE_PATH;
    }
}
