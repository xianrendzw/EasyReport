package com.easytoolsoft.easyreport.web.controller.report;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import com.easytoolsoft.easyreport.engine.data.ReportDataSet;
import com.easytoolsoft.easyreport.engine.exception.NotFoundLayoutColumnException;
import com.easytoolsoft.easyreport.engine.exception.QueryParamsException;
import com.easytoolsoft.easyreport.engine.exception.SQLQueryException;
import com.easytoolsoft.easyreport.engine.exception.TemplatePraseException;
import com.easytoolsoft.easyreport.meta.domain.Report;
import com.easytoolsoft.easyreport.meta.domain.options.ReportOptions;
import com.easytoolsoft.easyreport.meta.form.BootstrapQueryFormView;
import com.easytoolsoft.easyreport.meta.form.EasyUIQueryFormView;
import com.easytoolsoft.easyreport.meta.form.QueryParamFormView;
import com.easytoolsoft.easyreport.meta.service.ChartReportService;
import com.easytoolsoft.easyreport.meta.service.ReportService;
import com.easytoolsoft.easyreport.meta.service.TableReportService;
import com.easytoolsoft.easyreport.support.annotation.OpLog;
import com.easytoolsoft.easyreport.support.model.ResponseResult;
import com.easytoolsoft.easyreport.web.util.ReportUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 报表生成控制器
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Slf4j
@Controller
@RequestMapping(value = "/report")
public class ReportController {
    @Resource
    private ReportService reportService;
    @Resource
    private TableReportService tableReportService;
    @Resource
    private ChartReportService chartReportService;

    @OpLog(name = "预览报表")
    @GetMapping(value = {"/uid/{uid}"})
    //@RequiresPermissions("report.designer:preview")
    public ModelAndView preview(@PathVariable final String uid) {
        final ModelAndView modelAndView = new ModelAndView("report/display");
        modelAndView.addObject("report", ReportUtils.getReportMetaData(uid));
        return modelAndView;
    }

    @OpLog(name = "预览报表")
    @RequestMapping(value = {"/{type}/uid/{uid}"})
    //@RequiresPermissions("report.designer:preview")
    public ModelAndView preview(@PathVariable final String type, @PathVariable final String uid,
                                final String theme, final Boolean isRenderByForm, final String uiStyle,
                                final HttpServletRequest request) {
        final String typeName = StringUtils.equalsIgnoreCase("chart", type) ? "chart" : "table";
        final String themeName = StringUtils.isBlank(theme) ? "default" : theme;
        final String viewName = String.format("report/themes/%s/%s", themeName, typeName);
        final ModelAndView modelAndView = new ModelAndView(viewName);
        try {
            if (BooleanUtils.isTrue(isRenderByForm)) {
                ReportUtils.renderByFormMap(uid, modelAndView, request);
            } else {
                final QueryParamFormView formView = StringUtils.equalsIgnoreCase("bootstrap", uiStyle)
                    ? new BootstrapQueryFormView() : new EasyUIQueryFormView();
                ReportUtils.renderByTemplate(uid, modelAndView, formView, request);
            }
        } catch (QueryParamsException | TemplatePraseException ex) {
            modelAndView.addObject("formHtmlText", ex.getMessage());
            log.error("查询参数生成失败", ex);
        } catch (final Exception ex) {
            modelAndView.addObject("formHtmlText", "报表系统错误:" + ex.getMessage());
            log.error("报表系统出错", ex);
        }
        return modelAndView;
    }

    @OpLog(name = "获取报表DataSet JSON格式数据")
    @ResponseBody
    @RequestMapping(value = "/getDataSet.json")
    //@RequiresPermissions("report.designer:preview")
    public ResponseResult getDataSet(final String uid, final HttpServletRequest request) {
        ResponseResult result;
        try {
            final Report po = this.reportService.getByUid(uid);
            final ReportOptions options = this.reportService.parseOptions(po.getOptions());
            final Map<String, Object> formParameters = this.tableReportService.getFormParameters(
                request.getParameterMap(), options.getDataRange());
            result = ResponseResult.success(this.tableReportService.getReportDataSet(po, formParameters));
        } catch (QueryParamsException | NotFoundLayoutColumnException | SQLQueryException | TemplatePraseException ex) {
            log.error("报表生成失败", ex);
            result = ResponseResult.failure(10007, "报表生成失败", ex.getMessage());
        } catch (final Exception ex) {
            log.error("报表系统出错", ex);
            result = ResponseResult.failure(10008, "报表系统出错", ex.getMessage());
        }
        return result;
    }

    @OpLog(name = "获取表格报表JSON格式数据")
    @ResponseBody
    @PostMapping(value = "/table/getData.json")
    //@RequiresPermissions("report.designer:preview")
    public JSONObject getTableData(final String uid, final HttpServletRequest request) {
        final JSONObject data = new JSONObject();
        try {
            ReportUtils.generate(uid, data, request);
        } catch (QueryParamsException | NotFoundLayoutColumnException | SQLQueryException | TemplatePraseException ex) {
            data.put("htmlTable", ex.getMessage());
            log.error("报表生成失败", ex);
        } catch (final Exception ex) {
            data.put("htmlTable", "报表系统错误:" + ex.getMessage());
            log.error("报表系统出错", ex);
        }

        return data;
    }

    @OpLog(name = "获取图表报表JSON格式数据")
    @ResponseBody
    @PostMapping(value = "/chart/getData.json")
    //@RequiresPermissions("report.designer:preview")
    public JSONObject getChartData(final String uid, final HttpServletRequest request) {
        final JSONObject data = ReportUtils.getDefaultChartData();
        if (StringUtils.isNotBlank(uid)) {
            try {
                final Report po = this.reportService.getByUid(uid);
                final ReportOptions options = this.reportService.parseOptions(po.getOptions());
                final Map<String, Object> formParameters = this.tableReportService.getFormParameters(
                    request.getParameterMap(), options.getDataRange());
                final ReportDataSet reportDataSet = this.tableReportService.getReportDataSet(po, formParameters);
                data.put("dimColumnMap", this.chartReportService.getDimColumnMap(reportDataSet));
                data.put("dimColumns", this.chartReportService.getDimColumns(reportDataSet));
                data.put("statColumns", this.chartReportService.getStatColumns(reportDataSet));
                data.put("dataRows", this.chartReportService.getDataRows(reportDataSet));
            } catch (QueryParamsException | NotFoundLayoutColumnException | SQLQueryException |
                TemplatePraseException ex) {
                data.put("msg", ex.getMessage());
                log.error("报表生成失败", ex);
            } catch (final Exception ex) {
                data.put("msg", "报表系统错误:" + ex.getMessage());
                log.error("报表系统出错", ex);
            }
        }
        return data;
    }

    @PostMapping(value = "/table/exportExcel")
    @OpLog(name = "导出报表为Excel")
    //@RequiresPermissions("report.designer:export")
    public void exportToExcel(final String uid, final String name, final String htmlText,
                              final HttpServletRequest request, final HttpServletResponse response) {
        try {
            ReportUtils.exportToExcel(uid, name, htmlText, request, response);
        } catch (final Exception ex) {
            log.error("导出Excel失败", ex);
        }
    }
}