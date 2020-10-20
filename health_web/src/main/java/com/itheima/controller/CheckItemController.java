package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import entity.PageResult;
import entity.QueryPageBean;
import entity.Result;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/8 17:34
 * @see
 */
@RestController
@RequestMapping("/checkItem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    /**
     * 查询所有检查项
     *
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public Result findAll() {
        try {
            List<CheckItem> checkItemList = checkItemService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 分页查询检查项
     *
     * @param pagination
     * @return
     */
    @RequestMapping(value = "/findPage", method = RequestMethod.POST)
    public PageResult queryPageBean(@RequestBody QueryPageBean pagination) {
        try {
            return checkItemService.findPage(pagination.getCurrentPage(), pagination.getPageSize(),
                    pagination.getQueryString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 新增检查项
     *
     * @param checkItem
     * @return
     */
    @RequestMapping(value = "/addCheckItem", method = RequestMethod.POST)
    public Result addCheckItem(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.addCheckItem(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    /**
     * 根据ID回显数据
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public Result findById(Integer id) {
        try {
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,
                    checkItemService.findById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 编辑检查项
     *
     * @param checkItem
     * @return
     */
    @RequestMapping(value = "/editCheckItem", method = RequestMethod.POST)
    public Result edit(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.edit(checkItem);
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    /**
     * 删除检查项
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    @RequestMapping(value = "/deleteCheckItem", method = RequestMethod.GET)
    public Result deleteCheckItem(Integer id) {
        try {
            checkItemService.deleterCheckItem(id);
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }
}
