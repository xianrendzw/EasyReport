package com.easytoolsoft.easyreport.membership.service.impl;

import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.data.helper.PageInfo;
import com.easytoolsoft.easyreport.data.service.AbstractCrudService;
import com.easytoolsoft.easyreport.membership.dao.IModuleDao;
import com.easytoolsoft.easyreport.membership.example.ModuleExample;
import com.easytoolsoft.easyreport.membership.po.Module;
import com.easytoolsoft.easyreport.membership.service.IModuleService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service("EzrptMemberModuleService")
public class ModuleService
        extends AbstractCrudService<IModuleDao, Module, ModuleExample>
        implements IModuleService {

    @Override
    protected ModuleExample getPageExample(String fieldName, String keyword) {
        ModuleExample example = new ModuleExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public int add(Module record) {
        this.dao.insert(record);
        this.updateHasChild(record.getParentId(), true);
        String path = this.getPath(record.getParentId(), record.getId());
        return this.updatePath(record.getId(), path);
    }

    @Override
    public List<Module> getByPage(PageInfo pageInfo, Integer pid) {
        ModuleExample example = new ModuleExample();
        example.or().andParentIdEqualTo(pid);
        return this.getByPage(pageInfo, example);
    }

    @Override
    public List<Module> getModules(String moduleIds) {
        if (StringUtils.isBlank(moduleIds)) {
            return new ArrayList<>();
        }

        List<Module> moduleList = this.getAll();
        // 0表示返回所有系统模块
        if (moduleIds.equals("0")) {
            return moduleList;
        }
        Map<Integer, Module> moduleMap = new HashMap<>(moduleList.size());
        for (Module module : moduleList) {
            moduleMap.put(module.getId(), module);
        }
        String[] ids = StringUtils.split(moduleIds, ',');
        List<Module> modules = new ArrayList<>(ids.length);
        for (String id : ids) {
            Integer mId = Integer.valueOf(id);
            if (moduleMap.containsKey(mId)) {
                modules.add(moduleMap.get(mId));
            }
        }

        return modules;
    }

    @Override
    public List<Module> getChildren(int id) {
        ModuleExample example = new ModuleExample();
        example.or().andParentIdEqualTo(id);
        return this.dao.selectByExample(example);
    }

    @Override
    public boolean hasChildren(int id) {
        ModuleExample example = new ModuleExample();
        example.or().andParentIdEqualTo(id);
        return this.dao.countByExample(example) > 0;
    }

    @Override
    @Transactional
    public boolean remove(int id, int pid) {
        this.dao.deleteById(id);
        boolean hasChild = this.hasChildren(pid);
        return this.updateHasChild(pid, hasChild) > 0;
    }

    @Override
    @Transactional
    public void move(int sourceId, int targetId, int sourcePid, String sourcePath) {
        // 修改source节点的pid与path，hasChild值
        this.updateParentId(sourceId, targetId);
        this.updateHasChild(targetId, true);
        this.dao.updatePath(sourcePath, this.getPath(targetId, sourceId));
        // 修改source节点的父节点hasChild值
        this.updateHasChild(sourcePid, this.hasChildren(sourcePid));
    }

    @Override
    @Transactional
    public Module paste(int sourceId, int targetId) {
        Module module = this.dao.selectById(sourceId);
        int count = this.count(targetId, module.getName());
        if (count > 0) {
            module.setName(String.format("%s_copy%s", module.getName(), count));
        }
        module.setParentId(targetId);
        this.dao.insert(module);
        this.updateHasChild(targetId, true);
        return module;
    }

    @Override
    public void rebuildPathById(int id) {
        List<Module> modules = this.getChildren(id);
        for (Module entity : modules) {
            String path = this.getPath(entity.getParentId(), entity.getId());
            this.updateHasChild(entity.getParentId(), path.split(",").length > 1);
            this.updatePath(entity.getId(), path);
            this.rebuildPathById(entity.getId());
        }
    }

    @Override
    public void clone(int sourceId, int targetId) {
        List<Module> modules = this.getChildren(sourceId);
        modules.stream().filter(child -> child.getId().equals(sourceId)).forEach(child -> {
            child.setParentId(targetId);
            int srcPid = child.getId();
            this.dao.insert(child);
            int newPid = child.getId();
            this.recursionClone(modules, newPid, srcPid);
        });
    }

    private void recursionClone(List<Module> modules, int newPid, int srcPid) {
        for (Module child : modules) {
            if (child.getParentId().equals(srcPid)) {
                child.setParentId(newPid);
                srcPid = child.getId();
                this.dao.insert(child);
                newPid = child.getId();
                this.recursionClone(modules, newPid, srcPid);
            }
        }
    }

    @Override
    public void rebuildAllPath() {
        List<Module> modules = this.getAll();
        List<Module> roots = modules.stream()
                .filter(module -> module.getParentId().equals(0))
                .collect(Collectors.toList());
        for (Module root : roots) {
            root.setPath(root.getId().toString());
            this.rebuildPath(modules, root);
        }
        this.dao.batchUpdate(modules);
    }

    @Override
    public List<EasyUITreeNode<Module>> getModuleTree(List<Module> modules, Predicate<Module> predicate) {
        List<EasyUITreeNode<Module>> roots = new ArrayList<>();
        modules.stream()
                .filter(predicate)
                .filter(module -> Objects.equals(module.getParentId(), 0))
                .sorted((x, y) -> x.getSequence() > y.getSequence() ? 1 : -1)
                .forEach((Module module) -> this.addModuleTreeNode(roots, modules, module, predicate));
        return roots;
    }

    private void addModuleTreeNode(List<EasyUITreeNode<Module>> children, List<Module> modules, Module module,
                                   Predicate<Module> predicate) {
        String cateId = Integer.toString(module.getId());
        String pid = Integer.toString(module.getParentId());
        String text = module.getCode();
        String state = module.getHasChild() > 0 ? "closed" : "open";
        EasyUITreeNode<Module> parentNode = new EasyUITreeNode<>(cateId, pid, text, state, module.getIcon(), false, module);
        this.addChildModuleTreeNodes(modules, parentNode, predicate);
        children.add(parentNode);
    }

    private void addChildModuleTreeNodes(List<Module> modules, EasyUITreeNode<Module> parentNode,
                                         Predicate<Module> predicate) {
        Integer id = Integer.valueOf(parentNode.getId());
        modules.stream()
                .filter(predicate)
                .filter(module -> Objects.equals(module.getParentId(), id))
                .sorted((x, y) -> x.getSequence() > y.getSequence() ? 1 : -1)
                .forEach(module -> this.addModuleTreeNode(parentNode.getChildren(), modules, module, predicate));
    }

    private void rebuildPath(List<Module> modules, final Module parent) {
        List<Module> children = modules.stream()
                .filter(module -> module.getParentId().equals(parent.getId()))
                .collect(Collectors.toList());
        int hasChild = CollectionUtils.isEmpty(children) ? 0 : 1;
        parent.setHasChild((byte) hasChild);
        for (Module child : children) {
            String path = parent.getPath() + "," + child.getId().toString();
            child.setPath(path);
            this.rebuildPath(modules, child);
        }
    }

    private int count(int parentId, String name) {
        ModuleExample example = new ModuleExample();
        example.or()
                .andParentIdEqualTo(parentId)
                .andNameEqualTo(name);
        return this.dao.countByExample(example);
    }

    private int updateHasChild(int id, boolean hasChild) {
        Module module = Module.builder().id(id).hasChild(hasChild ? (byte) 1 : (byte) 0).build();
        return this.dao.updateById(module);
    }

    private int updatePath(Integer id, String path) {
        Module module = Module.builder().id(id).path(path).build();
        return this.dao.updateById(module);
    }

    private int updateParentId(int sourceId, int targetId) {
        Module module = Module.builder().id(sourceId).parentId(targetId).build();
        return this.dao.updateById(module);
    }

    private String getPath(int pid, int id) {
        if (pid <= 0) {
            return "" + id;
        }
        Module po = this.dao.selectById(pid);
        return po.getPath() + "," + id;
    }
}