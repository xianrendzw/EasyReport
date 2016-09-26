package com.easytoolsoft.easyreport.web.controller.metadata;

import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.metadata.example.CategoryExample;
import com.easytoolsoft.easyreport.data.metadata.po.Category;
import com.easytoolsoft.easyreport.domain.metadata.service.ICategoryService;
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

/**
 * 报表类别控制器
 */
@RestController
@RequestMapping(value = "/rest/metadata/category")
public class CategoryController
        extends BaseController<ICategoryService, Category, CategoryExample> {

    @GetMapping(value = "/getCategoryTree")
    @OpLog(name = "获取报表分类树")
    @RequiresPermissions("report.designer:view")
    public JsonResult getCategoryTree() {
        JsonResult<Object> result = new JsonResult<>();
        List<EasyUITreeNode<Category>> roots = new ArrayList<>();
        List<Category> categories = this.service.getAll();
        categories.stream().filter(category -> category.getParentId() == 0).forEach(category -> {
            String cateId = Integer.toString(category.getId());
            String text = category.getName();
            String state = category.getHasChild() > 0 ? "closed" : "open";
            String iconCls = category.getHasChild() > 0 ? "icon-categories" : "icon-category";
            EasyUITreeNode<Category> parentNode = new EasyUITreeNode<>(cateId, text, state, category);
            parentNode.setIconCls(iconCls);
            this.getChildCategories(categories, parentNode);
            roots.add(parentNode);
        });
        result.setData(roots);
        return result;
    }

    private void getChildCategories(List<Category> categories, EasyUITreeNode<Category> parentNode) {
        int id = Integer.valueOf(parentNode.getId());
        categories.stream().filter(category -> category.getParentId() == id).forEach(category -> {
            String cateId = Integer.toString(category.getId());
            String text = category.getName();
            String state = category.getHasChild() > 0 ? "closed" : "open";
            String iconCls = category.getHasChild() > 0 ? "icon-categories" : "icon-category";
            EasyUITreeNode<Category> childNode = new EasyUITreeNode<>(cateId, text, state, category);
            childNode.setIconCls(iconCls);
            this.getChildCategories(categories, childNode);
            parentNode.getChildren().add(childNode);
        });
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页查找报表分类")
    @RequiresPermissions("report.designer:view")
    public Map<String, Object> list(DataGridPager pager, String fieldName, String keyword) {
        PageInfo pageInfo = pager.toPageInfo();
        List<Category> list = this.service.getByPage(pageInfo, fieldName, "%" + keyword + "%");
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @PostMapping(value = "/add")
    @OpLog(name = "增加报表分类")
    @RequiresPermissions("report.designer:add")
    public JsonResult add(Category po) {
        JsonResult<Object> result = new JsonResult<>();
        po.setHasChild((byte) 0);
        po.setPath("");
        po.setGmtCreated(new Date());
        po.setGmtModified(new Date());
        this.service.add(po);
        result.setData(po);
        return result;
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "编辑报表分类")
    @RequiresPermissions("report.designer:edit")
    public JsonResult edit(Category po) {
        JsonResult<Object> result = new JsonResult<>();
        this.service.editById(po);
        result.setData(po);
        return result;
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除报表分类")
    @RequiresPermissions("report.designer:remove")
    public JsonResult remove(Integer id, Integer pid) {
        JsonResult<Object> result = new JsonResult<>();
        this.service.remove(id, pid);
        result.setData(pid);
        return result;
    }

    @PostMapping(value = "/move")
    @OpLog(name = "移动报表分类")
    @RequiresPermissions("report.designer:edit")
    public JsonResult move(Integer sourceId, Integer targetId, Integer sourcePid, String sourcePath) {
        JsonResult<Object> result = new JsonResult<>();
        this.service.move(sourceId, targetId, sourcePid, sourcePath);
        result.setData(new HashMap<String, Integer>(3) {
            {
                put("sourceId", sourceId);
                put("targetId", targetId);
                put("sourcePid", sourcePid);
            }
        });
        return result;
    }

    @PostMapping(value = "/paste")
    @OpLog(name = "复制与粘贴报表分类")
    @RequiresPermissions("report.designer:edit")
    public JsonResult paste(Integer sourceId, Integer targetId) {
        JsonResult<Object> result = new JsonResult<>();
        List<EasyUITreeNode<Category>> nodes = new ArrayList<>(1);
        Category po = this.service.paste(sourceId, targetId);
        String id = Integer.toString(po.getId());
        String pid = Integer.toString(po.getParentId());
        String text = po.getName();
        String state = po.getHasChild() > 0 ? "closed" : "open";
        nodes.add(new EasyUITreeNode<>(id, pid, text, state, "", false, po));
        result.setData(nodes);
        return result;
    }

    @GetMapping(value = "/rebuildPath")
    @OpLog(name = "重新构建分类树路径")
    @RequiresPermissions("report.designer:edit")
    public JsonResult rebuildPath() {
        JsonResult<Object> result = new JsonResult<>();
        this.service.rebuildAllPath();
        return result;
    }
}