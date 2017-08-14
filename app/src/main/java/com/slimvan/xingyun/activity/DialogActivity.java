package com.slimvan.xingyun.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.alertview.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.slimvan.xingyun.R;
import com.xingyun.slimvan.base.BaseHeaderActivity;
import com.xingyun.slimvan.listener.AreaPickerConfirmListener;
import com.xingyun.slimvan.listener.DialogConfirmClickListener;
import com.xingyun.slimvan.listener.DialogMultiConfirmClickListener;
import com.xingyun.slimvan.listener.TimePickerConfirmListener;
import com.xingyun.slimvan.util.ToastUtils;
import com.xingyun.slimvan.view.DialogHelper;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogActivity extends BaseHeaderActivity {

    private static final int REQUEST_CODE_CHOOSE = 101;
    @BindView(R.id.tv_ios_dialog)
    TextView tvIosDialog;
    @BindView(R.id.tv_ios_list_dialog)
    TextView tvIosListDialog;
    @BindView(R.id.tv_ios_alert_dialog)
    TextView tvIosAlertDialog;
    @BindView(R.id.tv_alert_dialog)
    TextView tvAlertDialog;
    @BindView(R.id.tv_list_dialog)
    TextView tvListDialog;
    @BindView(R.id.tv_single_choice_dialog)
    TextView tvSingleChoiceDialog;
    @BindView(R.id.tv_multi_choice_dialog)
    TextView tvMultiChoiceDialog;
    @BindView(R.id.tv_time_picker)
    TextView tvTimePicker;
    @BindView(R.id.tv_area_picker)
    TextView tvAreaPicker;
    @BindView(R.id.iv_photo_picker)
    ImageView ivPhotoPicker;
    @BindView(R.id.tv_flex_layout)
    TextView tvFlexLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
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
        ToastUtils.showShort("Nothing to do");
    }

    @OnClick({R.id.tv_ios_dialog, R.id.tv_ios_list_dialog
            , R.id.tv_ios_alert_dialog, R.id.tv_alert_dialog, R.id.tv_list_dialog, R.id.tv_single_choice_dialog
            , R.id.tv_multi_choice_dialog, R.id.tv_time_picker, R.id.tv_area_picker, R.id.iv_photo_picker
    ,R.id.tv_flex_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_ios_dialog:
                DialogHelper.showIOSActionSheetDialog(mContext, new String[]{"拍照", "相册中选取"}, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        ToastUtils.showShort(position + "");
                    }
                });
                break;
            case R.id.tv_ios_list_dialog:

                DialogHelper.showIOSListDialog(mContext, "Title", new String[]{"第一项", "第二项", "第三项"}, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        ToastUtils.showShort(position + "");
                    }
                });
                break;
            case R.id.tv_ios_alert_dialog:
                DialogHelper.showIOSAlertDialog(mContext, "Title", "Content", new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        ToastUtils.showShort(position + "");
                    }
                });
                break;
            case R.id.tv_alert_dialog:
                DialogHelper.showAlertDialog(mContext, "Title", "Content", new DialogConfirmClickListener() {
                    @Override
                    public void onDialogConfirmClick(DialogInterface dialog, int position) {
                        dialog.dismiss();
                        ToastUtils.showShort(position + "");
                    }
                });
                break;
            case R.id.tv_list_dialog:
                DialogHelper.showListDialog(mContext, "Title", new String[]{"第一项", "第二项", "第三项"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ToastUtils.showShort(which + "");
                    }
                });
                break;
            case R.id.tv_single_choice_dialog:
                DialogHelper.showSingleChoiceDialog(mContext, new String[]{"第一项", "第二项", "第三项"}, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtils.showShort(which + "");
                    }
                }, new DialogConfirmClickListener() {
                    @Override
                    public void onDialogConfirmClick(DialogInterface dialog, int position) {
                        dialog.dismiss();
                        ToastUtils.showShort(position + "");
                    }
                });
                break;
            case R.id.tv_multi_choice_dialog:
                DialogHelper.showMuliteChoiceDialog(mContext, new String[]{"第一项", "第二项", "第三项"}, new boolean[]{true, true, false}, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        ToastUtils.showShort(which + "____isChecked____" + isChecked);
                    }
                }, new DialogMultiConfirmClickListener() {
                    @Override
                    public void onDialogMultiConfirmClick(DialogInterface dialog, SparseBooleanArray checkedItemPositions) {
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < checkedItemPositions.size(); i++) {
                            boolean isCheck = checkedItemPositions.get(i);
                            sb.append(i + "--" + isCheck);
                            if (i < checkedItemPositions.size() - 1) {
                                sb.append(",");
                            }
                        }
                        ToastUtils.showShort(sb);
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.tv_time_picker:
                DialogHelper.showTimePicker(DialogActivity.this, new TimePickerConfirmListener() {
                    @Override
                    public void onTimePickerConfirm(String dateStr) {
                        ToastUtils.showShort(dateStr);
                    }
                });
                break;
            case R.id.tv_area_picker:
                DialogHelper.showAreaPicker(DialogActivity.this, new AreaPickerConfirmListener() {
                    @Override
                    public void onAreaPickerConfirm(String areaStr) {
                        ToastUtils.showShort(areaStr);
                    }
                });
                break;
            case R.id.iv_photo_picker:
                Matisse.from(DialogActivity.this)
                        .choose(MimeType.allOf())
                        .countable(true)
                        .maxSelectable(9)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .forResult(REQUEST_CODE_CHOOSE);
                break;
            case R.id.tv_flex_layout:
                Intent intent = new Intent(mContext,FlexLayoutActivity.class);
                startActivity(intent);
                break;
        }
    }

    List<Uri> mSelected;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + mSelected);
            if (mSelected != null) {
                Glide.with(mContext).load(mSelected.get(0)).into(ivPhotoPicker);
            }
        }
    }

}
