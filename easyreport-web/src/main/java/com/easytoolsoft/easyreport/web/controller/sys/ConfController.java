package com.easytoolsoft.easyreport.web.controller.sys;

import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.sys.example.ConfExample;
import com.easytoolsoft.easyreport.data.sys.po.Conf;
import com.easytoolsoft.easyreport.domain.sys.service.IConfService;
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

@RestController("SysConfController")
@RequestMapping(value = "/rest/sys/conf")
public class ConfController
        extends BaseController<IConfService, Conf, ConfExample> {

    @GetMapping(value = "/list")
    @OpLog(name = "获取指定ID的系统配置项")
    @RequiresPermissions("sys.conf:view")
    public Map<String, Object> list(DataGridPager pager, Integer id) {
        int pid = (id == null ? 0 : id);
        PageInfo pageInfo = pager.toPageInfo();
        List<Conf> list = this.service.getByPage(pageInfo, pid);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", list.size());
        modelMap.put("rows", list);
        return modelMap;
    }

    @GetMapping(value = "/listChildren")
    @OpLog(name = "获取指定ID的所有子系统配置项")
    @RequiresPermissions("sys.conf:view")
    public List<EasyUITreeNode<Conf>> listChildren(Integer id) {
        List<Conf> list = this.service.getByParentId(id == null ? 0 : id);
        List<EasyUITreeNode<Conf>> EasyUITreeNodes = new ArrayList<>(list.size());
        for (Conf po : list) {
            String ConfId = Integer.toString(po.getId());
            String pid = Integer.toString(po.getParentId());
            String text = po.getName();
            String state = po.isHasChild() ? "closed" : "open";
            String icon = po.isHasChild() ? "icon-dict2" : "icon-item1";
            EasyUITreeNodes.add(new EasyUITreeNode<>(ConfId, pid, text, state, icon, false, po));
        }
        return EasyUITreeNodes;
    }

    @GetMapping(value = "/find")
    @OpLog(name = "分页查找指定ID的系统配置项")
    @RequiresPermissions("sys.conf:view")
    public Map<String, Object> find(DataGridPager pager, String fieldName, String keyword) {
        PageInfo pageInfo = pager.toPageInfo();
        List<Conf> list = this.service.getByPage(pageInfo, fieldName, "%" + keyword + "%");
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @PostMapping(value = "/add")
    @OpLog(name = "新增系统配置项")
    @RequiresPermissions("sys.conf:add")
    public JsonResult add(Conf po) {
        JsonResult<String> result = new JsonResult<>();
        po.setGmtCreated(new Date());
        po.setGmtModified(new Date());
        this.service.add(po);
        return result;
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "编辑系统配置项")
    @RequiresPermissions("sys.conf:edit")
    public JsonResult edit(Conf po) {
        JsonResult<String> result = new JsonResult<>();
        this.service.editById(po);
        return result;
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除系统配置项")
    @RequiresPermissions("sys.conf:remove")
    public JsonResult remove(int id) {
        JsonResult<String> result = new JsonResult<>();
        this.service.removeById(id);
        return result;
    }

    @GetMapping(value = "/getDepth1Items")
    @OpLog(name = "获取指定父配置项下的所有一级配置项")
    @RequiresPermissions("sys.conf:view")
    public List<Conf> getDepth1Items(String key) {
        return this.service.getByParentKey(key);
    }

    @GetMapping(value = "/getDepth2Items")
    @OpLog(name = "获取指定父配置项下的所有一、二级配置项")
    @RequiresPermissions("sys.conf:view")
    public Map<String, List<Conf>> getDepth2Items(String key) {
        return this.service.getDepth2ByParentKey(key);
    }
}