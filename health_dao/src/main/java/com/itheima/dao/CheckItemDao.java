package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;
import java.util.List;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/8 19:09
 * @see
 */
public interface CheckItemDao {

    /**
     * 不分页查询检查项
     *
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 分页查询检查项
     *
     * @param queryString
     * @return
     */
    Page<CheckItem> findPage(String queryString);

    /**
     * 添加检查项
     *
     * @param checkItem
     */
    void addCheckItem(CheckItem checkItem);

    /**
     * 根据ID回显数据
     *
     * @param id
     * @return
     */
    CheckItem findById(Integer id);

    /**
     * 编辑检查项
     *
     * @param checkItem
     */
    void edit(CheckItem checkItem);

    /**
     * 根据ID删除检查项
     *
     * @param id
     */
    void deleteCheckItem(Integer id);

    /**
     * 根据套餐对应的检查组查询检查项
     *
     * @param id
     * @return
     */
    List<CheckItem> findCheckItemListById(Integer id);
}
