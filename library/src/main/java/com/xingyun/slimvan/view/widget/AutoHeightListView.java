package com.xingyun.slimvan.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * 自定义GridView 解决在ScrollView中嵌套时，出现数据显示不全的问题。
 */
public class AutoHeightListView extends ListView {

    public AutoHeightListView(Context context) {
        // TODO Auto-generated method stub  
        super(context);
    }

    public AutoHeightListView(Context context, AttributeSet attrs) {
        // TODO Auto-generated method stub  
        super(context, attrs);
    }

    public AutoHeightListView(Context context, AttributeSet attrs, int defStyle) {
        // TODO Auto-generated method stub  
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub  
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}  