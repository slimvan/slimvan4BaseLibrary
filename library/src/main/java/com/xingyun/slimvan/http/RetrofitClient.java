package com.xingyun.slimvan.http;

import com.xingyun.slimvan.application.BaseLibrary;
import com.xingyun.slimvan.util.LogUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Retrofit构造器
 */
public class RetrofitClient {


    /**
     * create client
     *
     * @return
     */
    public static OkHttpClient createClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.writeTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        client.readTimeout(20 * 1000, TimeUnit.MILLISECONDS);
        client.connectTimeout(15 * 1000, TimeUnit.MILLISECONDS);
        //设置缓存路径
        File httpCacheDirectory = new File(BaseLibrary.getContext().getCacheDir(), "okhttpCache");
        //设置缓存 10M
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
        client.cache(cache);
        //设置拦截器
        client.addInterceptor(new MInterceptor());
        client.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.i("okHttpLog", message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY));
        return client.build();
    }
}
