package com.slimvan.xingyun.http;

/**
 * Created by lin on 2016/11/28.
 */
public class HttpConfig {

//    private static String baseUrl = "https://api.github.com/";
    private static String baseUrl = "http://api.tgy-test.xingyun.net/";

    public static String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

}
