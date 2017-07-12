package com.slimvan.xingyun.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.slimvan.xingyun.R;
import com.xingyun.slimvan.activity.BaseHeaderActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends BaseHeaderActivity {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.web_view)
    WebView webView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new myWebViewClient());
        initWebSettings();
        webView.loadUrl(url);
    }

    @Override
    protected void initIntentParams(Intent intent) {
        super.initIntentParams(intent);
        url = intent.getStringExtra("url");
    }

    /**
     * webView设置
     */
    private void initWebSettings() {
        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //设置WebView属性，能够执行Javascript脚本
        settings.setJavaScriptEnabled(true);
        //设置可以访问文件
        settings.setAllowFileAccess(true);
        //设置支持缩放
        settings.setBuiltInZoomControls(false);
    }

    public class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("").setMessage(message).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 申请权限。

                }
            }).create().show();
            result.confirm();
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            } else {
                if (progressBar.getVisibility() == View.GONE)
                    progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (!TextUtils.isEmpty(title)) {
                setTitle(title);
            } else {
                setTitle(title);
            }
        }
    }


    public class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
            showContent();
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            webView.setVisibility(View.GONE);
            Toast.makeText(mContext, "连接异常", Toast.LENGTH_SHORT).show();
            showNetWorkErrorView();
        }

    }


    @Override
    public void onStateLayoutClick() {
        webView.reload();
    }

    @Override
    public void onLeftClick(View v) {
        WebViewActivity.this.finish();
    }

    @Override
    public void onRightClick(View v) {

    }
}
