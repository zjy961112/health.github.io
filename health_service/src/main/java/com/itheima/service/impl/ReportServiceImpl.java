package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.service.ReportService;
import com.itheima.utils.DateUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/18 19:59
 * @see
 */
@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    /**
     * 获取运营统计数据
     *
     * @return
     */
    @Override
    public Map getBusinessReportData() throws Exception {
        Map map = new HashMap();
        //获取当前日期
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        Integer todayNewMember = memberDao.findMemberCountByDate(today);

        //获取总会员数
        Integer totalMember = memberDao.findMemberTotalCount();

        //获取本周第一天
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        //获取本周最后一天
        String sundayOfThisWeek = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());
        // 获得本月第一天的日期
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        // 获取本月最后一天的日期
        String lastDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());

        //获取本周新增会员数
        Integer thisWeekNewMember = memberDao
                .findMemberCountAfterDate(thisWeekMonday, sundayOfThisWeek);

        //获取本月新增会员数
        Integer thisMonthNewMember = memberDao
                .findMemberCountAfterDate(firstDay4ThisMonth, lastDay4ThisMonth);

        // 今日预约数
        Integer todayOrderNumber = orderDao.findOrderCountByDate(today);

        // 本周预约数
        Map<String, Object> weekMap = new HashMap<String, Object>();
        weekMap.put("begin", thisWeekMonday);
        weekMap.put("end", sundayOfThisWeek);
        Integer thisWeekOrderNumber = orderDao.findOrderCountBetweenDate(weekMap);

        // 本月预约数
        Map<String, Object> monthMap = new HashMap<String, Object>();
        monthMap.put("begin", firstDay4ThisMonth);
        monthMap.put("end", lastDay4ThisMonth);
        Integer thisMonthOrderNumber = orderDao.findOrderCountBetweenDate(monthMap);

        // 今日到诊数
        Integer todayVisitsNumber = orderDao.findVisitsCountByDate(today);

        // 本周到诊数
        Map<String, Object> weekMap2 = new HashMap<String, Object>();
        weekMap2.put("begin", thisWeekMonday);
        weekMap2.put("end", sundayOfThisWeek);
        Integer thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate(weekMap2);

        // 本月到诊数
        Map<String, Object> monthMap2 = new HashMap<String, Object>();
        monthMap2.put("begin", firstDay4ThisMonth);
        monthMap2.put("end", lastDay4ThisMonth);
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(monthMap2);

        List<Map> hotSetmeal = orderDao.findHotSetmeal();

        map.put("reportDate", today);
        map.put("todayNewMember", todayNewMember);
        map.put("totalMember", totalMember);
        map.put("thisWeekNewMember", thisWeekNewMember);
        map.put("thisMonthNewMember", thisMonthNewMember);
        map.put("todayOrderNumber", todayOrderNumber);
        map.put("todayVisitsNumber", todayVisitsNumber);
        map.put("thisWeekOrderNumber", thisWeekOrderNumber);
        map.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
        map.put("thisMonthOrderNumber", thisMonthOrderNumber);
        map.put("thisMonthVisitsNumber", thisMonthVisitsNumber);
        map.put("hotSetmeal", hotSetmeal);
        return map;
    }
}
