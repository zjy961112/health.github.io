package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import entity.PageResult;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/8 17:37
 * @see
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 不分页查询检查项
     *
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    /**
     * 分页查询检查项
     *
     * @param currentPage
     * @param pageSize
     * @param queryString
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckItem> page = checkItemDao.findPage(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 添加检查项
     *
     * @param checkItem
     */
    @Transactional
    @Override
    public void addCheckItem(CheckItem checkItem) {
        checkItemDao.addCheckItem(checkItem);
    }

    /**
     * 根据ID回显数据
     *
     * @param id
     */
    @Override
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }


    @Transactional
    @Override
    /**
     * 编辑检查项
     * @param checkItem
     */
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    /**
     * 根据ID删除检查项
     *
     * @param id
     */
    @Transactional
    @Override
    public void deleterCheckItem(Integer id) {
        checkItemDao.deleteCheckItem(id);
    }

}
