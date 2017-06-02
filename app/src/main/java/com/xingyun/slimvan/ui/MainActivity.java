package com.xingyun.slimvan.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.xingyun.slimvan.R;
import com.xingyun.slimvan.enterface.PermissionsResultListener;
import com.xingyun.slimvan.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.mToolBar)
    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initToolBar();
        String[] permissions = new String[]{Manifest.permission.CAMERA};
        requestPermissions("需要以下权限", permissions, 100, new PermissionsResultListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied() {

            }
        });
    }

    @Override
    protected void initIntentParams(Intent intent) {

    }

    private void initToolBar() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolBar.setNavigationIcon(R.mipmap.ic_back);

        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            Intent intent;

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_ring:
                        intent = new Intent(getApplicationContext(), BottomNaviActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_apps:
                        intent = new Intent(getApplicationContext(), ScrollingActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_camera:
//                        showProgressDialog("想照相？等等吧");
                        intent = new Intent(getApplicationContext(),TestListActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    // 创建关联菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
