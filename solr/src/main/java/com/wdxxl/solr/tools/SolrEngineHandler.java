package com.wdxxl.solr.tools;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolrEngineHandler {
    public static final Logger LOG = LoggerFactory.getLogger(SolrEngineHandler.class);
    /**
     * 将单个对象添加至索引
     * @param server
     * @param object solr机制进行对象转换
     */
    public static void addBean(SolrServer server, Object object) {
        try {
            server.addBean(object);
            server.commit(false, false);
            LOG.info("Add object to index finished.");
        } catch (Exception e) {
            LOG.error("Add object to index error, " + e.getMessage(), e);
            e.printStackTrace();
        }
    }
    /**
     * 添加集合对象至索引 [测试通过]
     * @param <T>
     * @param server
     * @param lists 使用solr内部转换机制
     */
    public static <T> void addBeans(SolrServer server, List<T> lists) {
        try {
            server.addBeans(lists);
            server.commit(false, false);
            LOG.info("Add object list to index finished. ");
        } catch (Exception e) {
            LOG.error("Add object list to index finished. ");
        }
    }
    /**
     * 根据关键字查询 [测试通过 - 使用 solr内部转换机制]
     *
     * @param <T>
     * @param server solr客户端
     * @param keyword 搜索关键字
     * @param pageNum 当前页码
     * @param pageSize 每页显示的大小
     * @param clzz 对象类型
     * @return
     */
    public static <T> Page<T> queryBean(SolrServer server, String keyword, int pageNum,
            int pageSize, Class<T> clzz) {
        SolrQuery query = new SolrQuery();
        query.setQuery(keyword);
        query.setStart((pageNum - 1) * pageSize);
        query.setRows(pageSize);
        QueryResponse response = null;
        try {
            response = server.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
            return null;
        }
        // 查询到的记录总数
        long totalRow = Long.valueOf(response.getResults().getNumFound()).intValue();
        // 查询结果集
        List<T> items = response.getBeans(clzz);
        // 填充page对象
        return new Page<T>(pageNum, pageSize, totalRow, items);
    }

    /**
     * 根据关键字查询 [测试通过 - 使用 solr内部转换机制]
     * @param <T>
     * @param server solr客户端
     * @param keyword 搜索关键字
     * @param pageNum 当前页码
     * @param pageSize 每页显示的大小
     * @param clzz 对象类型
     * @return
     */
    public static <T> Page<T> queryBinderBean(SolrServer server, String keyword, int pageNum,
            int pageSize, Class<T> clzz) {
        SolrQuery query = new SolrQuery();
        query.setQuery(keyword);
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
        // 使用DocumentObjectBinder获取
        List<T> items = server.getBinder().getBeans(clzz, documents);
        // 查询到的记录总数
        long totalRow = Long.valueOf(response.getResults().getNumFound()).intValue();
        // 填充page对象
        return new Page<T>(pageNum, pageSize, totalRow, items);
    }
}
