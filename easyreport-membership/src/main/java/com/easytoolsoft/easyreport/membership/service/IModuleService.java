package com.easytoolsoft.easyreport.membership.service;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.common.service.ICrudService;
import com.easytoolsoft.easyreport.data.membership.example.ModuleExample;
import com.easytoolsoft.easyreport.data.membership.po.Module;

import java.util.List;

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

    void move(int sourceId, int targetId, int sourcePid);

    Module paste(int sourceId, int targetId);

    void clone(int sourceId, int targetId);

    void rebuildPathById(int id);

    void rebuildAllPath();
}