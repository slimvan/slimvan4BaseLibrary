package com.xingyun.slimvan.application;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * Created by xingyun on 2017/7/14.
 */

public class BaseLibrary {
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;


    public static void init(Context mContext) {
        BaseLibrary.mContext = mContext;
    }

    private BaseLibrary() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Context getContext() {
        if (mContext != null) return mContext;
        throw new NullPointerException("u should init first");
    }
}
