package com.xingyun.slimvan.http;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 通用请求接口
 */
public interface ApiService {

    @GET("{url}")
    Observable<String> getRequest(@Path("url") String url,
                                  @QueryMap Map<String, Object> params);

    @POST("{url}")
    @FormUrlEncoded
    Observable<String> postRequest(@Path("url") String url,
                                   @FieldMap Map<String, Object> params);


}