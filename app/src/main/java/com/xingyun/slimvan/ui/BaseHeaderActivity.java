package com.xingyun.slimvan.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xingyun.slimvan.R;

public abstract class BaseHeaderActivity extends BaseActivity implements View.OnClickListener {

    protected Context mContext;

    /*标题栏控件*/
    protected ImageView ivBack;
    protected TextView tvTitle;
    protected TextView tvTitleRight;
    protected RelativeLayout llTitleBar;
    /*标题栏控件*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base_header);
        mContext = super.mContext;
        initTitleBar();
    }

    /**
     * 重写一个新增View的视图 子类设置layout的时候调用该方法即可
     */
    public void setContentView(int layoutResId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        //根布局
        LinearLayout llContent = (LinearLayout) findViewById(R.id.ll_root);
        //子类布局
        View v = inflater.inflate(layoutResId, null);
        //子类布局的布局参数
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //先后加入子类布局
        llContent.addView(v, layoutParams);
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
        switch (v.getId()) {
            case R.id.iv_back:
                onLeftClick(v);
                break;
            case R.id.tv_title_right:
                onRightClick(v);
                break;
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

}
