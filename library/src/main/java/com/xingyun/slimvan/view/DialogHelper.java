package com.xingyun.slimvan.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.TimePickerView;
import com.xingyun.slimvan.R;
import com.xingyun.slimvan.listener.DialogConfirmClickListener;
import com.xingyun.slimvan.listener.DialogMultiConfirmClickListener;
import com.xingyun.slimvan.util.TimeUtils;
import com.xingyun.slimvan.util.ToastUtils;

import java.util.Calendar;
import java.util.Date;

import static android.R.id.list;

/**
 * 弹窗辅助工具类
 * Created by xingyun on 2017/6/9.
 */

public class DialogHelper {

    /**
     * 常用时间选择器
     *
     * @param mContext 上下文对象 要求传入Activity类型，否则实例化TimePicker会报ClassCastException
     */
    public static void showTimePicker(Activity mContext) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1949, 10, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 1, 1);

        TimePickerView pvTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                ToastUtils.showShort(TimeUtils.date2String(date));
            }
        })
                .setType(new boolean[]{true, true, true, true, true, false})//默认全部显示 布尔值分别对应label
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
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式 否则将从屏幕底部弹出
                .build();

        pvTime.show(true);
    }

    /**
     * 仿IOS 底部ActionSheet样式对话框
     *
     * @param mContext            上下文对象
     * @param items               操作项
     * @param onItemClickListener 点击监听
     */
    public static void showIOSActionSheetDialog(Context mContext, String[] items, OnItemClickListener onItemClickListener) {
        new AlertView.Builder().setContext(mContext)
                .setStyle(AlertView.Style.ActionSheet)
                .setTitle("选择操作")
                .setMessage(null)
                .setCancelText("取消")
                .setDestructive(items)
                .setOthers(null)
                .setOnItemClickListener(onItemClickListener)
                .build()
                .setCancelable(true)
                .show();
    }

    /**
     * 仿IOS 列表样式对话框
     *
     * @param mContext            上下文对象
     * @param title               标题
     * @param items               选择项
     * @param onItemClickListener 点击监听
     */
    public static void showIOSListDialog(Context mContext, String title, String[] items, OnItemClickListener onItemClickListener) {
        new AlertView.Builder().setContext(mContext)
                .setStyle(AlertView.Style.Alert)
                .setTitle(title)
                .setMessage(null)
                .setCancelText("取消")
                .setDestructive(items)
                .setOthers(null)
                .setOnItemClickListener(onItemClickListener)
                .build()
                .setCancelable(true)
                .show();
    }

    /**
     * 仿IOS 警告内容对话框
     *
     * @param mContext            上下文对象
     * @param title               标题
     * @param content             内容
     * @param onItemClickListener 点击监听
     */
    public static void showIOSAlertDialog(Context mContext, String title, String content, OnItemClickListener onItemClickListener) {
        new AlertView(title, content, "取消", new String[]{"确定"}, null, mContext, AlertView.Style.Alert, onItemClickListener).setCancelable(true).show();
    }

    /**
     * 警告内容对话框
     *
     * @param mContext 上下文对象
     * @param title    标题
     * @param content  内容
     * @param listener 确认 点击监听
     */
    public static void showAlertDialog(Context mContext, String title, String content, final DialogConfirmClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title)
                .setMessage(content)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDialogConfirmClick(dialog, which);
                    }
                })
                .create()
                .show();

    }

    /**
     * 列表样式对话框
     *
     * @param mContext 上下文对象
     * @param Title    标题
     * @param items    选择项
     * @param listener 点击监听
     */
    public static void showListDialog(Context mContext, String Title, String[] items, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(Title)
                .setItems(items, listener)
                .create()
                .show();
    }

    /**
     * 单选列表对话框
     *
     * @param mContext        上下文对象
     * @param items           选择项
     * @param defaultChoice   默认选中
     * @param itemListener    列表项点击事件
     * @param confirmListener 确认点击监听  返回选择的列表项 position
     */
    public static void showSingleChoiceDialog(Context mContext, String[] items, int defaultChoice, DialogInterface.OnClickListener itemListener, final DialogConfirmClickListener confirmListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setSingleChoiceItems(items, defaultChoice, itemListener)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        confirmListener.onDialogConfirmClick(dialog, position);
                    }
                })
                .create()
                .show();
    }

    public static void showMuliteChoiceDialog(Context mContext, String[] items, boolean[] choiceItems, DialogInterface.OnMultiChoiceClickListener itemListener, final DialogMultiConfirmClickListener confirmListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMultiChoiceItems(items, choiceItems, itemListener)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SparseBooleanArray checkedItemPositions = ((AlertDialog) dialog).getListView().getCheckedItemPositions();
                        confirmListener.onDialogMultiConfirmClick(dialog, checkedItemPositions);
                    }
                })
                .create()
                .show();
    }

}
