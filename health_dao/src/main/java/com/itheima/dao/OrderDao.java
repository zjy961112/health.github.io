package com.itheima.dao;

import com.itheima.pojo.Order;
import java.util.List;
import java.util.Map;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/15 19:47
 * @see
 */
public interface OrderDao {

    /**
     * 查询用户是否已经预约
     *
     * @param ow
     * @return
     */
    Order findByCondition(Order ow);

    /**
     * 提交预约
     *
     * @param order
     */
    void submit(Order order);

    /**
     * 预约成功后查询预约信息
     *
     * @param id
     * @return
     */
    Map findById(Integer id);

    Integer findOrderCountByDate(String today);

    Integer findOrderCountBetweenDate(Map<String, Object> map);

    Integer findVisitsCountByDate(String today);

    Integer findVisitsCountAfterDate(Map<String, Object> map);

    /**
     * 获取热门套餐
     *
     * @return
     */
    List<Map> findHotSetmeal();
}
