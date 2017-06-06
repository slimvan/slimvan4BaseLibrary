package com.slimvan.xingyun.http;

import com.slimvan.xingyun.bean.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("?")
    Call<User> getUsers(@QueryMap Map<String,Object> params);

    @POST("test/Test")
    @FormUrlEncoded
    Call<User> createUser(@Field("username") String username, @Field("password") String password);
}