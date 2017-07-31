package com.slimvan.xingyun.application;

import android.app.Application;
import android.content.Context;

import com.xingyun.slimvan.application.BaseLibrary;
import com.xingyun.slimvan.http.HttpConfig;
import com.xingyun.slimvan.util.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by xingyun on 2017/7/14.
 */

public class App extends Application {
    private static Context applicationContext;

    public static Context getContext() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();

        init();
    }

    private void init() {
        //工具类初始化
        Utils.init(applicationContext);
        //lib初始化
        BaseLibrary.init(getContext());
        initOkHttp();
    }

    private void initOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
}
