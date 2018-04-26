package com.slimvan.xingyun.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.slimvan.xingyun.R;
import com.xingyun.slimvan.base.BaseActivity;
import com.xingyun.slimvan.bean.PopupListBean;
import com.xingyun.slimvan.view.widget.StateLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StateLayoutActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.state_layout)
    StateLayout stateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_layout);
        ButterKnife.bind(this);
        stateLayout.switchStates(StateLayout.STATE_NORMAL);
    }

    @OnClick({R.id.iv_back, R.id.tv_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.tv_title_right:
                List<PopupListBean> items = new ArrayList<>();
                items.add(new PopupListBean("默认"));
                items.add(new PopupListBean("空数据"));
                items.add(new PopupListBean("无网络"));
                items.add(new PopupListBean("后台异常"));
                showPopupMenu(items, tvTitleRight, new PopupMenuItemClick() {
                    @Override
                    public void onPopupMenuItemClick(PopupWindow popupWindow, int position) {
                        switch (position) {
                            case 0:
                                stateLayout.switchStates(StateLayout.STATE_NORMAL);
                                popupWindow.dismiss();
                                break;
                            case 1:
                                stateLayout.switchStates(StateLayout.STATE_EMPTY_DATA);
                                popupWindow.dismiss();
                                break;
                            case 2:
                                stateLayout.switchStates(StateLayout.STATE_NETWORK_ERROR);
                                popupWindow.dismiss();
                                break;
                            case 3:
                                stateLayout.switchStates(StateLayout.STATE_SERVER_ERROR);
                                popupWindow.dismiss();
                                break;
                        }
                    }
                });
                break;
        }
    }
}
