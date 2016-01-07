package com.wdxxl.solr.test;

import com.wdxxl.solr.client.SolrClient;

import org.apache.solr.client.solrj.SolrServer;
import org.junit.Before;
import org.junit.Test;

public class SolrTest {
    private SolrServer server;

    @Before
    public void init() {
        server = SolrClient.getHttpSolrServer(SolrClient.URL.Local.urlAddress);
    }

    @Test
    public void pingSolr() {
        System.out.println("ping solr result:" + SolrClient.ping(server));
    }

}
