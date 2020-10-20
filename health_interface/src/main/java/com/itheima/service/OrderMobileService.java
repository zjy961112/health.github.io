package com.itheima.service;

import entity.Result;
import java.util.Map;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/15 18:26
 * @see
 */
public interface OrderMobileService {

    /**
     * 体检预约
     *
     * @param map
     */
    Result submit(Map map);

    /**
     * 预约成功后查询预约信息
     *
     * @param id
     * @return
     */
    Map findById(Integer id);
}
