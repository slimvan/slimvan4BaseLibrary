package com.xingyun.slimvan.http;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 自定义Json数据解密转换器
 * <p>
 * desc:将服务器返回的数据解密后 再转换为实体类
 * Created by xingyun on 2017/8/1.
 */

public class DecodeJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson mGson;//gson对象
    private final TypeAdapter<T> adapter;

    public DecodeJsonResponseBodyConverter(Gson mGson, TypeAdapter<T> adapter) {
        this.mGson = mGson;
        this.adapter = adapter;
    }


    @Override
    public T convert(ResponseBody responseBody) throws IOException {
        String string = responseBody.string();
        //解密字符串
        String desStr = ThreeDESUtil.decodeUnicode(ThreeDESUtil.des3DecodeCBC(BaseJsonUtil.getIv(string), BaseJsonUtil.getXy(string)));
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (entityClass == String.class) {
            return (T) string;
        }
        T bean = new Gson().fromJson(desStr, entityClass);
        return bean;
    }
}
