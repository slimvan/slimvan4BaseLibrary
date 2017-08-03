package com.xingyun.slimvan.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.xingyun.slimvan.util.LogUtils;
import com.xingyun.slimvan.util.ToastUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okHttp 响应 返回实体类
 * <p>
 * desc:使用okHttp作为网络框架时，响应直接返回java实体类，并对数据做了解密。
 *
 * @param <T>
 */
public abstract class JavaBeanCallBack<T> extends Callback<T> {

    private boolean loadingFlag;
    private boolean cancelable;
    private ProgressDialog mProgressDialog;
    private Context mContext;
    private RequestCall request;

    public JavaBeanCallBack(Context mContext, RequestCall request, boolean loadingFlag, boolean cancelable) {
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
    public void onResponse(T response, int id) {
        if (loadingFlag) {
            if (mProgressDialog != null) {
                mProgressDialog.hide();
            }
        }
        onSuccess(response);
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        LogUtils.i("JavaBeanCallBack", "onError");
        if (e instanceof UnknownHostException) { //无网络
            ToastUtils.showShort("请检查网络");
        } else if (e instanceof SocketTimeoutException) {
            ToastUtils.showShort("网络连接超时"); //网络连接超时
        } else if (e instanceof IllegalStateException) {
            ToastUtils.showShort("数据解析失败"); //json解析失败
        }

        if (loadingFlag) {
            if (mProgressDialog != null) {
                mProgressDialog.hide();
            }
        }
        errorCallBack(e);
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws IOException {
        String string = response.body().string();
        /*ThreeDes解密*/
        //解密字符串
        String desStr = ThreeDESUtil.decodeUnicode(ThreeDESUtil.des3DecodeCBC(BaseJsonUtil.getIv(string), BaseJsonUtil.getXy(string)));
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (entityClass == String.class) {
            return (T) string;
        }
        T bean = new Gson().fromJson(desStr, entityClass);
        return bean;
    }

    public abstract void onSuccess(T t);

    public abstract void errorCallBack(Throwable e);

}