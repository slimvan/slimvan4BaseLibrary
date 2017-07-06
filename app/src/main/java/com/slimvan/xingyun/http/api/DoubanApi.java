package com.slimvan.xingyun.http.api;

import com.slimvan.xingyun.bean.DoubanBookList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xingyun on 2017/7/5.
 */

public interface DoubanApi {


//    book/search 查询图书列表
    @GET("book/search")
    Observable<DoubanBookList> bookSearch(@Query("q") String q);
}
