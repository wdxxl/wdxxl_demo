package com.wdxxl.solr.example.annotation.highlight;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wdxxl.solr.client.SolrClient;
import com.wdxxl.solr.model.Article;
import com.wdxxl.solr.tools.Page;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class NormalHighLight {

    public static <T extends Article> Page<T> QueryHighLight(SolrServer server, String queryString,
            int pageNum, int pageSize, Class<T> clazz) {
        SolrQuery query = new SolrQuery();
        query.setQuery(queryString);
        query.setHighlight(true);// 开启高亮功能
        query.addHighlightField("title");// 高亮字段
        query.addHighlightField("author");
        query.setHighlightSimplePre("<font color='red'>");// 渲染标签
        query.setHighlightSimplePost("</font>");// 渲染标签
        query.setStart((pageNum - 1) * pageSize);
        query.setRows(pageSize);
        QueryResponse response = null;
        try {
            response = server.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
            return null;
        }
        // 查询结果集
        SolrDocumentList documents = response.getResults();
        List<T> items = new ArrayList<T>();
        Map<String, Map<String, List<String>>> highlightMap = response.getHighlighting();
        for (SolrDocument solrDocument : documents) {
            Article at = new Article();
            String tmpId = solrDocument.getFieldValue("id").toString();
            at.setId(tmpId);
            List<String> authorList = highlightMap.get(tmpId).get("author");
            if (authorList != null && authorList.size() > 0) {
                at.setAuthor(authorList.get(0));
            } else {
                // 获取并设置高亮的字段author
                at.setAuthor(solrDocument.getFieldValue("author").toString());
            }
            List<String> titleList = highlightMap.get(tmpId).get("title");
            if (titleList != null && titleList.size() > 0) {
                at.setTitle(titleList);
            } else {
                // 获取并设置高亮的字段title
                List<String> noHighLightTitle = new ArrayList<String>();
                noHighLightTitle.add(solrDocument.getFieldValue("title").toString());
                at.setTitle(noHighLightTitle);
            }
            items.add((T) at);
        }
        // 查询到的记录总数
        long totalRow = Long.valueOf(response.getResults().getNumFound()).intValue();
        // 填充page对象
        return new Page<T>(pageNum, pageSize, totalRow, items);
    }

    @Test
    public void testQueryHihhLight() {
        SolrServer server = SolrClient.getHttpSolrServer(SolrClient.URL.Local.urlAddress);
        Page<Article> page = NormalHighLight.QueryHighLight(server,
                "author:凉州词 OR title:春眠 OR title:鸟 OR title:夜来 OR title:泉眼 OR title:细流 OR title:晴柔",
                1, 10, Article.class);
        for (Article article : page.getDatas()) {
            System.out.println(article.toString());
        }
    }
}
