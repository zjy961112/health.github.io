package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import entity.PageResult;
import entity.QueryPageBean;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/10 15:26
 * @see
 */
@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 分页查询检查组
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            return checkGroupService
                    .findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(),
                            queryPageBean.getQueryString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 新增检查组
     *
     * @param checkItemIds
     * @param checkGroup
     * @return
     */
    @RequestMapping(value = "/addCheckGroup", method = RequestMethod.POST)
    public Result addCheckGroup(Integer[] checkItemIds, @RequestBody CheckGroup checkGroup) {
        try {
            checkGroupService.addCheckGroup(checkItemIds, checkGroup);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    /**
     * 根据ID查询检查组
     *
     * @param checkGroupId
     * @return
     */
    @RequestMapping(value = "/findCheckGroupById", method = RequestMethod.GET)
    public Result findCheckGroupById(Integer checkGroupId) {
        try {
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,
                    checkGroupService.findCheckGroupById(checkGroupId));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * 根据检查组的ID查询与之对应的检查项
     *
     * @param checkGroupId
     * @return
     */
    @RequestMapping(value = "/findCheckGroupAndCheckItemById", method = RequestMethod.GET)
    public Result findCheckGroupAndCheckItemById(Integer checkGroupId) {
        try {
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,
                    checkGroupService.findCheckGroupAndCheckItemById(checkGroupId));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 编辑检查组
     *
     * @return
     */
    @RequestMapping(value = "/editCheckGroup", method = RequestMethod.POST)
    public Result editCheckGroup(Integer[] checkItemIds, @RequestBody CheckGroup checkGroup) {
        try {
            checkGroupService.editCheckGroup(checkItemIds, checkGroup);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    /**
     * 删除检查组
     *
     * @param checkGroupId
     * @return
     */
    @RequestMapping(value = "/deleteCheckGroup", method = RequestMethod.GET)
    public Result deleteCheckGroup(Integer checkGroupId) {
        try {
            checkGroupService.deleteCheckGroup(checkGroupId);
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    /**
     * 查询所有的检查组
     *
     * @return
     */
    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    public Result findAll() {
        try {
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,
                    checkGroupService.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
}
