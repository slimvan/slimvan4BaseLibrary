package com.slimvan.xingyun.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.xingyun.slimvan.util.LogUtils;

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
        if (loadingFlag) {
            if (mProgressDialog != null) {
                mProgressDialog.show();
            }
        }
    }

    @Override
    public void onCompleted() {
        //暂时不知道有啥用
    }

    @Override
    public void onError(Throwable e) {
        //隐藏加载框
        hideProgressDialog();
        errorCallBack(e);
    }

    @Override
    public void onNext(T t) {
        //隐藏加载框
        hideProgressDialog();
        onSuccess(t);
    }


    public abstract void onSuccess(T t);

    public abstract void errorCallBack(Throwable e);

}