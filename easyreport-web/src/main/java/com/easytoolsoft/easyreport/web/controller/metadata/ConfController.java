package com.easytoolsoft.easyreport.web.controller.metadata;

import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.metadata.example.ConfExample;
import com.easytoolsoft.easyreport.data.metadata.po.Conf;
import com.easytoolsoft.easyreport.domain.metadata.service.IConfService;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.spring.aop.OpLog;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表配置控制器
 */
@RestController("MetaDataConfController")
@RequestMapping(value = "/rest/metadata/conf")
public class ConfController
        extends BaseController<IConfService, Conf, ConfExample> {

    @RequestMapping(value = "/list")
    @OpLog(name = "获取指定ID的报表元数据配置项")
    @RequiresPermissions("report.conf:view")
    public Map<String, Object> list(DataGridPager pager, Integer id) {
        int pid = (id == null ? 0 : id);
        PageInfo pageInfo = pager.toPageInfo();
        List<Conf> list = this.service.getByPage(pageInfo, pid);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", list.size());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/listChildren")
    @OpLog(name = "获取指定ID的所有子报表元数据配置项")
    @RequiresPermissions("report.conf:view")
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

    @RequestMapping(value = "/find")
    @OpLog(name = "分页查找指定ID的报表元数据配置项")
    @RequiresPermissions("report.conf:view")
    public Map<String, Object> find(DataGridPager pager, String fieldName, String keyword) {
        PageInfo pageInfo = pager.toPageInfo();
        List<Conf> list = this.service.getByPage(pageInfo, fieldName, keyword);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/add")
    @OpLog(name = "新增报表元数据配置项")
    @RequiresPermissions("report.conf:add")
    public JsonResult add(Conf po) {
        JsonResult<String> result = new JsonResult<>();
        po.setGmtCreated(new Date());
        po.setGmtModified(new Date());
        this.service.add(po);
        return result;
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "编辑报表元数据配置项")
    @RequiresPermissions("report.conf:edit")
    public JsonResult edit(Conf po) {
        JsonResult<String> result = new JsonResult<>();
        this.service.editById(po);
        return result;
    }

    @RequestMapping(value = "/remove")
    @OpLog(name = "删除报表元数据配置项")
    @RequiresPermissions("report.conf:remove")
    public JsonResult remove(int id) {
        JsonResult<String> result = new JsonResult<>();
        this.service.removeById(id);
        return result;
    }

    @GetMapping(value = "/getConfItems")
    @OpLog(name = "获取指定父key下的所有配置项")
    @RequiresPermissions("report.conf:view")
    public JsonResult getConfItems(String key) {
        JsonResult<List<Conf>> result = new JsonResult<>();
        result.setData(this.service.getByParentKey(key));
        return result;
    }
}