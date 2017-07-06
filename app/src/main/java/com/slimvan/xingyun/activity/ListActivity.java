package com.slimvan.xingyun.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.adapter.RecyclerListAdapter;
import com.slimvan.xingyun.adapter.RecyclerViewAdapter;
import com.slimvan.xingyun.bean.DoubanBookList;
import com.slimvan.xingyun.http.MSubscriber;
import com.slimvan.xingyun.http.RetrofitBuilder;
import com.slimvan.xingyun.http.api.DoubanApi;
import com.xingyun.slimvan.activity.BaseHeaderActivity;
import com.xingyun.slimvan.bean.PopupListBean;
import com.xingyun.slimvan.util.LogUtils;
import com.xingyun.slimvan.util.ToastUtils;
import com.xingyun.slimvan.view.DividerItemDecoration;
import com.xingyun.slimvan.view.LoadMoreView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 列表页   实现下拉刷新 上拉加载  （adapter添加HeaderView导致分割线出问题，暂时用NestedScrollView嵌套头布局处理）
 */
public class ListActivity extends BaseHeaderActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private RecyclerListAdapter adapter;
    private ArrayList<DoubanBookList.BooksBean> dataList = new ArrayList<>();
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        setTitle("列表测试");
        initRefreshLayout();
        initRecyclerView();
        getData();
    }

    @Override
    public void onStateLayoutClick() {

    }

    private void getData() {
        RetrofitBuilder.build(DoubanApi.class).
                bookSearch("android").
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new MSubscriber<DoubanBookList>(mContext, true, false) {
                    @Override
                    public void onSuccess(DoubanBookList bookList) {
                        Log.i(TAG, "success");
                        adapter.setNewData(bookList.getBooks());
                    }

                    @Override
                    public void errorCallBack(Throwable e) {
                        Log.i(TAG, "error");
                    }
                });
    }

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

        //刷新、加载更多监听
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                refreshData();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                if (currentPage <= 5) {
                    LogUtils.i("onLoadMore", "onLoadMore");
                    getMoreData();
                } else {
                    refreshLayout.finishLoadmore();
                }
            }
        });
    }

    private void initRecyclerView() {
        adapter = new RecyclerListAdapter(dataList);

        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));

        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShort("点击了" + position);
            }
        });

        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShort("长按了" + position);
                return true;
            }
        });
        recyclerView.setFocusable(false);
    }

    private void refreshData() {
        RetrofitBuilder.build(DoubanApi.class).
                bookSearch("android").
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new MSubscriber<DoubanBookList>(mContext, true, false) {
                    @Override
                    public void onSuccess(DoubanBookList bookList) {
                        Log.i(TAG, "success");
                        adapter.setNewData(bookList.getBooks());
                        currentPage = 1;
                        adapter.disableLoadMoreIfNotFullPage(recyclerView);
                        refreshLayout.finishRefreshing();
                    }

                    @Override
                    public void errorCallBack(Throwable e) {
                        Log.i(TAG, "error");
                        refreshLayout.finishRefreshing();
                    }
                });
    }

    private void getMoreData() {
        RetrofitBuilder.build(DoubanApi.class).
                bookSearch("村上春树").
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new MSubscriber<DoubanBookList>(mContext, false, false) {
                    @Override
                    public void onSuccess(DoubanBookList bookList) {
                        adapter.addData(bookList.getBooks());
                        currentPage++;
                        refreshLayout.finishLoadmore();
                    }

                    @Override
                    public void errorCallBack(Throwable e) {
                        Log.i(TAG, "error");
                    }
                });
    }

    @Override
    public void onLeftClick(View v) {
        ListActivity.this.finish();
    }

    @Override
    public void onRightClick(View v) {
        List<PopupListBean> menuItems = new ArrayList<>();
        menuItems.add(new PopupListBean("Settings"));
        menuItems.add(new PopupListBean("Settings"));
        menuItems.add(new PopupListBean("Settings"));
        menuItems.add(new PopupListBean("Settings"));
        showPopupMenu(menuItems, new PopupMenuItemClick() {
            @Override
            public void onPopupMenuItemClick(PopupWindow popupWindow, int position) {

            }
        });
    }
}