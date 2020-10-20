package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.UserDao;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户认证接口实现类
 *
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/17 15:23
 * @see
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 根据用户名查找用户信息
     *
     * @param username
     * @return
     */
    @Override
    public User findUserByUserName(String username) {
        return userDao.findUserByUserName(username);
    }
}
