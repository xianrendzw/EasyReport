package com.easytoolsoft.easyreport.web.controller.membership;

import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.membership.example.ModuleExample;
import com.easytoolsoft.easyreport.data.membership.po.Module;
import com.easytoolsoft.easyreport.membership.service.IModuleService;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.spring.aop.OpLog;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/membership/module")
public class ModuleController
        extends BaseController<IModuleService, Module, ModuleExample> {

    @GetMapping(value = "/getModuleTree")
    @OpLog(name = "获取系统模块树型列表")
    @RequiresPermissions("membership.module:view")
    public JsonResult getModuleTree() {
        JsonResult<Object> result = new JsonResult<>();
        List<Module> modules = this.service.getAll();
        List<EasyUITreeNode<Module>> roots = this.service.getModuleTree(modules, x -> x.getStatus() < 2);
        result.setData(roots);
        return result;
    }

    @GetMapping(value = "/list")
    @OpLog(name = "获取系统模块树型列表")
    @RequiresPermissions("membership.module:view")
    public Map<String, Object> list(DataGridPager pager, Integer id) {
        int pid = (id == null ? 0 : id);
        PageInfo pageInfo = pager.toPageInfo();
        List<Module> list = this.service.getByPage(pageInfo, pid);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @PostMapping(value = "/add")
    @OpLog(name = "新增系统模块")
    @RequiresPermissions("membership.module:add")
    public JsonResult add(Module po) {
        JsonResult<Object> result = new JsonResult<>();
        po.setHasChild((byte) 0);
        po.setPath("");
        po.setGmtCreated(new Date());
        po.setGmtModified(new Date());
        this.service.add(po);
        return result;
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "编辑指定ID的系统模块")
    @RequiresPermissions("membership.module:edit")
    public JsonResult edit(Module po) {
        JsonResult<String> result = new JsonResult<>();
        this.service.editById(po);
        return result;
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "邮件指定ID的系统模块")
    @RequiresPermissions("membership.module:remove")
    public JsonResult remove(Integer id, Integer pid) {
        JsonResult<String> result = new JsonResult<>();
        this.service.remove(id, pid);
        return result;
    }

    @GetMapping(value = "/getModule")
    @OpLog(name = "获取指定ID系统模块信息")
    @RequiresPermissions("membership.module:view")
    public Module getModule(Integer id) {
        return this.service.getById(id);
    }

    @GetMapping(value = "/getChildModules")
    @OpLog(name = "获取子模块树型列表")
    @RequiresPermissions("membership.module:view")
    public List<EasyUITreeNode<Module>> getChildModules(Integer id) {
        int parentId = (id == null ? 0 : id);
        List<Module> modules = this.service.getChildren(parentId);
        List<EasyUITreeNode<Module>> treeNodes = new ArrayList<>(modules.size());
        for (Module po : modules) {
            String mid = Integer.toString(po.getId());
            String pid = Integer.toString(po.getParentId());
            String text = po.getName();
            EasyUITreeNode<Module> node = new EasyUITreeNode<>(mid, pid, text, "closed", po.getIcon(), false, po);
            treeNodes.add(node);
        }
        return treeNodes;
    }

    @PostMapping(value = "/move")
    @OpLog(name = "移动模块树型关系")
    @RequiresPermissions("membership.module:edit")
    public JsonResult move(Integer sourceId, Integer targetId, Integer sourcePid, String sourcePath) {
        JsonResult<Object> result = new JsonResult<>();
        this.service.move(sourceId, targetId, sourcePid ,sourcePath);
        result.setData(new HashMap<String, Integer>(3) {
            {
                put("sourceId", sourceId);
                put("targetId", targetId);
                put("sourcePid", sourcePid);
            }
        });
        return result;
    }

    @GetMapping(value = "/rebuildPath")
    @OpLog(name = "重新模块树路径")
    @RequiresPermissions("membership.module:edit")
    public JsonResult rebuildPath() {
        JsonResult<Object> result = new JsonResult<>();
        this.service.rebuildAllPath();
        return result;
    }
}
