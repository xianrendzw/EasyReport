package com.easytoolsoft.easyreport.web.util;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataSet;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;
import com.easytoolsoft.easyreport.engine.data.ReportTable;
import com.easytoolsoft.easyreport.engine.query.Queryer;
import com.easytoolsoft.easyreport.engine.util.DateUtils;
import com.easytoolsoft.easyreport.meta.domain.Report;
import com.easytoolsoft.easyreport.meta.domain.options.ReportOptions;
import com.easytoolsoft.easyreport.meta.form.QueryParamFormView;
import com.easytoolsoft.easyreport.meta.form.control.HtmlFormElement;
import com.easytoolsoft.easyreport.meta.service.ReportService;
import com.easytoolsoft.easyreport.meta.service.TableReportService;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Tom Deng
 * @date 2017-03-25
 */
@Component
public class ReportUtils {
    private static ReportService reportService;
    private static TableReportService tableReportService;

    @Autowired
    public ReportUtils(final ReportService reportService, final TableReportService tableReportService) {
        ReportUtils.reportService = reportService;
        ReportUtils.tableReportService = tableReportService;
    }

    public static Report getReportMetaData(final String uid) {
        return reportService.getByUid(uid);
    }

    public static JSONObject getDefaultChartData() {
        return new JSONObject(6) {
            {
                put("dimColumnMap", null);
                put("dimColumns", null);
                put("statColumns", null);
                put("dataRows", null);
                put("msg", "");
            }
        };
    }

    public static ReportDataSource getReportDataSource(final Report report) {
        return reportService.getReportDataSource(report.getDsId());
    }

    public static ReportParameter getReportParameter(final Report report, final Map<?, ?> parameters) {
        return tableReportService.getReportParameter(report, parameters);
    }

    public static void renderByFormMap(final String uid, final ModelAndView modelAndView,
                                       final HttpServletRequest request) {
        final Report report = reportService.getByUid(uid);
        final ReportOptions options = reportService.parseOptions(report.getOptions());
        final Map<String, Object> buildInParams = tableReportService.getBuildInParameters(request.getParameterMap(),
            options.getDataRange());
        final Map<String, HtmlFormElement> formMap = tableReportService.getFormElementMap(report, buildInParams, 1);
        modelAndView.addObject("formMap", formMap);
        modelAndView.addObject("uid", uid);
        modelAndView.addObject("id", report.getId());
        modelAndView.addObject("name", report.getName());
    }

    public static void renderByTemplate(final String uid, final ModelAndView modelAndView,
                                        final QueryParamFormView formView,
                                        final HttpServletRequest request) {
        final Report report = reportService.getByUid(uid);
        final ReportOptions options = reportService.parseOptions(report.getOptions());
        final List<ReportMetaDataColumn> metaDataColumns = reportService.parseMetaColumns(report.getMetaColumns());
        final Map<String, Object> buildInParams = tableReportService.getBuildInParameters(request.getParameterMap(),
            options.getDataRange());
        final List<HtmlFormElement> dateAndQueryElements = tableReportService.getDateAndQueryParamFormElements(report,
            buildInParams);
        final HtmlFormElement statColumnFormElements = tableReportService.getStatColumnFormElements(metaDataColumns, 0);
        final List<HtmlFormElement> nonStatColumnFormElements = tableReportService.getNonStatColumnFormElements(
            metaDataColumns);
        modelAndView.addObject("uid", uid);
        modelAndView.addObject("id", report.getId());
        modelAndView.addObject("name", report.getName());
        modelAndView.addObject("comment", report.getComment().trim());
        modelAndView.addObject("formHtmlText", formView.getFormHtmlText(dateAndQueryElements));
        modelAndView.addObject("statColumHtmlText", formView.getFormHtmlText(statColumnFormElements));
        modelAndView.addObject("nonStatColumHtmlText", formView.getFormHtmlText(nonStatColumnFormElements));
    }

