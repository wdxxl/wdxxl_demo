package com.wdxxl.xml.sax.product.model;

import com.alibaba.fastjson.annotation.JSONField;

public class InStock {
    @JSONField(ordinal = 1, name = "SKU")
    private String sku;
    @JSONField(ordinal = 2, name = "Size")
    private String size;
    @JSONField(ordinal = 3, name = "Color")
    private String color;
    @JSONField(ordinal = 4, name = "Price")
    private String price;

    public InStock() {
        super();
    }

    public InStock(String sku, String size, String color, String price) {
        super();
        this.sku = sku;
        this.size = size;
        this.color = color;
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
