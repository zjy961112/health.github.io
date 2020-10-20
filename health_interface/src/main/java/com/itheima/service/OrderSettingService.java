package com.itheima.service;

import com.itheima.pojo.OrderSetting;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/12 17:42
 * @see
 */
public interface OrderSettingService {

    /**
     * 批量设置预约人数
     * @param orderSettingArrayList
     */
    void batchOrderSettingService(ArrayList<OrderSetting> orderSettingArrayList);

    /**
     * 批量预约设置-单个预约设置公共方法
     * @param orderSetting
     */
    void updateOrInsertOS(OrderSetting orderSetting);

    /**
     * 查询预约信息
     * @return
     */
    List<Map> getData(String newData);
}
