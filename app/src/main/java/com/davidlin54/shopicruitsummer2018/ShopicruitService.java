package com.davidlin54.shopicruitsummer2018;

import com.davidlin54.shopicruitsummer2018.models.Product;
import com.davidlin54.shopicruitsummer2018.models.Products;
import com.davidlin54.shopicruitsummer2018.models.SingleProduct;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ShopicruitService {
    @GET("products.json?fields=id,title,body_html,images")
    Call<Products> listProducts(@Query("page") int page, @Query("access_token") String access_token);

    @GET("products/{id}.json?fields=id,title,body_html,images,variants")
    Call<SingleProduct> getProduct(@Path("id") long id, @Query("access_token") String access_token);
}
