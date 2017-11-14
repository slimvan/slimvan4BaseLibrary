package com.xingyun.slimvan.view.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.xingyun.slimvan.R;

/**
 * 状态布局  目前实现三种状态：空布局、网络异常、服务器异常
 * Created by xingyun on 2017/7/6.
 */

public class StateLayout extends FrameLayout {
    public static final int STATE_EMPTY_DATA = 1;
    public static final int STATE_NETWORK_ERROR = 2;
    public static final int STATE_SERVER_ERROR = 3;
    private View empty_view;
    private View network_error_view;
    private View server_error_view;

    public StateLayout(@NonNull Context context) {
        super(context);
        initViews(context);
    }

    public StateLayout(Context context, AttributeSet paramAttributeSet) {
        super(context, paramAttributeSet);
        initViews(context);
    }

    public StateLayout(@NonNull Context context, StateLayoutClickListener mStateLayoutClickListener) {
        super(context);
        this.mStateLayoutClickListener = mStateLayoutClickListener;
        initViews(context);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs, StateLayoutClickListener mStateLayoutClickListener) {
        super(context, attrs);
        this.mStateLayoutClickListener = mStateLayoutClickListener;
        initViews(context);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, StateLayoutClickListener mStateLayoutClickListener) {
        super(context, attrs, defStyleAttr);
        this.mStateLayoutClickListener = mStateLayoutClickListener;
        initViews(context);
    }

    /**
     * 初始化控件
     *
     * @param context
     */
    private void initViews(@NonNull Context context) {
        empty_view = LayoutInflater.from(context).inflate(R.layout.layout_states_empty, null);
        network_error_view = LayoutInflater.from(context).inflate(R.layout.layout_states_network_error, null);
        server_error_view = LayoutInflater.from(context).inflate(R.layout.layout_states_server_error, null);
        this.addView(empty_view);
        this.addView(network_error_view);
        this.addView(server_error_view);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        this.setLayoutParams(params);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStateLayoutClickListener != null) {
                    StateLayout.this.mStateLayoutClickListener.onStateLayoutClick();
                }
            }
        });

        //默认显示空布局
        switchStates(STATE_EMPTY_DATA);
    }

    /**
     * 切换状态
     */
    public void switchStates(int states) {
        switch (states) {
            case STATE_EMPTY_DATA:
                empty_view.setVisibility(View.VISIBLE);
                network_error_view.setVisibility(View.GONE);
                server_error_view.setVisibility(View.GONE);
                break;
            case STATE_NETWORK_ERROR:
                empty_view.setVisibility(View.GONE);
                network_error_view.setVisibility(View.VISIBLE);
                server_error_view.setVisibility(View.GONE);
                break;
            case STATE_SERVER_ERROR:
                empty_view.setVisibility(View.GONE);
                network_error_view.setVisibility(View.GONE);
                server_error_view.setVisibility(View.VISIBLE);
                break;
        }
    }

    public interface StateLayoutClickListener {
        void onStateLayoutClick();
    }

    private StateLayoutClickListener mStateLayoutClickListener;

}
