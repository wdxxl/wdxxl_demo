package com.wdxxl.solr.example.annotation.delete;

import java.util.ArrayList;
import java.util.List;

import com.wdxxl.solr.client.SolrClient;
import com.wdxxl.solr.tools.SolrEngineHandler;

import org.apache.solr.client.solrj.SolrServer;
import org.junit.Test;

public class NormalDelete {
    @Test
    public void testDeleteById() {
        SolrServer server = SolrClient.getHttpSolrServer(SolrClient.URL.Local.urlAddress);
        SolrEngineHandler.deleteById(server, "id", "1");
    }

    @Test
    public void testDeleteByIds() {
        SolrServer server = SolrClient.getHttpSolrServer(SolrClient.URL.Local.urlAddress);
        List<String> ids = new ArrayList<>();
        ids.add("8");
        ids.add("19");
        SolrEngineHandler.deleteByIds(server, "id", ids);
    }

    @Test
    public void testDeleteByQuery() {
        SolrServer server = SolrClient.getHttpSolrServer(SolrClient.URL.Local.urlAddress);
        String query = "author:\"凉州词 王之涣\"";
        SolrEngineHandler.deleteByQuery(server, query);
    }

    @Test
    public void testDeleteAll() {
        SolrServer server = SolrClient.getHttpSolrServer(SolrClient.URL.Local.urlAddress);
        SolrEngineHandler.deleteAll(server);
    }

    // TODO need one more reflection demo
}
