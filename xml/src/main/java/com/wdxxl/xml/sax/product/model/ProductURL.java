package com.wdxxl.xml.sax.product.model;

import com.alibaba.fastjson.annotation.JSONField;

public class ProductURL {
    @JSONField(ordinal = 1, name = "Type")
    private String type;

    @JSONField(ordinal = 2, name = "ImageURL")
    private String imageURL;

    public ProductURL() {
        super();
    }

    public ProductURL(String type, String imageURL) {
        super();
        this.type = type;
        this.imageURL = imageURL;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
