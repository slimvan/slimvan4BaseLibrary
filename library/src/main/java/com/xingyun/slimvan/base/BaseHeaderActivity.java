package com.xingyun.slimvan.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xingyun.slimvan.R;
import com.xingyun.slimvan.adapter.PopupListAdapter;
import com.xingyun.slimvan.bean.PopupListBean;
import com.xingyun.slimvan.view.widget.StateLayout;

import java.util.List;

/**
 * 顶部带有标题栏的Activity基类
 */
public abstract class BaseHeaderActivity extends BaseActivity implements View.OnClickListener {

    protected Context mContext;

    private LinearLayout llContent;
    private StateLayout stateLayout;

    /*标题栏控件*/
    protected ImageView ivBack;
    protected TextView tvTitle;
    protected TextView tvTitleRight;
    protected Toolbar toolbar;
    /*标题栏控件*/

    private View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base_header);
        mContext = super.mContext;

        initViews();
        initTitleBar();
    }

    /**
     * 初始化布局
     */
    private void initViews() {
        stateLayout = (StateLayout) findViewById(R.id.state_layout);
        stateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStateLayoutClick();
            }
        });
    }

    public abstract void onStateLayoutClick();

    /**
     * 重写一个新增View的视图 子类设置layout的时候调用该方法即可
     */
    public void setContentView(int layoutResId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        //根布局
        llContent = (LinearLayout) findViewById(R.id.ll_root);
        //子类布局
        v = inflater.inflate(layoutResId, null);
        //子类布局的布局参数
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //先后加入子类布局
        llContent.addView(v, layoutParams);
    }

    @Override
    public void showProgressDialog(String message) {
        hideContent();
        super.showProgressDialog(message);
    }

    @Override
    public void hideProgressDialog() {
        showContent();
        super.hideProgressDialog();
    }

    @Override
    public void showSVProgressHUD() {
        hideContent();
        super.showSVProgressHUD();
    }

    @Override
    public void hideSVProgressHUD() {
        showContent();
        super.hideSVProgressHUD();
    }

    /**
     * 显示界面内容布局
     */
    protected void showContent() {
        v.setVisibility(View.VISIBLE);
        llContent.setVisibility(View.VISIBLE);
        stateLayout.setVisibility(View.GONE);
    }

    /**
     * 隐藏界面内容布局
     */
    protected void hideContent() {
        llContent.setVisibility(View.GONE);
        stateLayout.setVisibility(View.GONE);
    }

    /**
     * 显示空布局
     */
    protected void showEmptyView() {
        if (stateLayout != null) {
            stateLayout.setVisibility(View.VISIBLE);
            stateLayout.switchStates(StateLayout.STATE_EMPTY_DATA);
            v.setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏空布局
     */
    protected void hideEmptyView() {
        if (stateLayout != null) {
            stateLayout.setVisibility(View.GONE);
            v.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示网络错误布局
     */
    protected void showNetWorkErrorView() {
        if (stateLayout != null) {
            stateLayout.setVisibility(View.VISIBLE);
            stateLayout.switchStates(StateLayout.STATE_NETWORK_ERROR);
            v.setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏网络错误布局
     */
    protected void hideNetWorkErrorView() {
        if (stateLayout != null) {
            stateLayout.setVisibility(View.GONE);
            v.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示服务器错误布局
     */
    protected void showServerErrorView() {
        if (stateLayout != null) {
            stateLayout.setVisibility(View.VISIBLE);
            stateLayout.switchStates(StateLayout.STATE_SERVER_ERROR);
            v.setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏服务器错误布局
     */
    protected void hideServerErrorView() {
        if (stateLayout != null) {
            stateLayout.setVisibility(View.GONE);
            v.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化标题栏
     */
    protected void initTitleBar() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitleRight = (TextView) findViewById(R.id.tv_title_right);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitleBarListeners();
    }

    /**
     * 标题栏点击事件
     */
    private void setTitleBarListeners() {
        ivBack.setOnClickListener(this);
        tvTitleRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_back) {
            onLeftClick(v);

        } else if (i == R.id.tv_title_right) {
            onRightClick(v);

        }
    }

    /**
     * 标题栏左按钮点击事件 通常是back
     *
     * @param v
     */
    public abstract void onLeftClick(View v);

    /**
     * 标题栏右按钮点击事件 根据业务自定义
     *
     * @param v
     */
    public abstract void onRightClick(View v);

    /**
     * 隐藏标题栏左侧按钮
     */
    protected void hideLeftButton() {
        ivBack.setVisibility(View.GONE);
    }

    /**
     * 隐藏标题栏右侧按钮
     */
    protected void hideRightButton() {
        tvTitleRight.setVisibility(View.GONE);
    }

    /**
     * 设置标题栏文字
     *
     * @param title
     */
    protected void setTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 右上角更多菜单 使用popupWindow实现
     *
     * @param menuItems 数据源 可传入 icon和title
     */
    protected void showPopupMenu(List<PopupListBean> menuItems, final PopupMenuItemClick popupMenuItemClick) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_popupwindow_listview, null);
        final PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ListView listView = (ListView) contentView.findViewById(R.id.listView);
        listView.setAdapter(new PopupListAdapter(mContext, menuItems));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupMenuItemClick.onPopupMenuItemClick(popupWindow, position);
            }
        });
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(tvTitleRight, -120, 0);
    }

    public interface PopupMenuItemClick {
        void onPopupMenuItemClick(PopupWindow popupWindow, int position);
    }

}
