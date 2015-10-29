package com.wdxxl.xml.sax.product.model;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class AlternateImages {
    @JSONField(ordinal = 1, name = "ImageURL")
    private List<String> imageURL;

    public AlternateImages() {
        super();
    }

    public AlternateImages(List<String> imageURL) {
        super();
        this.imageURL = imageURL;
    }

    public List<String> getImageURL() {
        return imageURL;
    }

    public void setImageURL(List<String> imageURL) {
        this.imageURL = imageURL;
    }

    public void appendImageURL(String imageURLStr) {
        if (imageURL == null) {
            imageURL = new ArrayList<>();
        }
        imageURL.add(imageURLStr);
    }
}
