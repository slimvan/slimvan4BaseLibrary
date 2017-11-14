package com.xingyun.slimvan.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * Created by xiaoming on 2016/9/6. 
 * 计算listview 每个item的宽度，取最长长度作为listview的宽度 
 */  
public class AutoWidthListView extends ListView {
  
    public AutoWidthListView(Context context) {
        super(context);  
    }  
  
    public AutoWidthListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);  
    }  
  
    public AutoWidthListView(Context context, AttributeSet attrs) {
        super(context, attrs);  
    }  
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        int width = getMaxWidthOfChildren() + getPaddingLeft() + getPaddingRight();//计算listview的宽度  
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), heightMeasureSpec);//设置listview的宽高  
  
    }  
  
    /** 
     * 计算item的最大宽度 
     * 
     * @return 
     */  
    private int getMaxWidthOfChildren() {  
        int maxWidth = 0;  
        View view = null;
        int count = getAdapter().getCount();  
        for (int i = 0; i < count; i++) {  
            view = getAdapter().getView(i, view, this);  
            view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);  
            if (view.getMeasuredWidth() > maxWidth)  
                maxWidth = view.getMeasuredWidth();  
        }  
        return maxWidth;  
    }  
}  