package com.slimvan.xingyun.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.adapter.RecyclerListAdapter;
import com.slimvan.xingyun.bean.DoubanBookList;
import com.slimvan.xingyun.http.MSubscriber;
import com.slimvan.xingyun.http.RetrofitBuilder;
import com.slimvan.xingyun.http.api.DoubanApi;
import com.xingyun.slimvan.base.BaseHeaderActivity;
import com.xingyun.slimvan.bean.PopupListBean;
import com.xingyun.slimvan.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SecondActivity extends BaseHeaderActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    private RecyclerListAdapter adapter;
    private int currentPage = 1;
    private ArrayList<DoubanBookList.BooksBean> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);

        setTitle("豆瓣读书");

        initRefreshLayout();
        initRecyclerView();
        getData();
    }

    @Override
    public void onStateLayoutClick() {
        getData();
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
                        showContent();
                        adapter.setNewData(bookList.getBooks());
                    }

                    @Override
                    public void errorCallBack(Throwable e) {
                        Log.i(TAG, "error");
                    }
                });
    }

    private void initRecyclerView() {
        adapter = new RecyclerListAdapter(dataList);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (currentPage <= 5) {
                    LogUtils.i("onLoadMore", "onLoadMore");
                    getMoreData();
                } else {
                    adapter.loadMoreEnd();
                }
            }
        }, recyclerView);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
//        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        adapter.disableLoadMoreIfNotFullPage(recyclerView);
    }


    private void initRefreshLayout() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
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
                        showContent();
                        adapter.setNewData(bookList.getBooks());
                        currentPage = 1;
                        adapter.disableLoadMoreIfNotFullPage(recyclerView);
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void errorCallBack(Throwable e) {
                        Log.i(TAG, "error");
                        refreshLayout.setRefreshing(false);
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
                        adapter.loadMoreComplete();
                    }

                    @Override
                    public void errorCallBack(Throwable e) {
                        Log.i(TAG, "error");
                    }
                });
    }

    @Override
    public void onLeftClick(View v) {
        SecondActivity.this.finish();
    }

    @Override
    public void onRightClick(View v) {
        List<PopupListBean> menuItems = new ArrayList<>();
        menuItems.add(new PopupListBean("Empty Data"));
        menuItems.add(new PopupListBean("NetWork Error"));
        menuItems.add(new PopupListBean("Server Error"));
        menuItems.add(new PopupListBean("Settings"));
        showPopupMenu(menuItems, new PopupMenuItemClick() {
            @Override
            public void onPopupMenuItemClick(PopupWindow popupWindow, int position) {
                try {
                    switch (position) {
                        case 0:
                            showEmptyView();
                            popupWindow.dismiss();
                            break;
                        case 1:
                            showNetWorkErrorView();
                            popupWindow.dismiss();
                            break;
                        case 2:
                            showServerErrorView();
                            popupWindow.dismiss();
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
