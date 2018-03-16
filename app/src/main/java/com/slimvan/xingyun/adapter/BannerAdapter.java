package com.slimvan.xingyun.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slimvan.xingyun.R;

import java.util.List;

/**
 * author xingyun
 * email: slimvan@163.com
 * date 2018/3/2.
 * desc:
 */

public class BannerAdapter extends BaseQuickAdapter<String,BaseViewHolder>{
    public BannerAdapter(@Nullable List<String> data) {
        super(R.layout.item_banner,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
