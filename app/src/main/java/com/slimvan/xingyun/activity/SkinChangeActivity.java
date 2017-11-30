package com.slimvan.xingyun.activity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.Config;
import com.afollestad.materialdialogs.internal.ThemeSingleton;
import com.slimvan.xingyun.R;
import com.slimvan.xingyun.view.skin.SkinHelper;
import com.xingyun.slimvan.base.BaseHeaderActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SkinChangeActivity extends BaseHeaderActivity {

    @BindView(R.id.listView)
    ListView listView;

    private int main_color = 0xFFcc413a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_change);
        ButterKnife.bind(this);

        initListView();
    }


    private void initListView() {

        String[] listItems = new String[]{
                "红色",
                "橙色",
                "黑色",
                "绿色",
                "蓝色",
                "青色",
                "紫色"
        };
        List<String> data = new ArrayList<>();
        Collections.addAll(data, listItems);
        listView.setAdapter(new ArrayAdapter<>(mContext,android.R.layout.simple_list_item_1,data));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        main_color = ContextCompat.getColor(mContext,R.color.red_color);
                        if (main_color != 0) {
                            final Config config = ATE.config(mContext, getATEKey());
                            config.primaryColor(main_color);
                            config.accentColor(main_color);
                            config.commit();
                            resetMDDialog();
                            SkinHelper.setSkinType(position+1);
                            finish();
                            recreate(); // recreation needed to reach the checkboxes in the preferences layout
                        }
                        break;
                    case 1:
                        main_color = ContextCompat.getColor(mContext,R.color.orange_color);
                        if (main_color != 0) {
                            final Config config = ATE.config(mContext, getATEKey());
                            config.primaryColor(main_color);
                            config.accentColor(main_color);
                            config.commit();
                            resetMDDialog();
                            SkinHelper.setSkinType(position+1);
                            finish();
                            recreate(); // recreation needed to reach the checkboxes in the preferences layout
                        }
                        break;
                    case 2:
                        main_color = ContextCompat.getColor(mContext,R.color.black_color);
                        if (main_color != 0) {
                            final Config config = ATE.config(mContext, getATEKey());
                            config.primaryColor(main_color);
                            config.accentColor(main_color);
                            config.commit();
                            resetMDDialog();
                            SkinHelper.setSkinType(position+1);
                            finish();
                            recreate(); // recreation needed to reach the checkboxes in the preferences layout
                        }
                        break;
                    case 3:
                        main_color = ContextCompat.getColor(mContext,R.color.green_color);
                        if (main_color != 0) {
                            final Config config = ATE.config(mContext, getATEKey());
                            config.primaryColor(main_color);
                            config.accentColor(main_color);
                            config.commit();
                            resetMDDialog();
                            SkinHelper.setSkinType(position+1);
                            finish();
                            recreate(); // recreation needed to reach the checkboxes in the preferences layout
                        }
                        break;
                    case 4:
                        main_color = ContextCompat.getColor(mContext,R.color.blue_color);
                        if (main_color != 0) {
                            final Config config = ATE.config(mContext, getATEKey());
                            config.primaryColor(main_color);
                            config.accentColor(main_color);
                            config.commit();
                            resetMDDialog();
                            SkinHelper.setSkinType(position+1);
                            finish();
                            recreate(); // recreation needed to reach the checkboxes in the preferences layout
                        }
                        break;
                    case 5:
                        main_color = ContextCompat.getColor(mContext,R.color.cyan_color);
                        if (main_color != 0) {
                            final Config config = ATE.config(mContext, getATEKey());
                            config.primaryColor(main_color);
                            config.accentColor(main_color);
                            config.commit();
                            SkinHelper.setSkinType(position+1);
                            finish();
                            recreate(); // recreation needed to reach the checkboxes in the preferences layout
                        }
                        break;
                    case 6:
                        main_color = ContextCompat.getColor(mContext,R.color.purple_color);
                        if (main_color != 0) {
                            final Config config = ATE.config(mContext, getATEKey());
                            config.primaryColor(main_color);
                            config.accentColor(main_color);
                            config.commit();
                            resetMDDialog();
                            SkinHelper.setSkinType(position+1);
                            finish();
                            recreate(); // recreation needed to reach the checkboxes in the preferences layout
                        }
                        break;
                }
            }
        });
    }

    /**
     * 配置MDDialog的颜色属性
     */
    private void resetMDDialog() {
        final ThemeSingleton md = ThemeSingleton.get();
        md.titleColor = Config.textColorPrimary(mContext, getATEKey());
        md.contentColor = Config.textColorSecondary(mContext, getATEKey());
        md.itemColor = md.titleColor;
        md.widgetColor = Config.accentColor(mContext, getATEKey());
        md.linkColor = ColorStateList.valueOf(md.widgetColor);
        md.positiveColor = ColorStateList.valueOf(md.widgetColor);
        md.neutralColor = ColorStateList.valueOf(md.widgetColor);
        md.negativeColor = ColorStateList.valueOf(md.widgetColor);
    }

    @Override
    public void onStateLayoutClick() {

    }

    @Override
    public void onLeftClick(View v) {

    }

    @Override
    public void onRightClick(View v) {

    }
}
