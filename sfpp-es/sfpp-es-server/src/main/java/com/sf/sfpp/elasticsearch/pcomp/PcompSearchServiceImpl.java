package com.sf.sfpp.elasticsearch.pcomp;

import com.alibaba.fastjson.JSON;
import com.sf.sfpp.common.Constants;
import com.sf.sfpp.elasticsearch.ESClient;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.common.model.PcompVersion;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

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
    public List getAllRelated(String keyword, String sortRule) throws PcompException {
        try {
            List result = new LinkedList();
            TreeMap<Double, List<String>> sorted = getSorted(keyword, PcompConstants.PCOMP_SOFTWARE, sortRule);
            TreeMap<Double, List> sortedResult = new TreeMap<>();
            if (sorted != null) {
                for (Double key : sorted.keySet()) {
                    for (String json : sorted.get(key)) {
                        List list = sortedResult.get(key);
                        if (list == null) {
                            list = new ArrayList();
                            sortedResult.put(key, list);
                        }
                        list.add(JSON.parseObject(json, PcompSoftware.class));
                    }
                }
            }

            TreeMap<Double, List<String>> sorted1 = getSorted(keyword, PcompConstants.PCOMP_VERSION, sortRule);
            TreeMap<Double, List> sortedResult1 = new TreeMap<>();
            if (sorted1 != null) {
                for (Double key : sorted1.keySet()) {
                    for (String json : sorted1.get(key)) {
                        List list = sortedResult1.get(key);
                        if (list == null) {
                            list = new ArrayList();
                            sortedResult1.put(key, list);
                        }
                        list.add(JSON.parseObject(json, PcompVersion.class));
                    }
                }
            }
            sortedResult.putAll(sortedResult1);
            for (List list : sortedResult.values()) {
                for (Object o : list) {
                    result.add(o);
                }
            }
            return result;
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    private SearchHit[] getSearchHits(String keyword, String type) {
        String[] stringMeta = esClient.getStringMeta(Constants.PUBLIC_COMPONENT_SYSTEM, type);
        if (stringMeta == null) {
            return null;
        }
        MultiMatchQueryBuilder termQueryBuilder = QueryBuilders.multiMatchQuery(keyword, stringMeta);
        QueryBuilder qb = new BoolQueryBuilder()
                .must(QueryBuilders.termQuery("isDeleted", false))
                .must(termQueryBuilder);
        String[] index = {Constants.PUBLIC_COMPONENT_SYSTEM};
        String[] types = {type};
        return esClient.searchDocument(index, types, qb);
    }

    private TreeMap<Double, List<String>> getSorted(String keyword, String type, String sortRule) {
        SearchHit[] searchHit = getSearchHits(keyword, type);
        if (searchHit == null) {
            return null;
        }
        TreeMap<Double, List<String>> result = new TreeMap<>();
        for (int i = 0; i < searchHit.length; i++) {
            switch (sortRule) {
                case SortRule.BY_CORRELATION_DESC:
                    List<String> stringList = result.get((double) searchHit[i].getScore());
                    if (stringList == null) {
                        stringList = new LinkedList<>();
                        result.put((double) searchHit[i].getScore(), stringList);
                    }
                    stringList.add(searchHit[i].getSourceAsString());
                    break;
                case SortRule.BY_CORRELATION:
                    stringList = result.get((double) searchHit[i].getScore());
                    if (stringList == null) {
                        stringList = new LinkedList<>();
                        result.put(0.0 - searchHit[i].getScore(), stringList);
                    }
                    stringList.add(searchHit[i].getSourceAsString());
                    break;
                case SortRule.BY_MODIFIED_TIME_DESC:
                    long modifiedTime = (Long) JSON.parseObject(searchHit[i].getSourceAsString()).get("modifiedTime");
                    stringList = result.get((double) modifiedTime);
                    if (stringList == null) {
                        stringList = new LinkedList<>();
                        result.put((double) modifiedTime, stringList);
                    }
                    stringList.add(searchHit[i].getSourceAsString());
                    break;
                case SortRule.BY_MODIFIED_TIME:
                    modifiedTime = (Long) JSON.parseObject(searchHit[i].getSourceAsString()).get("modifiedTime");
                    stringList = result.get(0 - (double) modifiedTime);
                    if (stringList == null) {
                        stringList = new LinkedList<>();
                        result.put((0.0 - (double) modifiedTime), stringList);
                    }
                    stringList.add(searchHit[i].getSourceAsString());
                    break;
            }
        }
        return result;
    }

    @Override
    public List<PcompVersion> getVersionRelated(String keyword, String sortRule) throws PcompException {
        try {
            TreeMap<Double, List<String>> sorted = getSorted(keyword, PcompConstants.PCOMP_VERSION, sortRule);
            if (sorted == null) {
                return null;
            }
            LinkedList<PcompVersion> pcompVersions = new LinkedList<>();
            for (List<String> jsons : sorted.values()) {
                for (String json : jsons) {
                    pcompVersions.add(JSON.parseObject(json, PcompVersion.class));
                }
            }
            return pcompVersions;
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

    @Override
    public List<PcompSoftware> getSoftwareRelated(String keyword, String sortRule) throws PcompException {
        try {
            TreeMap<Double, List<String>> sorted = getSorted(keyword, PcompConstants.PCOMP_SOFTWARE, sortRule);
            if (sorted == null) {
                return null;
            }
            LinkedList<PcompSoftware> pcompSoftwares = new LinkedList<>();
            for (List<String> jsons : sorted.values()) {
                for (String json : jsons) {
                    pcompSoftwares.add(JSON.parseObject(json, PcompSoftware.class));
                }
            }
            return pcompSoftwares;
        } catch (Exception e) {
            throw new PcompException(e);
        }
    }

}
