package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.service.MemberService;
import entity.Result;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/16 18:23
 * @see
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

    @RequestMapping("/check")
    public Result login(HttpServletResponse response, @RequestBody Map map) {
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        String redisCode = jedisPool.getResource()
                .get(RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone);
        if (redisCode == null) {
            return new Result(false, MessageConstant.VALIDATECODE_DATED);
        }

        if (!validateCode.equals(redisCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        //判断是否进行注册,为注册就自动注册，已经注册就返回登录成功
        Result result = memberService.isRegister(telephone);

        Cookie coo = new Cookie("login_member_telephone", telephone);
        coo.setMaxAge(60 * 60 * 24 * 30);
        coo.setPath("/");
        response.addCookie(coo);

        return result;
    }
}
