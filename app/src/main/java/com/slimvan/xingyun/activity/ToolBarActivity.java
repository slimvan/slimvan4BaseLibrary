package com.slimvan.xingyun.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.adapter.RecyclerListAdapter;
import com.slimvan.xingyun.bean.DoubanBookList;
import com.slimvan.xingyun.http.api.DoubanApi;
import com.xingyun.slimvan.base.BaseRefreshActivity;
import com.xingyun.slimvan.http.MSubscriber;
import com.xingyun.slimvan.http.RetrofitBuilder;
import com.xingyun.slimvan.util.LogUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 测试BaseRefreshActivity
 */
public class ToolBarActivity extends BaseRefreshActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private RecyclerListAdapter adapter;
    private int currentPage = 1;
    private ArrayList<DoubanBookList.BooksBean> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar);
        ButterKnife.bind(this);

        initRecyclerView();
        getData();
    }

    @Override
    public void onStateLayoutClick() {

    }

    @Override
    public void onLeftClick(View v) {

    }

    @Override
    public void onRightClick(View v) {

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
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
//        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        adapter.disableLoadMoreIfNotFullPage(recyclerView);
    }



    @Override
    protected void onRefresh() {
        refreshData();
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
                        finishRefresh();
                    }

                    @Override
                    public void errorCallBack(Throwable e) {
                        Log.i(TAG, "error");
                        finishRefresh();
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


}
