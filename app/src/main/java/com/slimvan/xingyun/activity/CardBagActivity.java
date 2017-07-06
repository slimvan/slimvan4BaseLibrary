package com.slimvan.xingyun.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.czy1121.view.TurnCardListView;
import com.slimvan.xingyun.R;
import com.xingyun.slimvan.activity.BaseHeaderActivity;
import com.xingyun.slimvan.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardBagActivity extends BaseHeaderActivity {


    @BindView(R.id.card_list)
    TurnCardListView cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_bag);
        ButterKnife.bind(this);

        setTitle("xingyun Pay");
        initCardList();
    }

    @Override
    public void onStateLayoutClick() {

    }

    @Override
    public void onLeftClick(View v) {
        CardBagActivity.this.finish();
    }

    @Override
    public void onRightClick(View v) {

    }

    private void initCardList() {
        cardList.setAdapter(new BaseAdapter() {
            int[] colors = {0xffFF9800, 0xff3F51B5, 0xff673AB7, 0xff006064, 0xffC51162, 0xffFFEB3B, 0xff795548, 0xff9E9E9E};

            @Override
            public int getCount() {
                return colors.length;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View child, ViewGroup parent) {
                if (child == null) {
                    child = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_list, parent, false);
                }

                ((TextView) child.findViewById(R.id.pos)).setText("" + position);
                child.findViewById(R.id.image).setBackgroundColor(colors[position]);
                TextView tvContent = (TextView) child.findViewById(R.id.tv_content);
                tvContent.setText("我是熊本熊" + position + "号~~~~~~~");
                return child;
            }
        });

        cardList.setOnTurnListener(new TurnCardListView.OnTurnListener() {
            @Override
            public void onTurned(int position) {
                ToastUtils.showShort(position + "");
            }
        });
    }
}
