package com.slimvan.xingyun.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.slimvan.xingyun.R;
import com.slimvan.xingyun.fragment.RecyclerListFragment;
import com.xingyun.slimvan.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppBarActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private TabLayoutPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar);
        ButterKnife.bind(this);

        initToolBar();
        initTabLayout();

    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("TAB"));
        tabLayout.addTab(tabLayout.newTab().setText("TAB"));
        pagerAdapter = new TabLayoutPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setTabTextColors(ContextCompat.getColor(mContext, R.color.colorAccent), ContextCompat.getColor(mContext, R.color.colorAccent));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initToolBar() {
        toolbar.setTitle("This is ToolBar");
    }

    private class TabLayoutPagerAdapter extends FragmentPagerAdapter {
        public TabLayoutPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new RecyclerListFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return "TAB";
        }
    }
}
