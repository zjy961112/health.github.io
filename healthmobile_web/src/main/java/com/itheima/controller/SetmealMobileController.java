package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.pojo.Setmeal;
import com.itheima.service.CheckGroupService;
import com.itheima.service.SetmealService;
import entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 移动端控制器
 *
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/13 14:59
 * @see
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {

    @Reference
    private SetmealService setmealService;
    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 移动端获取所有套餐
     *
     * @return
     */
    @RequestMapping(value = "/getSetmeal", method = RequestMethod.POST)
    public Result getSetmeal() {
        try {
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,
                    setmealService.getSetmeal());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL,
                    setmealService.getSetmeal());
        }
    }

    /**
     * 移动端获取套餐详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public Result findById(Integer id) {
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
