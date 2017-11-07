package com.slimvan.xingyun.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.slimvan.xingyun.R;
import com.xingyun.slimvan.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 查看大图Fragment
 */
public class PhotoPreviewFragment extends BaseFragment {
    @BindView(R.id.photoView)
    PhotoView photoView;
    Unbinder unbinder;

    private String path;

    public PhotoPreviewFragment(String path) {
        this.path = path;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_preview, container, false);
        unbinder = ButterKnife.bind(this, view);

        initPhotoView();
        return view;
    }

    /**
     * PhotoView初始化
     */
    private void initPhotoView() {
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        Glide.with(mContext).load(path).into(photoView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
