package com.davidlin54.shopicruitsummer2018.presenters;

import com.davidlin54.shopicruitsummer2018.ShopicruitApplication;
import com.davidlin54.shopicruitsummer2018.models.Products;
import com.davidlin54.shopicruitsummer2018.views.ProductListView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListPresenterImpl implements ProductListPresenter {

    private ProductListView mView;

    public ProductListPresenterImpl(ProductListView view) {
        mView = view;
    }

    @Override
    public void fetchProducts() {
        Call<Products> call = ShopicruitApplication.mService.listProducts(1, ShopicruitApplication.ACCESS_TOKEN);
        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                Products products = response.body();
                mView.updateProductList(products);
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                fetchProducts();
            }
        });
    }
}
