package com.slimvan.xingyun.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.adapter.RecyclerListAdapter;
import com.xingyun.slimvan.activity.BaseHeaderActivity;
import com.xingyun.slimvan.util.ToastUtils;
import com.xingyun.slimvan.view.DividerItemDecoration;
import com.xingyun.slimvan.view.LoadMoreView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 列表页   实现下拉刷新 上拉加载  （adapter添加HeaderView导致分割线出问题，暂时用NestedScrollView嵌套头布局处理）
 */
public class ListActivity extends BaseHeaderActivity {

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private RecyclerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.inject(this);

        setTitle("列表测试");
        initRefreshLayout();
        initRecyclerView();
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addData(getAddData());
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });
    }

    private void initRecyclerView() {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add(i + ">>item");
        }
        adapter = new RecyclerListAdapter(dataList);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

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

    private List<String> getAddData() {
        ArrayList<String> addData = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            addData.add("addItems>>");
        }
        return addData;
    }

    @Override
    public void onLeftClick(View v) {
        hideEmptyView();
    }

    @Override
    public void onRightClick(View v) {
        showEmptyView();
    }
}
