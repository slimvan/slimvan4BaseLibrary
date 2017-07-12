package com.slimvan.xingyun.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.adapter.RecyclerListAdapter;
import com.slimvan.xingyun.bean.DoubanBookList;
import com.slimvan.xingyun.bean.WelfareBean;
import com.slimvan.xingyun.http.MSubscriber;
import com.slimvan.xingyun.http.RetrofitBuilder;
import com.slimvan.xingyun.http.api.GankApi;
import com.slimvan.xingyun.adapter.HomePageAdapter;
import com.xingyun.slimvan.fragment.BaseFragment;
import com.xingyun.slimvan.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 */
public class HomePageFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;


    private HomePageAdapter adapter;
    private List<WelfareBean.ResultsBean> dataList = new ArrayList<>();
    private int currentPage = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        unbinder = ButterKnife.bind(this, view);
        initRefreshLayout();
        initRecyclerView();
        getData();


        return view;
    }


    private void getData() {
        RetrofitBuilder.build2(GankApi.class).gankData("福利", "10", currentPage).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new MSubscriber<String>(mContext, true, true) {
                    @Override
                    public void onSuccess(String s) {
                        WelfareBean mData = new Gson().fromJson(s, WelfareBean.class);
                        if (mData != null) {
                            adapter.setNewData(mData.getResults());
                            adapter.disableLoadMoreIfNotFullPage(recyclerView);
                        }
                    }

                    @Override
                    public void errorCallBack(Throwable e) {

                    }
                });
    }

    private void initRecyclerView() {
        adapter = new HomePageAdapter(dataList);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                    LogUtils.i("onLoadMore", "onLoadMore");
                    getMoreData();
            }
        }, recyclerView);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
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

    private void getMoreData() {
        RetrofitBuilder.build2(GankApi.class).gankData("福利", "10", currentPage + 1).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new MSubscriber<String>(mContext, false, true) {
                    @Override
                    public void onSuccess(String s) {
                        WelfareBean mData = new Gson().fromJson(s, WelfareBean.class);
                        if (mData != null) {
                            adapter.addData(mData.getResults());
                            currentPage++;
                        }
                        adapter.loadMoreComplete();
                    }

                    @Override
                    public void errorCallBack(Throwable e) {
                        adapter.loadMoreFail();
                    }
                });
    }

    private void refreshData() {
        RetrofitBuilder.build2(GankApi.class).gankData("福利", "10", currentPage).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new MSubscriber<String>(mContext, true, true) {
                    @Override
                    public void onSuccess(String s) {
                        WelfareBean mData = new Gson().fromJson(s, WelfareBean.class);
                        if (mData != null) {
                            adapter.setNewData(mData.getResults());
                        }
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void errorCallBack(Throwable e) {
                        refreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
