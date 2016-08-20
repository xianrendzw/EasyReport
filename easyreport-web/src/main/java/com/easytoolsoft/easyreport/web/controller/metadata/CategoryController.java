package com.easytoolsoft.easyreport.web.controller.metadata;

import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.data.metadata.example.CategoryExample;
import com.easytoolsoft.easyreport.data.metadata.po.Category;
import com.easytoolsoft.easyreport.metadata.service.ICategoryService;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 报表类别控制器
 */
@RestController
@RequestMapping(value = "/rest/metadata/category")
public class CategoryController
        extends BaseController<ICategoryService, Category, CategoryExample> {

    @RequestMapping(value = "/getCategoryTree")
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

    @RequestMapping(value = "/add")
    public JsonResult add(Category po) {
        JsonResult<Object> result = new JsonResult<>();
        po.setHasChild((byte) 0);
        po.setPath("");
        po.setGmtCreated(new Date());
        po.setGmtModified(new Date());
        this.service.add(po);
        return result;
    }

    @RequestMapping(value = "/edit")
    public JsonResult edit(Category po) {
        JsonResult<Object> result = new JsonResult<>();
        this.service.editById(po);
        return result;
    }

    @RequestMapping(value = "/remove")
    public JsonResult remove(Integer id, Integer pid) {
        JsonResult<Object> result = new JsonResult<>();
        this.service.remove(id, pid);
        return result;
    }

    @RequestMapping(value = "/move")
    public JsonResult move(Integer sourceId, Integer targetId, Integer sourcePid) {
        JsonResult<Object> result = new JsonResult<>();
        this.service.move(sourceId, targetId, sourcePid);
        return result;
    }

    @RequestMapping(value = "/paste")
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

    @RequestMapping(value = "/rebuildPath")
    public JsonResult rebuildPath() {
        JsonResult<Object> result = new JsonResult<>();
        this.service.rebuildAllPath();
        return result;
    }
}