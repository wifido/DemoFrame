package com.sf.sfpp.user.service.impl;

import com.github.pagehelper.PageInfo;
import com.sf.sfpp.user.dao.domain.UserHistory;
import com.sf.sfpp.user.manager.UserHistoryManager;
import com.sf.sfpp.user.service.UserHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ding.yang 01139954
 * @date 2016/9/7.
 */
@Service
public class UserHistoryServiceImpl implements UserHistoryService {

    @Autowired
    private UserHistoryManager userHistoryManager;

    @Override
    public PageInfo<UserHistory> getUserHistoryByUserId(Integer userId, int pageNumber, String... targetKinds) {
        return userHistoryManager.getUserHistorysByUserId(userId, pageNumber, targetKinds).toPageInfo();
    }

    @Override
    public boolean addUserHistory(UserHistory userHistory) {
        return userHistoryManager.addUserHistory(userHistory);
    }
}
