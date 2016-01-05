package com.wdxxl.solr.example.annotation.query;

import com.wdxxl.solr.client.SolrClient;
import com.wdxxl.solr.model.Article;
import com.wdxxl.solr.tools.Page;
import com.wdxxl.solr.tools.SolrEngineHandler;

import org.apache.solr.client.solrj.SolrServer;
import org.junit.Test;

public class AnnotationQuery {
    @Test
    public void testQueryBeanReturn2() {
        String solrUrl = "http://localhost:8983/solr";
        SolrServer server = SolrClient.getHttpSolrServer(solrUrl);
        Page<Article> page = SolrEngineHandler.queryBean(server, "柳梦璃", 1, 10, Article.class);
        for (Article article : page.getDatas()) {
            System.out.println(article.toString());
        }
    }

    @Test
    public void testQueryBeanReturn1() {
        String solrUrl = "http://localhost:8983/solr";
        SolrServer server = SolrClient.getHttpSolrServer(solrUrl);
        Page<Article> page =
                SolrEngineHandler.queryBean(server, "author:柳梦璃", 1, 10, Article.class);
        for (Article article : page.getDatas()) {
            System.out.println(article.toString());
        }
    }

    @Test
    public void testQueryBinderBean() {
        String solrUrl = "http://localhost:8983/solr";
        SolrServer server = SolrClient.getHttpSolrServer(solrUrl);
        Page<Article> page = SolrEngineHandler.queryBinderBean(server, "苏若年", 1, 10, Article.class);
        for (Article article : page.getDatas()) {
            System.out.println(article.toString());
        }
    }
}
