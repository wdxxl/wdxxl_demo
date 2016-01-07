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
        String solrUrl = "http://localhost:8983/solr";
        SolrServer server = SolrClient.getHttpSolrServer(solrUrl);
        SolrEngineHandler.deleteById(server, "id",
                new String("9589e70b-9ce6-43a7-aa9c-835e2be7765f"));
    }

    @Test
    public void testDeleteByIds() {
        String solrUrl = "http://localhost:8983/solr";
        SolrServer server = SolrClient.getHttpSolrServer(solrUrl);
        List<String> ids = new ArrayList<>();
        ids.add("354e718f-845b-4b20-a1a3-ccd6aa8f774e");
        ids.add("c887b621-a3e0-4824-9632-3fda37b3b734");
        SolrEngineHandler.deleteByIds(server, "id", ids);
    }

    @Test
    public void testDeleteByQuery() {
        String solrUrl = "http://localhost:8983/solr";
        SolrServer server = SolrClient.getHttpSolrServer(solrUrl);
        String query = "author:\"凉州词 王之涣\"";
        SolrEngineHandler.deleteByQuery(server, query);
    }

    @Test
    public void testDeleteAll() {
        String solrUrl = "http://localhost:8983/solr";
        SolrServer server = SolrClient.getHttpSolrServer(solrUrl);
        SolrEngineHandler.deleteAll(server);
    }

    // TODO need one more reflection demo
}
