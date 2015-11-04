package com.wdxxl.xml.sax.product.model;


public class InStock {
    private String sku;
    private String size;
    private String color;
    private String price;

    public InStock() {
    }

    public InStock(String sku, String size, String color, String price) {
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
