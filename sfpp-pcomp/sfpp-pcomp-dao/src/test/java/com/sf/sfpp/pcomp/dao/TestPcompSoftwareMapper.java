package com.sf.sfpp.pcomp.dao;

import com.sf.sfpp.pcomp.common.model.PcompKind;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/25
 */
@ContextConfiguration(locations = {"/test-dao.xml"})
@TestExecutionListeners(value = {TransactionalTestExecutionListener.class})
public class TestPcompSoftwareMapper extends AbstractJUnit4SpringContextTests {
    @Autowired
    private PcompKindMapper pcompKindMapper;
    @Autowired
    private PcompSoftwareMapper pcompSoftwareMapper;
    @Autowired
    private PcompVersionMapper pcompVersionMapper;

    @Test
    @Transactional
    public void testSelectAllAvailableIdByKindId() {
        List<PcompKind> pcompKinds = pcompKindMapper.selectAllAvailabeleKinds();
        for (PcompKind pcompKind : pcompKinds) {
            System.out.println(pcompSoftwareMapper.selectAllAvailableIdByKindId(pcompKind.getId()));
        }
    }
}
