package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.CheckGroupDao;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import entity.PageResult;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/10 15:31
 * @see
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 分页查询检查组
     *
     * @return
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckGroup> page = checkGroupDao.findPage(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 新增检查组
     *
     * @param checkItemIds
     * @param checkGroup
     */
    @Transactional
    @Override
    public void addCheckGroup(Integer[] checkItemIds, CheckGroup checkGroup) {
        checkGroupDao.addCheckGroup(checkGroup);
        if (checkItemIds != null) {
            for (Integer checkItemId : checkItemIds) {
                checkGroupDao.setCheckGroupByCheckItem(checkItemId, checkGroup.getId());
            }
        }
    }

    /**
     * 根据ID查询检查组
     *
     * @param checkGroupId
     * @return
     */
    @Override
    public CheckGroup findCheckGroupById(Integer checkGroupId) {
        return checkGroupDao.findCheckGroupById(checkGroupId);
    }

    /**
     * 根据检查组的ID查询与之对应的检查项
     *
     * @param checkGroupId
     * @return
     */
    @Override
    public Integer[] findCheckGroupAndCheckItemById(Integer checkGroupId) {
        return checkGroupDao.findCheckGroupAndCheckItemById(checkGroupId);
    }

    /**
     * 编辑检查组
     *
     * @param checkItemIds
     * @param checkGroup
     */
    @Transactional
    @Override
    public void editCheckGroup(Integer[] checkItemIds, CheckGroup checkGroup) {
        checkGroupDao.editCheckGroup(checkGroup);
        checkGroupDao.deleteCheckGroupAndCheckItemById(checkGroup.getId());
        if (checkItemIds != null) {
            for (Integer checkItemId : checkItemIds) {
                checkGroupDao.setCheckGroupByCheckItem(checkItemId, checkGroup.getId());
            }
        }
    }

    /**
     * 根据id删除检查组对应的检查项
     *
     * @param checkGroupId
     */
    @Transactional
    @Override
    public void deleteCheckGroup(Integer checkGroupId) throws RuntimeException {
        Integer[] CheckItemIds = findCheckGroupAndCheckItemById(checkGroupId);
        Integer checkGroupAndSetmealById = checkGroupDao.findCheckGroupAndSetmealById(checkGroupId);
        if (CheckItemIds.length > 0) {
            throw new RuntimeException(MessageConstant.DELETE_CHECKGROUP_CHECKITEM_FAIL);
        } else {
            if (checkGroupAndSetmealById > 0) {
                throw new RuntimeException(MessageConstant.DELETE_SETMEAL_CHECKGROUP_FAIL);
            } else {
                checkGroupDao.deleteCheckGroup(checkGroupId);
            }
        }
    }

    /**
     * 查询所有的检查组
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

}
