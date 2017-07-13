package com.slimvan.xingyun.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.adapter.RecyclerListAdapter;
import com.slimvan.xingyun.bean.DoubanBookList;
import com.slimvan.xingyun.http.MSubscriber;
import com.slimvan.xingyun.http.RetrofitBuilder;
import com.slimvan.xingyun.http.api.DoubanApi;
import com.xingyun.slimvan.base.BaseFragment;
import com.xingyun.slimvan.util.LogUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerListFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private RecyclerListAdapter adapter;
    private int currentPage = 1;
    private ArrayList<DoubanBookList.BooksBean> dataList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initRefreshLayout();
        initRecyclerView();
        getData();
        return view;
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
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
