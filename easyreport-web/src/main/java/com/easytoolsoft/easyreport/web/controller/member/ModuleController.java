package com.easytoolsoft.easyreport.web.controller.member;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.membership.domain.Module;
import com.easytoolsoft.easyreport.membership.domain.example.ModuleExample;
import com.easytoolsoft.easyreport.membership.service.ModuleService;
import com.easytoolsoft.easyreport.mybatis.pager.PageInfo;
import com.easytoolsoft.easyreport.support.annotation.OpLog;
import com.easytoolsoft.easyreport.support.model.ResponseResult;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.model.DataGridPager;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tom Deng
 * @date 2017-03-25
 */
@RestController
@RequestMapping(value = "/rest/member/module")
public class ModuleController
    extends BaseController<ModuleService, Module, ModuleExample, Integer> {

    @GetMapping(value = "/getModuleTree")
    @OpLog(name = "获取系统模块树型列表")
    @RequiresPermissions("membership.module:view")
    public ResponseResult getModuleTree() {
        final List<Module> modules = this.service.getAll();
        final List<EasyUITreeNode<Module>> roots = this.service.getModuleTree(modules, x -> x.getStatus() < 2);
        return ResponseResult.success(roots);
    }

    @GetMapping(value = "/list")
    @OpLog(name = "获取系统模块树型列表")
    @RequiresPermissions("membership.module:view")
    public Map<String, Object> list(final DataGridPager pager, final Integer id) {
        final int pid = (id == null ? 0 : id);
        final PageInfo pageInfo = pager.toPageInfo();
        final List<Module> list = this.service.getByPage(pageInfo, pid);
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @PostMapping(value = "/add")
    @OpLog(name = "新增系统模块")
    @RequiresPermissions("membership.module:add")
    public ResponseResult add(final Module po) {
        po.setHasChild((byte)0);
        po.setPath("");
        po.setGmtCreated(new Date());
        po.setGmtModified(new Date());
        this.service.add(po);
        return ResponseResult.success("");
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "编辑指定ID的系统模块")
    @RequiresPermissions("membership.module:edit")
    public ResponseResult edit(final Module po) {
        this.service.editById(po);
        return ResponseResult.success("");
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "邮件指定ID的系统模块")
    @RequiresPermissions("membership.module:remove")
    public ResponseResult remove(final Integer id, final Integer pid) {
        this.service.remove(id, pid);
        return ResponseResult.success("");
    }

    @GetMapping(value = "/getModule")
    @OpLog(name = "获取指定ID系统模块信息")
    @RequiresPermissions("membership.module:view")
    public Module getModule(final Integer id) {
        return this.service.getById(id);
    }

    @GetMapping(value = "/getChildModules")
    @OpLog(name = "获取子模块树型列表")
    @RequiresPermissions("membership.module:view")
    public List<EasyUITreeNode<Module>> getChildModules(final Integer id) {
        final int parentId = (id == null ? 0 : id);
        final List<Module> modules = this.service.getChildren(parentId);
        final List<EasyUITreeNode<Module>> treeNodes = new ArrayList<>(modules.size());
        for (final Module po : modules) {
            final String mid = Integer.toString(po.getId());
            final String pid = Integer.toString(po.getParentId());
            final String text = po.getName();
            final EasyUITreeNode<Module> node = new EasyUITreeNode<>(mid, pid, text, "closed", po.getIcon(), false, po);
            treeNodes.add(node);
        }
        return treeNodes;
    }

    @PostMapping(value = "/move")
    @OpLog(name = "移动模块树型关系")
    @RequiresPermissions("membership.module:edit")
    public ResponseResult move(final Integer sourceId, final Integer targetId, final Integer sourcePid,
                               final String sourcePath) {
        this.service.move(sourceId, targetId, sourcePid, sourcePath);
        return ResponseResult.success(new HashMap<String, Integer>(3) {
            {
                put("sourceId", sourceId);
                put("targetId", targetId);
                put("sourcePid", sourcePid);
            }
        });
    }

    @GetMapping(value = "/rebuildPath")
    @OpLog(name = "重新模块树路径")
    @RequiresPermissions("membership.module:edit")
    public ResponseResult rebuildPath() {
        this.service.rebuildAllPath();
        return ResponseResult.success("");
    }
}
