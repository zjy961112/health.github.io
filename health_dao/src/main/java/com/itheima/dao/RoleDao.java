package com.itheima.dao;

import com.itheima.pojo.Role;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/17 16:34
 * @see
 */
public interface RoleDao {

    /**
     * 根据用户ID查询用户对应的角色
     *
     * @param id
     * @return
     */
    Role findRoleByUserId(Integer id);
}
