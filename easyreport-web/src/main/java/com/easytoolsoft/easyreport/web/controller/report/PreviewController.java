package com.easytoolsoft.easyreport.web.controller.report;

import com.alibaba.fastjson.JSONObject;
import com.easytoolsoft.easyreport.common.form.BootstrapQueryFormView;
import com.easytoolsoft.easyreport.common.form.EasyUIQueryFormView;
import com.easytoolsoft.easyreport.common.form.QueryParamFormView;
import com.easytoolsoft.easyreport.data.metadata.po.Report;
import com.easytoolsoft.easyreport.data.metadata.po.ReportOptions;
import com.easytoolsoft.easyreport.domain.metadata.exception.QueryParamsException;
import com.easytoolsoft.easyreport.domain.metadata.service.impl.ReportService;
import com.easytoolsoft.easyreport.domain.report.impl.ChartReportService;
import com.easytoolsoft.easyreport.domain.report.impl.TableReportService;
import com.easytoolsoft.easyreport.engine.data.ReportDataSet;
import com.easytoolsoft.easyreport.engine.exception.NotFoundLayoutColumnException;
import com.easytoolsoft.easyreport.engine.exception.SQLQueryException;
import com.easytoolsoft.easyreport.engine.exception.TemplatePraseException;
import com.easytoolsoft.easyreport.web.spring.aop.OpLog;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 报表生成控制器
 */
@Slf4j
@Controller
@RequestMapping(value = "/report")
public class PreviewController {
    @Resource
    private ReportService reportService;
    @Resource
    private TableReportService tableReportService;
    @Resource
    private ChartReportService chartReportService;

    @OpLog(name = "预览报表")
    @GetMapping(value = {"/uid/{uid}"})
    //@RequiresPermissions("report.designer:preview")
    public ModelAndView preview(@PathVariable String uid) {
        ModelAndView modelAndView = new ModelAndView("report/main");
        modelAndView.addObject("report", ReportHelper.getReportMetaData(uid));
        return modelAndView;
    }

    @OpLog(name = "预览报表")
    @RequestMapping(value = {"/{type}/uid/{uid}"})
    //@RequiresPermissions("report.designer:preview")
    public ModelAndView preview(@PathVariable String type, @PathVariable String uid,
                                String theme, Boolean isRenderByForm, String uiStyle,
                                HttpServletRequest request) {
        String typeName = StringUtils.equalsIgnoreCase("chart", type) ? "chart" : "table";
        String themeName = StringUtils.isBlank(theme) ? "default" : theme;
        String viewName = String.format("report/themes/%s/%s", themeName, typeName);
        ModelAndView modelAndView = new ModelAndView(viewName);
        try {
            if (BooleanUtils.isTrue(isRenderByForm)) {
                ReportHelper.renderByFormMap(uid, modelAndView, request);
            } else {
                QueryParamFormView formView = StringUtils.equalsIgnoreCase("bootstrap", uiStyle)
                        ? new BootstrapQueryFormView() : new EasyUIQueryFormView();
                ReportHelper.renderByTemplate(uid, modelAndView, formView, request);
            }
        } catch (QueryParamsException | TemplatePraseException ex) {
            modelAndView.addObject("formHtmlText", ex.getMessage());
            log.error("查询参数生成失败", ex);
        } catch (Exception ex) {
            modelAndView.addObject("formHtmlText", "报表系统错误:" + ex.getMessage());
            log.error("报表系统出错", ex);
        }
        return modelAndView;
    }

    @OpLog(name = "获取表格报表JSON格式数据")
    @ResponseBody
    @PostMapping(value = "/table/getData.json")
    //@RequiresPermissions("report.designer:preview")
    public JSONObject getTableData(String uid, HttpServletRequest request) {
        JSONObject data = new JSONObject();
        try {
            ReportHelper.generate(uid, data, request);
        } catch (QueryParamsException | NotFoundLayoutColumnException | SQLQueryException | TemplatePraseException ex) {
            data.put("htmlTable", ex.getMessage());
            log.error("报表生成失败", ex);
        } catch (Exception ex) {
            data.put("htmlTable", "报表系统错误:" + ex.getMessage());
            log.error("报表系统出错", ex);
        }

        return data;
    }

    @OpLog(name = "获取图表报表JSON格式数据")
    @ResponseBody
    @PostMapping(value = "/chart/getData.json")
    //@RequiresPermissions("report.designer:preview")
    public JSONObject getChartData(String uid, HttpServletRequest request) {
        JSONObject data = ReportHelper.getDefaultChartData();
        if (StringUtils.isNotBlank(uid)) {
            try {
                Report po = reportService.getByUid(uid);
                ReportOptions options = reportService.parseOptions(po.getOptions());
                Map<String, Object> formParameters = tableReportService.getFormParameters(request.getParameterMap(),
                        options.getDataRange());
                ReportDataSet reportData = tableReportService.getReportDataSet(po, formParameters);
                data.put("dimColumnMap", chartReportService.getDimColumnMap(reportData));
                data.put("dimColumns", chartReportService.getDimColumns(reportData));
                data.put("statColumns", chartReportService.getStatColumns(reportData));
                data.put("dataRows", chartReportService.getDataRows(reportData));
            } catch (QueryParamsException | NotFoundLayoutColumnException | SQLQueryException | TemplatePraseException ex) {
                data.put("msg", ex.getMessage());
                log.error("报表生成失败", ex);
            } catch (Exception ex) {
                data.put("msg", "报表系统错误:" + ex.getMessage());
                log.error("报表系统出错", ex);
            }
        }
        return data;
    }

    @PostMapping(value = "/table/exportExcel")
    @OpLog(name = "导出报表为Excel")
    //@RequiresPermissions("report.designer:export")
    public void exportToExcel(String uid, String name, String htmlText,
                              HttpServletRequest request, HttpServletResponse response) {
        try {
            ReportHelper.exportToExcel(uid, name, htmlText, request, response);
        } catch (Exception ex) {
            log.error("导出Excel失败", ex);
        }
    }
}