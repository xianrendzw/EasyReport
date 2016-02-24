package com.easytoolsoft.easyreport.web.controllers;

import com.easytoolsoft.easyreport.common.viewmodel.JsonResult;
import com.easytoolsoft.easyreport.common.viewmodel.TreeNode;
import com.easytoolsoft.easyreport.po.ConfigDictPo;
import com.easytoolsoft.easyreport.service.ConfigDictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 报表配置控制器
 */
@Controller
@RequestMapping(value = "/report/config")
public class ConfigDictController extends AbstractController {
    @Resource
    private ConfigDictService configDictService;

    @RequestMapping(value = {"", "/", "/index"})
    public String index() {
        return "report/config";
    }

    @RequestMapping(value = "/query")
    @ResponseBody
    public List<ConfigDictPo> query(Integer id) {
        if (id == null)
            id = 0;
        return this.configDictService.getDao().queryByPid(id);
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public JsonResult add(ConfigDictPo po) {
        JsonResult result = new JsonResult(false, "");

        try {
            this.configDictService.add(po);
            this.setSuccessResult(result, "");
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }

        return result;
    }

    @RequestMapping(value = "/edit")
    @ResponseBody
    public JsonResult edit(ConfigDictPo po) {
        JsonResult result = new JsonResult(false, "");

        try {
            this.configDictService.edit(po);
            this.setSuccessResult(result, "");
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }

        return result;
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public JsonResult remove(int id) {
        JsonResult result = new JsonResult(false, "");

        try {
            this.configDictService.remove(id);
            this.setSuccessResult(result, "");
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }

        return result;
    }

    @RequestMapping(value = "/batchRemove")
    @ResponseBody
    public JsonResult remove(String ids) {
        JsonResult result = new JsonResult(false, "");

        try {
            this.configDictService.remove(ids);
            this.setSuccessResult(result, "");
        } catch (Exception ex) {
            this.setExceptionResult(result, ex);
        }

        return result;
    }

    @RequestMapping(value = "/listchildnodes")
    @ResponseBody
    public List<TreeNode<ConfigDictPo>> listChildNodes(Integer id) {
        if (id == null)
            id = 0;

        List<ConfigDictPo> configDicts = this.configDictService.getDao().queryByPid(id);
        List<TreeNode<ConfigDictPo>> treeNodes = new ArrayList<TreeNode<ConfigDictPo>>(configDicts.size());

        for (ConfigDictPo po : configDicts) {
            String configId = Integer.toString(po.getId());
            String text = po.getName();
            String state = "closed";
            TreeNode<ConfigDictPo> vmMode = new TreeNode<ConfigDictPo>(configId, text, state, po);
            treeNodes.add(vmMode);
        }

        return treeNodes;
    }
}