package com.sf.sfpp.elasticsearch.pcomp;

import com.sf.sfpp.elasticsearch.ESClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/17
 */
@Service
public class PcompSearchServiceImpl implements PcompSearchService {
    @Autowired
    private ESClient esClient;

    @Override
    public List getAllRelated(String keyword) {

        return null;
    }
}