    public static void generate(final String uid, final JSONObject data, final HttpServletRequest request) {
        generate(uid, data, request.getParameterMap());
    }

    public static void generate(final String uid, final JSONObject data, final Map<?, ?> parameters) {
        generate(uid, data, new HashMap<>(0), parameters);
    }

    public static void generate(final String uid, final JSONObject data, final Map<String, Object> attachParams,
                                final Map<?, ?> parameters) {
        if (StringUtils.isBlank(uid)) {
            data.put("htmlTable", "uid参数为空导致数据不能加载！");
            return;
        }
        final ReportTable reportTable = generate(uid, attachParams, parameters);
        data.put("htmlTable", reportTable.getHtmlText());
        data.put("metaDataRowCount", reportTable.getMetaDataRowCount());
        data.put("metaDataColumnCount", reportTable.getMetaDataColumnCount());
    }

    public static void generate(final Queryer queryer, final ReportParameter reportParameter, final JSONObject data) {
        final ReportTable reportTable = tableReportService.getReportTable(queryer, reportParameter);
        data.put("htmlTable", reportTable.getHtmlText());
        data.put("metaDataRowCount", reportTable.getMetaDataRowCount());
    }

    public static void generate(final ReportMetaDataSet metaDataSet, final ReportParameter reportParameter,
                                final JSONObject data) {
        final ReportTable reportTable = tableReportService.getReportTable(metaDataSet, reportParameter);
        data.put("htmlTable", reportTable.getHtmlText());
        data.put("metaDataRowCount", reportTable.getMetaDataRowCount());
    }

    public static ReportTable generate(final String uid, final Map<?, ?> parameters) {
        return generate(uid, new HashMap<>(0), parameters);
    }

    public static ReportTable generate(final String uid, final Map<String, Object> attachParams,
                                       final Map<?, ?> parameters) {
        final Report report = reportService.getByUid(uid);
        final ReportOptions options = reportService.parseOptions(report.getOptions());
        final Map<String, Object> formParams = tableReportService.getFormParameters(parameters, options.getDataRange());
        if (MapUtils.isNotEmpty(attachParams)) {
            for (final Entry<String, Object> es : attachParams.entrySet()) {
                formParams.put(es.getKey(), es.getValue());
            }
        }
        return tableReportService.getReportTable(report, formParams);
    }

    public static void exportToExcel(final String uid, final String name, String htmlText,
                                     final HttpServletRequest request,
                                     final HttpServletResponse response) {
        htmlText = htmlText.replaceFirst("<table>", "<tableFirst>");
        htmlText = htmlText.replaceAll("<table>",
            "<table cellpadding=\"3\" cellspacing=\"0\"  border=\"1\" rull=\"all\" style=\"border-collapse: "
                + "collapse\">");
        htmlText = htmlText.replaceFirst("<tableFirst>", "<table>");
        try (OutputStream out = response.getOutputStream()) {
            String fileName = name + "_" + DateUtils.getNow("yyyyMMddHHmmss");
            fileName = new String(fileName.getBytes(), "ISO8859-1") + ".xls";
            if ("large".equals(htmlText)) {
                final Report report = reportService.getByUid(uid);
                final ReportOptions options = reportService.parseOptions(report.getOptions());
                final Map<String, Object> formParameters = tableReportService.getFormParameters(
                    request.getParameterMap(),
                    options.getDataRange());
                final ReportTable reportTable = tableReportService.getReportTable(report, formParameters);
                htmlText = reportTable.getHtmlText();
            }
            response.reset();
            response.setHeader("Content-Disposition", String.format("attachment; filename=%s", fileName));
            response.setContentType("application/vnd.ms-excel; charset=utf-8");
            response.addCookie(new Cookie("fileDownload", "true"));
            //out.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF}); // 生成带bom的utf8文件
            out.write(htmlText.getBytes());
            out.flush();
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
