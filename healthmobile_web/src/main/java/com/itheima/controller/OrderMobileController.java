package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.pojo.Order;
import com.itheima.service.OrderMobileService;
import entity.Result;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * 预约控制层
 *
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/15 17:57
 * @see
 */
@RestController
@RequestMapping("/order")
public class OrderMobileController {

    @Reference
    private OrderMobileService orderMobileService;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 预约
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    private Result submit(@RequestBody Map map) {
        String telephone = map.get("telephone").toString();
        String validateCode = map.get("validateCode").toString();
        String redisCode = jedisPool.getResource()
                .get(RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone);
        //如果验证码和手机号码为空，就返回提示用户
        if (StringUtils.isEmpty(validateCode) || StringUtils.isEmpty(telephone)) {
            return new Result(true, MessageConstant.TELEPHONE_VALIDATECODE_NOTNULL);
        }
        //走到这就代表用户传过来的验证码和手机号码不为空，判断Redis和用户的验证码是否相等，不相等返回提示用户
        if (StringUtils.isEmpty(redisCode) || !redisCode.equals(validateCode)) {
            return new Result(true, MessageConstant.VALIDATECODE_ERROR);
        }
        map.put("orderType", Order.ORDERTYPE_WEIXIN);
        return orderMobileService.submit(map);
    }

    /**
     * 预约成功后查询预约信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById", method = RequestMethod.POST)
    public Result findById( Integer id) {
        try {
            Map resultMap = orderMobileService.findById(id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
