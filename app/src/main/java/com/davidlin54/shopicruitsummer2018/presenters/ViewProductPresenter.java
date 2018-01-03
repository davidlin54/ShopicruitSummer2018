package com.davidlin54.shopicruitsummer2018.presenters;

public interface ViewProductPresenter {
    void fetchProduct(long productID);
    void onVariantSelected(int position);
}
