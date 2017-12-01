package com.xingyun.slimvan.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.lcodecore.tkrefreshlayout.IBottomView;
import com.xingyun.slimvan.R;

/**
 * 加载更多底部布局 (配合 tkrefreshlayout,已废弃)
 * Created by xingyun on 2017/6/30.
 */

public class LoadMoreView extends FrameLayout implements IBottomView{

    private final ProgressBar progress;

    public LoadMoreView(Context context) {
        this(context, null);
    }

    public LoadMoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_load_more, null);
        progress = (ProgressBar) view.findViewById(R.id.progressBar);
        this.addView(view);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        this.setLayoutParams(params);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxBottomHeight, float bottomHeight) {

    }

    @Override
    public void startAnim(float maxBottomHeight, float bottomHeight) {

    }

    @Override
    public void onPullReleasing(float fraction, float maxBottomHeight, float bottomHeight) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void reset() {

    }

}
