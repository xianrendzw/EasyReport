package com.easytoolsoft.easyreport.membership.service;

import com.easytoolsoft.easyreport.data.common.service.ICrudService;
import com.easytoolsoft.easyreport.data.membership.example.PermissionExample;
import com.easytoolsoft.easyreport.data.membership.po.Permission;

import java.util.List;
import java.util.Map;

/**
 * 系统权限服务类
 *
 * @author Tom Deng
 */
public interface IPermissionService extends ICrudService<Permission, PermissionExample> {
    void reloadCache();

    List<Permission> getByModuleId(Integer moduleId);

    Map<String, String> getIdCodeMap();

    String getPermissionIds(String[] codes);

    String getModuleIds(String permissionIds);
}