package com.xingyun.slimvan.http;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * okHttp 请求拦截器
 * <p>
 * desc:使用okHttp作为网络框架时，拦截网络请求，对数据进行加密传输。
 * Created by xingyun on 2017/8/1.
 */

public class MInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (HttpConfig.SWITCH_ENCRYPT) {
            //GET POST DELETE PUT PATCH
            String method = request.method();
            JSONObject jsonObject = null;
            if ("GET".equals(method)) {
                try {
                    jsonObject = doGet(request);
                    String iv = ThreeDESUtil.getKeyIV();
                    String xy = ThreeDESUtil.des3EncodeCBC(iv, jsonObject.toString());
                    String newUrl = request.url() + "?" + "xy=" + xy + "&iv=" + iv;
                    Log.i("OkHttp_Get", newUrl);
                    request = request.newBuilder().url(newUrl).build();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if ("POST".equals(method) || "PUT".equals(method) || "DELETE".equals(method) || "PATCH".equals(method)) {
                RequestBody body = request.body();
                if (body != null && body instanceof FormBody) {
                    jsonObject = doForm(request);
                    FormBody.Builder bodyBuilder = new FormBody.Builder();
                    String iv = ThreeDESUtil.getKeyIV();
                    String xy = ThreeDESUtil.des3EncodeCBC(iv, jsonObject.toString());
                    bodyBuilder.add("iv", iv);
                    bodyBuilder.add("xy", xy);
                    request = request.newBuilder().method(method, bodyBuilder.build()).build();
                }
            }
        }
        return chain.proceed(request);
    }


    /**
     * 获取get方式的请求参数
     *
     * @param request
     * @return
     */
    private static JSONObject doGet(Request request) throws JSONException {
        JSONObject jsonObject = null;
        HttpUrl url = request.url();
        Set<String> strings = url.queryParameterNames();
        if (strings != null) {
            Iterator<String> iterator = strings.iterator();
            jsonObject = new JSONObject();
            int i = 0;
            while (iterator.hasNext()) {
                String name = iterator.next();
                String value = url.queryParameterValue(i);
                jsonObject.put(name, value);
                i++;
            }
        }
        return jsonObject;
    }

    /**
     * 获取表单的请求参数
     *
     * @param request
     * @return
     */
    private static JSONObject doForm(Request request) {
        Map<String, String> params = null;
        JSONObject jsonObject = null;
        FormBody body = null;
        try {
            body = (FormBody) request.body();
        } catch (ClassCastException c) {
        }
        if (body != null) {
            int size = body.size();
            if (size > 0) {
                jsonObject = new JSONObject();
                for (int i = 0; i < size; i++) {
                    params.put(body.name(i), body.value(i));
                }
            }
        }
        return jsonObject;
    }
}
