package com.wdxxl.solr.example.insert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.wdxxl.solr.client.SolrClient;
import com.wdxxl.solr.model.Article;

import org.apache.solr.client.solrj.SolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationEntityConvert {
    public static final Logger LOG = LoggerFactory.getLogger(ReflectionEntityConvert.class);
    /**
     * 将对象添加至索引
     */
    public static void addBean(SolrServer server, Article article) {
        try {
            server.addBean(article);
            server.commit(false, false);
            LOG.info("Add object to index finished. ");
        } catch (Exception e) {
            LOG.error("Add object to index error, " + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        Article article1 = new Article();
        article1.setId(UUID.randomUUID().toString());
        List<String> title = new ArrayList<String>();
        title.add("谁言别后终无悔，寒月清宵绮梦回");
        title.add("深知身在情长在，前尘不共彩云飞");
        article1.setTitle(title);
        article1.setAuthor("苏若年");
        String solrUrl = "http://localhost:8983/solr";
        addBean(SolrClient.getHttpSolrServer(solrUrl), article1);
    }
}
