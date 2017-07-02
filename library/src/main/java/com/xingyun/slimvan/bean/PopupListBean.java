package com.xingyun.slimvan.bean;

import android.support.annotation.Nullable;

/**
 * popupWindow列表实体类
 * Created by xingyun on 2017/6/29.
 */

public class PopupListBean {
    private String title;
    private int resource;


    public PopupListBean(String title, @Nullable int resource) {
        this.title = title;
        this.resource = resource;
    }

    public PopupListBean(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }
}
