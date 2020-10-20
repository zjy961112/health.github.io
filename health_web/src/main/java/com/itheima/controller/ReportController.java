package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.service.MemberService;
import com.itheima.service.ReportService;
import com.itheima.service.SetmealService;
import entity.Result;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/18 16:19
 * @see
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private SetmealService setmealService;

    @Reference
    private ReportService reportService;

    /**
     * 统计过去一年时间内每个月的会员总数据量
     *
     * @return
     */
    @RequestMapping(value = "/getMemberReport", method = RequestMethod.GET)
    public Result getMemberReport() {
        try {
            Map map = memberService.getMemberReport();
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    /**
     * 查询会员预约的各个套餐占比情况
     *
     * @return
     */
    @RequestMapping(value = "/getSetmealReport", method = RequestMethod.GET)
    public Result getSetmealReport() {
        try {
            Map map = setmealService.getSetmealReport();
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    /**
     * 获取运营统计数据
     *
     * @return
     */
    @RequestMapping(value = "/getBusinessReportData", method = RequestMethod.GET)
    public Result getBusinessReportData() {
        try {
            Map map = reportService.getBusinessReportData();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    /**
     * 导出Excel文件
     *
     * @return
     */
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            //获取到数据
            Map map = reportService.getBusinessReportData();
            //获取模板路径
            String realPath = request.getSession().getServletContext()
                    .getRealPath("template") + File.separator + "report_template.xlsx";
            //创建工作薄对象，关联模板文件
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(realPath)));
            //获取到工作表
            XSSFSheet sheet = workbook.getSheetAt(0);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformWorkbook(workbook, map);
            //获取到输出流
            ServletOutputStream outputStream = response.getOutputStream();
            //设置输出类型和名称
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            //输出文件
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_FAIL);
        }
    }

    /**
     * 导出PDF文档
     *
     * @return
     */
    @RequestMapping(value = "/exportBusinessReportPDF", method = RequestMethod.GET)
    public Result exportBusinessReportPDF(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            String jrxmlPath = request.getSession().getServletContext().getRealPath("template")
                    + File.separator + "exportBusinessTemplate.jrxml";
            String jasperPath = request.getSession().getServletContext().getRealPath("template")
                    + File.separator + "exportBusinessTemplate.jasper";
            JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);

            //获取数据
            Map map = reportService.getBusinessReportData();
            List<Map> hotSetmeal = (List<Map>) map.get("hotSetmeal");

            //填充数据
            JasperPrint print = JasperFillManager
                    .fillReport(jasperPath, map, new JRBeanCollectionDataSource(hotSetmeal));
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("application/pdf");
            response.setHeader("content-Disposition", "attachment;filename=report.pdf");
            JasperExportManager.exportReportToPdfStream(print, outputStream);
            outputStream.flush();
            outputStream.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.IMPORT_FAIL);
        }
    }
}
