package org.easyframework.report.web.controllers;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easyframework.report.common.util.DateUtils;
import org.easyframework.report.engine.exception.NotFoundLayoutColumnException;
import org.easyframework.report.engine.exception.SQLQueryException;
import org.easyframework.report.exception.QueryParamsException;
import org.easyframework.report.po.ReportingPo;
import org.easyframework.report.service.ReportingGenerationService;
import org.easyframework.report.service.ReportingService;
import org.easyframework.report.view.EasyUIQueryFormView;
import org.easyframework.report.viewmodel.HtmlFormElement;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

/**
 * 报表生成控制器
 */
@Controller
@RequestMapping(value = "/report")
public class ReportingController extends AbstractController {
	@Resource
	private ReportingService reportingService;
	@Resource
	private ReportingGenerationService generationService;

	@RequestMapping(value = "/home")
	public String home() {
		return "/home";
	}

	@RequestMapping(value = { "", "/", "/index" })
	public String index(HttpServletRequest request) {
		return "/designer";
	}

	@RequestMapping(value = { "/uid/{uid}" })
	public ModelAndView generateReport(@PathVariable String uid, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("/template");

		try {
			ReportingPo po = reportingService.getByUid(uid);
			Map<String, Object> buildinParams = generationService.getBuildInParameters(request.getParameterMap(), po.getDataRange());
			List<HtmlFormElement> dateAndQueryElements = generationService.getDateAndQueryParamFormElements(po, buildinParams);
			HtmlFormElement statColumnFormElements = generationService.getStatColumnFormElements(po.getMetaColumnList(), 1);
			EasyUIQueryFormView formView = new EasyUIQueryFormView();
			modelAndView.addObject("uid", uid);
			modelAndView.addObject("id", po.getId());
			modelAndView.addObject("name", po.getName());
			modelAndView.addObject("comment", po.getComment().trim());
			modelAndView.addObject("formHtmlText", formView.getFormHtmlText(dateAndQueryElements));
			modelAndView.addObject("statColumHtmlText", formView.getFormHtmlText(statColumnFormElements));
		} catch (QueryParamsException ex) {
			modelAndView.addObject("formHtmlText", String.format("<div>%s</div>", ex.getMessage()));
		} catch (Exception ex) {
			modelAndView.addObject("formHtmlText", "<div>报表查询参数生成失败！请联系管理员.</div>");
			this.logException("报表查询参数生成失败", ex);
		}

		return modelAndView;
	}

	@RequestMapping(value = "/generate", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject generateReport(String uid, Integer id, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();

		if (id == null) {
			return jsonObject;
		}

		try {
			ReportingPo po = reportingService.getByUid(uid);
			Map<String, Object> formParams = generationService.getFormParameters(request.getParameterMap(), po.getDataRange());
			String htmlTable = generationService.getHtmlTable(po, formParams);
			jsonObject.put("htmlTable", htmlTable);
		} catch (QueryParamsException | NotFoundLayoutColumnException | SQLQueryException ex) {
			jsonObject.put("htmlTable", String.format("<div>%s</div>", ex.getMessage()));
		} catch (Exception ex) {
			jsonObject.put("htmlTable", "<table><tr><td>报表生成失败！请联系管理员.</div>");
			this.logException("报表生成失败", ex);
		}

		return jsonObject;
	}

	@RequestMapping(value = "/exportexcel", method = RequestMethod.POST)
	public void exportToExcel(Integer id, String name, String htmlText, HttpServletRequest request, HttpServletResponse response) {

		try (OutputStream out = response.getOutputStream()) {
			String fileName = name + "_" + DateUtils.getNow("yyyyMMddHHmmss");
			fileName = new String(fileName.getBytes(), "ISO8859-1") + ".xls";

			if ("large".equals(htmlText)) {
				ReportingPo po = reportingService.getById(id);
				Map<String, Object> formParameters = generationService.getFormParameters(request.getParameterMap(), po.getDataRange());
				htmlText = generationService.getHtmlTable(po, formParameters);
			}
			response.reset();
			response.setHeader("Content-Disposition", String.format("attachment; filename=%s", fileName));
			response.setContentType("application/vnd.ms-excel; charset=utf-8");
			response.addCookie(new Cookie("fileDownload", "true"));
			out.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF }); // 生成带bom的utf8文件
			out.write(htmlText.getBytes());
			out.flush();
		} catch (Exception ex) {
			this.logException("导出excel失败", ex);
		}
	}
}