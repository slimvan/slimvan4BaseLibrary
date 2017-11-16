package com.slimvan.xingyun.utils;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.slimvan.xingyun.R;
import com.slimvan.xingyun.activity.DialogActivity;
import com.xingyun.slimvan.util.AppUtils;
import com.xingyun.slimvan.util.FileUtils;
import com.xingyun.slimvan.util.ToastUtils;
import com.yalantis.ucrop.UCrop;

import java.util.Locale;

/**
 * Created by xingyun on 2017/11/16.
 */

public class UCropUtils {

    /**
     * 从Activity跳转UCrop裁剪 在Activity的 OnActivityResult回调
     *
     * @param mActivity      Activity
     * @param originUri      原图Uri
     * @param aspectRatioX   裁剪后的比例 x边
     * @param AspectRatioY   裁剪后的比例 y边
     * @param maxResultSizeX 裁剪后的像素点 x边
     * @param maxResultSizeY 裁剪后的像素点 y边
     */
    public static void startCrop(Activity mActivity, Uri originUri,
                                 float aspectRatioX, float AspectRatioY,
                                 int maxResultSizeX, int maxResultSizeY) {
        //图片保存在系统缓存目录下的App包名下的文件夹
        String cachePath = mActivity.getApplication().getCacheDir().getAbsolutePath() + AppUtils.getAppPackageName(mActivity);
        if (!FileUtils.createOrExistsDir(cachePath)) {
            ToastUtils.showShort("图片缓存目录创建失败");
            return;
        }
        //UCrop界面配置
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));//裁剪标题栏颜色
        options.setStatusBarColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));//裁剪状态栏颜色
        options.setActiveWidgetColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));//控件颜色
        //裁剪后图片保存的目标路径
        Uri destUri = new Uri.Builder()
                .scheme("file")
                .appendPath(cachePath)
                .appendPath(String.format(Locale.US, "%s.png", System.currentTimeMillis()))
                .build();
        UCrop.of(originUri, destUri)
                .withOptions(options)
                .withAspectRatio(aspectRatioX, AspectRatioY)
                .withMaxResultSize(maxResultSizeX, maxResultSizeY)
                .start(mActivity);
    }

    /**
     * 从Fragment跳转UCrop裁剪 在Fragment的 OnActivityResult回调
     *
     * @param mActivity      Activity
     * @param mFragment      Fragment
     * @param originUri      原图Uri
     * @param aspectRatioX   裁剪后的比例 x边
     * @param AspectRatioY   裁剪后的比例 y边
     * @param maxResultSizeX 裁剪后的像素点 x边
     * @param maxResultSizeY 裁剪后的像素点 y边
     */
    public static void startCrop(Activity mActivity, Fragment mFragment, Uri originUri,
                                 float aspectRatioX, float AspectRatioY,
                                 int maxResultSizeX, int maxResultSizeY) {
        //图片保存在系统缓存目录下的App包名下的文件夹
        String cachePath = mActivity.getApplication().getCacheDir().getAbsolutePath() + AppUtils.getAppPackageName(mActivity);
        if (!FileUtils.createOrExistsDir(cachePath)) {
            ToastUtils.showShort("图片缓存目录创建失败");
            return;
        }
        //UCrop界面配置
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));//裁剪标题栏颜色
        options.setStatusBarColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));//裁剪状态栏颜色
        options.setActiveWidgetColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));//控件颜色
        //裁剪后图片保存的目标路径
        Uri destUri = new Uri.Builder()
                .scheme("file")
                .appendPath(cachePath)
                .appendPath(String.format(Locale.US, "%s.png", System.currentTimeMillis()))
                .build();
        UCrop.of(originUri, destUri)
                .withOptions(options)
                .withAspectRatio(aspectRatioX, AspectRatioY)
                .withMaxResultSize(maxResultSizeX, maxResultSizeY)
                .start(mActivity, mFragment);
    }
}
