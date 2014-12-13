package org.easyframework.report.web.util;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.easyframework.report.common.util.DateUtils;
import org.easyframework.report.po.ReportingPo;
import org.easyframework.report.service.ReportingGenerationService;
import org.easyframework.report.service.ReportingService;
import org.easyframework.report.view.QueryParamFormView;
import org.easyframework.report.viewmodel.HtmlFormElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

@Service
public class ReportingUtils {
	private static ReportingService reportingService;
	private static ReportingGenerationService generationService;

	@Autowired
	public ReportingUtils(ReportingService reportingService, ReportingGenerationService generationService) {
		ReportingUtils.reportingService = reportingService;
		ReportingUtils.generationService = generationService;
	}

	public static void previewByFormMap(String uid, ModelAndView modelAndView, HttpServletRequest request) {
		ReportingPo report = reportingService.getByUid(uid);
		Map<String, Object> buildinParams = generationService.getBuildInParameters(request.getParameterMap(), report.getDataRange());
		Map<String, HtmlFormElement> formMap = generationService.getFormElementMap(report, buildinParams, 1);
		modelAndView.addObject("formMap", formMap);
		modelAndView.addObject("uid", uid);
		modelAndView.addObject("id", report.getId());
		modelAndView.addObject("name", report.getName());
	}

	public static void previewByTemplate(String uid, ModelAndView modelAndView, QueryParamFormView formView, HttpServletRequest request) {
		ReportingPo report = reportingService.getByUid(uid);
		Map<String, Object> buildinParams = generationService.getBuildInParameters(request.getParameterMap(), report.getDataRange());
		List<HtmlFormElement> dateAndQueryElements = generationService.getDateAndQueryParamFormElements(report, buildinParams);
		HtmlFormElement statColumnFormElements = generationService.getStatColumnFormElements(report.getMetaColumnList(), 1);
		modelAndView.addObject("uid", uid);
		modelAndView.addObject("id", report.getId());
		modelAndView.addObject("name", report.getName());
		modelAndView.addObject("comment", report.getComment().trim());
		modelAndView.addObject("formHtmlText", formView.getFormHtmlText(dateAndQueryElements));
		modelAndView.addObject("statColumHtmlText", formView.getFormHtmlText(statColumnFormElements));
	}

	public static void generate(String uid, JSONObject jsonObject, HttpServletRequest request) {
		if (StringUtils.isBlank(uid)) {
			jsonObject.put("htmlTable", "uid参数为空导致数据不能加载！");
			return;
		}

		ReportingPo report = reportingService.getByUid(uid);
		Map<String, Object> formParams = generationService.getFormParameters(request.getParameterMap(), report.getDataRange());
		String htmlTable = generationService.getHtmlTable(report, formParams);
		jsonObject.put("htmlTable", htmlTable);
	}

	public static void exportToExcel(String uid, String name, String htmlText, HttpServletRequest request, HttpServletResponse response) {
		try (OutputStream out = response.getOutputStream()) {
			String fileName = name + "_" + DateUtils.getNow("yyyyMMddHHmmss");
			fileName = new String(fileName.getBytes(), "ISO8859-1") + ".xls";
			if ("large".equals(htmlText)) {
				ReportingPo report = reportingService.getByUid(uid);
				Map<String, Object> formParameters = generationService.getFormParameters(request.getParameterMap(), report.getDataRange());
				htmlText = generationService.getHtmlTable(report, formParameters);
			}
			response.reset();
			response.setHeader("Content-Disposition", String.format("attachment; filename=%s", fileName));
			response.setContentType("application/vnd.ms-excel; charset=utf-8");
			response.addCookie(new Cookie("fileDownload", "true"));
			out.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF }); // 生成带bom的utf8文件
			out.write(htmlText.getBytes());
			out.flush();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
