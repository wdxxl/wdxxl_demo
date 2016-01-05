package com.wdxxl.solr.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.solr.client.solrj.beans.Field;

public class Article implements Serializable {
    private static final long serialVersionUID = 4017316764889231758L;

    @Field("id")
    private String id;
    @Field("title")
    private List<String> title;
    @Field // @Field无参数时,匹配当前字段
    private String author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
