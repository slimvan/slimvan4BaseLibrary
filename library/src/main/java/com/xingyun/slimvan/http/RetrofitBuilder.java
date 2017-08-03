package com.xingyun.slimvan.http;


import android.text.TextUtils;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Retrofit对象构造器
 */
public class RetrofitBuilder {

    private static Retrofit retrofit;

    /**
     * 构造Retrofit对象
     *
     * @param clzz Api类
     * @param <T>  泛型
     * @return Retrofit对象
     */
    public static <T> T build(Class<T> clzz) {
        if (!TextUtils.isEmpty(HttpConfig.BASE_URL)) {
            retrofit = new Retrofit.Builder()
                    .client(RetrofitClient.createClient())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(new DecodeGsonConverterFactory(new Gson()))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(HttpConfig.BASE_URL)
                    .build();
        } else {
            throw new IllegalArgumentException("HttpConfig.BaseUrl is Empty!Please set BaseUrl First.");
        }
        return retrofit.create(clzz);
    }


    /**
     * 构造Retrofit对象，指定域名
     *
     * @param clzz    Api类
     * @param BaseUrl 域名
     * @param <T>     泛型
     * @return Retrofit对象
     */
    public static <T> T build(Class<T> clzz, String BaseUrl) {
        if (!TextUtils.isEmpty(BaseUrl)) {
            retrofit = new Retrofit.Builder()
                    .client(RetrofitClient.createClient())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(new DecodeGsonConverterFactory(new Gson()))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(BaseUrl)
                    .build();
        } else {
            throw new IllegalArgumentException("BaseUrl is Empty!Please set BaseUrl First.");
        }
        return retrofit.create(clzz);
    }

}