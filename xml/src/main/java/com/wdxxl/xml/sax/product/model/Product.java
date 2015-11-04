package com.wdxxl.xml.sax.product.model;

import java.util.ArrayList;
import java.util.List;


public class Product {
    private String retailer;
    private String productId;
    private String name;
    private String brand;
    private String price;
    private String description;
    private List<ProductUrl> productUrl;
    private String imageUrl;
    private List<String> alternateImages;
    private List<String> categories;
    private List<String> size;
    private List<Color> color;
    private List<InStock> inStock;
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

    public List<ProductUrl> getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(List<ProductUrl> productUrl) {
        this.productUrl = productUrl;
    }

    public void appendProductUrl(ProductUrl productUrl) {
        if (this.productUrl == null) {
            this.productUrl = new ArrayList<>();
        }
        this.productUrl.add(productUrl);
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

    public void appendAlternateImages(List<String> imageUrls) {
        if (alternateImages == null) {
            alternateImages = new ArrayList<>();
        }
        alternateImages.addAll(imageUrls);
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void appendCategory(List<String> categories) {
        if (this.categories == null) {
            this.categories = new ArrayList<>();
        }
        this.categories.addAll(categories);
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
                    return color.get(i).getImageUrl();
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
