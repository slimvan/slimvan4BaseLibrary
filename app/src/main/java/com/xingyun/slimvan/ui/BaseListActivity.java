package com.xingyun.slimvan.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.xingyun.slimvan.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public abstract class BaseListActivity extends BaseHeaderActivity {

    @InjectView(R.id.listView)
    protected ListView listView;
    @InjectView(R.id.refreshLayout)
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

        initRefreshLayout();
        initListView();

    }

    /**
     * 初始化刷新布局
     */
    private void initRefreshLayout() {
        ProgressLayout headView = new ProgressLayout(mContext);
        refreshLayout.setHeaderView(headView);
        LoadingView loadView = new LoadingView(mContext);
        refreshLayout.setBottomView(loadView);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                refresh();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        refreshLayout.finishRefreshing();
//                    }
//                },2000);
            }


            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                loadMore();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        refreshLayout.finishLoadmore();
//                    }
//                },2000);
            }

        });
    }

    /**
     * 下拉刷新回调
     */
    protected void refresh(){}

    /**
     * 上拉加载更多回调
     */
    protected void loadMore(){}

    /**
     * 停止下拉刷新
     */
    protected void finishRefreshing(){
        if(refreshLayout!=null){
            refreshLayout.finishRefreshing();
        }
    }

    /**
     * 停止加载更多
     */
    protected void finishLoadMore(){
        if(refreshLayout!=null){
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
