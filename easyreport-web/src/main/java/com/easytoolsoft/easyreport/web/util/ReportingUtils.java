package com.easytoolsoft.easyreport.web.util;

import com.alibaba.fastjson.JSONObject;
import com.easytoolsoft.easyreport.common.util.DateUtils;
import com.easytoolsoft.easyreport.engine.data.*;
import com.easytoolsoft.easyreport.engine.query.Queryer;
import com.easytoolsoft.easyreport.po.ReportingPo;
import com.easytoolsoft.easyreport.service.ReportingGenerationService;
import com.easytoolsoft.easyreport.service.ReportingService;
import com.easytoolsoft.easyreport.view.QueryParamFormView;
import com.easytoolsoft.easyreport.viewmodel.HtmlFormElement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class ReportingUtils {
    private static ReportingService reportingService;
    private static ReportingGenerationService generationService;

    @Autowired
    public ReportingUtils(ReportingService reportingService, ReportingGenerationService generationService) {
        ReportingUtils.reportingService = reportingService;
        ReportingUtils.generationService = generationService;
    }

    public static ReportingPo getReportingPo(String uid) {
        return reportingService.getByUid(uid);
    }

    public static ReportDataSource getReportDataSource(ReportingPo report) {
        return generationService.getReportDataSource(report);
    }

    public static ReportParameter getReportParameter(ReportingPo report, Map<?, ?> parameters) {
        return generationService.getReportParameter(report, parameters);
    }

    public static void previewByFormMap(String uid, ModelAndView modelAndView, HttpServletRequest request) {
        ReportingPo report = reportingService.getByUid(uid);
        Map<String, Object> buildInParams = generationService.getBuildInParameters(request.getParameterMap(), report.getDataRange());
        Map<String, HtmlFormElement> formMap = generationService.getFormElementMap(report, buildInParams, 1);
        modelAndView.addObject("formMap", formMap);
        modelAndView.addObject("uid", uid);
        modelAndView.addObject("id", report.getId());
        modelAndView.addObject("name", report.getName());
    }

    public static void previewByTemplate(String uid, ModelAndView modelAndView, QueryParamFormView formView, HttpServletRequest request) {
        ReportingPo report = reportingService.getByUid(uid);
        List<ReportMetaDataColumn> metaDataColumns = report.getMetaColumnList();
        Map<String, Object> buildInParams = generationService.getBuildInParameters(request.getParameterMap(), report.getDataRange());
        List<HtmlFormElement> dateAndQueryElements = generationService.getDateAndQueryParamFormElements(report, buildInParams);
        HtmlFormElement statColumnFormElements = generationService.getStatColumnFormElements(metaDataColumns, 0);
        List<HtmlFormElement> nonStatColumnFormElements = generationService.getNonStatColumnFormElements(metaDataColumns);
        modelAndView.addObject("uid", uid);
        modelAndView.addObject("id", report.getId());
        modelAndView.addObject("name", report.getName());
        modelAndView.addObject("comment", report.getComment().trim());
        modelAndView.addObject("formHtmlText", formView.getFormHtmlText(dateAndQueryElements));
        modelAndView.addObject("statColumHtmlText", formView.getFormHtmlText(statColumnFormElements));
        modelAndView.addObject("nonStatColumHtmlText", formView.getFormHtmlText(nonStatColumnFormElements));
    }

    public static void generate(String uid, JSONObject jsonObject, HttpServletRequest request) {
        generate(uid, jsonObject, request.getParameterMap());
    }

    public static void generate(String uid, JSONObject jsonObject, Map<?, ?> parameters) {
        generate(uid, jsonObject, new HashMap<String, Object>(0), parameters);
    }

    public static void generate(String uid, JSONObject jsonObject, Map<String, Object> attachParams, Map<?, ?> parameters) {
        if (StringUtils.isBlank(uid)) {
            jsonObject.put("htmlTable", "uid参数为空导致数据不能加载！");
            return;
        }
        ReportTable reportTable = generate(uid, attachParams, parameters);
        jsonObject.put("htmlTable", reportTable.getHtmlText());
        jsonObject.put("metaDataRowCount", reportTable.getMetaDataRowCount());
        jsonObject.put("metaDataColumnCount", reportTable.getMetaDataColumnCount());
    }

    public static void generate(Queryer queryer, ReportParameter reportParameter, JSONObject jsonObject) {
        ReportTable reportTable = generationService.getReportTable(queryer, reportParameter);
        jsonObject.put("htmlTable", reportTable.getHtmlText());
        jsonObject.put("metaDataRowCount", reportTable.getMetaDataRowCount());
    }

    public static void generate(ReportMetaDataSet metaDataSet, ReportParameter reportParameter, JSONObject jsonObject) {
        ReportTable reportTable = generationService.getReportTable(metaDataSet, reportParameter);
        jsonObject.put("htmlTable", reportTable.getHtmlText());
        jsonObject.put("metaDataRowCount", reportTable.getMetaDataRowCount());
    }

    public static ReportTable generate(String uid, Map<?, ?> parameters) {
        return generate(uid, new HashMap<String, Object>(0), parameters);
    }

    public static ReportTable generate(String uid, Map<String, Object> attachParams, Map<?, ?> parameters) {
        ReportingPo report = reportingService.getByUid(uid);
        Map<String, Object> formParams = generationService.getFormParameters(parameters, report.getDataRange());
        if (attachParams != null && attachParams.size() > 0) {
            for (Entry<String, Object> es : attachParams.entrySet()) {
                formParams.put(es.getKey(), es.getValue());
            }
        }
        return generationService.getReportTable(report, formParams);
    }

    public static void exportToExcel(String uid, String name, String htmlText, HttpServletRequest request, HttpServletResponse response) {
        htmlText = htmlText.replaceFirst("<table>", "<tableFirst>");
        htmlText = htmlText.replaceAll("<table>", "<table cellpadding=\"3\" cellspacing=\"0\"  border=\"1\"  rull=\"all\" style=\"border-collapse: collapse\">");
        htmlText = htmlText.replaceFirst("<tableFirst>", "<table>");
        try (OutputStream out = response.getOutputStream()) {
            String fileName = name + "_" + DateUtils.getNow("yyyyMMddHHmmss");
            fileName = new String(fileName.getBytes(), "ISO8859-1") + ".xls";
            if ("large".equals(htmlText)) {
                ReportingPo report = reportingService.getByUid(uid);
                Map<String, Object> formParameters = generationService.getFormParameters(request.getParameterMap(), report.getDataRange());
                ReportTable reportTable = generationService.getReportTable(report, formParameters);
                htmlText = reportTable.getHtmlText();
            }
            response.reset();
            response.setHeader("Content-Disposition", String.format("attachment; filename=%s", fileName));
            response.setContentType("application/vnd.ms-excel; charset=utf-8");
            response.addCookie(new Cookie("fileDownload", "true"));
            //out.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF}); // 生成带bom的utf8文件
            out.write(htmlText.getBytes());
            out.flush();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
