package com.easytoolsoft.easyreport.membership.service;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.common.service.ICrudService;
import com.easytoolsoft.easyreport.data.membership.example.RoleExample;
import com.easytoolsoft.easyreport.data.membership.po.Role;
import com.easytoolsoft.easyreport.data.membership.po.User;

import java.util.List;
import java.util.Map;

/**
 * 系统角色服务类
 *
 * @author Tom Deng
 */
public interface IRoleService extends ICrudService<Role, RoleExample> {
    boolean isSuperAdminRole(String roleIds);

    String getNames(String roleIds);

    String getModuleIds(String roleIds);

    String getPermissionIds(String roleIds);

    List<Role> getByPage(PageInfo page, User currentUser, String fieldName, String keyword);

    String getRoleIdsBy(String createUser);

    List<Role> getRolesList(User currentUser);

    Map<String, String[]> getRoleModulesAndPermissions(Integer roleId);
}