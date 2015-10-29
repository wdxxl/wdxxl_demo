package com.wdxxl.xml.sax.product.model;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class Product {
    @JSONField(ordinal = 1, name = "Retailer")
    private String retailer;
    @JSONField(ordinal = 2, name = "ProductId")
    private String productId;
    @JSONField(ordinal = 3, name = "Name")
    private String name;
    @JSONField(ordinal = 4, name = "Brand")
    private String brand;
    @JSONField(ordinal = 5, name = "Price")
    private String price;
    @JSONField(ordinal = 6, name = "Description")
    private String description;
    @JSONField(ordinal = 7, name = "ProductURL")
    private List<ProductURL> productURL;
    @JSONField(ordinal = 8, name = "ImageURL")
    private String imageURL;
    @JSONField(ordinal = 9, name = "AlternateImages")
    private AlternateImages alternateImages;
    @JSONField(ordinal = 10, name = "Categories")
    private List<Category> categories;
    @JSONField(ordinal = 11, name = "Sizes")
    private List<String> size;
    @JSONField(ordinal = 12, name = "Color")
    private List<Color> color;
    @JSONField(ordinal = 13, name = "InStock")
    private List<InStock> inStock;
    @JSONField(ordinal = 14, name = "Time")
    private String time;

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public void appendProductURL(ProductURL productURL) {
        if (this.productURL == null) {
            this.productURL = new ArrayList<>();
        }
        this.productURL.add(productURL);
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

    public void appendCategory(Category category) {
        if (categories == null) {
            categories = new ArrayList<>();
        }
        categories.add(category);
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

    public void appendSize(String size) {
        if (this.size == null) {
            this.size = new ArrayList<>();
        }
        this.size.add(size);
    }

    public List<Color> getColor() {
        return color;
    }

    public void setColor(List<Color> color) {
        this.color = color;
    }

    public void appendColor(Color color) {
        if (this.color == null) {
            this.color = new ArrayList<>();
        }
        this.color.add(color);
    }

    public String retrieveColor(String colorName) {
        if (colorName != null && color != null) {
            for (int i = 0; i < color.size(); i++) {
                if (colorName.equals(color.get(i).getName())) {
                    return color.get(i).getImageURL();
                }
            }
        }
        return null;
    }

    public List<InStock> getInStock() {
        return inStock;
    }

    public void setInStock(List<InStock> inStock) {
        this.inStock = inStock;
    }

    public void appendInStock(InStock inStock) {
        if (this.inStock == null) {
            this.inStock = new ArrayList<>();
        }
        this.inStock.add(inStock);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
