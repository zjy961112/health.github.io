package com.itheima.dao;

import com.itheima.pojo.OrderSetting;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/12 18:17
 * @see
 */
public interface OrderSettingDao {

    /**
     * 根据日期查找是否已设置预约人数
     *
     * @param orderDate
     * @return
     */
    int findCountByOrderDate(Date orderDate);

    /**
     * 根据日期修改预约人数
     *
     * @param orderSetting
     */
    void updateNumberByOrderDate(OrderSetting orderSetting);

    /**
     * 根据日期添加预约人数
     *
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 查询预约信息
     *
     * @return
     */
    List<OrderSetting> getData(String data);

    /**
     * 根据日期查询是否可预约
     *
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(String orderDate);

    /**
     * 预约成功后修改预约设置表的已经预约人数
     *
     * @param orderSetting
     */
    void updateReservationsByOrderDate(OrderSetting orderSetting);
}
