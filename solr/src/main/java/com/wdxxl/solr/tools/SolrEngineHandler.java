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

    /**
     * 根据id从索引中删除记录[测试通过]
     *
     * @param server
     * @param idName 主键名
     * @param id 主键值
     */
    public static void deleteById(SolrServer server, String idName, Object id) {
        try {
            server.deleteByQuery(idName + ":" + id.toString());
            server.commit(false, false);
            LOG.info("Delete from index by id " + id + " finished. operate param is: " + idName
                    + ":" + id.toString());
        } catch (Exception e) {
            LOG.info("Delete from index by id " + id + " error, " + e.getMessage(), e);
        }
    }

    /**
     * 根据id集合从索引中删除记录[测试通过]
     *
     * @param server
     * @param idName 主键名
     * @param ids 主键值
     */
    public static <T> void deleteByIds(SolrServer server, String idName, List<T> ids) {
        try {
            if (ids.size() > 0) {
                StringBuffer query = new StringBuffer(idName + ":" + ids.get(0));
                for (int i = 1; i < ids.size(); i++) {
                    if (null != ids.get(i)) {
                        query.append(" OR " + idName + ":" + ids.get(i));
                    }
                }
                server.deleteByQuery(query.toString());
                server.commit(false, false);
                LOG.info("Delete from index by id list " + ids + " finished.");
            } else {
                LOG.info("Delete ids list is null.");
            }
        } catch (Exception e) {
            LOG.info("Delete from index by id " + ids + " error, " + e.getMessage(), e);
        }
    }

    /**
     * 根据查询从索引中删除[测试通过]
     * @param server
     * @param query
     */
    public static void deleteByQuery(SolrServer server, String query) {
        try {
            server.deleteByQuery(query);
            server.commit(false, false);
            LOG.info("Delete from index by query string " + query + " finished.");
        } catch (Exception e) {
            LOG.info("Delete from index by query string " + query + " error, " + e.getMessage(), e);
        }
    }

    /**
     * 根据查询从索引中删除[测试通过]
     * @param server
     */
    public static void deleteAll(SolrServer server) {
        try {
            server.deleteByQuery("*:*");
            server.commit(false, false);
            LOG.info("All index delete finished.");
        } catch (Exception e) {
            LOG.info("Delete all index error." + e.getMessage(), e);
        }
    }
}
