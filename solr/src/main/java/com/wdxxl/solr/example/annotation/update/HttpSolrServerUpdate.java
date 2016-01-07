package com.wdxxl.solr.example.annotation.update;

import java.io.IOException;
import java.util.HashMap;

import com.wdxxl.solr.client.SolrClient;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class HttpSolrServerUpdate {
    @Test
    public void testUpdate() throws SolrServerException, IOException {
        String id = "1";
        String fieldValue = "春晓 孟浩然1";
        HashMap<String, Object> oper = new HashMap<String, Object>();
        oper.put("set", fieldValue);

        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", id);
        doc.addField("author", oper);

        SolrServer server = SolrClient.getHttpSolrServer(SolrClient.URL.Local.urlAddress);
        server.add(doc);
        server.commit(false, false);
    }
}
