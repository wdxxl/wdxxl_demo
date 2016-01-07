package com.wdxxl.solr.example.annotation.query;

import com.wdxxl.solr.client.SolrClient;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class NormalQuery {

    @Test
    public void testQueryWithFilter() throws SolrServerException {
        SolrServer server = SolrClient.getHttpSolrServer(SolrClient.URL.Local.urlAddress);
        SolrQuery query = new SolrQuery();
        query.setQuery("不");
        query.addFilterQuery("id:1", "author:春晓");
        query.setFields("id", "title", "author", "author_s", "_version_");
        query.setStart(0);

        QueryResponse response = server.query(query);
        SolrDocumentList results = response.getResults();
        for (int i = 0; i < results.size(); ++i) {
            System.out.println(results.get(i));
        }
    }

    @Test
    public void testQueryWithSort() throws SolrServerException {
        SolrServer server = SolrClient.getHttpSolrServer(SolrClient.URL.Local.urlAddress);
        SolrQuery query = new SolrQuery();
        query.setQuery("不");
        query.setSort("id", SolrQuery.ORDER.desc);
        query.setFields("id", "title", "author", "author_s", "_version_");
        query.setStart(0);

        QueryResponse response = server.query(query);
        SolrDocumentList results = response.getResults();
        for (int i = 0; i < results.size(); ++i) {
            System.out.println(results.get(i));
        }
    }
}
