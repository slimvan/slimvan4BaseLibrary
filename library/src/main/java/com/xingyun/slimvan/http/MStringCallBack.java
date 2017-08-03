package com.xingyun.slimvan.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.xingyun.slimvan.util.LogUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okHttp 响应 返回String
 * <p>
 * desc:响应返回字符串，并对数据做了解密。
 * Created by xingyun on 2017/7/31.
 */

public abstract class MStringCallBack extends Callback<String> {
    private boolean loadingFlag;
    private boolean cancelable;
    private ProgressDialog mProgressDialog;
    private Context mContext;
    private RequestCall request;

    public MStringCallBack(Context mContext, RequestCall request, boolean loadingFlag, boolean cancelable) {
        this.mContext = mContext;
        this.request = request;
        this.loadingFlag = loadingFlag;
        this.cancelable = cancelable;
        initProgressDialog("");
    }

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
                    if (request != null) {
                        request.cancel();
                    }
                }
            });
        }
        mProgressDialog.setMessage(!TextUtils.isEmpty(message) ? message : "加载中");
    }

    @Override
    public void onBefore(Request request, int id) {
        super.onBefore(request, id);
        if (loadingFlag) {
            if (mProgressDialog != null) {
                mProgressDialog.show();
            }
        }
    }

    @Override
    public void onResponse(String response, int id) {
        if (loadingFlag) {
            if (mProgressDialog != null) {
                mProgressDialog.hide();
            }
        }
        onSuccess(response);
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        if (loadingFlag) {
            if (mProgressDialog != null) {
                mProgressDialog.hide();
            }
        }
        errorCallBack(e);
    }

    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException {
        String string = response.body().string();
        /*ThreeDes解密*/
        //解密字符串
        String desStr = ThreeDESUtil.decodeUnicode(ThreeDESUtil.des3DecodeCBC(BaseJsonUtil.getIv(string), BaseJsonUtil.getXy(string)));
        return desStr;
    }

    public abstract void onSuccess(String t);

    public abstract void errorCallBack(Throwable e);
}
