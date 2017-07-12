package com.slimvan.xingyun.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.bean.WelfareBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xingyun on 2017/7/7.
 */

public class HomePageAdapter extends BaseQuickAdapter<WelfareBean.ResultsBean, BaseViewHolder> {
    private final ArrayList<Integer> mHeights;

    public HomePageAdapter(List<WelfareBean.ResultsBean> dataList) {
        super(R.layout.item_stagger, dataList);
        mHeights = new ArrayList<>();
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, WelfareBean.ResultsBean item) {
        int position = viewHolder.getLayoutPosition();
        // 随机高度, 模拟瀑布效果.
        if (mHeights.size() <= position) {
            mHeights.add((int) (400 + Math.random() * 300));
        }
        ViewGroup.LayoutParams lp = viewHolder.itemView.getLayoutParams();
        lp.height = mHeights.get(position);
        viewHolder.itemView.setLayoutParams(lp);

        ImageView imageView = viewHolder.getView(R.id.image);
        Glide.with(mContext).load(item.getUrl()).into(imageView);
    }
}
