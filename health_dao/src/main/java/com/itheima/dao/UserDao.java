package com.itheima.dao;

import com.itheima.pojo.User;

/**
 * 用户接口
 *
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/17 15:41
 * @see
 */
public interface UserDao {

    /**
     * 根据用户名查找用户信息
     *
     * @param username
     * @return
     */
    User findUserByUserName(String username);
}
