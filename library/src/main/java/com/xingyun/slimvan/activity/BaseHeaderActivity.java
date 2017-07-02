package com.xingyun.slimvan.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xingyun.slimvan.R;
import com.xingyun.slimvan.adapter.PopupListAdapter;
import com.xingyun.slimvan.bean.PopupListBean;
import com.xingyun.slimvan.util.ScreenUtils;
import com.xingyun.slimvan.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseHeaderActivity extends BaseActivity implements View.OnClickListener {

    protected Context mContext;

    protected ViewStub viewStub;
    /*标题栏控件*/
    protected ImageView ivBack;
    protected TextView tvTitle;
    protected TextView tvTitleRight;
    protected RelativeLayout llTitleBar;
    private View v;
    /*标题栏控件*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base_header);
        mContext = super.mContext;

        initViews();
        initTitleBar();
    }

    /**
     * 初始化布局
     */
    private void initViews() {
        viewStub = (ViewStub) findViewById(R.id.viewStub);
    }

    /**
     * 重写一个新增View的视图 子类设置layout的时候调用该方法即可
     */
    public void setContentView(int layoutResId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        //根布局
        LinearLayout llContent = (LinearLayout) findViewById(R.id.ll_root);
        //子类布局
        v = inflater.inflate(layoutResId, null);
        //子类布局的布局参数
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //先后加入子类布局
        llContent.addView(v, layoutParams);
    }

    protected void showEmptyView() {
        if (viewStub != null) {
            viewStub.setVisibility(View.VISIBLE);
            v.setVisibility(View.GONE);
        }
    }

    protected void hideEmptyView() {
        if (viewStub != null) {
            viewStub.setVisibility(View.GONE);
            v.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化标题栏
     */
    protected void initTitleBar() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitleRight = (TextView) findViewById(R.id.tv_title_right);
        llTitleBar = (RelativeLayout) findViewById(R.id.ll_title_bar);
        setTitleBarListeners();
    }

    /**
     * 标题栏点击事件
     */
    private void setTitleBarListeners() {
        ivBack.setOnClickListener(this);
        tvTitleRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_back) {
            onLeftClick(v);

        } else if (i == R.id.tv_title_right) {
            onRightClick(v);

        }
    }

    /**
     * 标题栏左按钮点击事件 通常是back
     *
     * @param v
     */
    public abstract void onLeftClick(View v);

    /**
     * 标题栏右按钮点击事件 根据业务自定义
     *
     * @param v
     */
    public abstract void onRightClick(View v);

    /**
     * 隐藏标题栏左侧按钮
     */
    protected void hideLeftButton() {
        ivBack.setVisibility(View.GONE);
    }

    /**
     * 隐藏标题栏右侧按钮
     */
    protected void hideRightButton() {
        tvTitleRight.setVisibility(View.GONE);
    }

    /**
     * 设置标题栏文字
     *
     * @param title
     */
    protected void setTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 右上角更多菜单 使用popupWindow实现
     *
     * @param menuItems 数据源 可传入 icon和title
     */
    protected void showPopupMenu(List<PopupListBean> menuItems) {
        final PopupWindow popupWindow = new PopupWindow(mContext);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_popupwindow_listview, null);
        ListView listView = (ListView) contentView.findViewById(R.id.listView);
        listView.setAdapter(new PopupListAdapter(mContext, menuItems));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showShort(position + "");
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(contentView);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(tvTitleRight, 0, -100);
    }

}
