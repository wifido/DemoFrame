package com.sf.sfpp.user.dao.mapper;

import java.util.List;

import com.sf.sfpp.user.dao.domain.UserHistory;
import com.sf.sfpp.user.dao.dto.UserHistoryPara;

/**
 * 用户操作历史
 *
 * @author ding.yang 01139954
 * @date 2016/9/7.
 */
public interface UserHistoryMapper {
    int insert(UserHistory record);

    List<UserHistory> selectByPrimaryKey(UserHistoryPara userHistoryPara);
}
