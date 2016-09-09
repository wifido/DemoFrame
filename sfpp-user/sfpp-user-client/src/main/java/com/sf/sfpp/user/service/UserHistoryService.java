package com.sf.sfpp.user.service;

import com.github.pagehelper.PageInfo;
import com.sf.sfpp.user.dao.domain.UserHistory;
/**
 * 用户操作历史
 *
 * @author ding.yang 01139954
 * @date 2016/9/7.
 */
public interface UserHistoryService {

    public PageInfo<UserHistory> getUserHistoryByUserId(Integer userId, int pageNumber);

    boolean addUserHistory(UserHistory userHistory);

}
