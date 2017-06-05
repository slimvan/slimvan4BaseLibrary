package com.xingyun.slimvan.application;

import android.app.Application;
import android.content.Context;

import com.xingyun.slimvan.util.Utils;

/**
 * Created by xingyun on 2017/5/6.
 */

public class App extends Application {
    private static Context applicationContext;

    public Context getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;

        init();
    }

    private void init() {
        Utils.init(applicationContext);
    }
}
