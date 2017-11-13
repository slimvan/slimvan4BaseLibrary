package com.slimvan.xingyun.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.github.chrisbanes.photoview.PhotoView;
import com.slimvan.xingyun.R;
import com.xingyun.slimvan.base.BaseFragment;
import com.xingyun.slimvan.util.TimeUtils;
import com.xingyun.slimvan.util.ToastUtils;
import com.xingyun.slimvan.view.DialogHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 查看大图Fragment
 */
public class PhotoPreviewFragment extends BaseFragment {
    @BindView(R.id.photoView)
    PhotoView photoView;
    Unbinder unbinder;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private String path;


    public static PhotoPreviewFragment getInstance(){
        return new PhotoPreviewFragment();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public PhotoView getPhotoView() {
        return photoView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_preview, container, false);
        unbinder = ButterKnife.bind(this, view);

        initPhotoView();
        initProgressBar();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (photoView != null) {
                    photoView.setTransitionName("image");
                }
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (photoView != null) {
                    photoView.setTransitionName(null);
                }
            }
        }
    }

    private void initProgressBar() {
        progressBar.animate();
    }

    /**
     * PhotoView初始化
     */
    private void initPhotoView() {
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
//                    ActivityCompat.finishAfterTransition(getActivity());
//                }else{
                    getActivity().finish();
//                }
            }
        });
        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDialog();
                return true;
            }
        });
        Glide.with(mContext).load(path).into(new GlideDrawableImageViewTarget(photoView) {
            @Override
            public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                super.onResourceReady(drawable, anim);
                progressBar.setVisibility(View.GONE);
                //图片加载完成的回调中，启动过渡动画
//                getActivity().supportStartPostponedEnterTransition();
            }
        });
    }

    private void showDialog() {
        String[] items = new String[]{"保存图片"};
        DialogHelper.showListDialog(mContext, "您可以", items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                savePhoto();
            }
        });
    }

    /**
     * 保存图片到本地 并更新相册
     */
    private void savePhoto() {
        showProgressDialog("图片保存中，请稍后");
        OkHttpUtils
                .get()
                .url(path)
                .build()
                .execute(
                        new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(),
                                TimeUtils.getNowMills() + ".jpg") {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                hideProgressDialog();
                                ToastUtils.showShort("保存失败");
                            }

                            @Override
                            public void onResponse(File response, int id) {
                                hideProgressDialog();
                                if (response != null) {
                                    ToastUtils.showShort("保存成功");
                                    mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                            Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                                }
                            }

                            @Override
                            public void inProgress(float progress, long total, int id) {
                                super.inProgress(progress, total, id);
                            }
                        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
