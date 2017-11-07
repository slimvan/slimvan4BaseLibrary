package com.slimvan.xingyun.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.slimvan.xingyun.R;
import com.slimvan.xingyun.fragment.PhotoPreviewFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;


/**
 * 图片浏览器
 */
public class PhotoPreviewActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;

    private ArrayList<String> images = new ArrayList<>();
    private int defaultPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_photo_preview);
        ButterKnife.bind(this);

        images = (ArrayList<String>) getIntent().getSerializableExtra("image_list");
        defaultPosition = getIntent().getIntExtra("default_position", -1);

        if (images != null) {
            initViewPager();
        }
    }

    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new PhotoPreviewFragment(images.get(position));
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
    }
}
