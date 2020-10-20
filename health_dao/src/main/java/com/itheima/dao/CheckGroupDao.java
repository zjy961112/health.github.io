package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/10 15:39
 * @see
 */
public interface CheckGroupDao {
    /**
     * 分页查询检查组
     * @param queryString
     * @return
     */
    Page<CheckGroup> findPage(String queryString);

    /**
     * 新增检查组
     * @param checkGroup
     */
    void addCheckGroup(CheckGroup checkGroup);

    /**
     * 往检查组和检查项的中间表添加对应关系
     * @param checkItemId
     * @param checkGroupId
     */
    void setCheckGroupByCheckItem(@Param("checkItemId") Integer checkItemId,@Param("checkGroupId") Integer checkGroupId);

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
     * @param checkGroup
     */
    void editCheckGroup(CheckGroup checkGroup);

    /**
     * 根据检查组的ID查询与之对应的套餐
     * @param checkGroupId
     */
    Integer findCheckGroupAndSetmealById(Integer checkGroupId);

    /**
     *真正删除检查组
     * @param checkGroupId
     */
    void deleteCheckGroup(Integer checkGroupId);

    /**
     * 删除检查组与之对应的检查项，方便修改检查组与之对应的检查项
     * @param id
     */
    void deleteCheckGroupAndCheckItemById(Integer id);

    /**
     * 查询所有的检查组
     * @return
     */
    List<CheckGroup> findAll();

    /**
     * 查询套餐关联的检查组
     * @param id
     * @return
     */
    List<CheckGroup> findCheckGroupListById(Integer id);
}
