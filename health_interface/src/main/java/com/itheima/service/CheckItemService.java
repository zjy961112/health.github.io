package com.itheima.service;

import com.itheima.pojo.CheckItem;
import entity.PageResult;

import java.util.List;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/8 17:36
 * @see
 */
public interface CheckItemService {

    /**
     * 不分页查询检查项
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 分页查询检查项
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     *添加检查项
     * @param checkItem
     */
    void addCheckItem(CheckItem checkItem);

    /**
     * 根据ID回显数据
     * @param id
     */
    CheckItem findById(Integer id);

    /**
     * 编辑检查项
     * @param checkItem
     */
    void edit(CheckItem checkItem);

    /**
     * 根据ID删除检查项
     * @param id
     */
    void deleterCheckItem(Integer id);
}
