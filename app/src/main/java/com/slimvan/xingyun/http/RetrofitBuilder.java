package com.slimvan.xingyun.http;

import com.slimvan.xingyun.http.api.HttpConfig;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by lin on 2016/8/28.
 */
public class RetrofitBuilder {

    private static Retrofit retrofit;

    public static <T> T build(Class<T> clzz) {
        retrofit = new Retrofit.Builder()
                .client(RetrofitClient.createClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(HttpConfig.DOUBAN_V2)
                .build();
        return retrofit.create(clzz);
    }

    public static <T> T build2(Class<T> clzz) {
        retrofit = new Retrofit.Builder()
                .client(RetrofitClient.createClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(HttpConfig.GANK)
                .build();
        return retrofit.create(clzz);
    }

}