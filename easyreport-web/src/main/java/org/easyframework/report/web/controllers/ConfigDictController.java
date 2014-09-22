package org.easyframework.report.web.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.easyframework.report.common.util.CheckUtils;
import org.easyframework.report.entity.ConfigDict;
import org.easyframework.report.service.ConfigDictService;
import org.easyframework.report.web.models.JsonResult;
import org.easyframework.report.web.models.TreeNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ConfigDict控制器
 */
@Controller
@RequestMapping(value = "/config")
public class ConfigDictController extends AbstractController {
	@Resource
	private ConfigDictService configDictService;

	@RequestMapping(value = { "/", "/index" })
	public String index() {
		return "/config/index";
	}

	@RequestMapping(value = "/getchildconfig")
	@ResponseBody
	public List<ConfigDict> getChildConfig(Integer id) {
		try {
			id = CheckUtils.validateNull(id, 0);
			return this.configDictService.getDao().queryByPid(id);
		} catch (Exception ex) {
			logger.error(ex.toString());
			return new ArrayList<ConfigDict>(0);
		}
	}

	@RequestMapping(value = "/getchildnodes")
	@ResponseBody
	public List<TreeNode<ConfigDict>> getChildNodes(Integer id) {
		try {
			id = CheckUtils.validateNull(id, 0);
			List<ConfigDict> configDicts = this.configDictService.getDao().queryByPid(id);
			List<TreeNode<ConfigDict>> treeNodes = new ArrayList<TreeNode<ConfigDict>>(configDicts.size());
			for (ConfigDict po : configDicts) {
				String configId = Integer.toString(po.getId());
				String text = po.getName();
				String state = "closed";
				TreeNode<ConfigDict> vmMode = new TreeNode<ConfigDict>(configId, text, state, po);
				treeNodes.add(vmMode);
			}
			return treeNodes;
		} catch (Exception ex) {
			logger.error(ex.toString());
			return new ArrayList<TreeNode<ConfigDict>>(0);
		}
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public JsonResult add(ConfigDict model) {
		JsonResult result = new JsonResult(true, "操作成功！");

		try {
			this.configDictService.add(model);
		} catch (Exception ex) {
			this.setErrorResult(result, ex);
		}

		return result;
	}

	@RequestMapping(value = "/edit")
	@ResponseBody
	public JsonResult edit(ConfigDict model) {
		JsonResult result = new JsonResult(true, "操作成功！");

		try {
			this.configDictService.edit(model);
		} catch (Exception ex) {
			this.setErrorResult(result, ex);
		}

		return result;
	}

	@RequestMapping(value = "/remove")
	@ResponseBody
	public JsonResult remove(int id) {
		JsonResult result = new JsonResult(true, "操作成功！");

		try {
			this.configDictService.remove(id);
		} catch (Exception ex) {
			this.setErrorResult(result, ex);
		}

		return result;
	}

	@RequestMapping(value = "/batchremove")
	@ResponseBody
	public JsonResult batchRemove(String ids) {
		JsonResult result = new JsonResult(true, "操作成功！");

		try {
			this.configDictService.remove(ids);
		} catch (Exception ex) {
			this.setErrorResult(result, ex);
		}

		return result;
	}
}