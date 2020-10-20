package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import entity.PageResult;
import entity.QueryPageBean;
import entity.Result;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

/**
 * 后台控制器
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/11 16:11
 * @see
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 图片自动上传到七牛云
     * @param imgFile
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result upload(MultipartFile imgFile) {
        try {
            String imgFileName = imgFile.getOriginalFilename();
            String suffix = imgFileName.substring(imgFileName.indexOf("."));
            UUID prefix = UUID.randomUUID();
            String newFileName = prefix.toString() + suffix;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), newFileName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,newFileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, newFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 添加套餐
     * @param checkGroupIds
     * @param setmeal
     * @return
     */
    @RequestMapping(value = "/addSetmeal", method = RequestMethod.POST)
    public Result addSetmeal(Integer[] checkGroupIds, @RequestBody Setmeal setmeal) {
        try {
            setmealService.addSetmeal(checkGroupIds, setmeal);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    /**
     * 分页查询套餐
     *
     * @param pagination
     * @return
     */
    @RequestMapping(value = "/findPage", method = RequestMethod.POST)
    public PageResult findPage(@RequestBody QueryPageBean pagination) {
        try {
            return setmealService.findPage(pagination);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据套餐ID回显数据
     * @return
     */
    @RequestMapping(value = "/findSetmealById",method = RequestMethod.GET)
    public Result findSetmealById(Integer SetmealId){
        try {
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmealService.findSetmealById(SetmealId));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    /**
     * 查询套餐对应的检查组
     * @param setmealId
     * @return
     */
    @RequestMapping("/findSetmealAndCheckGroupById")
    public Result findSetmealAndCheckGroupById(Integer setmealId){
        try {
            return new Result(true,MessageConstant.QUERY_SETMEAL_CHECKGROUP_SUCCESS,setmealService.findSetmealAndCheckGroupById(setmealId));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_CHECKGROUP_FAIL);
        }
    }

    /**
     * 编辑套餐
     * @param checkGroupIds
     * @param setmeal
     * @return
     */
    @RequestMapping(value = "/editSetmeal",method = RequestMethod.POST)
    public Result editSetmeal(Integer[] checkGroupIds ,@RequestBody Setmeal setmeal){
        try {
            setmealService.editSetmeal(checkGroupIds,setmeal);
            return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_SETMEAL_FAIL);
        }
    }

    /**
     * 删除套餐
     * @param setmealId
     * @return
     */
    @RequestMapping(value = "/deleteSetmeal",method = RequestMethod.GET)
    public Result deleteSetmeal(Integer setmealId){
        try {
            setmealService.deleteSetmeal(setmealId);
            return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_SETMEAL_CHECKGROUP_FAIL);
        }
    }
}
