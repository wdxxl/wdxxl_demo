package com.wdxxl.solr.example.annotation.query;

import com.wdxxl.solr.client.SolrClient;
import com.wdxxl.solr.model.Article;
import com.wdxxl.solr.tools.Page;
import com.wdxxl.solr.tools.SolrEngineHandler;

import org.apache.solr.client.solrj.SolrServer;
import org.junit.Test;

public class AnnotationQuery {
    @Test
    public void testQueryBeanReturnMore() {
        SolrServer server = SolrClient.getHttpSolrServer(SolrClient.URL.Local.urlAddress);
        Page<Article> page = SolrEngineHandler.queryBean(server, "不", 1, 10, Article.class);
        for (Article article : page.getDatas()) {
            System.out.println(article.toString());
        }
    }

    @Test
    public void testQueryBeanReturnOne() {
        SolrServer server = SolrClient.getHttpSolrServer(SolrClient.URL.Local.urlAddress);
        Page<Article> page =
                SolrEngineHandler.queryBean(server, "author:'春晓 孟浩然'", 1, 10, Article.class);
        for (Article article : page.getDatas()) {
            System.out.println(article.toString());
        }
    }

    @Test
    public void testQueryBinderBean() {
        SolrServer server = SolrClient.getHttpSolrServer(SolrClient.URL.Local.urlAddress);
        Page<Article> page = SolrEngineHandler.queryBinderBean(server, "王之涣", 1, 10, Article.class);
        for (Article article : page.getDatas()) {
            System.out.println(article.toString());
        }
    }
}
