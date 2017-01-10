package com.easytoolsoft.easyreport.membership.service;

import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.data.helper.PageInfo;
import com.easytoolsoft.easyreport.data.service.ICrudService;
import com.easytoolsoft.easyreport.membership.example.ModuleExample;
import com.easytoolsoft.easyreport.membership.po.Module;

import java.util.List;
import java.util.function.Predicate;

/**
 * 系统模块服务类
 *
 * @author Tom Deng
 */
public interface IModuleService extends ICrudService<Module, ModuleExample> {
    List<Module> getByPage(PageInfo pageInfo, Integer pid);

    List<Module> getModules(String moduleIds);

    List<Module> getChildren(int moduleId);

    boolean hasChildren(int moduleId);

    boolean remove(int id, int pid);

    void move(int sourceId, int targetId, int sourcePid, String sourcePath);

    Module paste(int sourceId, int targetId);

    void clone(int sourceId, int targetId);

    void rebuildPathById(int id);

    void rebuildAllPath();

    List<EasyUITreeNode<Module>> getModuleTree(List<Module> modules, Predicate<Module> predicate);
}