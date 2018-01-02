package com.slimvan.xingyun.bean;

import android.support.annotation.DrawableRes;

/**
 * 列表实体类
 * Created by xingyun on 2018/1/2.
 */

public class SimpleListBean {

    private String title;
    private @DrawableRes int icon;

    private SimpleListBean() {
    }

    public SimpleListBean(String title) {
        this.title = title;
    }

    public SimpleListBean(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
