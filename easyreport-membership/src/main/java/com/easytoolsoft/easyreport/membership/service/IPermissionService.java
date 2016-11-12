package com.easytoolsoft.easyreport.membership.service;

import com.easytoolsoft.easyreport.data.helper.PageInfo;
import com.easytoolsoft.easyreport.data.service.ICrudService;
import com.easytoolsoft.easyreport.membership.example.PermissionExample;
import com.easytoolsoft.easyreport.membership.po.Permission;

import java.util.List;
import java.util.Map;

/**
 * 系统权限服务类
 *
 * @author Tom Deng
 */
public interface IPermissionService extends ICrudService<Permission, PermissionExample> {
    void reloadCache();

    List<Permission> getByPage(PageInfo pageInfo, Integer moduleId);

    List<Permission> getByModuleId(Integer moduleId);

    Map<String, String> getIdCodeMap();

    String getPermissionIds(String[] codes);

    String getModuleIds(String permissionIds);
}