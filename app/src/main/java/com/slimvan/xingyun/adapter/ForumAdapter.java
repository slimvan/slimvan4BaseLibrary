package com.slimvan.xingyun.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.bean.ForumBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xingyun on 2017/7/7.
 */

public class ForumAdapter extends BaseQuickAdapter<ForumBean.ResultsBean, ForumAdapter.ViewHolder> {

    public ForumAdapter(@Nullable List<ForumBean.ResultsBean> dataList) {
        super(R.layout.item_forum, dataList);
    }

    @Override
    protected void convert(ViewHolder holder, ForumBean.ResultsBean item) {
        if (item != null) {
            holder.tvTitle.setText(item.getDesc());
            holder.tvSource.setText(item.getSource());
            holder.tvType.setText(item.getType());
            holder.tvAuthor.setText(item.getWho());
            if (item.getImages() != null && !TextUtils.isEmpty(item.getImages().get(0))) {
                Glide.with(mContext).load(item.getImages().get(0)).into(holder.ivIcon);
                holder.ivIcon.setVisibility(View.VISIBLE);
            } else {
                holder.ivIcon.setVisibility(View.GONE);
            }
        }
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_source)
        TextView tvSource;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_author)
        TextView tvAuthor;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
