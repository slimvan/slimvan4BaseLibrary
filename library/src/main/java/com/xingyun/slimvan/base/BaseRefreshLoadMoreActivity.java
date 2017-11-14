package com.xingyun.slimvan.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.xingyun.slimvan.R;
import com.xingyun.slimvan.view.widget.LoadMoreView;

/**
 * 下拉刷新、上拉加载功能的Activity基类
 */
public abstract class BaseRefreshLoadMoreActivity extends BaseHeaderActivity {

    public TwinklingRefreshLayout refreshLayout;
    private View v;
    private NestedScrollView llContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base_refresh_load_more);

        initViews();
    }

    /**
     * 重写一个新增View的视图 子类设置layout的时候调用该方法即可
     */
    public void setContentView(int layoutResId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        //根布局
        llContent = (NestedScrollView) findViewById(R.id.ll_content);
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
        refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout);
        initRefreshLayout();
    }

    private void initRefreshLayout() {
        //悬浮刷新
        refreshLayout.setFloatRefresh(true);
        //越界回弹
        refreshLayout.setEnableOverScroll(true);

        //刷新布局样式
        ProgressLayout progressLayout = new ProgressLayout(mContext);
        progressLayout.setColorSchemeColors(Color.parseColor("#FF4081"));
        refreshLayout.setHeaderView(progressLayout);

        //加载更多布局样式
        refreshLayout.setBottomView(new LoadMoreView(mContext));

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                BaseRefreshLoadMoreActivity.this.onRefresh();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                BaseRefreshLoadMoreActivity.this.onLoadMore();
            }
        });
    }

    /**
     * 下拉刷新
     */
    protected abstract void onRefresh();

    /**
     * 上拉加载
     */
    protected abstract void onLoadMore();

    public void finishRefresh() {
        if (refreshLayout != null) {
            refreshLayout.finishRefreshing();
        }
    }

    public void finishLoadMore() {
        if (refreshLayout != null) {
            refreshLayout.finishLoadmore();
        }
    }
}
