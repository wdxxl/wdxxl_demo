package com.wdxxl.xml.sax.product.model;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class SplitProductInStock {
    @JSONField(ordinal = 1, name = "product_id")
    private String productId;
    @JSONField(ordinal = 2, name = "retailer")
    private String retailer;
    @JSONField(ordinal = 3, name = "name")
    private String name;
    @JSONField(ordinal = 4, name = "brand")
    private String brand;
    @JSONField(ordinal = 5, name = "Price")
    private String price;
    @JSONField(ordinal = 6, name = "description")
    private String description;
    @JSONField(ordinal = 7, name = "productURL")
    private List<ProductURL> productURL;
    @JSONField(ordinal = 8, name = "imageURL")
    private String imageURL;
    @JSONField(ordinal = 9, name = "alternateImages")
    private AlternateImages alternateImages;
    @JSONField(ordinal = 10, name = "categories")
    private List<Category> categories;
    @JSONField(ordinal = 11, name = "sizes")
    private List<String> size;
    @JSONField(ordinal = 12, name = "product_color")
    private String productColor;
    @JSONField(ordinal = 13, name = "color_imageURL")
    private String colorImageURL;
    @JSONField(ordinal = 14, name = "SKU")
    private String sku;
    @JSONField(ordinal = 15, name = "instock_size")
    private String inStockSize;
    @JSONField(ordinal = 16, name = "instock_color")
    private String inStockColor;
    @JSONField(ordinal = 17, name = "instock_price")
    private String inStockPrice;
    @JSONField(ordinal = 18, name = "time")
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

    public List<ProductURL> getProductURL() {
        return productURL;
    }

    public void setProductURL(List<ProductURL> productURL) {
        this.productURL = productURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public AlternateImages getAlternateImages() {
        return alternateImages;
    }

    public void setAlternateImages(AlternateImages alternateImages) {
        this.alternateImages = alternateImages;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
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

    public String getColorImageURL() {
        return colorImageURL;
    }

    public void setColorImageURL(String colorImageURL) {
        this.colorImageURL = colorImageURL;
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
