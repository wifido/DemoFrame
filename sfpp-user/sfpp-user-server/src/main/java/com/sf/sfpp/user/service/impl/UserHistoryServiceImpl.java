package com.sf.sfpp.user.service.impl;

import com.github.pagehelper.PageInfo;
import com.sf.sfpp.user.dao.domain.UserHistory;
import com.sf.sfpp.user.manager.UserHistoryManager;
import com.sf.sfpp.user.service.UserHistoryService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ding.yang 01139954
 * @date 2016/9/7.
 */
public class UserHistoryServiceImpl implements UserHistoryService {

    @Autowired
    private UserHistoryManager userHistoryManager;

    @Override
    public PageInfo<UserHistory> getUserHistoryByUserId(Integer userId, int pageNumber) {
        return userHistoryManager.getUserHistorysByUserId(userId, pageNumber).toPageInfo();
    }

    @Override
    public boolean addUserHistory(UserHistory userHistory) {
        return userHistoryManager.addUserHistory(userHistory);
    }
}
