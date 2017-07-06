package com.slimvan.xingyun.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.bean.DoubanBookList;
import com.xingyun.slimvan.view.LoadMoreView;

import java.util.List;

/**
 * 列表适配器
 * Created by xingyun on 2017/6/22.
 */

public class RecyclerListAdapter extends BaseQuickAdapter<DoubanBookList.BooksBean, BaseViewHolder> {

    public RecyclerListAdapter(List<DoubanBookList.BooksBean> dataList) {
        super(R.layout.item_image, dataList);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, DoubanBookList.BooksBean item) {
        int position = viewHolder.getLayoutPosition();
        ImageView imageView = viewHolder.getView(R.id.image);
        Glide.with(mContext).load(item.getImages().getLarge()).into(imageView);
    }


}
