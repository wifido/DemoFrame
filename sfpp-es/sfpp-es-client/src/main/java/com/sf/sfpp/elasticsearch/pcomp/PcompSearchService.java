package com.sf.sfpp.elasticsearch.pcomp;

import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.common.model.PcompVersion;

import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/17
 */
public interface PcompSearchService {
    List getAllRelated(String keyword, SortRule sortRule);

    List<PcompSoftware> getSoftwareRelated(String keyword, SortRule sortRule);

    List<PcompVersion> getVersionRelated(String keyword, SortRule sortRule);
}
