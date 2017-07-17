package com.xingyun.slimvan.listener;

import android.content.DialogInterface;

/**
 * 弹窗点击事件监听
 * Created by xingyun on 2017/7/17.
 */

public interface DialogConfirmClickListener {
    void onDialogConfirmClick(DialogInterface dialog, int position);
}
