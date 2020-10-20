package com.itheima.service;

import entity.Result;
import java.util.Map;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/16 19:21
 * @see
 */
public interface MemberService {

    /**
     * 判断用户是否已注册
     *
     * @param telephone
     * @return Result
     */
    Result isRegister(String telephone);

    /**
     * 统计过去一年时间内每个月的会员总数据量
     *
     * @return
     */
    Map getMemberReport();
}
