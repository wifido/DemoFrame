package com.sf.sfpp.user.manager;

import com.github.pagehelper.Page;
import com.sf.sfpp.user.dao.domain.UserHistory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * @author ding.yang 01139954
 * @date 2016/9/9.
 */
@ContextConfiguration(locations = {"/test-manager.xml"})
@TestExecutionListeners(value = {TransactionalTestExecutionListener.class})
public class TestUserHistoryManager extends AbstractJUnit4SpringContextTests {
    private final static Logger log = LoggerFactory.getLogger(TestUserHistoryManager.class);

    @Autowired
    UserHistoryManager userHistoryManager;

    @Test
    public void testGetUserHistory() {
        Page<UserHistory> userHistorys = userHistoryManager.getUserHistorysByUserId(1, 1, "pcomp_title", "pcomp_kind");
        log.info(userHistorys.toString());
    }

    @Test
    public void testGetUserHistory1() {
        Page<UserHistory> userHistorys = userHistoryManager.getUserHistorysByUserId(1, 1);
        log.info(userHistorys.toString());
    }


}
