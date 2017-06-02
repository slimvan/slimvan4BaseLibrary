package com.xingyun.slimvan.ui;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.widget.ListAdapter;

import com.xingyun.slimvan.R;
import com.xingyun.slimvan.adapter.TestListAdapter;
import com.xingyun.slimvan.util.EmptyUtils;

import java.util.ArrayList;
import java.util.List;

public class TestListActivity extends BaseListActivity {

    private TestListAdapter adapter;
    private List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);
        setTitle("hoopchina");
    }

    @Override
    protected void initListView() {
        for (int i = 0; i < 30; i++) {
            dataList.add(i + "_hoopchina");
        }
        adapter = new TestListAdapter(mContext, dataList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void refresh() {
        showProgressDialog("");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finishRefreshing();
                hideProgressDialog();
            }
        }, 2000);
    }

    @Override
    protected void loadMore() {
        showProgressDialog("拼命加载中");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finishLoadMore();
                hideProgressDialog();
            }
        }, 2000);
    }

    @Override
    public void onLeftClick(View v) {
        super.onLeftClick(v);
        finish();
    }
}
