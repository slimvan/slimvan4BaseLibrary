package com.slimvan.xingyun.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.adapter.BannerAdapter;
import com.slimvan.xingyun.view.banner.AnimManager;
import com.slimvan.xingyun.view.banner.GalleryRecyclerView;
import com.xingyun.slimvan.base.BaseActivity;
import com.xingyun.slimvan.util.ToastUtils;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RvGalleryActivity extends BaseActivity {

    @BindView(R.id.rv_banner)
    GalleryRecyclerView rvBanner;
    @BindView(R.id.banner)
    MZBannerView banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_gallery);
        ButterKnife.bind(this);

        initBanner();
        initMZBanner();
    }

    private void initMZBanner() {
        // 设置数据
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("");
        }
        banner.setPages(list, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        banner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //抬起
                    if (banner != null) {
                        banner.start();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //按下
                    if (banner != null) {
                        banner.pause();
                    }
                }
                return false;
            }
        });
        banner.start();
    }

    public class BannerViewHolder implements MZViewHolder<String> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, String data) {
            // 数据绑定
            Glide.with(RvGalleryActivity.this).load(data).error(R.mipmap.google).into(mImageView);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.start();//开始轮播
    }


    private void initBanner() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            datas.add("");
        }
        BannerAdapter adapter = new BannerAdapter(datas);
        rvBanner.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvBanner.setAdapter(adapter);
        rvBanner.initFlingSpeed(5000)                                   // 设置滑动速度（像素/s）
                .initPageParams(0, 30)                                 // 设置页边距和左右图片的可见宽度，单位dp
                .setAnimFactor(0.15f)                                   // 设置切换动画的参数因子
                .setAnimType(AnimManager.ANIM_BOTTOM_TO_TOP)            // 设置切换动画类型，目前有AnimManager.ANIM_BOTTOM_TO_TOP和目前有AnimManager.ANIM_TOP_TO_BOTTOM
                .setOnItemClickListener(new GalleryRecyclerView.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ToastUtils.showShort(position + "");
                    }
                });                          // 设置点击事件
    }

}
