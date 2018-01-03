package com.davidlin54.shopicruitsummer2018.presenters;

import com.davidlin54.shopicruitsummer2018.ShopicruitApplication;
import com.davidlin54.shopicruitsummer2018.models.Product;
import com.davidlin54.shopicruitsummer2018.models.SingleProduct;
import com.davidlin54.shopicruitsummer2018.views.ViewProductView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProductPresenterImpl implements ViewProductPresenter {

    private ViewProductView mView;
    private Product mProduct;

    public ViewProductPresenterImpl(ViewProductView view) {
        mView = view;
    }

    @Override
    public void fetchProduct(long productID) {
        Call<SingleProduct> call = ShopicruitApplication.mService.getProduct(productID, ShopicruitApplication.ACCESS_TOKEN);
        call.enqueue(new Callback<SingleProduct>() {
            @Override
            public void onResponse(Call<SingleProduct> call, Response<SingleProduct> response) {
                Product product = response.body().getProduct();
                mProduct = product;
                mView.updateProductView(product);
            }

            @Override
            public void onFailure(Call<SingleProduct> call, Throwable t) {

            }
        });
    }

    @Override
    public void onVariantSelected(int position) {
        mView.changeVariantDisplayed(mProduct.getVariants().get(position));
    }
}
