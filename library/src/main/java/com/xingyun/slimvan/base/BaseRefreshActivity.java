package com.xingyun.slimvan.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xingyun.slimvan.R;

/**
 * 下拉刷新Activity基类
 */
public abstract class BaseRefreshActivity extends BaseHeaderActivity {
    public SwipeRefreshLayout refreshLayout;
    private SwipeRefreshLayout llContent;
    private View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base_refresh);

        initViews();
    }

    /**
     * 重写一个新增View的视图 子类设置layout的时候调用该方法即可
     */
    public void setContentView(int layoutResId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        //根布局
        llContent = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        //子类布局
        v = inflater.inflate(layoutResId, null);
        //子类布局的布局参数
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //先后加入子类布局
        llContent.addView(v, layoutParams);
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        initRefreshLayout();
    }

    /**
     * 初始化刷新布局
     */
    private void initRefreshLayout() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BaseRefreshActivity.this.onRefresh();
            }
        });
    }

    /**
     * 下拉刷新监听
     */
    protected abstract void onRefresh();

    public void finishRefresh() {
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(false);
        }
    }

}
