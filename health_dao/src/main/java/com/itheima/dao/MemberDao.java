package com.itheima.dao;

import com.itheima.pojo.Member;
import org.apache.ibatis.annotations.Param;

/**
 * 会员dao层
 *
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/15 19:35
 * @see
 */
public interface MemberDao {

    /**
     * 查询用户是否已经注册
     *
     * @param telephone
     * @return
     */
    Member findMemberByTelephone(String telephone);

    /**
     * 会员注册
     *
     * @param member
     */
    void register(Member member);

    /**
     * 统计过去一年时间内每个月的会员总数据量
     *
     * @param month
     * @return
     */
    Integer getMemberReport(String month);

    /**
     * 获取今日新增会员数
     *
     * @param today
     * @return
     */
    Integer findMemberCountByDate(String today);

    /**
     * 获取总会员数
     *
     * @return
     */
    Integer findMemberTotalCount();

    /**
     * 获取本周或本月新增会员数
     *
     * @param thisWeekMonday
     * @param sundayOfThisWeek
     * @return
     */
    Integer findMemberCountAfterDate(@Param("thisWeekMonday") String thisWeekMonday,
            @Param("sundayOfThisWeek") String sundayOfThisWeek);

}
