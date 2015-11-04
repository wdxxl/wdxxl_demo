package com.wdxxl.xml.sax.product.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SplitProductInStock {
    @JsonProperty("product_id")
    private String productId;
    private String retailer;
    private String name;
    private String brand;
    private String price;
    private String description;
    private List<ProductUrl> productUrl;
    private String imageUrl;
    private List<String> alternateImages;
    private List<String> categories;
    private List<String> size;
    private String productColor;
    private String colorImageUrl;
    private String sku;
    private String inStockSize;
    private String inStockColor;
    private String inStockPrice;
    private String time;

    public SplitProductInStock() {
        super();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductUrl> getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(List<ProductUrl> productUrl) {
        this.productUrl = productUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getAlternateImages() {
        return alternateImages;
    }

    public void setAlternateImages(List<String> alternateImages) {
        this.alternateImages = alternateImages;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getColorImageUrl() {
        return colorImageUrl;
    }

    public void setColorImageUrl(String colorImageUrl) {
        this.colorImageUrl = colorImageUrl;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getInStockSize() {
        return inStockSize;
    }

    public void setInStockSize(String inStockSize) {
        this.inStockSize = inStockSize;
    }

    public String getInStockColor() {
        return inStockColor;
    }

    public void setInStockColor(String inStockColor) {
        this.inStockColor = inStockColor;
    }

    public String getInStockPrice() {
        return inStockPrice;
    }

    public void setInStockPrice(String inStockPrice) {
        this.inStockPrice = inStockPrice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
