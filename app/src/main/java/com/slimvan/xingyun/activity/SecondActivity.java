package com.slimvan.xingyun.activity;

import android.os.Bundle;
import android.view.View;

import com.slimvan.xingyun.R;
import com.xingyun.slimvan.activity.BaseHeaderActivity;
import com.xingyun.slimvan.bean.PopupListBean;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends BaseHeaderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    @Override
    public void onLeftClick(View v) {

    }

    @Override
    public void onRightClick(View v) {
        try {
            List<PopupListBean> menuItems = new ArrayList<>();
            menuItems.add(new PopupListBean("分享"));
            menuItems.add(new PopupListBean("更多信息"));
            showPopupMenu(menuItems);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
