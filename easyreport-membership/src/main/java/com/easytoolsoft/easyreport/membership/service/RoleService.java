package com.easytoolsoft.easyreport.membership.service;

import java.util.List;
import java.util.Map;

import com.easytoolsoft.easyreport.membership.domain.Role;
import com.easytoolsoft.easyreport.membership.domain.User;
import com.easytoolsoft.easyreport.membership.domain.example.RoleExample;
import com.easytoolsoft.easyreport.mybatis.pager.PageInfo;
import com.easytoolsoft.easyreport.mybatis.service.CrudService;

/**
 * 系统角色服务类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface RoleService extends CrudService<Role, RoleExample, Integer> {
    /**
     * @param roleIds
     * @return
     */
    boolean isSuperAdminRole(String roleIds);

    /**
     * @param roleIds
     * @return
     */
    String getNames(String roleIds);

    /**
     * @param roleIds
     * @return
     */
    String getModuleIds(String roleIds);

    /**
     * @param roleIds
     * @return
     */
    String getPermissionIds(String roleIds);

    /**
     * @param page
     * @param currentUser
     * @param fieldName
     * @param keyword
     * @return
     */
    List<Role> getByPage(PageInfo page, User currentUser, String fieldName, String keyword);

    /**
     * @param createUser
     * @return
     */
    String getRoleIdsBy(String createUser);

    /**
     * @param currentUser
     * @return
     */
    List<Role> getRolesList(User currentUser);

    /**
     * @param roleId
     * @return
     */
    Map<String, String[]> getRoleModulesAndPermissions(Integer roleId);
}