package com.xingyun.slimvan.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;

/**
 * 处理背景变暗
 * https://blog.nex3z.com/2016/12/04/%E5%BC%B9%E5%87%BApopupwindow%E5%90%8E%E8%AE%A9%E8%83%8C%E6%99%AF%E5%8F%98%E6%9A%97%E7%9A%84%E6%96%B9%E6%B3%95/
 * Created by xingyun on 2017/12/14.
 */

public class DimUtils {
    //背景变暗时透明度
    public static float mDimValue = 0.7f;
    //背景变暗颜色
    @ColorInt
    public static int mDimColor = Color.BLACK;

    /**
     * 指定ViewGroup变暗
     *
     * @param dimView
     */
    public static void applyDim(ViewGroup dimView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ViewGroup parent = dimView;
            Drawable dim = new ColorDrawable(mDimColor);
            dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
            dim.setAlpha((int) (255 * mDimValue));
            ViewGroupOverlay overlay = parent.getOverlay();
            overlay.add(dim);
        }
    }

    /**
     * 清除指定ViewGroup的变暗效果
     *
     * @param dimView
     */
    public static void clearDim(ViewGroup dimView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ViewGroup parent = dimView;
            ViewGroupOverlay overlay = parent.getOverlay();
            overlay.clear();
        }
    }

    /**
     * 应用界面变暗效果
     *
     * @param activity
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void applyDim(Activity activity) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ViewGroup parent = (ViewGroup) activity.getWindow().getDecorView().getRootView();
            //activity跟布局
//        ViewGroup parent = (ViewGroup) parent1.getChildAt(0);
            Drawable dim = new ColorDrawable(mDimColor);
            dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
            dim.setAlpha((int) (255 * mDimValue));
            ViewGroupOverlay overlay = null;
            overlay = parent.getOverlay();
            overlay.add(dim);
        }
    }


    /**
     * 清除界面变暗效果
     *
     * @param activity
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void clearDim(Activity activity) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ViewGroup parent = (ViewGroup) activity.getWindow().getDecorView().getRootView();
            //activity跟布局
//        ViewGroup parent = (ViewGroup) parent1.getChildAt(0);
            ViewGroupOverlay overlay = parent.getOverlay();
            overlay.clear();
        }
    }
}
