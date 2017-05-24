package com.easytoolsoft.easyreport.web.controller.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.meta.domain.Category;
import com.easytoolsoft.easyreport.meta.domain.example.CategoryExample;
import com.easytoolsoft.easyreport.meta.service.CategoryService;
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
 * 报表类别控制器
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@RestController
@RequestMapping(value = "/rest/report/category")
public class CategoryController
    extends BaseController<CategoryService, Category, CategoryExample, Integer> {

    @GetMapping(value = "/getCategoryTree")
    @OpLog(name = "获取报表分类树")
    @RequiresPermissions("report.designer:view")
    public ResponseResult getCategoryTree() {
        final List<EasyUITreeNode<Category>> roots = new ArrayList<>();
        final List<Category> categories = this.service.getAll();
        categories.stream().filter(category -> category.getParentId() == 0).forEach(category -> {
            final String cateId = Integer.toString(category.getId());
            final String text = category.getName();
            final String state = category.getHasChild() > 0 ? "closed" : "open";
            final String iconCls = category.getHasChild() > 0 ? "icon-categories" : "icon-category";
            final EasyUITreeNode<Category> parentNode = new EasyUITreeNode<>(cateId, text, state, category);
            parentNode.setIconCls(iconCls);
            this.getChildCategories(categories, parentNode);
            roots.add(parentNode);
        });
        return ResponseResult.success(roots);
    }

    private void getChildCategories(final List<Category> categories, final EasyUITreeNode<Category> parentNode) {
        final int id = Integer.valueOf(parentNode.getId());
        categories.stream().filter(category -> category.getParentId() == id).forEach(category -> {
            final String cateId = Integer.toString(category.getId());
            final String text = category.getName();
            final String state = category.getHasChild() > 0 ? "closed" : "open";
            final String iconCls = category.getHasChild() > 0 ? "icon-categories" : "icon-category";
            final EasyUITreeNode<Category> childNode = new EasyUITreeNode<>(cateId, text, state, category);
            childNode.setIconCls(iconCls);
            this.getChildCategories(categories, childNode);
            parentNode.getChildren().add(childNode);
        });
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页查找报表分类")
    @RequiresPermissions("report.designer:view")
    public Map<String, Object> list(final DataGridPager pager, final String fieldName, final String keyword) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<Category> list = this.service.getByPage(pageInfo, fieldName, "%" + keyword + "%");
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @PostMapping(value = "/add")
    @OpLog(name = "增加报表分类")
    @RequiresPermissions("report.designer:add")
    public ResponseResult add(final Category po) {
        po.setHasChild((byte)0);
        po.setPath("");
        po.setGmtCreated(new Date());
        po.setGmtModified(new Date());
        this.service.add(po);
        return ResponseResult.success(po);
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "编辑报表分类")
    @RequiresPermissions("report.designer:edit")
    public ResponseResult edit(final Category po) {
        this.service.editById(po);
        return ResponseResult.success(po);
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除报表分类")
    @RequiresPermissions("report.designer:remove")
    public ResponseResult remove(final Integer id, final Integer pid) {
        this.service.remove(id, pid);
        return ResponseResult.success(pid);
    }

    @PostMapping(value = "/move")
    @OpLog(name = "移动报表分类")
    @RequiresPermissions("report.designer:edit")
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

    @PostMapping(value = "/paste")
    @OpLog(name = "复制与粘贴报表分类")
    @RequiresPermissions("report.designer:edit")
    public ResponseResult paste(final Integer sourceId, final Integer targetId) {
        final List<EasyUITreeNode<Category>> nodes = new ArrayList<>(1);
        final Category po = this.service.paste(sourceId, targetId);
        final String id = Integer.toString(po.getId());
        final String pid = Integer.toString(po.getParentId());
        final String text = po.getName();
        final String state = po.getHasChild() > 0 ? "closed" : "open";
        nodes.add(new EasyUITreeNode<>(id, pid, text, state, "", false, po));
        return ResponseResult.success(nodes);
    }

    @GetMapping(value = "/rebuildPath")
    @OpLog(name = "重新构建分类树路径")
    @RequiresPermissions("report.designer:edit")
    public ResponseResult rebuildPath() {
        this.service.rebuildAllPath();
        return ResponseResult.success("");
    }
}