package com.slimvan.xingyun.http;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lin on 2016/8/28.
 */
public class RetrofitBuilder {

    private static Retrofit retrofit;

    public static <T> T build( Class<T> clzz) {
        if (retrofit==null){
            retrofit = new  Retrofit.Builder()
                    .client(RetrofitClient.createClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(HttpConfig.getBaseUrl())
                    .build();
        }
        return retrofit.create(clzz);
    }

}