package com.itheima.service;

import com.itheima.pojo.Setmeal;
import entity.PageResult;
import entity.QueryPageBean;
import java.util.List;
import java.util.Map;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/11 16:17
 * @see
 */
public interface SetmealService {

    /**
     * 添加套餐
     *
     * @param checkGroupIds
     * @param setmeal
     */
    void addSetmeal(Integer[] checkGroupIds, Setmeal setmeal);

    /**
     * 分页查询套餐
     *
     * @param pagination
     * @return
     */
    PageResult findPage(QueryPageBean pagination);

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
     * @param checkGroupIds
     * @param setmeal
     */
    void editSetmeal(Integer[] checkGroupIds, Setmeal setmeal);

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
    Map getSetmealReport();

}
