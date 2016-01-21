package com.wdxxl.solr.client;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

public class SolrClient {
    public enum URL {
        Local("http://localhost:8983/solr/tang-poetry");

        public final String urlAddress;

        private URL(String urlAddress) {
            this.urlAddress = urlAddress;
        }
    }
    private static Map<String, SolrServer> solrServerMap =
            Collections.synchronizedMap(new HashMap<String, SolrServer>());

    public static SolrServer getHttpSolrServer(final String solrUrl) {
        if (!solrServerMap.containsKey(solrUrl)) {
            SolrServer solrServer = new HttpSolrServer(solrUrl);
            if (solrServer != null) {
                solrServerMap.put(solrUrl, solrServer);
            }
        }
        return solrServerMap.get(solrUrl);
    }

    public static String ping(SolrServer solrServer){
        try {
            return solrServer.ping().getResponse().toString();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
