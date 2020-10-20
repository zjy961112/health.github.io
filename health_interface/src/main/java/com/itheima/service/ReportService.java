package com.itheima.service;

import java.util.Map;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/18 19:56
 * @see
 */
public interface ReportService {

    /**
     * 获取运营统计数据
     *
     * @return
     */
    Map getBusinessReportData() throws Exception;
}
