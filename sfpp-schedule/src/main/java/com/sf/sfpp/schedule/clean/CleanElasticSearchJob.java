package com.sf.sfpp.schedule.clean;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.utils.ExceptionUtils;
import com.sf.sfpp.pcomp.common.PcompConstants;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/31
 */
public class CleanElasticSearchJob {
    private final Client client;
    private final static Logger log = LoggerFactory.getLogger(CleanElasticSearchJob.class);
    private final static String[] indexes = {Constants.PUBLIC_COMPONENT_SYSTEM};
    private final String types[]= {PcompConstants.PCOMP_KIND,PcompConstants.PCOMP_SOFTWARE,PcompConstants.PCOMP_TITLE,PcompConstants.PCOMP_VERSION};

    public CleanElasticSearchJob(String clusterName, String hosts) throws UnknownHostException {
        for (int i = 0; i < this.types.length; i++) {
            this.types[i] = this.types[i].trim();
        }
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

    public void cleanDocuments() {
        QueryBuilder qb = new BoolQueryBuilder()
                .must(QueryBuilders.termQuery("isDeleted", true));
        SearchResponse searchResponse = client.prepareSearch(indexes).setTypes(types).setQuery(qb).execute().actionGet();
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (int i = 0; i < hits.length; i++) {
            try {
                client.prepareDelete(hits[i].getIndex(), hits[i].getType(), hits[i].getId()).execute().actionGet();
            }catch (Exception e){
                log.warn(ExceptionUtils.getStackTrace(e));
            }
        }

    }
}
