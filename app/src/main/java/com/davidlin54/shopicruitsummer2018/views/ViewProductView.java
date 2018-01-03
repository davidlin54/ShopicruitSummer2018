package com.davidlin54.shopicruitsummer2018.views;

import com.davidlin54.shopicruitsummer2018.models.Product;
import com.davidlin54.shopicruitsummer2018.models.ProductVariant;

public interface ViewProductView {
    void updateProductView(Product product);
    void changeVariantDisplayed(ProductVariant variant);
}
