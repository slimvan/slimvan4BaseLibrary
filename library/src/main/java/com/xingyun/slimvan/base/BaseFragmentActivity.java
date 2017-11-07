package com.xingyun.slimvan.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import java.util.List;


/**
 * FragmentActivity基类
 */
public abstract class BaseFragmentActivity extends BaseActivity {

    private List<Fragment> fragments;
    private int containerId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initFragments();
    }

    /**
     * 初始化Fragment
     */
    protected void initFragments() {
        FragmentTransaction ts = getSupportFragmentManager().beginTransaction();
        fragments = setFragments();
        containerId = setContainerId();
        if (fragments != null && containerId != 0) {
            for (int i = 0; i < fragments.size(); i++) {
                ts.add(containerId, fragments.get(i), fragments.get(i).getClass().getName());
            }
            ts.commit();
        }
    }

    /**
     * 显示指定的Fragment
     *
     * @param fragment 指定Fragment
     * @param create   是否需要重新实例化 为true时会执行该Fragment的onCreate方法
     */
    protected void showFragment(Fragment fragment, boolean create) {
        FragmentTransaction ts = getSupportFragmentManager().beginTransaction();
        if (fragment != null) {
            if (create) {
                ts.detach(fragment);
                ts.attach(fragment);
                ts.commit();
            } else {
                for (int i = 0; i < fragments.size(); i++) {
                    ts.hide(fragments.get(i));
                }
                ts.show(fragment);
                ts.commit();
            }
        }
    }

    /**
     * 隐藏指定Fragment
     *
     * @param fragment
     */
    protected void hideFragment(Fragment fragment) {
        FragmentTransaction ts = getSupportFragmentManager().beginTransaction();
        if (fragment != null) {
            ts.hide(fragment);
            ts.commit();
        }
    }


    /**
     * 初始化Fragment
     */
    protected abstract List<Fragment> setFragments();

    /**
     * 初始化容器
     */
    protected abstract int setContainerId();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Fragment fragment : fragments) {
            fragment.onActivityResult(requestCode,resultCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
