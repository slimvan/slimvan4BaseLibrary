package com.slimvan.xingyun.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slimvan.xingyun.R;

import java.util.List;

/**
 * 列表适配器
 * Created by xingyun on 2017/6/22.
 */

public class RecyclerListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public RecyclerListAdapter(List<String> dataList) {
        super(R.layout.item_list, dataList);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, String item) {
        viewHolder.setText(R.id.tv_text, item);
    }

}
