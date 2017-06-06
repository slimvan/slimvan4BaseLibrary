package com.xingyun.slimvan.application;

import android.app.Application;
import android.content.Context;

import com.xingyun.slimvan.util.Utils;

/**
 * Created by xingyun on 2017/5/6.
 */

public class App extends Application {
    private static Context applicationContext;
    private static App application;

    public static Context getContext() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        application = this;

        init();
    }

    private void init() {
        //工具类初始化
        Utils.init(applicationContext);
    }
}
