package com.xingyun.slimvan.listener;

import android.view.View;

import com.xingyun.slimvan.enterface.OnCheckDoubleClick;

import java.util.Calendar;

/**
 * 自定义点击监听
 * 防止快速双击执行两次点击事件
 * Created by xingyun on 2017/3/9.
 */
public class CheckDoubleClickListener implements View.OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    private OnCheckDoubleClick checkDoubleClick;

    public CheckDoubleClickListener(OnCheckDoubleClick checkDoubleClick){
        this.checkDoubleClick = checkDoubleClick;
    }

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            checkDoubleClick.onCheckDoubleClick(v);
        }
    }
}
