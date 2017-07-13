package com.slimvan.xingyun.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slimvan.xingyun.R;
import com.slimvan.xingyun.utils.GlideImageLoader;
import com.xingyun.slimvan.base.BaseFragment;
import com.xingyun.slimvan.util.ToastUtils;
import com.yyydjk.library.BannerLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.banner)
    BannerLayout banner;
    private List<String> imageList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        unbinder = ButterKnife.bind(this, view);

        initBanner();
        return view;
    }

    private void initBanner() {
        imageList = new ArrayList<>();
        imageList.add("http://pic.90sjimg.com/back_pic/qk/back_origin_pic/00/03/75/9b84e7c1fca9670e481323096a63e0cc.jpg");
        imageList.add("http://pic.90sjimg.com/back_pic/qk/back_origin_pic/00/01/44/40b366afb1c10d58fa05d9c419802f24.jpg");
        imageList.add("http://pic.90sjimg.com/back_pic/00/00/69/40/f5b8e8d8206523e353a7335ae8c66e86.jpg");
        imageList.add("http://img4.web07.cn/UPics/Picture/2016/1116/208271160911291.jpg");

        //设置加载器
        banner.setImageLoader(new GlideImageLoader());
        //网络地址
        banner.setViewUrls(imageList);
        //添加点击监听
        banner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int i) {
                ToastUtils.showShort(i);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
