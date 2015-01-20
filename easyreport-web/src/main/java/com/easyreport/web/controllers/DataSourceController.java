package com.easyreport.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.easyreport.common.viewmodel.JsonResult;
import com.easyreport.data.PageInfo;
import com.easyreport.po.DataSourcePo;
import com.easyreport.service.DataSourceService;

/**
 * 报表数据源控制器
 */
@Controller
@RequestMapping(value = "report/ds")
public class DataSourceController extends AbstractController {
	@Resource
	private DataSourceService datasourceService;

	@RequestMapping(value = { "", "/", "/index" })
	public String index() {
		return "report/dataSource";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public List<DataSourcePo> list(HttpServletRequest request) {
		return this.datasourceService.getAll();
	}

	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(Integer page, Integer rows, HttpServletRequest request) {
		if (page == null)
			page = 1;
		if (rows == null)
			rows = 50;

		PageInfo pageInfo = new PageInfo((page - 1) * rows, rows);
		List<DataSourcePo> list = this.datasourceService.getByPage(pageInfo);

		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		modelMap.put("total", pageInfo.getTotals());
		modelMap.put("rows", list);
		return modelMap;
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public JsonResult add(DataSourcePo po, HttpServletRequest request) {
		JsonResult result = new JsonResult(false, "");

		try {
			po.setUid(UUID.randomUUID().toString());
			this.datasourceService.add(po);
			this.setSuccessResult(result, "");
		} catch (Exception ex) {
			this.setExceptionResult(result, ex);
		}

		return result;
	}

	@RequestMapping(value = "/edit")
	@ResponseBody
	public JsonResult edit(DataSourcePo po, HttpServletRequest request) {
		JsonResult result = new JsonResult(false, "");

		try {
			String[] columnNames = new String[] {
					DataSourcePo.Name, DataSourcePo.User,
					DataSourcePo.Password, DataSourcePo.JdbcUrl };
			this.datasourceService.edit(po, columnNames);
			this.setSuccessResult(result, "");
		} catch (Exception ex) {
			this.setExceptionResult(result, ex);
		}

		return result;
	}

	@RequestMapping(value = "/testConnection")
	@ResponseBody
	public JsonResult testConnection(String url, String pass, String user) {
		JsonResult result = new JsonResult(false, "");

		try {
			result.setSuccess(this.datasourceService.getDao().testConnection(url, pass, user));
		} catch (Exception ex) {
			this.setExceptionResult(result, ex);
		}

		return result;
	}

	@RequestMapping(value = "/remove")
	@ResponseBody
	public JsonResult remove(int id, HttpServletRequest request) {
		JsonResult result = new JsonResult(false, "");

		try {
			this.datasourceService.remove(id);
			this.setSuccessResult(result, "");
		} catch (Exception ex) {
			this.setExceptionResult(result, ex);
		}

		return result;
	}

	@RequestMapping(value = "/batchRemove")
	@ResponseBody
	public JsonResult remove(String ids, HttpServletRequest request) {
		JsonResult result = new JsonResult(false, "您没有权限执行该操作!");

		try {
			this.datasourceService.remove(ids);
			this.setSuccessResult(result, "");
		} catch (Exception ex) {
			this.setExceptionResult(result, ex);
		}

		return result;
	}
}