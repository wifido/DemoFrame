package com.sf.sfpp.user.manager;

import com.github.pagehelper.Page;
import com.sf.sfpp.user.dao.domain.UserHistory;

/**
 * @author ding.yang 01139954
 * @date 2016/9/7.
 */
public interface UserHistoryManager {

    /**
     * 通过用户ID和页数获取用户操作历史
     */
    Page<UserHistory> getUserHistorysByUserId(Integer UserId, int pageNumber);

    /**
     * 通过用户ID和页数以及actions码获取用户操作历史
     * 传入的actions是action码的后4位
     */
    Page<UserHistory> getUserHistorysByUserId(Integer UserId, int pageNumber, String... actions);


    /**
     * 新增用户操作历史
     * 返回false表示失败，true表示成功
     */
    boolean addUserHistory(UserHistory userHistory);
}
