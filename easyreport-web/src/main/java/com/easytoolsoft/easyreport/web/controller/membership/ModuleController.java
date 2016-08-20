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

    @RequestMapping(value = "/getModuleTree")
    @OpLog(name = "获取系统模块树型列表")
    public JsonResult getModuleTree() {
        JsonResult<Object> result = new JsonResult<>();
        List<EasyUITreeNode<Module>> roots = new ArrayList<>();
        List<Module> modules = this.service.getAll();
        modules.stream()
                .filter(module -> module.getParentId().equals(0))
                .forEach((Module module) -> {
                    String cateId = Integer.toString(module.getId());
                    String pid = Integer.toString(module.getParentId());
                    String text = module.getName();
                    String state = module.getHasChild() > 0 ? "closed" : "open";
                    EasyUITreeNode<Module> parentNode = new EasyUITreeNode<>(
                            cateId, pid, text, state, module.getIcon(), false, module);
                    this.getChildModules(modules, parentNode);
                    roots.add(parentNode);
                });
        result.setData(roots);
        return result;
    }

    private void getChildModules(List<Module> modules, EasyUITreeNode<Module> parentNode) {
        Integer id = Integer.valueOf(parentNode.getId());
        modules.stream()
                .filter(module -> module.getParentId().equals(id))
                .forEach(module -> {
                    String cateId = Integer.toString(module.getId());
                    String pid = Integer.toString(module.getParentId());
                    String text = module.getName();
                    String state = module.getHasChild() > 0 ? "closed" : "open";
                    EasyUITreeNode<Module> childNode = new EasyUITreeNode<>(
                            cateId, pid, text, state, module.getIcon(), false, module);
                    this.getChildModules(modules, childNode);
                    parentNode.getChildren().add(childNode);
                });
    }

    @RequestMapping(value = "/list")
    @OpLog(name = "获取系统模块树型列表")
    public Map<String, Object> list(DataGridPager pager, Integer id) {
        PageInfo pageInfo = pager.toPageInfo();
        List<Module> list = this.service.getByPage(pageInfo, id);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/add")
    @OpLog(name = "新增系统模块")
    public JsonResult add(Module po) {
        JsonResult<Object> result = new JsonResult<>();
        po.setHasChild((byte) 0);
        po.setPath("");
        po.setGmtCreated(new Date());
        po.setGmtModified(new Date());
        this.service.add(po);
        return result;
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "编辑指定ID的系统模块")
    public JsonResult edit(Module po) {
        JsonResult<String> result = new JsonResult<>();
        this.service.editById(po);
        return result;
    }

    @RequestMapping(value = "/remove")
    @OpLog(name = "邮件指定ID的系统模块")
    public JsonResult remove(Integer id, Integer pid) {
        JsonResult<String> result = new JsonResult<>();
        this.service.remove(id, pid);
        return result;
    }

    @RequestMapping(value = "/getModule")
    @OpLog(name = "获取指定ID系统模块信息")
    public Module getModule(Integer id) {
        return this.service.getById(id);
    }

    @RequestMapping(value = "/getChildModules")
    @OpLog(name = "获取子模块树型列表")
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

    @RequestMapping(value = "/move")
    @OpLog(name = "移动模块树型关系")
    public JsonResult move(Integer sourceId, Integer targetId, Integer sourcePid) {
        JsonResult<Object> result = new JsonResult<>();
        this.service.move(sourceId, targetId, sourcePid);
        return result;
    }

    @RequestMapping(value = "/rebuildPath")
    @OpLog(name = "重新模块树路径")
    public JsonResult rebuildPath() {
        JsonResult<Object> result = new JsonResult<>();
        this.service.rebuildAllPath();
        return result;
    }
}
