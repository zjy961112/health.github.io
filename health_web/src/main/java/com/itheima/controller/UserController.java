package com.itheima.controller;

import com.itheima.constant.MessageConstant;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/17 18:04
 * @see
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/getUsername", method = RequestMethod.GET)
    public Result getUsername() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = user.getUsername();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, username);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }
}
