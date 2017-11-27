package com.slimvan.xingyun.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bilibili.boxing.BoxingCrop;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.loader.IBoxingCallback;
import com.bilibili.boxing.loader.IBoxingCrop;
import com.bilibili.boxing.loader.IBoxingMediaLoader;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.config.BoxingCropOption;
import com.slimvan.xingyun.utils.BoxingGlideLoader;
import com.slimvan.xingyun.utils.BoxingUcrop;
import com.xingyun.slimvan.application.BaseLibrary;
import com.xingyun.slimvan.http.HttpConfig;
import com.xingyun.slimvan.util.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import skin.support.SkinCompatManager;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

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
        //初始化okHttp
        initOkHttp();
        //初始化Boxing图片选择器
        initBoxing();
        //初始化换肤框架
        initSkinCompatManager();
    }

    /**
     * 初始化换肤框架
     */
    private void initSkinCompatManager() {
        SkinCompatManager.withoutActivity(this)                         // 基础控件换肤初始化
                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
                .setSkinStatusBarColorEnable(true)                     // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(true)                   // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();
    }

    /**
     * bilibili图片选择器
     */
    private void initBoxing() {
        BoxingMediaLoader.getInstance().init(new BoxingGlideLoader()); // 需要实现IBoxingMediaLoader
        BoxingCrop.getInstance().init(new BoxingUcrop());  // 需要实现 IBoxingCrop
//        BoxingConfig config = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG); // Mode：Mode.SINGLE_IMG, Mode.MULTI_IMG, Mode.VIDEO
//        config.needCamera().needGif().withMaxCount(9); // 支持gif，相机，设置最大选图数
//        config.withMediaPlaceHolderRes(resInt);// 设置默认图片占位图，默认无
//        config.withAlbumPlaceHolderRes(resInt);// 设置默认相册占位图，默认无
//        config.withVideoDurationRes(resInt);// 视频模式下，时长的图标，默认无
    }

    /**
     * 初始化网络框架
     */
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
