package com.sf.sfpp.pcomp.service.impl;

import com.sf.sfpp.pcomp.common.model.extend.PcompVersionExtend;
import com.sf.sfpp.pcomp.service.PcompVersionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 注意，和其他服务不同的是，这个服务所有对象参数为PcompVersionExtend，而不是PcompVersion
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/12
 */
@Service
public class PcompVersionServiceImpl implements PcompVersionService {

    @Override
    public List<PcompVersionExtend> fetchAllVersionsSeparatelyBySoftware(String softwareId) {
        return null;
    }

    @Override
    public boolean existsVersion(String softwareId, String version) {
        return false;
    }

    @Override
    public boolean addVersion(String softwareId, PcompVersionExtend version) {
        return false;
    }

    @Override
    public boolean updateVersion(PcompVersionExtend version) {
        return false;
    }

    @Override
    public boolean removeVersion(String versionId) {
        return false;
    }
}
