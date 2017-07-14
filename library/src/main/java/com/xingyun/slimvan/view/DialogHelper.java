package com.xingyun.slimvan.view;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.bigkoo.pickerview.TimePickerView;
import com.xingyun.slimvan.R;
import com.xingyun.slimvan.util.TimeUtils;
import com.xingyun.slimvan.util.ToastUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 弹窗辅助工具类
 * Created by xingyun on 2017/6/9.
 */

public class DialogHelper {

    /**
     * 常用时间选择器
     * @param mContext 上下文对象 要求传入Activity类型，否则实例化TimePicker会报ClassCastException
     */
    public static void commonTimePicker(Activity mContext){
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1949,10,1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020,1,1);

        TimePickerView pvTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                ToastUtils.showShort(TimeUtils.date2String(date));
            }
        })
                .setType(new boolean[]{true,true,true,true,true,false})//默认全部显示 布尔值分别对应label
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(16)//滚轮文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleText("选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(ContextCompat.getColor(mContext, R.color.colorAccent))//确定按钮文字颜色
                .setCancelColor(ContextCompat.getColor(mContext, R.color.colorAccent))//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色
                .setBgColor(Color.WHITE)//滚轮背景颜色
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分","秒")
                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式 否则将从屏幕底部弹出
                .build();

        pvTime.show(true);
    }

}
