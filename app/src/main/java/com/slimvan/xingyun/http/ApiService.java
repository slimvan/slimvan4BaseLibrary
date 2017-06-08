package com.slimvan.xingyun.http;

import com.slimvan.xingyun.bean.Test;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface ApiService {

    @GET("{url}")
    Observable<String> getRequest(@Path("url") String url,
                                  @QueryMap Map<String, Object> params);

    @POST("{url}")
    @FormUrlEncoded
    Observable<String> postRequest(@Path("url") String url,
                                     @FieldMap Map<String, Object> params);


}