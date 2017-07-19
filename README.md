# slimvan4BaseLibrary

## 摘要

+ 常用Activity、Fragment基类封装
+ MD风格或是IOS风格的Progress加载框、右上角弹窗PopupWindow
+ IOS风格的对话框和PickerView，可以快速实现生日、省市区选择等需求。
+ 常用工具类集成
+ EventBus通信

## 实现与介绍

### 界面

+ [BaseActivity](https://github.com/slimvan/slimvan4BaseLibrary/blob/master/library/src/main/java/com/xingyun/slimvan/activity/BaseActivity.java)(Activity基类，默认只能竖屏显示，包含权限申请、控件批量隐藏显示、Android/IOS两种风格的加载框显示)
+ [BaseFragmentActivity](https://github.com/slimvan/slimvan4BaseLibrary/blob/master/library/src/main/java/com/xingyun/slimvan/activity/BaseFragmentActivity.java)(提供setFragments存储Fragment，通过hideFragment/ShowFragment控制界面的隐藏/显示，事件监听需要自行实现)
+ [BaseHeaderActivity](https://github.com/slimvan/slimvan4BaseLibrary/blob/master/library/src/main/java/com/xingyun/slimvan/activity/BaseHeaderActivity.java)(带标题栏的Activity，顶部标题栏由RelativeLayout实现，左右两边各有一个按钮，灵活处理点击事件，这里不用ToolBar的原因是很多情况下ToolBar并不能方便的满足各种奇葩需求… 你还可以根据业务逻辑灵活控制显示或隐藏界面内容，配置标题栏；显示空布局、无网络布局、服务器错误布局)
+ [BaseRefreshActivity](https://github.com/slimvan/slimvan4BaseLibrary/blob/master/library/src/main/java/com/xingyun/slimvan/base/BaseRefreshActivity.java)(实现下拉刷新的Activity，刷新控件使用Android原生SwipeRefreshLayout，子类Activity布局只需填充需要刷新的内容即可；子类中需要重写onRefresh方法，自行根据业务逻辑实现即可)
+ [BaseRefreshLoadMoreActivity](https://github.com/slimvan/slimvan4BaseLibrary/blob/master/library/src/main/java/com/xingyun/slimvan/base/BaseRefreshLoadMoreActivity.java)(使用方法和BaseRefreshActivity类似，子类中 需要重写onRefresh和onLoadMore方法，自行根据业务逻辑实现即可)
+ [BaseFragment](https://github.com/slimvan/slimvan4BaseLibrary/blob/master/library/src/main/java/com/xingyun/slimvan/fragment/BaseFragment.java)(和BaseActivity对应，不多说）

此外：所有的界面都实现了EventBus监听，只需在OnMessageEvent回调中，从MessageEvent中获取想要的数据，进行处理即可。

#

### 网络模块

#### 示例

```
RetrofitBuilder.build(DoubanApi.class).
        bookSearch("android").
        subscribeOn(Schedulers.io()).
        observeOn(AndroidSchedulers.mainThread()).
        subscribe(new MSubscriber<DoubanBookList>(mContext, true, false) {
            @Override
            public void onSuccess(DoubanBookList bookList) {
                Log.i(TAG, "success");
                adapter.setNewData(bookList.getBooks());
            }

            @Override
            public void errorCallBack(Throwable e) {
                Log.i(TAG, "error");
            }
        });
```

##  

### Dialog

##### IOS风格底部ActionSheet

```
DialogHelper.showIOSActionSheetDialog(mContext, new String[]{"拍照", "相册中选取"}, new OnItemClickListener() {
    @Override
    public void onItemClick(Object o, int position) {
        ToastUtils.showShort(position + "");
    }
});
```

##### IOS风格列表Dialog

```
DialogHelper.showIOSListDialog(mContext, "Title", new String[]{"第一项", "第二项", "第三项"}, new OnItemClickListener() {
    @Override
    public void onItemClick(Object o, int position) {
        ToastUtils.showShort(position + "");
    }
});
```

##### IOS风格警告对话框

```
DialogHelper.showIOSAlertDialog(mContext, "Title", "Content", new OnItemClickListener() {
    @Override
    public void onItemClick(Object o, int position) {
        ToastUtils.showShort(position + "");
    }
});
```

##### Android原生警告对话框

```
DialogHelper.showAlertDialog(mContext, "Title", "Content", new DialogConfirmClickListener() {
    @Override
    public void onDialogConfirmClick(DialogInterface dialog, int position) {
        dialog.dismiss();
        ToastUtils.showShort(position + "");
    }
});
```

##### Android原生列表对话框

```
DialogHelper.showListDialog(mContext, "Title", new String[]{"第一项", "第二项", "第三项"}, new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        ToastUtils.showShort(which + "");
    }
});
```

##### Android原生单选列表对话框

```
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
```

##### Android原生多选列表对话框

```
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
```

##### 右上角菜单式弹窗

```
List<PopupListBean> menuItems = new ArrayList<>();
menuItems.add(new PopupListBean("Settings"));
menuItems.add(new PopupListBean("Settings"));
menuItems.add(new PopupListBean("Settings"));
menuItems.add(new PopupListBean("Settings"));
showPopupMenu(menuItems, new PopupMenuItemClick() {
    @Override
    public void onPopupMenuItemClick(PopupWindow popupWindow, int position) {

    }
});

*PopupListBean可以传入String类型的文字内容和int类型图片资源
```

##### 时间选择器

```
DialogHelper.showTimePicker(DialogActivity.this, new TimePickerConfirmListener() {
    @Override
    public void onTimePickerConfirm(String dateStr) {
        ToastUtils.showShort(dateStr);
    }
});
```

##### 省市区三级联动选择器

```
DialogHelper.showAreaPicker(DialogActivity.this, new AreaPickerConfirmListener() {
    @Override
    public void onAreaPickerConfirm(String areaStr) {
        ToastUtils.showShort(areaStr);
    }
});
```



## 其他

##### 防双击误操作

```
CheckDoubleClickListener

在OnClick方法参数中传入CheckDoubleClickListener 其他逻辑和原生方法一致。
```

————————————————————————The End————————————————————

