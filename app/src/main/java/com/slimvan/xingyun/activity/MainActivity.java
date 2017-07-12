package com.slimvan.xingyun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.fragment.ForumFragment;
import com.slimvan.xingyun.fragment.HomePageFragment;
import com.slimvan.xingyun.fragment.PersonalFragment;
import com.xingyun.slimvan.activity.BaseFragmentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseFragmentActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.ll_title_bar)
    RelativeLayout llTitleBar;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.tv_tab_1)
    TextView tvTab1;
    @BindView(R.id.tv_tab_2)
    TextView tvTab2;
    @BindView(R.id.tv_tab_3)
    TextView tvTab3;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initTitleBar();
        showFragment(fragments.get(0), false);

    }

    /**
     * 初始化标题栏
     */
    private void initTitleBar() {
        ivBack.setVisibility(View.GONE);
        tvTitle.setText("Professor");
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
//                ViewUtils.commonTimePicker(MainActivity.this);
                showAlertDialog();
                break;
            case R.id.tv_tab_1:
                showFragment(fragments.get(0), false);
                break;
            case R.id.tv_tab_2:
                showFragment(fragments.get(1), false);
                break;
            case R.id.tv_tab_3:
                showFragment(fragments.get(2), false);
                break;
        }
    }

    private void showAlertDialog() {
        //或者builder模式创建
        new AlertView.Builder().setContext(MainActivity.this)
                .setStyle(AlertView.Style.Alert)
                .setTitle("Title")
                .setMessage("Message")
                .setCancelText("取消")
//                .setOthers(new String[]{"确定"})
                .setOthers(new String[]{"一环", "二环", "三环", "四环", "五环", "比五环多一环"})
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        Intent intent;
                        switch (position) {
                            case 0:
                                intent = new Intent(mContext, ListActivity.class);
                                startActivity(intent);
                                break;
                            case 1:
                                intent = new Intent(mContext, SecondActivity.class);
                                startActivity(intent);
                                break;
                            case 2:
                                intent = new Intent(mContext, ToolBarActivity.class);
                                startActivity(intent);
                                break;
                            case 3:
                                intent = new Intent(mContext, AppBarActivity.class);
                                startActivity(intent);
                                break;
                            case 4:
                                intent = new Intent(mContext, CoordinatorActivity.class);
                                startActivity(intent);
                                break;
                            case 5:
                                intent = new Intent(mContext, CardBagActivity.class);
                                startActivity(intent);
                                break;
                        }
                    }
                })
                .build()
                .setCancelable(true)
                .show();

    }

}
