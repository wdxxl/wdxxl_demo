package com.wdxxl.xml.sax.product.model;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class Category {
    @JSONField(ordinal = 1, name = "Part")
    private List<String> part;

    public Category() {
        super();
    }

    public Category(List<String> part) {
        super();
        this.part = part;
    }

    public List<String> getPart() {
        return part;
    }

    public void setPart(List<String> part) {
        this.part = part;
    }

    public void appendPart(String partStr) {
        if (part == null) {
            part = new ArrayList<>();
        }
        part.add(partStr);
    }

}
