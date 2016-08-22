package com.sf.sfpp.pcomp.manager;

import com.alibaba.fastjson.JSON;
import com.sf.kafka.exception.KafkaException;
import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.kafka.KafkaConnectionPool;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.model.PcompKind;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.common.model.PcompTitle;
import com.sf.sfpp.pcomp.dao.PcompKindMapper;
import com.sf.sfpp.pcomp.dao.PcompSoftwareMapper;
import com.sf.sfpp.pcomp.dao.PcompTitleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/15
 */
@Component
public class PcompTitleManager {
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

    public List<PcompTitle> getAllTitles() {
        return pcompTitleMapper.selectAllAvailable();
    }

    public PcompTitle getPcompTitleByPcompKindId(String pcompKindId) {
        PcompKind pcompKind = pcompKindMapper.selectByPrimaryKey(pcompKindId);
        return pcompTitleMapper.selectByPrimaryKey(pcompKind.getPcompTitleId());
    }

    public PcompTitle getPcompTitleByPcompSoftwareId(String pcompSoftwareId) {
        PcompSoftware pcompSoftware = pcompSoftwareMapper.selectByPrimaryKey(pcompSoftwareId);
        return getPcompTitleByPcompKindId(pcompSoftware.getPcompKindId());
    }

    public PcompTitle getPcompTitleByPcompTitleName(String pcompTitleName) {
        return pcompTitleMapper.selectByUniqueKey(pcompTitleName);
    }

    public boolean existsPcompTitleName(String pcompTitleName) {
        return pcompTitleMapper.selectByUniqueKey(pcompTitleName) != null;
    }

    public boolean addPcompTitle(PcompTitle pcompTitle) throws KafkaException {
        boolean b = pcompTitleMapper.insertSelective(pcompTitle) > 0;
        pcompTitle = pcompTitleMapper.selectByPrimaryKey(pcompTitle.getId());
        if (b) {
            kafkaConnectionPool.getKafkaConnection(kafkaConnectionKey)
                    .send(StrUtils.makeString(PcompConstants.PCOMP_TITLE, Constants.KAFKA_TYPE_SEPARATOR, JSON.toJSONString(pcompTitle)));
        }
        return b;
    }
}
