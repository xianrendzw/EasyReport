package com.easytoolsoft.easyreport.web.controller.report;

import com.alibaba.fastjson.JSONObject;
import com.easytoolsoft.easyreport.common.form.EasyUIQueryFormView;
import com.easytoolsoft.easyreport.engine.data.ReportDataSet;
import com.easytoolsoft.easyreport.engine.exception.NotFoundLayoutColumnException;
import com.easytoolsoft.easyreport.engine.exception.SQLQueryException;
import com.easytoolsoft.easyreport.engine.exception.TemplatePraseException;
import com.easytoolsoft.easyreport.metadata.exception.QueryParamsException;
import com.easytoolsoft.easyreport.data.metadata.po.Report;
import com.easytoolsoft.easyreport.data.metadata.po.ReportOptions;
import com.easytoolsoft.easyreport.metadata.service.impl.ReportService;
import com.easytoolsoft.easyreport.report.impl.ChartReportService;
import com.easytoolsoft.easyreport.report.impl.TableReportService;
import com.easytoolsoft.easyreport.web.controller.common.AbstractController;
import com.easytoolsoft.easyreport.web.util.ReportUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 图表报表生成控制器
 */
@Controller
@Slf4j
@RequestMapping(value = "/report/chart")
public class ChartReportController extends AbstractController {
    @Resource
    private ReportService reportService;
    @Resource
    private TableReportService tableReportService;
    @Resource
    private ChartReportService chartReportService;

    @RequestMapping(value = {"/{uid}"})
    public ModelAndView preview(@PathVariable String uid, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("report/chart");
        try {
            ReportUtils.previewByTemplate(uid, modelAndView, new EasyUIQueryFormView(), request);
        } catch (QueryParamsException | TemplatePraseException ex) {
            modelAndView.addObject("formHtmlText", ex.getMessage());
            log.error("查询参数生成失败", ex);
        } catch (Exception ex) {
            modelAndView.addObject("formHtmlText", "查询参数生成失败！请联系管理员.");
            log.error("查询参数生成失败", ex);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    public JSONObject getData(String uid, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dimColumnMap", null);
        jsonObject.put("dimColumns", null);
        jsonObject.put("statColumns", null);
        jsonObject.put("dataRows", null);
        jsonObject.put("msg", "");

        if (uid == null) {
            return jsonObject;
        }

        try {
            Report po = reportService.getByUid(uid);
            ReportOptions options = reportService.parseOptions(po.getOptions());
            Map<String, Object> formParameters = tableReportService.getFormParameters(
                    request.getParameterMap(), options.getDataRange());
            ReportDataSet reportData = tableReportService.getReportDataSet(po, formParameters);
            jsonObject.put("dimColumnMap", chartReportService.getDimColumnMap(reportData));
            jsonObject.put("dimColumns", chartReportService.getDimColumns(reportData));
            jsonObject.put("statColumns", chartReportService.getStatColumns(reportData));
            jsonObject.put("dataRows", chartReportService.getDataRows(reportData));
        } catch (QueryParamsException | NotFoundLayoutColumnException | SQLQueryException | TemplatePraseException ex) {
            jsonObject.put("msg", ex.getMessage());
            log.error("报表生成失败", ex);
        } catch (Exception ex) {
            jsonObject.put("msg", "报表生成失败！请联系管理员。");
            log.error("报表生成失败", ex);
        }
        return jsonObject;
    }
}