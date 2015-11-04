package com.wdxxl.xml.sax.product.model;


public class ProductUrl {
    private String type;
    private String imageUrl;

    public ProductUrl() {
    }

    public ProductUrl(String type, String imageUrl) {
        this.type = type;
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
