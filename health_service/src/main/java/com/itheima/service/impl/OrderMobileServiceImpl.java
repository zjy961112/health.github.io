package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderMobileService;
import com.itheima.utils.DateUtils;
import com.itheima.utils.SMSUtils;
import entity.Result;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/15 18:27
 * @see
 */
@Service(interfaceClass = OrderMobileService.class)
@Transactional
public class OrderMobileServiceImpl implements OrderMobileService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    /**
     * 体检预约
     *
     * @param map
     */
    @Override
    public Result submit(Map map) {
        try {
            String orderDate = (String) map.get("orderDate");
            String telephone = (String) map.get("telephone");
            Integer setmealId = Integer.parseInt((String) map.get("setmealId"));
            //将日期转为Date，设置到order
            Date newDate = DateUtils.parseString2Date(orderDate);
            String orderType = (String) map.get("orderType");
            //判断用户预约的日期时候可预约，不可预约就返回所选日期不能进行体检预约
            OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDate);
            if (orderSetting == null) {
                return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
            }

            //判断用户预约的日期是否已经约满，如果是则返回预约已满
            if (orderSetting.getNumber() <= orderSetting.getReservations()) {
                return new Result(false, MessageConstant.ORDER_FULL);
            }

            //判断用户是否是会员
            Member member = memberDao.findMemberByTelephone(telephone);
            if (member != null) {
                //如果是则判断是否已经预约，防止重复预约，不是则返回提示
                Order ow = new Order();
                ow.setMemberId(member.getId());
                ow.setOrderDate(newDate);
                ow.setSetmealId(setmealId);
                Order order = orderDao.findByCondition(ow);
                if (order != null) {
                    //说明已经预约
                    return new Result(false, MessageConstant.HAS_ORDERED);
                }
            } else {
                //未注册
                member = new Member();
                member.setName((String) map.get("name"));
                member.setSex(map.get("sex").toString());
                member.setIdCard((String) map.get("idCard"));
                member.setPhoneNumber((String) map.get("telephone"));
                member.setRegTime(new Date());
                memberDao.register(member);
            }
            //进行预约
            Order order = new Order(member.getId(), newDate, orderType, Order.ORDERSTATUS_NO,
                    setmealId);
            orderDao.submit(order);

            //更改预约设置表
            orderSetting.setReservations(orderSetting.getReservations() + 1);
            orderSettingDao.updateReservationsByOrderDate(orderSetting);

            //发送短信提示
            SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE, telephone, orderDate);
            return new Result(true, MessageConstant.ORDER_SUCCESS,order);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FAIL);
        }
    }

    /**
     * 预约成功后查询预约信息
     *
     * @param id
     * @return
     */
    @Override
    public Map findById(Integer id) {
        return orderDao.findById(id);
    }
}
