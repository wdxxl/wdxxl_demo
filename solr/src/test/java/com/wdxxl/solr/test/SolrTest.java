package com.wdxxl.solr.test;

import com.wdxxl.solr.client.SolrClient;

import org.apache.solr.client.solrj.SolrServer;
import org.junit.Before;
import org.junit.Test;

public class SolrTest {
    private SolrServer server;

    @Before
    public void init() {
        String solrUrl = "http://localhost:8983/solr";
        server = SolrClient.getHttpSolrServer(solrUrl);
    }

    @Test
    public void pingSolr() {
        System.out.println("ping solr result:" + SolrClient.ping(server));
    }

}
