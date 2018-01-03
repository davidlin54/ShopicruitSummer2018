package com.davidlin54.shopicruitsummer2018;

import android.app.Application;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShopicruitApplication extends Application {
    public static ShopicruitService mService;
    public final static String API_BASE_URL = "https://shopicruit.myshopify.com/admin/";
    public final static String ACCESS_TOKEN = "c32313df0d0ef512ca64d5b336a0d7c6";

    public ShopicruitApplication() {
        super();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();

        mService = retrofit.create(ShopicruitService.class);
    }
}
