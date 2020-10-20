package com.itheima.controller;

import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.utils.ValidateCodeUtils;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * 发送验证码的控制层
 *
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/15 17:11
 * @see
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 预约发送验证码
     *
     * @param telephone
     * @return
     */
    @RequestMapping(value = "/send4Order", method = RequestMethod.POST)
    public Result send4Order(String telephone) {
        try {
            String validateCode = ValidateCodeUtils.generateValidateCode(6).toString();
            //SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode);
            jedisPool.getResource()
                    .setex(RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone, 60 * 5,
                            validateCode);
            System.out.println("验证码为：" + validateCode);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    /**
     * 快速登录发送验证码
     *
     * @param telephone
     * @return
     */
    @RequestMapping(value = "/send4Login", method = RequestMethod.POST)
    public Result send4Login(String telephone) {
        try {
            String validateCode = ValidateCodeUtils.generateValidateCode(6).toString();
            //SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode);
            jedisPool.getResource()
                    .setex(RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone, 60 * 5,
                            validateCode);
            System.out.println("验证码为：" + validateCode);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
