package com.xingyun.slimvan.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.xingyun.slimvan.R;
import com.xingyun.slimvan.view.LoadMoreView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 带标题头的列表页面基类
 * 内置刷新布局和ListView 如果是其他样式列表 请自行实现。
 */
public abstract class BaseListActivity extends BaseHeaderActivity {

    protected ListView listView;
    protected TwinklingRefreshLayout refreshLayout;

    public ListView getListView() {
        return listView;
    }

    public TwinklingRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_list);
        ButterKnife.inject(this);

        initViews();
        initRefreshLayout();
        initListView();

    }

    /**
     * 控件初始化
     */
    private void initViews() {
        listView = (ListView) findViewById(R.id.listView);
        refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout);
    }

    /**
     * 初始化刷新布局
     */
    private void initRefreshLayout() {
        //悬浮刷新
        refreshLayout.setFloatRefresh(true);
        //越界回弹
        refreshLayout.setEnableOverScroll(false);

        //刷新布局样式
        ProgressLayout progressLayout = new ProgressLayout(mContext);
        progressLayout.setColorSchemeColors(Color.parseColor("#FF4081"));
        refreshLayout.setHeaderView(progressLayout);

        //加载更多布局样式
        refreshLayout.setBottomView(new LoadMoreView(mContext));

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                refresh();
            }


            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                loadMore();
            }

        });
    }

    /**
     * 下拉刷新回调
     */
    protected abstract void refresh();

    /**
     * 上拉加载更多回调
     */
    protected abstract void loadMore();

    /**
     * 停止下拉刷新
     */
    protected void finishRefreshing() {
        if (refreshLayout != null) {
            refreshLayout.finishRefreshing();
        }
    }

    /**
     * 停止加载更多
     */
    protected void finishLoadMore() {
        if (refreshLayout != null) {
            refreshLayout.finishLoadmore();
        }
    }

    /**
     * 初始化列表
     */
    protected abstract void initListView();

    @Override
    public void onLeftClick(View v) {

    }

    @Override
    public void onRightClick(View v) {

    }
}
