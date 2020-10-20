package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/11 17:29
 * @see
 */
public interface SetmealDao {

    /**
     * 添加套餐
     *
     * @param setmeal
     */
    void addSetmeal(Setmeal setmeal);

    /**
     * 往套餐和检查组的中间表添加对应关系
     *
     * @param map
     */
    void setSetmealAndCheckGroup(HashMap map);

    /**
     * 分页查询套餐
     *
     * @param queryString
     * @return
     */
    Page<Setmeal> findPage(String queryString);

    /**
     * 根据套餐的ID回显数据
     *
     * @param setmealId
     * @return
     */
    Setmeal findSetmealById(Integer setmealId);

    /**
     * 查询套餐对应的检查组
     *
     * @param setmealId
     * @return
     */
    Integer[] findSetmealAndCheckGroupById(Integer setmealId);

    /**
     * 编辑套餐
     *
     * @param setmeal
     */
    void editSetmeal(Setmeal setmeal);

    /**
     * 根据套餐ID删除与之对应的检查组
     *
     * @param setmealId
     */
    void deleteSetmealAndCheckGroupById(Integer setmealId);

    /**
     * 删除套餐
     *
     * @param setmealId
     */
    void deleteSetmeal(Integer setmealId);

    /**
     * 移动端获取所有套餐
     *
     * @return
     */
    List<Setmeal> getSetmeal();

    /**
     * 移动端获取套餐详情
     *
     * @param id
     * @return
     */
    Setmeal findById(Integer id);

    /**
     * 查询会员预约的各个套餐占比情况
     *
     * @return
     */
    List<Map> getSetmealReport();
}
