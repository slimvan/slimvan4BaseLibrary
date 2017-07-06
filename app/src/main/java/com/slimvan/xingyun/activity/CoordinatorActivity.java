package com.slimvan.xingyun.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.adapter.RecyclerViewAdapter;
import com.xingyun.slimvan.activity.BaseActivity;
import com.xingyun.slimvan.util.ToastUtils;
import com.xingyun.slimvan.view.DividerItemDecoration;
import com.xingyun.slimvan.view.LoadMoreView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CoordinatorActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;
    private RecyclerViewAdapter  adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        ButterKnife.bind(this);

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
//                        adapter.addData();
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });
    }

    private List<String> getAddData() {
        ArrayList<String> addData = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            addData.add("addItems>>");
        }
        return null;
    }

    private void initRecyclerView() {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            dataList.add(i + ">>item");
        }
        adapter = new RecyclerViewAdapter (dataList);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(adapter);

//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ToastUtils.showShort("点击了" + position);
//            }
//        });
//
//        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
//                ToastUtils.showShort("长按了" + position);
//                return true;
//            }
//        });
        recyclerView.setFocusable(false);
    }

    @OnClick(R.id.fab_add)
    public void onViewClicked() {
        ToastUtils.showShort("FAB Click");
    }
}
