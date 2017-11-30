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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.xingyun.slimvan.R;
import com.xingyun.slimvan.bean.JsonBean;
import com.xingyun.slimvan.bean.ProvinceBean;
import com.xingyun.slimvan.listener.AreaPickerConfirmListener;
import com.xingyun.slimvan.listener.DialogConfirmClickListener;
import com.xingyun.slimvan.listener.DialogMultiConfirmClickListener;
import com.xingyun.slimvan.listener.TimePickerConfirmListener;
import com.xingyun.slimvan.util.GetJsonDataUtil;
import com.xingyun.slimvan.util.TimeUtils;
import com.xingyun.slimvan.util.ToastUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.R.id.list;

/**
 * 弹窗辅助工具类
 * Created by xingyun on 2017/6/9.
 */

public class DialogHelper {
    private static ArrayList<JsonBean> options1Items = new ArrayList<>();
    private static ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private static ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    /**
     * 常用时间选择器
     *
     * @param mContext 上下文对象 要求传入Activity类型，否则实例化TimePicker会报ClassCastException
     */
    public static void showTimePicker(Activity mContext, final TimePickerConfirmListener listener) {

        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1949, 10, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 1, 1);

        TimePickerView pvTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String dateStr = TimeUtils.date2String(date);
                listener.onTimePickerConfirm(dateStr);
            }
        })
                .setType(new boolean[]{true, true, true, true, true, false})//默认全部显示 布尔值分别对应label
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(16)//滚轮文字大小
                .setTitleSize(16)//标题文字大小
                .setTitleText("选择时间")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(ContextCompat.getColor(mContext, R.color.colorAccent))//确定按钮文字颜色
//                .setCancelColor(ContextCompat.getColor(mContext, R.color.colorAccent))//取消按钮文字颜色
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
     * 省市区三级联动选择器
     *
     * @param mContext 上下文对象
     */
    public static void showAreaPicker(Activity mContext, final AreaPickerConfirmListener listener) {
        initJsonData(mContext);
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                listener.onAreaPickerConfirm(tx);

            }
        })
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(16)//滚轮文字大小
                .setTitleSize(16)//标题文字大小
                .setTitleText("地区选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setOutSideCancelable(false)// default is true
                .setTitleBgColor(Color.WHITE)//标题背景颜色
                .setBgColor(Color.WHITE)//滚轮背景颜色
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();

    }


    /**
     * 读取本地省市区json数据
     *
     * @param mContext 上下文对象
     */
    private static void initJsonData(Context mContext) {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(mContext, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = GetJsonDataUtil.parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }


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
     * 警告内容对话框
     *
     * @param mContext   上下文对象
     * @param title      标题
     * @param content    内容
     * @param titleColor 标题颜色
     * @param listener   确认 点击监听
     */
    public static void showAlertDialog(Context mContext, String title, String content, int titleColor, final DialogConfirmClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        AlertDialog dialog = builder.setTitle(title)
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
                .create();
        dialog.show();
        //反射修改原生Dialog中控件属性，可修改包括颜色、字体大小等。
        try {
            /*private ImageView mIconView;
            private TextView mTitleView;
            private TextView mMessageView;
            private View mCustomTitleView;*/
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(dialog);

            Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
            mTitle.setAccessible(true);
            TextView mTitleView = (TextView) mTitle.get(mAlertController);
            mTitleView.setTextColor(titleColor);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
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

    /**
     * 多选列表对话框
     *
     * @param mContext        上下文对象
     * @param items           选择项
     * @param choiceItems     默认选中项
     * @param itemListener    列表项点击事件
     * @param confirmListener 确认点击监听 返回选择的列表项 SparseBooleanArray类型
     */
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
