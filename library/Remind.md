# slimvan4BaseLibrary#

## 摘要##

+ 常用Activity、Fragment基类封装
+ MD风格或是IOS风格的Progress加载框、右上角弹窗PopupWindow
+ IOS风格的对话框和PickerView，可以快速实现生日、省市区选择等需求。
+ 常用工具类集成
+ EventBus通信

## 实现与介绍##

### 界面###

+ [BaseActivity](https://github.com/slimvan/slimvan4BaseLibrary/blob/master/library/src/main/java/com/xingyun/slimvan/activity/BaseActivity.java)(Activity基类，默认只能竖屏显示，包含权限申请、控件批量隐藏显示、Android/IOS两种风格的加载框显示)
+ [BaseFragmentActivity](https://github.com/slimvan/slimvan4BaseLibrary/blob/master/library/src/main/java/com/xingyun/slimvan/activity/BaseFragmentActivity.java)(提供setFragments存储Fragment，通过hideFragment/ShowFragment控制界面的隐藏/显示，事件监听需要自行实现)
+ [BaseHeaderActivity](https://github.com/slimvan/slimvan4BaseLibrary/blob/master/library/src/main/java/com/xingyun/slimvan/activity/BaseHeaderActivity.java)(带标题栏的Activity，顶部标题栏由RelativeLayout实现，左右两边各有一个按钮，灵活处理点击事件，这里不用ToolBar的原因是很多情况下ToolBar并不能方便的满足各种奇葩需求… 你还可以根据业务逻辑灵活控制显示或隐藏界面内容，配置标题栏；显示空布局、无网络布局、服务器错误布局)
+ [BaseRefreshActivity](https://github.com/slimvan/slimvan4BaseLibrary/blob/master/library/src/main/java/com/xingyun/slimvan/base/BaseRefreshActivity.java)(实现下拉刷新的Activity，刷新控件使用Android原生SwipeRefreshLayout，子类Activity布局只需填充需要刷新的内容即可；子类中需要重写onRefresh方法，自行根据业务逻辑实现即可)
+ [BaseRefreshLoadMoreActivity](https://github.com/slimvan/slimvan4BaseLibrary/blob/master/library/src/main/java/com/xingyun/slimvan/base/BaseRefreshLoadMoreActivity.java)(使用方法和BaseRefreshActivity类似，子类中 需要重写onRefresh和onLoadMore方法，自行根据业务逻辑实现即可)
+ [BaseFragment](https://github.com/slimvan/slimvan4BaseLibrary/blob/master/library/src/main/java/com/xingyun/slimvan/fragment/BaseFragment.java)(和BaseActivity对应，不多说）

此外：所有的界面都实现了EventBus监听，只需在OnMessageEvent回调中，从MessageEvent中获取想要的数据，进行处理即可。

## 

### 

### 网络模块###

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



## 其他

##### 防双击误操作

```
CheckDoubleClickListener

在OnClick方法参数中传入CheckDoubleClickListener 其他逻辑和原生方法一致。
```

————————————————————————The End————————————————————

