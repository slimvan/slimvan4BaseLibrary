package com.xingyun.slimvan.listener;

import android.content.DialogInterface;
import android.util.SparseBooleanArray;

/**
 * Created by xingyun on 2017/7/17.
 */

public interface DialogMultiConfirmClickListener {
    void onDialogMultiConfirmClick(DialogInterface dialog, SparseBooleanArray checkedItemPositions);
}
