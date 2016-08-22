package com.sf.sfpp.pcomp.manager;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sf.kafka.exception.KafkaException;
import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.kafka.KafkaConnectionPool;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.model.PcompKind;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.dao.PcompKindMapper;
import com.sf.sfpp.pcomp.dao.PcompSoftwareMapper;
import com.sf.sfpp.pcomp.dao.PcompTitleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/15
 */
@Component
public class PcompKindManager {
    @Value("${pcomp.connection.key}")
    private String kafkaConnectionKey;
    @Autowired
    private KafkaConnectionPool kafkaConnectionPool;
    @Autowired
    private PcompTitleMapper pcompTitleMapper;
    @Autowired
    private PcompKindMapper pcompKindMapper;
    @Autowired
    private PcompSoftwareMapper pcompSoftwareMapper;

    public Page<PcompKind> getKindsByTitle(String titleId, int pageNumber) {
        PageHelper.startPage(pageNumber, PcompConstants.numberPerPage);
        return (Page<PcompKind>) pcompKindMapper.selectAvailabeleKindsByTitleID(titleId);
    }

    public Page<PcompKind> getAllKinds(int pageNumber) {
        PageHelper.startPage(pageNumber, PcompConstants.numberPerPage);
        Page<PcompKind> pcompKinds = (Page<PcompKind>) pcompKindMapper.selectAllAvailabeleKinds();
        pcompKinds.getPages();
        return pcompKinds;
    }

    public PcompKind getPcompKindByPcompKindId(String pcompKindId) {
        return pcompKindMapper.selectByPrimaryKey(pcompKindId);
    }

    public PcompKind getPcompKindByPcompSoftwareId(String pcompSoftwareId) {
        PcompSoftware pcompSoftware = pcompSoftwareMapper.selectByPrimaryKey(pcompSoftwareId);
        return pcompKindMapper.selectByPrimaryKey(pcompSoftware.getPcompKindId());
    }

    public boolean existsPcompKind(String pcompTitleName, String pcompKindName) {
        String pcompTitleId = pcompTitleMapper.selectByUniqueKey(pcompTitleName).getId();
        return pcompKindMapper.selectByUniqueKey(pcompTitleId, pcompKindName) != null;
    }

    public boolean addPcompTitle(PcompKind pcompKind) throws KafkaException {
        boolean b = pcompKindMapper.insertSelective(pcompKind) > 0;
        pcompKind = pcompKindMapper.selectByPrimaryKey(pcompKind.getId());
        if (b) {
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey)
                    .send(StrUtils.makeString(PcompConstants.PCOMP_KIND, Constants.KAFKA_TYPE_SEPARATOR, JSON.toJSONString(pcompKind)));
        }
        return b;
    }
}
