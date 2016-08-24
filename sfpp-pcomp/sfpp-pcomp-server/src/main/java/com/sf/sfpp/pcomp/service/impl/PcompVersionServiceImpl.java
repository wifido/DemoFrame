package com.sf.sfpp.pcomp.service.impl;

import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompVersion;
import com.sf.sfpp.pcomp.common.model.extend.PcompVersionExtend;
import com.sf.sfpp.pcomp.manager.PcompVersionManager;
import com.sf.sfpp.pcomp.service.PcompVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 注意，和其他服务不同的是，这个服务所有对象参数为PcompVersionExtend，而不是PcompVersion
 *
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/12
 */
@Service
public class PcompVersionServiceImpl implements PcompVersionService {
    @Autowired
    private PcompVersionManager pcompVersionManager;

    @Override
    public List<PcompVersion> fetchAllVersionsSeparatelyBySoftware(String softwareId) throws PcompException {
        try {
            return null;
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public PcompVersion fetchVersionById(String versionId) throws PcompException {
        try {
            return pcompVersionManager.getPcompVersionByPcompVersionId(versionId);
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public boolean existsVersion(String softwareId, String version) throws PcompException {
        try {
            return pcompVersionManager.existsPcompVersion(softwareId, version);
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public boolean addVersion(PcompVersionExtend version) throws PcompException {
        try {
            return false;
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public boolean updateVersion(PcompVersionExtend version) throws PcompException {
        try {
            return pcompVersionManager.updatePcompVersionOnly(version);
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public boolean removeVersion(String versionId) throws PcompException {
        try {
            return false;
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }
}
