package com.slimvan.xingyun.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.widget.MsgView;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.bean.TabEntity;
import com.slimvan.xingyun.fragment.ForumFragment;
import com.slimvan.xingyun.fragment.HomePageFragment;
import com.slimvan.xingyun.fragment.PersonalFragment;
import com.xingyun.slimvan.adapter.PopupListAdapter;
import com.xingyun.slimvan.base.BaseActivity;
import com.xingyun.slimvan.bean.PopupListBean;
import com.xingyun.slimvan.util.AppUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.commonTabLayout)
    CommonTabLayout commonTabLayout;

    private ArrayList<Fragment> fragments;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private int[] mTitles = {R.string.tab_home, R.string.tab_forum, R.string.tab_mine};
    private int[] mIconselectIds = {
            R.mipmap.ic_home,
            R.mipmap.ic_message,
            R.mipmap.ic_mine,};

    private int[] mIconUnSelectIds = {
            R.mipmap.ic_home_1,
            R.mipmap.ic_message_1,
            R.mipmap.ic_mine_1};
    private boolean ripple = true; //Tab点击水波纹效果  默认开

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initTitleBar();
//        showFragment(fragments.get(0), false);
        assignView();
        testTips();
    }

    private void testTips() {
        //设置购物右上角角标
        commonTabLayout.showMsg(0, 1);
        commonTabLayout.setMsgMargin(0, -8, 5);

        commonTabLayout.showMsg(1, 2);
        commonTabLayout.setMsgMargin(1, -8, 5);

        commonTabLayout.showMsg(2, 3);
        commonTabLayout.setMsgMargin(2, -8, 5);

        //设置不同颜色的msgView
        for (int i = 0; i < 3; i++) {
            MsgView msgView = commonTabLayout.getMsgView(i);
            if (msgView != null) {
                switch (i) {
                    case 0:
                        msgView.setBackgroundColor(Color.parseColor("#ec3a2d"));
                        break;
                    case 1:
                        msgView.setBackgroundColor(Color.parseColor("#FF4081"));
                        break;
                    case 2:
                        msgView.setBackgroundColor(Color.parseColor("#303F9F"));
                        break;
                }
            }
        }

        if (ripple) {
            try {
                Field mTabsContainer = commonTabLayout.getClass().getDeclaredField("mTabsContainer");
                mTabsContainer.setAccessible(true);
                LinearLayout mCommonTabLayout = (LinearLayout) mTabsContainer.get(commonTabLayout);
                for (int i = 0; i < mTitles.length; i++) {
                    View childAt = mCommonTabLayout.getChildAt(i);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        childAt.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ripple_tab));
                    }
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 绑定底部导航栏
     */
    private void assignView() {
        //添加Fragment
        fragments = new ArrayList<>();
        fragments.add(HomePageFragment.getInstance());
        fragments.add(ForumFragment.getInstance());
        fragments.add(PersonalFragment.getInstance());

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(getString(mTitles[i]), mIconselectIds[i], mIconUnSelectIds[i]));
        }
        //此处会添加fragments中的Fragment到container中，所以取消serFragments中添加的部分。
        commonTabLayout.setTabData(mTabEntities, this, R.id.fl_content, fragments);

    }

    /**
     * 初始化标题栏
     */
    private void initTitleBar() {
        ivBack.setVisibility(View.GONE);
        tvTitle.setText(AppUtils.getAppName(mContext));
    }


    @OnClick({R.id.iv_back, R.id.tv_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_title_right:
                showPopupMenu();
                break;
        }
    }

    /**
     * 右上角更多菜单 使用popupWindow实现
     */
    protected void showPopupMenu() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_popupwindow_listview, null);
        final PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ListView listView = (ListView) contentView.findViewById(R.id.listView);
        List<PopupListBean> menuItems = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            PopupListBean bean = new PopupListBean("测试页" + i);
            menuItems.add(bean);
        }
        listView.setAdapter(new PopupListAdapter(mContext, menuItems));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        intent = new Intent(mContext, SecondActivity.class);
                        startActivity(intent);
                        popupWindow.dismiss();
                        break;
                    case 2:
                        break;
                    case 3:
                        intent = new Intent(mContext, AppBarActivity.class);
                        startActivity(intent);
                        popupWindow.dismiss();
                        break;
                    case 4:
                        popupWindow.dismiss();
                        break;
                    case 5:
                        intent = new Intent(mContext, CardBagActivity.class);
                        startActivity(intent);
                        popupWindow.dismiss();
                        break;
                }

            }
        });
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(tvTitleRight, -120, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //处理back键不退出app
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
