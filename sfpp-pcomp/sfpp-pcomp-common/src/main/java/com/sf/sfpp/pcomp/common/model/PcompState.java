package com.sf.sfpp.pcomp.common.model;

import java.util.Date;

/**
 * @author 01139940 zyt
 * @version 1.0.0
 * @date 2016年9月8日上午11:36:58
 */
public interface PcompState {
    Boolean getIsDeleted();

    Date getCreatedTime();

    Date getModifiedTime();
}
