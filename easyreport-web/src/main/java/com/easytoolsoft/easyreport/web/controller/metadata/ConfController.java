package com.easytoolsoft.easyreport.web.controller.metadata;

import com.easytoolsoft.easyreport.web.common.JsonResult;
import com.easytoolsoft.easyreport.web.controller.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 报表配置控制器
 */
@Controller
@RequestMapping(value = "/report/config")
public class ConfController extends AbstractController {
    @Resource
    private ConfigDictService configDictService;

    @RequestMapping(value = {"", "/", "/index"})
    public String index() {
        return "report/config";
    }

    @RequestMapping(value = "/query")

    public List<ConfigDictPo> query(Integer id) {
        if (id == null)
            id = 0;
        return this.configDictService.getDao().queryByPid(id);
    }

    @RequestMapping(value = "/add")

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

    public List<EasyUITreeNode<ConfigDictPo>> listChildNodes(Integer id) {
        if (id == null)
            id = 0;

        List<ConfigDictPo> configDicts = this.configDictService.getDao().queryByPid(id);
        List<EasyUITreeNode<ConfigDictPo>> treeNodes = new ArrayList<EasyUITreeNode<ConfigDictPo>>(configDicts.size());

        for (ConfigDictPo po : configDicts) {
            String configId = Integer.toString(po.getId());
            String text = po.getName();
            String state = "closed";
            EasyUITreeNode<ConfigDictPo> vmMode = new EasyUITreeNode<ConfigDictPo>(configId, text, state, po);
            treeNodes.add(vmMode);
        }

        return treeNodes;
    }
}