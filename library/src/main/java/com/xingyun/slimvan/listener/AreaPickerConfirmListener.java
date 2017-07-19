package com.xingyun.slimvan.listener;

/**
 * 地区选择确认回调
 * Created by xingyun on 2017/7/19.
 */

public interface AreaPickerConfirmListener {

    //根据数据结构不同，可以在此处定义其他需要的数据，比如city_id,district_id等等
    void onAreaPickerConfirm(String areaStr);
}
