package com.itheima.service;

import com.itheima.pojo.CheckGroup;
import entity.PageResult;
import java.util.List;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/10 15:30
 * @see
 */
public interface CheckGroupService {
    /**
     * 分页查询检查组
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 新增检查组
     * @param checkItemIds
     * @param checkGroup
     */
    void addCheckGroup(Integer[] checkItemIds, CheckGroup checkGroup);

    /**
     * 根据ID查询检查组
     * @param checkGroupId
     * @return
     */
    CheckGroup findCheckGroupById(Integer checkGroupId);

    /**
     * 根据检查组的ID查询与之对应的检查项
     * @param checkGroupId
     * @return
     */
    Integer[] findCheckGroupAndCheckItemById(Integer checkGroupId);

    /**
     * 编辑检查组
     * @param checkItemIds
     * @param checkGroup
     */
    void editCheckGroup(Integer[] checkItemIds, CheckGroup checkGroup);

    /**
     * 删除检查项
     * @param checkGroupId
     */
    void deleteCheckGroup(Integer checkGroupId);

    /**
     * 查询所有的检查组
     * @return
     */
    List<CheckGroup> findAll();

}
