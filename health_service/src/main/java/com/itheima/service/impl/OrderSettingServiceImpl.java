package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/12 17:45
 * @see
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 批量设置预约人数
     *
     * @param orderSettingArrayList
     */
    @Override
    public void batchOrderSettingService(ArrayList<OrderSetting> orderSettingArrayList) {
        if (orderSettingArrayList != null) {
            for (OrderSetting orderSetting : orderSettingArrayList) {
                updateOrInsertOS(orderSetting);
            }
        }
    }

    /**
     * 批量预约设置-单个预约设置公共方法
     *
     * @param orderSetting
     */
    @Override
    public void updateOrInsertOS(OrderSetting orderSetting) {
        int count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if (count > 0) {
            //如果满足就代表已经存在预约人数，就做修改
            orderSettingDao.updateNumberByOrderDate(orderSetting);
        } else {
            //如果不满足就代表已经存在预约人数，就做添加
            orderSettingDao.add(orderSetting);
        }
    }

    /**
     * 查询预约信息
     *
     * @return
     */
    @Override
    public List<Map> getData(String data) {
        String newData = data+"%";
        //定义返回的数据集合
        List<Map> resultList = new ArrayList<>();
        List<OrderSetting> settingList = orderSettingDao.getData(newData);
        if (settingList != null && settingList.size()>0) {
            for (OrderSetting orderSetting : settingList) {
                //定义处理之后的返回数据
                Map resultMap = new HashMap();
                resultMap.put("date", orderSetting.getOrderDate().getDate());
                resultMap.put("number", orderSetting.getNumber());
                resultMap.put("reservations", orderSetting.getReservations());
                resultList.add(resultMap);
            }
        }
        System.out.println(resultList);
        return resultList;
    }
}
