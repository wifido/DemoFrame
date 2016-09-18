package com.sf.sfpp.user.dao.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01139954 on 2016/9/14.
 */
public class UserHistoryPara {
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * targetKind,只有pcomp_kind/pcomp_software/pcomp_title/pcomp_version四种情况,如果为空，则四种情况都取出
     */
    private List<String> targetKinds = new ArrayList<String>();

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<String> getTargetKinds() {
        return targetKinds;
    }

    public void setTargetKinds(List<String> targetKinds) {
        this.targetKinds = targetKinds;
    }
}
