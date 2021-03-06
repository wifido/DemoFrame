package com.sf.sfpp.user.manager.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.idgen.IDGenerator;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.user.dao.domain.UserHistory;
import com.sf.sfpp.user.dao.dto.UserHistoryPara;
import com.sf.sfpp.user.dao.mapper.UserHistoryMapper;
import com.sf.sfpp.user.manager.UserHistoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * @author ding.yang 01139954
 * @date 2016/9/7.
 */
@Component
public class UserHistoryManagerImpl implements UserHistoryManager {

    @Autowired
    private UserHistoryMapper userHistoryMapper;

    @Override
    public Page<UserHistory> getUserHistorysByUserId(Integer UserId, int pageNumber, String... targetKinds) {
        if (pageNumber != Constants.ALL_PAGE_NUMBER) {
            PageHelper.startPage(pageNumber, PcompConstants.NUMBER_PER_PAGE);
        } else {
            PageHelper.startPage(1, Integer.MAX_VALUE);
        }
        UserHistoryPara userHistoryPara = new UserHistoryPara();
        userHistoryPara.setUserId(UserId);
        if(targetKinds != null){
            for(String targetKind : targetKinds){
                userHistoryPara.getTargetKinds().add(targetKind);
            }
        }
        List<UserHistory> userHistorys = userHistoryMapper.selectByPrimaryKey(userHistoryPara);
        return (Page<UserHistory>) userHistorys;
    }

    @Override
    public boolean addUserHistory(UserHistory userHistory) {
        userHistory.setId(IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM));
        boolean b = userHistoryMapper.insert(userHistory) > 0;
        return b;
    }


}
