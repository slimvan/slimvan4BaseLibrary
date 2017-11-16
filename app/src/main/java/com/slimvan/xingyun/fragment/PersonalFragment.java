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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.config.BoxingCropOption;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing.utils.BoxingFileHelper;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.bumptech.glide.Glide;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.activity.DialogActivity;
import com.slimvan.xingyun.activity.PhotoPreviewActivity;
import com.slimvan.xingyun.activity.TipsDialogActivity;
import com.slimvan.xingyun.config.Constants;
import com.slimvan.xingyun.utils.UCropUtils;
import com.xingyun.slimvan.base.BaseFragment;
import com.xingyun.slimvan.listener.PermissionsResultListener;
import com.xingyun.slimvan.util.AppUtils;
import com.xingyun.slimvan.util.CacheUtils;
import com.xingyun.slimvan.util.ConvertUtils;
import com.xingyun.slimvan.util.FileUtils;
import com.xingyun.slimvan.util.ImageUtils;
import com.xingyun.slimvan.util.SDCardUtils;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.R.attr.data;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.tv_dialog)
    TextView tvDialog;
    @BindView(R.id.tv_webView)
    ImageView tvWebView;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.iv_google_bg)
    ImageView ivGoogleBg;

    public static PersonalFragment getInstance() {
        PersonalFragment personalFragment = new PersonalFragment();
        return personalFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
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

    @OnClick({R.id.tv_dialog, R.id.tv_webView, R.id.tv_tips})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_dialog:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, "personal_dialog");
                    intent = new Intent(mContext, DialogActivity.class);
                    ActivityCompat.startActivity(mContext, intent, options.toBundle());
                } else {
                    intent = new Intent(mContext, DialogActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_webView:
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
            case R.id.tv_tips:
                intent = new Intent(mContext, TipsDialogActivity.class);
                startActivity(intent);
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
                        UCropUtils.startCrop(getActivity(),PersonalFragment.this,Uri.fromFile(imgFile),1,1,200,200);
                    }
                }
                break;
            case UCrop.REQUEST_CROP:
                if (data != null) {
                    Uri output = UCrop.getOutput(data);
                    Glide.with(mContext).load(output).into(tvWebView);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
