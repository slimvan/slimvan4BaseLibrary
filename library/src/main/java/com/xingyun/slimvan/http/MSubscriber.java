package com.xingyun.slimvan.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.xingyun.slimvan.util.LogUtils;
import com.xingyun.slimvan.util.ToastUtils;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscriber;

/**
 * 自定义Subscriber
 */

public abstract class MSubscriber<T> extends Subscriber<T> {

    private boolean loadingFlag;
    private boolean cancelable;
    private ProgressDialog mProgressDialog;
    private Context mContext;


    /**
     * 显示提示框
     *
     * @param message 提示内容
     */
    private void initProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setCancelable(cancelable);
            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    LogUtils.w(HttpConfig.TAG, "取消请求");
                    if (!isUnsubscribed()) {
                        unsubscribe();
                    }
                }
            });
        }
        mProgressDialog.setMessage(!TextUtils.isEmpty(message) ? message : "加载中");
    }

    /**
     * 隐藏提示框
     */
    private void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }


    protected MSubscriber(Context mContext, boolean loadingFlag, boolean cancelable) {
        this.mContext = mContext;
        this.loadingFlag = loadingFlag;
        this.cancelable = cancelable;
        initProgressDialog("");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.i("MSubscriber", "onStart");
        if (loadingFlag) {
            if (mProgressDialog != null) {
                mProgressDialog.show();
            }
        }
    }

    @Override
    public void onCompleted() {
        LogUtils.i("MSubscriber", "onCompleted");
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                hideProgressDialog();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.i("MSubscriber", "onError");
        if (e instanceof UnknownHostException) { //无网络
            ToastUtils.showShort("请检查网络");
        } else if (e instanceof SocketTimeoutException) {
            ToastUtils.showShort("网络连接超时"); //网络连接超时
        } else if (e instanceof IllegalStateException) {
            ToastUtils.showShort("数据解析失败"); //json解析失败
        }

        //隐藏加载框
        hideProgressDialog();
        errorCallBack(e);
    }

    @Override
    public void onNext(T t) {
        LogUtils.i("MSubscriber", "onNext");
        //隐藏加载框
        hideProgressDialog();
        onSuccess(t);
    }


    public abstract void onSuccess(T t);

    public abstract void errorCallBack(Throwable e);

}