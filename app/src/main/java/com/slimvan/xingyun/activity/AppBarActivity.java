package com.slimvan.xingyun.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.slimvan.xingyun.R;
import com.slimvan.xingyun.fragment.RecyclerListFragment;
import com.xingyun.slimvan.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AppBarActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tab_layout)
    TabLayout tabLayout;
    @InjectView(R.id.view_pager)
    ViewPager viewPager;
    private TabLayoutPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar);
        ButterKnife.inject(this);

        initToolBar();
        initTabLayout();

    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("TAB"));
        tabLayout.addTab(tabLayout.newTab().setText("TAB"));
        tabLayout.setTabTextColors(ContextCompat.getColor(mContext, R.color.colorAccent), ContextCompat.getColor(mContext, R.color.colorAccent));
        pagerAdapter = new TabLayoutPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initToolBar() {
        toolbar.setTitle("This is ToolBar");
    }

    private class TabLayoutPagerAdapter extends FragmentStatePagerAdapter {
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
    }
}
