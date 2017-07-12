package com.slimvan.xingyun.http.api;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by xingyun on 2017/7/6.
 */

public interface GankApi {

    @GET("{type}/{count}/{page}")
    Observable<String> gankData(@Path("type") String type, @Path("count") String count, @Path("page") int page);

}
