package com.itheima.dao;

import com.itheima.pojo.Permission;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/17 16:41
 * @see
 */
public interface PermissionDao {

    /**
     * 根据角色ID查询对应的权限
     *
     * @param id
     * @return
     */
    Permission findPermissionByRoleId(Integer id);
}
