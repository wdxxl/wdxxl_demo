package com.wdxxl.xml.sax.product.model;

import com.alibaba.fastjson.annotation.JSONField;

public class Color {
    @JSONField(ordinal = 1, name = "Name")
    private String name;
    @JSONField(ordinal = 1, name = "ImageURL")
    private String imageURL;

    public Color() {
        super();
    }

    public Color(String name, String imageURL) {
        super();
        this.name = name;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
