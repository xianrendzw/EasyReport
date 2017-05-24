package com.easytoolsoft.easyreport.membership.service;

import java.util.List;
import java.util.Map;

import com.easytoolsoft.easyreport.membership.domain.Permission;
import com.easytoolsoft.easyreport.membership.domain.example.PermissionExample;
import com.easytoolsoft.easyreport.mybatis.pager.PageInfo;
import com.easytoolsoft.easyreport.mybatis.service.CrudService;

/**
 * 系统权限服务类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface PermissionService extends CrudService<Permission, PermissionExample, Integer> {
    /**
     *
     */
    void reloadCache();

    /**
     * @param pageInfo
     * @param moduleId
     * @return
     */
    List<Permission> getByPage(PageInfo pageInfo, Integer moduleId);

    /**
     * @param moduleId
     * @return
     */
    List<Permission> getByModuleId(Integer moduleId);

    /**
     * @return
     */
    Map<String, String> getIdCodeMap();

    /**
     * @param codes
     * @return
     */
    String getPermissionIds(String[] codes);

    /**
     * @param permissionIds
     * @return
     */
    String getModuleIds(String permissionIds);
}