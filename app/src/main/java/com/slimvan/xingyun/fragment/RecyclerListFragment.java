package com.slimvan.xingyun.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.adapter.RecyclerListAdapter;
import com.xingyun.slimvan.fragment.BaseFragment;
import com.xingyun.slimvan.util.ToastUtils;
import com.xingyun.slimvan.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerListFragment extends BaseFragment {


    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    private RecyclerListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_list, container, false);
        ButterKnife.inject(this, view);

        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
