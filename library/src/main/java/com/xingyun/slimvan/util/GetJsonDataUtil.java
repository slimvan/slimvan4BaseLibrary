package com.xingyun.slimvan.util;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.xingyun.slimvan.bean.AreaJsonBean;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * 读取Json文件工具类
 */

public class GetJsonDataUtil {


    public String getJson(Context context,String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }




    public static ArrayList<AreaJsonBean> parseData(String result) {//Gson 解析
        ArrayList<AreaJsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                AreaJsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), AreaJsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}

