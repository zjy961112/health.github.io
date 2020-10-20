package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import entity.Result;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/16 19:25
 * @see
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    /**
     * 判断用户是否已注册
     *
     * @param telephone
     */
    @Override
    public Result isRegister(String telephone) {
        Member member = memberDao.findMemberByTelephone(telephone);
        if (member != null) {
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        }

        Member registerMember = new Member();
        registerMember.setPhoneNumber(telephone);
        registerMember.setRegTime(new Date());
        memberDao.register(registerMember);

        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }

    /**
     * 统计过去一年时间内每个月的会员总数据量
     *
     * @return
     */
    @Override
    public Map getMemberReport() {
        Map map = new HashMap();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -12);
        //定义月集合
        ArrayList<String> monthList = new ArrayList<>();
        //定义每月增长的会员数集合
        ArrayList<Integer> memberCount = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, 1);
            String month = new SimpleDateFormat("yyyy-MM").format(calendar.getTime());
            monthList.add(month);
            Integer count = memberDao.getMemberReport(month);
            memberCount.add(count);
        }
        map.put("months", monthList);
        map.put("memberCount", memberCount);
        return map;
    }
}
