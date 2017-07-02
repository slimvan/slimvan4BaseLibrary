package com.xingyun.slimvan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingyun.slimvan.R;
import com.xingyun.slimvan.bean.PopupListBean;

import java.util.List;

/**
 * popupWindow列表适配器
 * Created by xingyun on 2017/6/29.
 */

public class PopupListAdapter extends BaseAdapter {
    private Context mContext;
    private List<PopupListBean> dataList;

    public PopupListAdapter(Context mContext, List<PopupListBean> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_popupwindow_listview, null);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (dataList != null) {
            PopupListBean item = dataList.get(position);
            if (item != null) {
                String title = item.getTitle();
                int resource = item.getResource();
                holder.tvTitle.setText(title);
                if (resource != 0) {
                    holder.ivIcon.setImageResource(resource);
                }
            }
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvTitle;
        ImageView ivIcon;
    }
}
