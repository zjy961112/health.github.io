package com.itheima.service;

import com.itheima.pojo.User;

/**
 * 用户认证接口
 *
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/17 15:22
 * @see
 */
public interface UserService {

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    User findUserByUserName(String username);
}
