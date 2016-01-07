package com.wdxxl.solr.example.annotation.insert;

import java.util.ArrayList;
import java.util.List;

import com.wdxxl.solr.client.SolrClient;
import com.wdxxl.solr.model.Article;
import com.wdxxl.solr.tools.SolrEngineHandler;

import org.apache.solr.client.solrj.SolrServer;
import org.junit.Test;

public class AnnotationInsert {
    @Test
    public void testAddBean() {
        SolrServer server = SolrClient.getHttpSolrServer(SolrClient.URL.Local.urlAddress);
        Article article = new Article();
        article.setId("1");
        List<String> title = new ArrayList<String>();
        title.add("春眠不觉晓,处处闻啼鸟");
        title.add("夜来风雨声,花落知多少");
        article.setTitle(title);
        article.setAuthor("春晓 孟浩然");
        SolrEngineHandler.addBean(server, article);
    }

    @Test
    public void testAddBeans() {
        SolrServer server = SolrClient.getHttpSolrServer(SolrClient.URL.Local.urlAddress);

        Article article1 = new Article();
        article1.setId("19");
        List<String> title1 = new ArrayList<String>();
        title1.add("泉眼无声惜细流，树阴照水爱晴柔");
        title1.add("小荷才露尖尖角，早有蜻蜓立上头");
        article1.setTitle(title1);
        article1.setAuthor("小池 杨万里");

        Article article2 = new Article();
        article2.setId("8");
        List<String> title2 = new ArrayList<String>();
        title2.add("黄河远上白云间，一片孤城万仞山");
        title2.add("羌笛何须怨杨柳，春风不度玉门关");
        article2.setTitle(title2);
        article2.setAuthor("凉州词 王之涣");

        List<Article> articleList = new ArrayList<>();
        articleList.add(article1);
        articleList.add(article2);
        SolrEngineHandler.addBeans(server, articleList);
    }
}
