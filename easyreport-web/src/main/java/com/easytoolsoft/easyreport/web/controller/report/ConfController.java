package com.easytoolsoft.easyreport.web.controller.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.meta.domain.Conf;
import com.easytoolsoft.easyreport.meta.domain.example.ConfExample;
import com.easytoolsoft.easyreport.meta.service.ConfService;
import com.easytoolsoft.easyreport.mybatis.pager.PageInfo;
import com.easytoolsoft.easyreport.support.annotation.OpLog;
import com.easytoolsoft.easyreport.support.model.ResponseResult;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.model.DataGridPager;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 报表配置控制器
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@RestController
@RequestMapping(value = "/rest/report/conf")
public class ConfController
    extends BaseController<ConfService, Conf, ConfExample, Integer> {

    @RequestMapping(value = "/list")
    @OpLog(name = "获取指定ID的报表元数据配置项")
    @RequiresPermissions("report.conf:view")
    public Map<String, Object> list(final DataGridPager pager, final Integer id) {
        final int pid = (id == null ? 0 : id);
        final PageInfo pageInfo = pager.toPageInfo();
        final List<Conf> list = this.service.getByPage(pageInfo, pid);
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", list.size());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/listChildren")
    @OpLog(name = "获取指定ID的所有子报表元数据配置项")
    @RequiresPermissions("report.conf:view")
    public List<EasyUITreeNode<Conf>> listChildren(final Integer id) {
        final List<Conf> list = this.service.getByParentId(id == null ? 0 : id);
        final List<EasyUITreeNode<Conf>> easyUITreeNodes = new ArrayList<>(list.size());
        for (final Conf po : list) {
            final String confId = Integer.toString(po.getId());
            final String pid = Integer.toString(po.getParentId());
            final String text = po.getName();
            final String state = po.isHasChild() ? "closed" : "open";
            final String icon = po.isHasChild() ? "icon-dict2" : "icon-item1";
            easyUITreeNodes.add(new EasyUITreeNode<>(confId, pid, text, state, icon, false, po));
        }
        return easyUITreeNodes;
    }

    @RequestMapping(value = "/find")
    @OpLog(name = "分页查找指定ID的报表元数据配置项")
    @RequiresPermissions("report.conf:view")
    public Map<String, Object> find(final DataGridPager pager, final String fieldName, final String keyword) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<Conf> list = this.service.getByPage(pageInfo, fieldName, keyword);
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/add")
    @OpLog(name = "新增报表元数据配置项")
    @RequiresPermissions("report.conf:add")
    public ResponseResult add(final Conf po) {
        po.setGmtCreated(new Date());
        po.setGmtModified(new Date());
        this.service.add(po);
        return ResponseResult.success("");
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "编辑报表元数据配置项")
    @RequiresPermissions("report.conf:edit")
    public ResponseResult edit(final Conf po) {
        this.service.editById(po);
        return ResponseResult.success("");
    }

    @RequestMapping(value = "/remove")
    @OpLog(name = "删除报表元数据配置项")
    @RequiresPermissions("report.conf:remove")
    public ResponseResult remove(final Integer id) {
        this.service.removeById(id);
        return ResponseResult.success("");
    }

    @GetMapping(value = "/getConfItems")
    @OpLog(name = "获取指定父key下的所有配置项")
    @RequiresPermissions("report.conf:view")
    public ResponseResult getConfItems(final String key) {
        return ResponseResult.success(this.service.getByParentKey(key));
    }
}