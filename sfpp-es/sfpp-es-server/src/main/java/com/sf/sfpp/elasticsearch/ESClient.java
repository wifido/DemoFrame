package com.sf.sfpp.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.sf.sfpp.common.Constants;
import com.sf.sfpp.pcomp.common.PcompConstants;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/17
 */
public class ESClient {
    private final Client client;
    private final static Logger log = LoggerFactory.getLogger(ESClient.class);

    public ESClient(String clusterName, String hosts) throws UnknownHostException {
        Settings settings = Settings.settingsBuilder()
                .put("client.transport.sniff", true)
                .put("cluster.name", clusterName)
                .build();
        String host[] = hosts.split(",");
        TransportClient build = TransportClient.builder().settings(settings).build();
        for (int i = 0; i < host.length; i++) {
            build.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host[i].substring(0, host[i].indexOf(":"))), Integer.parseInt(host[i].substring(host[i].indexOf(":") + 1))));
        }
        client = build;
    }

    public void addOrUpdateDocument(String index, String type, String id, Object document) {
        byte[] json = JSON.toJSONBytes(document);
        IndexResponse indexResponse = client.prepareIndex(index, type, id).setSource(json).get();
    }

    public void deleteDocument(String index, String type, String id) {
        DeleteResponse deleteResponse = client.prepareDelete(index, type, id).get();
    }

    public SearchHit[] searchDocument(String[] indexes, String[] types, QueryBuilder queryBuilder) {
        SearchResponse searchResponse = client.prepareSearch(indexes).setTypes(types).setQuery(queryBuilder).execute().actionGet();
        return searchResponse.getHits().getHits();
    }

    public String[] getStringMeta(String index, String type) {
        GetMappingsResponse getMappingsResponse = client.admin().indices().prepareGetMappings(index).get();
        try {
            HashMap properties = (HashMap) getMappingsResponse.getMappings().get(index).get(type).getSourceAsMap().get("properties");
            List<String> keyLists = new LinkedList<>();
            for (Object object : properties.keySet()) {
                if (((HashMap) properties.get(object)).get("type").equals("string")) {
                    keyLists.add((String) object);
                }
            }
            return keyLists.toArray(new String[]{});
        } catch (Exception e) {
            log.warn("Caught:", e);
            return null;
        }
    }

    /**
     * 测试用
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ESClient esClient = new ESClient("hash-es", "10.202.7.184:9300,10.202.7.185:9300,10.202.7.186:9300");
        GetMappingsResponse getMappingsResponse = esClient.client.admin().indices().prepareGetMappings(Constants.PUBLIC_COMPONENT_SYSTEM).get();
        HashMap properties = (HashMap) getMappingsResponse.getMappings().get(Constants.PUBLIC_COMPONENT_SYSTEM).get(PcompConstants.PCOMP_SOFTWARE).getSourceAsMap().get("properties");
        List<String> keyLists = new LinkedList<>();
        for (Object object : properties.keySet()) {
            if (((HashMap) properties.get(object)).get("type").equals("string")) {
                keyLists.add((String) object);
            }
        }



        MultiMatchQueryBuilder termQueryBuilder = QueryBuilders.multiMatchQuery("http://10.202.7.85:8080/image/01/000/000016812d74c-b9ce-4832-9965-c7fe1222486b.png", keyLists.toArray(new String[]{}));
        QueryBuilder qb = new BoolQueryBuilder()
                .must(QueryBuilders.termQuery("isDeleted", false))
                .must(termQueryBuilder);
        qb = new QueryStringQueryBuilder("\"http://10.202.7.85:8080/image/01/000/000016812d74c-b9ce-4832-9965-c7fe1222486a.png\"");
        String[] index = {Constants.PUBLIC_COMPONENT_SYSTEM};
        String[] types = {PcompConstants.PCOMP_SOFTWARE};
        SearchHit[] searchHits = esClient.searchDocument(index, types, qb);
        System.out.println(searchHits.length);
        for (int i = 0; i < searchHits.length; i++) {
            System.out.println(searchHits[i].getScore());
            System.out.println(searchHits[i].getSourceAsString());
        }
//        types[0] = PcompConstants.PCOMP_VERSION;
//        searchHits = esClient.searchDocument(index, types, termQueryBuilder);
//        System.out.println(searchHits.length);
//        for (int i = 0; i < searchHits.length ; i++) {
//            System.out.println(searchHits[i].getScore());
//            System.out.println(searchHits[i].getSourceAsString());
//        }
    }
}
