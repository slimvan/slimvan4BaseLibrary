package com.slimvan.xingyun.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.fragment.PhotoPreviewFragment;
import com.xingyun.slimvan.base.BaseActivity;
import com.xingyun.slimvan.util.BarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

import static com.xingyun.slimvan.util.ClipboardUtils.getIntent;


/**
 * 图片浏览器
 */
public class PhotoPreviewActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;

    private ArrayList<String> images = new ArrayList<>();
    private int defaultPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setExitTransition(new Explode());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);
        ButterKnife.bind(this);
        BarUtils.setColor(PhotoPreviewActivity.this, Color.parseColor("#000000"));

        images = (ArrayList<String>) getIntent().getSerializableExtra("image_list");
        defaultPosition = getIntent().getIntExtra("default_position", -1);

        if (images != null) {
            initViewPager();
        }
    }

    private List<Fragment> fragmentList = new ArrayList<>();

    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                PhotoPreviewFragment instance = PhotoPreviewFragment.getInstance();
                instance.setPath(images.get(position));
                fragmentList.add(instance);
                return instance;
            }

            @Override
            public int getCount() {
                return images.size();
            }
        });

        //默认位置
        viewPager.setCurrentItem(defaultPosition);
        //指示器
        indicator.setViewPager(viewPager);
        if (images.size() <= 10) {
            indicator.setVisibility(View.VISIBLE);
        }else{
            indicator.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
