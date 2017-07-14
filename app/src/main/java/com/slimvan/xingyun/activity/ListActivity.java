package com.slimvan.xingyun.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.adapter.RecyclerListAdapter;
import com.slimvan.xingyun.bean.DoubanBookList;
import com.slimvan.xingyun.http.api.DoubanApi;
import com.xingyun.slimvan.base.BaseRefreshLoadMoreActivity;
import com.xingyun.slimvan.bean.PopupListBean;
import com.xingyun.slimvan.http.MSubscriber;
import com.xingyun.slimvan.http.RetrofitBuilder;
import com.xingyun.slimvan.util.ToastUtils;
import com.xingyun.slimvan.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 测试BaseRefreshLoadMoreActivity  有瑕疵 加载更多略有卡顿
 */
public class ListActivity extends BaseRefreshLoadMoreActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private RecyclerListAdapter adapter;
    private ArrayList<DoubanBookList.BooksBean> dataList = new ArrayList<>();
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        setTitle("列表测试");
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


    @Override
    protected void onRefresh() {
        refreshData();
    }

    @Override
    protected void onLoadMore() {
        getMoreData();
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
                        finishLoadMore();
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
