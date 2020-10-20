package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import entity.Result;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/12 17:12
 * @see
 */
@RestController
@RequestMapping("/orderSetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 批量设置预约人数
     *
     * @param excelFile
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result upload(MultipartFile excelFile) {
        try {
            List<String[]> listStr = POIUtils.readExcel(excelFile);
            ArrayList<OrderSetting> orderSettingArrayList = new ArrayList();
            if (listStr != null) {
                for (String[] strings : listStr) {
                    OrderSetting orderSetting = new OrderSetting();
                    orderSetting.setOrderDate(new Date(strings[0]));
                    orderSetting.setNumber(Integer.parseInt(strings[1]));
                    orderSettingArrayList.add(orderSetting);
                }
                orderSettingService.batchOrderSettingService(orderSettingArrayList);
            }
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    /**
     * 单个设置预约人数
     *
     * @param os
     * @return
     */
    @RequestMapping(value = "/updateNumberByOrderDate", method = RequestMethod.POST)
    public Result updateNumberByOrderDate(@RequestBody OrderSetting os) {
        try {
            orderSettingService.updateOrInsertOS(os);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }

    /**
     * 查询预约信息
     * @return
     */
    @RequestMapping(value = "/getData",method = RequestMethod.GET)
    public Result getData(@RequestParam String data){
        try {
            System.out.println(orderSettingService.getData(data));
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,orderSettingService.getData(data));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }
}
