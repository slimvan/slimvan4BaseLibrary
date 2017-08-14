package com.slimvan.xingyun.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.slimvan.xingyun.R;
import com.xingyun.slimvan.base.BaseHeaderActivity;
import com.xingyun.slimvan.util.ConvertUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlexLayoutActivity extends BaseHeaderActivity {

    @BindView(R.id.flex_layout)
    FlexboxLayout flexLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flex_layout);
        ButterKnife.bind(this);

        setTitle("FlexLayout");
        initFlexLayout();
    }

    private void initFlexLayout() {
        try {
            TextView textview = new TextView(mContext);
            textview.setText("jjjjkkkkjj");
            textview.setBackground(ContextCompat.getDrawable(mContext, R.drawable.flex_item));
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(ConvertUtils.dp2px(5), ConvertUtils.dp2px(5), ConvertUtils.dp2px(5), ConvertUtils.dp2px(5));
            textview.setLayoutParams(params);
            textview.setPadding(ConvertUtils.dp2px(20), ConvertUtils.dp2px(5), ConvertUtils.dp2px(20), ConvertUtils.dp2px(5));
            flexLayout.addView(textview);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onStateLayoutClick() {

    }

    @Override
    public void onLeftClick(View v) {
        this.finish();
    }

    @Override
    public void onRightClick(View v) {

    }
}
