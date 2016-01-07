package com.wdxxl.solr.example.reflection.insert;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.wdxxl.solr.client.SolrClient;
import com.wdxxl.solr.model.Article;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionInsert {
    public static final Logger LOG = LoggerFactory.getLogger(ReflectionInsert.class);

    /**
     * 实体类与SolrInputDocument转换
     */
    public static SolrInputDocument entity2SolrInputDocument(Object obj) {
        if (obj != null) {
            Class<?> cls = obj.getClass();
            Field[] filedArrays = cls.getDeclaredFields(); // 获取类中所有属性
            Method m = null;
            SolrInputDocument sid = new SolrInputDocument();
            for (Field f : filedArrays) {
                //因为如果对象序列化之后,会增加该属性,不用对该属性进行反射
                if(!f.getName().equals("serialVersionUID")){
                    try {
                        //跟进属性xx构造对应的getXx()方法
                        String dynamicGetMethod = dynamicMethodName(f.getName(), "get");
                        //调用构造的getXx()方法
                        m = cls.getMethod(dynamicGetMethod);
                        //属性名,与对应的属性值 get方法获取到的值
                        LOG.info(f.getName() + ":" + m.invoke(obj));
                        sid.addField(""+ f.getName(), m.invoke(obj));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            return sid;
        }
        LOG.warn("Object to convert is null.");
        return null;
    }

    /**
     * 将对象添加至索引
     */
    public static void addConvertBean(SolrServer server, Article lists) {
        try {
            server.add(ReflectionInsert.entity2SolrInputDocument(lists));
            server.commit(false, false);
            LOG.info("Add convert object list to index finished. ");
        } catch (Exception e) {
            LOG.error("Add convert object list to index error, " + e.getMessage(), e);
        }
    }

    /**
     * 跟进属性xx构造对应的getXx()方法
     */
    private static String dynamicMethodName(String src, String get) {
        return get + new String(src.charAt(0) + "").toUpperCase() + src.substring(1);
    }

    public static void main(String[] args) {
        Article article = new Article();
        article.setId("29");
        List<String> atlts1 = new ArrayList<String>();
        atlts1.add("千山鸟飞绝，万径人踪灭");
        atlts1.add("孤舟蓑笠翁，独钓寒江雪");
        article.setTitle(atlts1);
        article.setAuthor("江雪 柳宗元");
        addConvertBean(SolrClient.getHttpSolrServer(SolrClient.URL.Local.urlAddress), article);
    }
}
