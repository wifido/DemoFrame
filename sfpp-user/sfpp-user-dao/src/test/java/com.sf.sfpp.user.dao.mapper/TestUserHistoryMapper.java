package com.sf.sfpp.user.dao.mapper;


import com.sf.sfpp.user.dao.domain.UserHistory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import java.util.List;
import java.util.Date;


/**
 *
 * @author ding.yang 01139954
 * @date 2016/9/7.
 */
@ContextConfiguration(locations = {"/test-dao.xml"})
@TestExecutionListeners(value = {TransactionalTestExecutionListener.class})
public class TestUserHistoryMapper extends AbstractJUnit4SpringContextTests {

    @Autowired
    private UserHistoryMapper userHistoryMapper;

    @Test
    public void testInsert() {
        UserHistory userHistory = new UserHistory();
        userHistory.setAction("00000000");
        userHistory.setDescription("test");
        userHistory.setUserId(5);
        userHistory.setModifiedTime(new Date());
        userHistory.setId("0001");
        userHistory.setTargetId("0000137331a0c-8885-4a2e-a596-e9750ba517d8");
        userHistoryMapper.insert(userHistory);
    }


    @Test
    public void testselectByPrimaryKey() {
        UserHistory userHistory = new UserHistory();

        List<UserHistory> list = userHistoryMapper.selectByPrimaryKey(12);
    }
}
