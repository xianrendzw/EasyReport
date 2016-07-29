package com.easytoolsoft.easyreport.web.controller.membership;

import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.membership.po.Module;
import com.easytoolsoft.easyreport.membership.service.IModuleService;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import com.easytoolsoft.easyreport.web.controller.common.AbstractController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/membership/module")
public class ModuleController extends AbstractController {
    @Resource
    private IModuleService moduleService;

    @RequestMapping(value = "/getModuleTree")
    public JsonResult getModuleTree(HttpServletRequest req) {
        JsonResult<Object> result = new JsonResult();
        try {
            List<EasyUITreeNode<Module>> roots = new ArrayList<>();
            List<Module> modules = this.moduleService.getAll();
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
            this.logSuccessResult(result, "获取模块树列表成功", req);
        } catch (Exception ex) {
            result.setMsg("获取模块树列表失败");
            this.logExceptionResult(result, ex, req);
        }
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
    public Map<String, Object> list(DataGridPager pager, Integer id) {
        PageInfo pageInfo = pager.toPageInfo();
        List<Module> list = this.moduleService.getByPage(pageInfo, id);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/add")
    public JsonResult add(Module po, HttpServletRequest req) {
        JsonResult<Object> result = new JsonResult<>();
        try {
            po.setHasChild((byte) 0);
            po.setPath("");
            po.setGmtCreated(new Date());
            po.setGmtModified(new Date());
            this.moduleService.add(po);
            this.logSuccessResult(result, String.format("增加模块[%s]操作成功!", po.getName()), req);
        } catch (Exception ex) {
            result.setMsg(String.format("增加模块:[%s]操作失败!", po.getName()));
            this.logExceptionResult(result, ex, req);
        }
        return result;
    }

    @RequestMapping(value = "/edit")
    public JsonResult edit(Module po, HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            this.moduleService.editById(po);
            this.logSuccessResult(result, String.format("修改模块[ID:%s]操作成功!", po.getId()), req);
        } catch (Exception ex) {
            result.setMsg(String.format("修改模块:[%s]操作失败!", po.getId()));
            this.logExceptionResult(result, ex, req);
        }
        return result;
    }

    @RequestMapping(value = "/remove")
    public JsonResult remove(Integer id, Integer pid, HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            this.moduleService.remove(id, pid);
            this.logSuccessResult(result, String.format("删除模块[ID:%s]操作成功!", id), req);
        } catch (Exception ex) {
            result.setMsg(String.format("删除模块[ID:%s]操作失败!", id));
            this.logExceptionResult(result, ex, req);
        }
        return result;
    }

    @RequestMapping(value = "/getModule")
    public Module getModule(Integer id) {
        return this.moduleService.getById(id);
    }

    @RequestMapping(value = "/getChildModules")
    public List<EasyUITreeNode<Module>> getChildModules(Integer id, HttpServletRequest req) {
        int parentId = (id == null ? 0 : id);
        List<Module> modules = this.moduleService.getChildren(parentId);
        List<EasyUITreeNode<Module>> treeNodes = new ArrayList<EasyUITreeNode<Module>>(modules.size());
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
    public JsonResult move(Integer sourceId, Integer targetId, Integer sourcePid, HttpServletRequest req) {
        JsonResult result = new JsonResult();
        try {
            this.moduleService.move(sourceId, targetId, sourcePid);
            this.logSuccessResult(result, String.format("移动模块[ID:%s]到[ID:%s]操作成功!", sourceId, targetId), req);
        } catch (Exception ex) {
            result.setMsg(String.format("移动模块[ID:%s]到[ID:%s]操作失败!", sourceId, targetId));
            this.logExceptionResult(result, ex, req);
        }
        return result;
    }

    @RequestMapping(value = "/rebuildPath")
    public JsonResult rebuildPath(HttpServletRequest req) {
        JsonResult result = new JsonResult();
        try {
            this.moduleService.rebuildAllPath();
            this.logSuccessResult(result, "重新模块树路径成功", req);
        } catch (Exception ex) {
            result.setMsg("重新模块树路径失败");
            this.logExceptionResult(result, ex, req);
        }
        return result;
    }
}
