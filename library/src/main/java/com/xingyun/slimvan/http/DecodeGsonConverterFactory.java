package com.xingyun.slimvan.http;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 自定义Gson转换器
 * <p>
 * desc:将服务器返回的数据解密后 再转换为实体类  请求转换器在此不作处理，在okHttp的Interceptor中处理
 * Created by xingyun on 2017/8/1.
 */

public class DecodeGsonConverterFactory extends Converter.Factory {
    public static DecodeGsonConverterFactory create() {
        return create(new Gson());
    }

    public static DecodeGsonConverterFactory create(Gson gson) {
        return new DecodeGsonConverterFactory(gson);

    }

    private final Gson gson;

    public DecodeGsonConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }


    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        if (HttpConfig.SWITCH_ENCRYPT) {
            return new DecodeJsonResponseBodyConverter<>(gson, adapter); //响应
        } else {
            return super.responseBodyConverter(type, annotations, retrofit);
        }
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit); //请求
    }
}
