package com.davidlin54.shopicruitsummer2018.models;

import java.util.List;

public class Product {
    private long id;
    private String title;
    private String body_html;
    private List<Image> images;
    private List<ProductVariant> variants;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return body_html;
    }

    public List<Image> getImages() {
        return images;
    }

    public List<ProductVariant> getVariants() {
        return variants;
    }
}
