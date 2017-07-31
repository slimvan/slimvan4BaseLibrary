package com.xingyun.slimvan.http;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 获取服务器返回json基本信息
 * Created by xingyun on 2016/8/25.
 */
public class BaseJsonUtil {
    /**
     * 获取Error状态码
     * @param response
     * @return
     */
    public static String getError(String response){
        Gson gson = new Gson();
        JsonBean data=gson.fromJson(response,JsonBean.class);
        return data.getError();
    }

    /**
     * 获取Msg
     * @param response
     * @return
     */
    public static String getMsg(String response){
        Gson gson = new Gson();
        JsonBean data=gson.fromJson(response,JsonBean.class);
        return data.getMsg();
    }

    /**
     * 获取iv
     * @param response
     * @return
     */
    public static String getIv(String response){
        Gson gson = new Gson();
        JsonBean data = gson.fromJson(response,JsonBean.class);
        return data.getIv();
    }

    /**
     * 获取xy
     * @param response
     * @return
     */
    public static String getXy(String response){
        Gson gson = new Gson();
        JsonBean data = gson.fromJson(response,JsonBean.class);
        return data.getXy();
    }


    /**
     * 根据Json字符串获取名称为items的json数组
     * @param desData
     * @return 数组返回toString
     */
    public static String getJsonAryStr(String desData){
        try{
            JSONObject obj = new JSONObject(desData);
            JSONArray jsonAry = obj.getJSONArray("items");
            return jsonAry.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
