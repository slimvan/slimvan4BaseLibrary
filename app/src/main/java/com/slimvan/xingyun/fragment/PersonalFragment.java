package com.slimvan.xingyun.fragment;


import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.activity.DialogActivity;
import com.slimvan.xingyun.activity.SkinChangeActivity;
import com.slimvan.xingyun.activity.TipsDialogActivity;
import com.slimvan.xingyun.adapter.SimpleListRecyclerAdapter;
import com.slimvan.xingyun.bean.SimpleListBean;
import com.slimvan.xingyun.config.Constants;
import com.slimvan.xingyun.utils.UCropUtils;
import com.xingyun.slimvan.base.BaseFragment;
import com.xingyun.slimvan.listener.PermissionsResultListener;
import com.xingyun.slimvan.util.AppUtils;
import com.xingyun.slimvan.util.ImageUtils;
import com.xingyun.slimvan.view.DividerItemDecoration;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.iv_google_bg)
    ImageView ivGoogleBg;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    public static PersonalFragment getInstance() {
        PersonalFragment personalFragment = new PersonalFragment();
        return personalFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        unbinder = ButterKnife.bind(this, view);

        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        List<SimpleListBean> datas = new ArrayList<>();
        datas.add(new SimpleListBean("Dialog使用体验",R.mipmap.brazil));
        datas.add(new SimpleListBean("交互提示框&popupWindow",R.mipmap.argentina));
        datas.add(new SimpleListBean("换肤实现",R.mipmap.chile));
        SimpleListRecyclerAdapter adapter = new SimpleListRecyclerAdapter(datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent;
                switch (position) {
                    case 0:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, "personal_dialog");
                            intent = new Intent(mContext, DialogActivity.class);
                            ActivityCompat.startActivity(mContext, intent, options.toBundle());
                        } else {
                            intent = new Intent(mContext, DialogActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case 1:
                        intent = new Intent(mContext, TipsDialogActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(mContext, SkinChangeActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    /**
     * 图片高斯模糊测试
     */
    private void GaussianBlur() {
        Bitmap bm = ((BitmapDrawable) (ivGoogleBg).getDrawable()).getBitmap();
        Bitmap blurBitmap = ImageUtils.stackBlur(bm, 30, false);
        ivGoogleBg.setImageBitmap(blurBitmap);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_icon:
                final String[] permissions = new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE};
                requestPermissions("", permissions, Constants.PERMISSION_REQUEST_CODE, new PermissionsResultListener() {
                    @Override
                    public void onPermissionGranted() {
                        selectImg();
                    }

                    @Override
                    public void onPermissionDenied() {
                        showRationaleDialog("请允许" + AppUtils.getAppName(mContext) + "获取相应权限", permissions, Constants.PERMISSION_REQUEST_CODE);
                    }
                });
                break;
        }
    }

    /**
     * 选择图片并裁剪
     */
    private void selectImg() {
        BoxingConfig config = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG);
        // 启动缩略图界面, 依赖boxing-impl.
        Boxing.of(config).withIntent(getActivity(), BoxingActivity.class).start(PersonalFragment.this, Constants.BOXING_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.BOXING_IMAGE_REQUEST_CODE:
                if (data != null) {
                    List<BaseMedia> medias = Boxing.getResult(data);
                    if (medias != null) {
                        GaussianBlur(); //背景图高斯模糊
                        String path = medias.get(0).getPath();
                        File imgFile = new File(path);
                        UCropUtils.startCrop(getActivity(), PersonalFragment.this, Uri.fromFile(imgFile), 1, 1, 200, 200);
                    }
                }
                break;
            case UCrop.REQUEST_CROP:
                if (data != null) {
                    Uri output = UCrop.getOutput(data);
                    Glide.with(mContext).load(output).into(ivIcon);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
