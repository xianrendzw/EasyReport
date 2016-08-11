package com.easytoolsoft.easyreport.metadata.service.impl;

import com.easytoolsoft.easyreport.data.common.service.AbstractCrudService;
import com.easytoolsoft.easyreport.data.metadata.dao.ICategoryDao;
import com.easytoolsoft.easyreport.data.metadata.example.CategoryExample;
import com.easytoolsoft.easyreport.data.metadata.po.Category;
import com.easytoolsoft.easyreport.metadata.service.ICategoryService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("EzrptMetaCategoryService")
public class CategoryService
        extends AbstractCrudService<ICategoryDao, Category, CategoryExample>
        implements ICategoryService {

    @Override
    protected CategoryExample getPageExample(String fieldName, String keyword) {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public int add(Category record) {
        this.dao.insert(record);
        this.updateHasChild(record.getParentId(), true);
        String path = this.getPath(record.getParentId(), record.getId());
        return this.updatePath(record.getId(), path);
    }

    @Override
    public List<Category> getChildren(int id) {
        CategoryExample example = new CategoryExample();
        example.or().andParentIdEqualTo(id);
        return this.dao.selectByExample(example);
    }

    @Override
    public boolean hasChildren(int id) {
        CategoryExample example = new CategoryExample();
        example.or().andParentIdEqualTo(id);
        return this.exists(example);
    }

    @Override
    public boolean remove(int id, int pid) {
        this.dao.deleteById(id);
        boolean hasChild = this.hasChildren(pid);
        return this.updateHasChild(pid, hasChild) > 0;
    }

    @Override
    public void move(int sourceId, int targetId, int sourcePid) {
        // 修改source节点的pid与path，hasChild值
        this.updateParentId(sourceId, targetId);
        this.updateHasChild(targetId, true);
        this.updatePath(sourceId, this.getPath(targetId, sourceId));
        // 递归修改source节点的所有子节点的path值
        this.rebuildPathById(sourceId);
        // 修改source节点的父节点hasChild值
        this.updateHasChild(sourcePid, this.hasChildren(sourcePid));
        this.rebuildAllPath();
    }

    @Override
    public Category paste(int sourceId, int targetId) {
        Category category = this.dao.selectById(sourceId);
        int count = this.count(targetId, category.getName());
        if (count > 0) {
            category.setName(String.format("%s_复件%s", category.getName(), count));
        }
        category.setParentId(targetId);
        this.dao.insert(category);
        this.updateHasChild(targetId, true);
        return category;
    }

    @Override
    public void clone(int sourceId, int targetId) {
        List<Category> categories = this.getChildren(sourceId);
        categories.stream().filter(child -> child.getId().equals(sourceId)).forEach(child -> {
            child.setParentId(targetId);
            int srcPid = child.getId();
            this.dao.insert(child);
            int newPid = child.getId();
            this.recursionClone(categories, newPid, srcPid);
        });
    }

    private void recursionClone(List<Category> categories, int newPid, int srcPid) {
        for (Category child : categories) {
            if (child.getParentId().equals(srcPid)) {
                child.setParentId(newPid);
                srcPid = child.getId();
                this.dao.insert(child);
                newPid = child.getId();
                this.recursionClone(categories, newPid, srcPid);
            }
        }
    }

    @Override
    public void rebuildPathById(int id) {
        List<Category> categories = this.getChildren(id);
        for (Category entity : categories) {
            String path = this.getPath(entity.getParentId(), entity.getId());
            this.updateHasChild(entity.getParentId(), path.split(",").length > 1);
            this.updatePath(entity.getId(), path);
            this.rebuildPathById(entity.getId());
        }
    }

    @Override
    public void rebuildAllPath() {
        List<Category> categories = this.getAll();
        List<Category> roots = categories.stream()
                .filter(cate -> cate.getParentId().equals(0))
                .collect(Collectors.toList());
        for (Category root : roots) {
            root.setPath(root.getId().toString());
            this.rebuildPath(categories, root);
        }
        this.dao.batchUpdate(categories);
    }

    private void rebuildPath(List<Category> entities, final Category parent) {
        List<Category> children = entities.stream()
                .filter(cate -> cate.getParentId().equals(parent.getId()))
                .collect(Collectors.toList());
        int hasChild = CollectionUtils.isEmpty(children) ? 0 : 1;
        parent.setHasChild((byte) hasChild);
        for (Category child : children) {
            String path = parent.getPath() + "," + child.getId().toString();
            child.setPath(path);
            this.rebuildPath(entities, child);
        }
    }

    private int count(int parentId, String name) {
        CategoryExample example = new CategoryExample();
        example.or()
                .andParentIdEqualTo(parentId)
                .andNameEqualTo(name);
        return this.dao.countByExample(example);
    }

    private int updateHasChild(int id, boolean hasChild) {
        Category category = Category.builder().id(id).hasChild(hasChild ? (byte) 1 : (byte) 0).build();
        return this.dao.updateById(category);
    }

    private int updatePath(Integer id, String path) {
        Category category = Category.builder().id(id).path(path).build();
        return this.dao.updateById(category);
    }

    private int updateParentId(int sourceId, int targetId) {
        Category category = Category.builder().id(sourceId).parentId(targetId).build();
        return this.dao.updateById(category);
    }

    private String getPath(int pid, int id) {
        if (pid <= 0) {
            return "" + id;
        }
        Category po = this.dao.selectById(pid);
        return po.getPath() + "," + id;
    }
}
