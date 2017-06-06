package com.slimvan.xingyun.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.slimvan.xingyun.R;
import com.slimvan.xingyun.fragment.ForumFragment;
import com.slimvan.xingyun.fragment.HomePageFragment;
import com.slimvan.xingyun.fragment.PersonalFragment;
import com.xingyun.slimvan.activity.BaseFragmentActivity;
import com.xingyun.slimvan.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseFragmentActivity {

    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_title_right)
    TextView tvTitleRight;
    @InjectView(R.id.ll_title_bar)
    RelativeLayout llTitleBar;
    @InjectView(R.id.fl_content)
    FrameLayout flContent;
    @InjectView(R.id.tv_tab_1)
    TextView tvTab1;
    @InjectView(R.id.tv_tab_2)
    TextView tvTab2;
    @InjectView(R.id.tv_tab_3)
    TextView tvTab3;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initTitleBar();
        showFragment(fragments.get(0),false);

    }

    /**
     * 初始化标题栏
     */
    private void initTitleBar() {
        ivBack.setVisibility(View.GONE);
        tvTitle.setText("HoopChina");
    }

    @Override
    protected List<Fragment> setFragments() {
        fragments = new ArrayList<>();
        fragments.add(new HomePageFragment());
        fragments.add(new ForumFragment());
        fragments.add(new PersonalFragment());
        return fragments;
    }

    @Override
    protected int setContainerId() {
        return R.id.fl_content;
    }

    @OnClick({R.id.iv_back, R.id.tv_title_right, R.id.tv_tab_1, R.id.tv_tab_2, R.id.tv_tab_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_title_right:
                ToastUtils.showShort("尚未开放");
                break;
            case R.id.tv_tab_1:
                showFragment(fragments.get(0),false);
                break;
            case R.id.tv_tab_2:
                showFragment(fragments.get(1),false);
                break;
            case R.id.tv_tab_3:
                showFragment(fragments.get(2),false);
                break;
        }
    }
}
