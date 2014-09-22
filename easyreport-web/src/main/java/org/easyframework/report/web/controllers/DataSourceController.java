package org.easyframework.report.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.easyframework.report.common.util.CheckUtils;
import org.easyframework.report.data.PageInfo;
import org.easyframework.report.entity.DataSource;
import org.easyframework.report.service.DataSourceService;
import org.easyframework.report.web.models.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * DataSource控制器
 */
@Controller
@RequestMapping(value = "/ds")
public class DataSourceController extends AbstractController {
	@Resource
	private DataSourceService datasourceService;

	@RequestMapping(value = { "/", "/index" })
	public String index() {
		return "/datasource/index";
	}

	@RequestMapping(value = "/getall")
	@ResponseBody
	public List<DataSource> getAll() {
		return this.datasourceService.getAll();
	}

	@RequestMapping(value = "/getpage")
	@ResponseBody
	public Map<String, Object> getPage(Integer page, Integer rows, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>(2);

		try {
			page = CheckUtils.validateNull(page, 1);
			rows = CheckUtils.validateNull(rows, 50);
			PageInfo pageInfo = new PageInfo((page - 1) * rows, rows);
			List<DataSource> list = this.datasourceService.getByPage(pageInfo);
			modelMap.put("total", pageInfo.getTotals());
			modelMap.put("rows", list);
			return modelMap;
		} catch (Exception ex) {
			logger.error(ex.toString());
			modelMap.put("total", 0);
			modelMap.put("rows", "[]");
			return modelMap;
		}
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public JsonResult add(DataSource model, HttpServletRequest request) {
		JsonResult result = new JsonResult(true, "操作成功！");

		try {
			model.setUid(UUID.randomUUID().toString());
			this.datasourceService.addWithId(model);
		} catch (Exception ex) {
			this.setErrorResult(result, ex);
		}

		return result;
	}

	@RequestMapping(value = "/edit")
	@ResponseBody
	public JsonResult edit(DataSource model, HttpServletRequest request) {
		JsonResult result = new JsonResult(true, "操作成功！");

		try {
			this.datasourceService.edit(model);
		} catch (Exception ex) {
			this.setErrorResult(result, ex);
		}

		return result;
	}

	@RequestMapping(value = "/remove")
	@ResponseBody
	public JsonResult remove(int id, HttpServletRequest request) {
		JsonResult result = new JsonResult(true, "操作成功！");

		try {
			this.datasourceService.remove(id);
			result.setMsg("操作成功！");
		} catch (Exception ex) {
			this.setErrorResult(result, ex);
		}

		return result;
	}

	@RequestMapping(value = "/batchremove")
	@ResponseBody
	public JsonResult batchRemove(String ids, HttpServletRequest request) {
		JsonResult result = new JsonResult(true, "操作成功！");

		try {
			this.datasourceService.remove(ids);
		} catch (Exception ex) {
			this.setErrorResult(result, ex);
		}

		return result;
	}

	@RequestMapping(value = "/connect")
	@ResponseBody
	public JsonResult testConnection(String url, String pass, String user) {
		JsonResult result = new JsonResult(true, "操作成功！");

		try {
			boolean success = this.datasourceService.getDao().testConnection(url, pass, user);
			result.setMsg(success ? "连接成功！" : "连接失败！");
		} catch (Exception ex) {
			this.setErrorResult(result, ex);
		}

		return result;
	}
}