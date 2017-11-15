package com.slimvan.xingyun.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.activity.WebViewActivity;
import com.slimvan.xingyun.adapter.ForumAdapter;
import com.slimvan.xingyun.bean.ForumBean;
import com.slimvan.xingyun.http.api.GankApi;
import com.slimvan.xingyun.utils.GlideImageLoader;
import com.xingyun.slimvan.base.BaseFragment;
import com.xingyun.slimvan.http.HttpConfig;
import com.xingyun.slimvan.http.MSubscriber;
import com.xingyun.slimvan.http.RetrofitBuilder;
import com.xingyun.slimvan.util.ConvertUtils;
import com.xingyun.slimvan.util.LogUtils;
import com.xingyun.slimvan.util.ToastUtils;
import com.yyydjk.library.BannerLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForumFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    Unbinder unbinder;
//    @BindView(R.id.banner)
    BannerLayout banner;

    private List<String> imageList;

    private ForumAdapter adapter;
    private List<ForumBean.ResultsBean> dataList = new ArrayList<>();
    private int currentPage = 1;

    public static ForumFragment getInstance() {
        ForumFragment fragment = new ForumFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        unbinder = ButterKnife.bind(this, view);

        initRefreshLayout();
//        initBanner();
        initRecyclerView();

//        getData();
//        getDataByOkhttp();


        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getDataByOkhttp();
        }
    }

    private void initRecyclerView() {
        adapter = new ForumAdapter(dataList);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                LogUtils.i("onLoadMore", "onLoadMore");
                getMoreData();
            }
        }, recyclerView);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        View headerView = LayoutInflater.from(mContext).inflate(R.layout.layout_banner,null);
        ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(150));
        headerView.setLayoutParams(params);
        banner= (BannerLayout) headerView.findViewById(R.id.banner);
        adapter.addHeaderView(headerView);
        initBanner();

        adapter.disableLoadMoreIfNotFullPage(recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ForumBean.ResultsBean> currentData = adapter.getData();
                if (currentData != null) {
                    ForumBean.ResultsBean item = currentData.get(position);
                    if (item != null) {
                        String url = item.getUrl();
                        Intent intent = new Intent(mContext, WebViewActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void initBanner() {
        imageList = new ArrayList<>();
        imageList.add("http://pic.90sjimg.com/back_pic/qk/back_origin_pic/00/03/75/9b84e7c1fca9670e481323096a63e0cc.jpg");
        imageList.add("http://pic.90sjimg.com/back_pic/qk/back_origin_pic/00/01/44/40b366afb1c10d58fa05d9c419802f24.jpg");
        imageList.add("http://pic.90sjimg.com/back_pic/00/00/69/40/f5b8e8d8206523e353a7335ae8c66e86.jpg");
        imageList.add("http://img4.web07.cn/UPics/Picture/2016/1116/208271160911291.jpg");

        //设置加载器
        banner.setImageLoader(new GlideImageLoader());
        //网络地址
        banner.setViewUrls(imageList);
        //添加点击监听
        banner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int i) {
                ToastUtils.showShort(i + "");
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

    private void getData() {
        RetrofitBuilder.build(GankApi.class, HttpConfig.GANK_BASE_URL).gankData("all", "10", currentPage).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new MSubscriber<String>(mContext, true, true) {
                    @Override
                    public void onSuccess(String s) {
                        ForumBean mData = new Gson().fromJson(s, ForumBean.class);
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

    /**
     * okHttp get请求
     */
    private void getDataByOkhttp() {
        RequestCall request = OkHttpUtils
                .get()
                .url(HttpConfig.GANK_BASE_URL + "all/10/1")
                .build();
        request.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                ForumBean forumBean = new Gson().fromJson(response, ForumBean.class);
                if (forumBean != null) {
                    adapter.setNewData(forumBean.getResults());
                    adapter.disableLoadMoreIfNotFullPage(recyclerView);
                }
            }
        });
    }


    private void refreshData() {
        RetrofitBuilder.build(GankApi.class, HttpConfig.GANK_BASE_URL).gankData("all", "10", currentPage).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new MSubscriber<String>(mContext, true, true) {
                    @Override
                    public void onSuccess(String s) {
                        ForumBean mData = new Gson().fromJson(s, ForumBean.class);
                        if (mData != null) {
                            banner.setViewUrls(imageList);
                            adapter.setNewData(mData.getResults());
                            adapter.disableLoadMoreIfNotFullPage(recyclerView);
                            refreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void errorCallBack(Throwable e) {
                        refreshLayout.setRefreshing(false);
                    }
                });
    }

    private void getMoreData() {
        RetrofitBuilder.build(GankApi.class, HttpConfig.GANK_BASE_URL).gankData("all", "10", currentPage + 1).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new MSubscriber<String>(mContext, false, true) {
                    @Override
                    public void onSuccess(String s) {
                        ForumBean mData = new Gson().fromJson(s, ForumBean.class);
                        if (mData != null) {
                            adapter.addData(mData.getResults());
                            currentPage++;
                            adapter.loadMoreComplete();
                        }
                    }

                    @Override
                    public void errorCallBack(Throwable e) {
                        adapter.loadMoreFail();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
