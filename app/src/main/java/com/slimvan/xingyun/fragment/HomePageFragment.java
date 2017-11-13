package com.slimvan.xingyun.fragment;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.activity.PhotoPreviewActivity;
import com.slimvan.xingyun.bean.WelfareBean;
import com.slimvan.xingyun.http.api.GankApi;
import com.slimvan.xingyun.adapter.HomePageAdapter;
import com.xingyun.slimvan.base.BaseFragment;
import com.xingyun.slimvan.http.HttpConfig;
import com.xingyun.slimvan.http.MSubscriber;
import com.xingyun.slimvan.http.RetrofitBuilder;
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

    public static HomePageFragment getInstance() {
        HomePageFragment fragment = new HomePageFragment();
        return fragment;
    }

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
        RetrofitBuilder.build(GankApi.class, HttpConfig.GANK_BASE_URL).gankData("福利", "10", currentPage).
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

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<WelfareBean.ResultsBean> data = adapter.getData();
                if (data != null) {
                    ArrayList<String> images = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        WelfareBean.ResultsBean resultsBean = data.get(i);
                        if (resultsBean != null) {
                            String url = resultsBean.getUrl();
                            images.add(url);
                        }
                    }
//                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
//                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
//                                getActivity(),view,"image");
//                        Intent intent = new Intent(mContext, PhotoPreviewActivity.class);
//                        intent.putExtra("image_list",images);
//                        intent.putExtra("default_position",position);
//                        ActivityCompat.startActivity(mContext,intent,options.toBundle());
//                    }else {
                    Intent intent = new Intent(mContext, PhotoPreviewActivity.class);
                    intent.putExtra("image_list", images);
                    intent.putExtra("default_position", position);
                    mContext.startActivity(intent);
//                }
                }
            }
        });
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
        RetrofitBuilder.build(GankApi.class, HttpConfig.GANK_BASE_URL).gankData("福利", "10", currentPage + 1).
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
        RetrofitBuilder.build(GankApi.class, HttpConfig.GANK_BASE_URL).gankData("福利", "10", currentPage).
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
