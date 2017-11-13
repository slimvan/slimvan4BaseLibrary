package com.slimvan.xingyun.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.slimvan.xingyun.R;
import com.xingyun.slimvan.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageActivity extends BaseActivity {

    @BindView(R.id.iv_icon)
    ImageView ivIcon;

    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);

        path = getIntent().getStringExtra("path");

        Glide.with(mContext).load(path).into(ivIcon);
    }
}
