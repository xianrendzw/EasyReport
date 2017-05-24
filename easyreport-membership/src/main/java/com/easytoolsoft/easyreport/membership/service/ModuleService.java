package com.easytoolsoft.easyreport.membership.service;

import java.util.List;
import java.util.function.Predicate;

import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.membership.domain.Module;
import com.easytoolsoft.easyreport.membership.domain.example.ModuleExample;
import com.easytoolsoft.easyreport.mybatis.pager.PageInfo;
import com.easytoolsoft.easyreport.mybatis.service.CrudService;

/**
 * 系统模块服务类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface ModuleService extends CrudService<Module, ModuleExample, Integer> {
    /**
     * @param pageInfo
     * @param pid
     * @return
     */
    List<Module> getByPage(PageInfo pageInfo, Integer pid);

    /**
     * @param moduleIds
     * @return
     */
    List<Module> getModules(String moduleIds);

    /**
     * @param moduleId
     * @return
     */
    List<Module> getChildren(int moduleId);

    /**
     * @param moduleId
     * @return
     */
    boolean hasChildren(int moduleId);

    /**
     * @param id
     * @param pid
     * @return
     */
    boolean remove(int id, int pid);

    /**
     * @param sourceId
     * @param targetId
     * @param sourcePid
     * @param sourcePath
     */
    void move(int sourceId, int targetId, int sourcePid, String sourcePath);

    /**
     * @param sourceId
     * @param targetId
     * @return
     */
    Module paste(int sourceId, int targetId);

    /**
     * @param sourceId
     * @param targetId
     */
    void clone(int sourceId, int targetId);

    /**
     * @param id
     */
    void rebuildPathById(int id);

    /**
     *
     */
    void rebuildAllPath();

    /**
     * @param modules
     * @param predicate
     * @return
     */
    List<EasyUITreeNode<Module>> getModuleTree(List<Module> modules, Predicate<Module> predicate);
}