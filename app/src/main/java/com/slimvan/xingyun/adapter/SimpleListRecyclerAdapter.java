package com.slimvan.xingyun.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.bean.SimpleListBean;

import java.util.List;

/**
 * 列表适配器
 * Created by xingyun on 2018/1/2.
 */

public class SimpleListRecyclerAdapter extends BaseQuickAdapter<SimpleListBean, BaseViewHolder> {
    public SimpleListRecyclerAdapter(@Nullable List<SimpleListBean> data) {
        super(R.layout.item_simple_listview, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, SimpleListBean item) {
        if (item.getIcon() != 0) {
            holder.setImageResource(R.id.iv_icon, item.getIcon());
            holder.setVisible(R.id.iv_icon, true);
        } else {
            holder.setVisible(R.id.iv_icon, false);
        }
        holder.setText(R.id.tv_title, item.getTitle());
    }
}
