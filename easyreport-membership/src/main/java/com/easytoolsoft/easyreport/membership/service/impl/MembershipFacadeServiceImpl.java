package com.easytoolsoft.easyreport.membership.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import javax.annotation.Resource;

import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.membership.domain.Module;
import com.easytoolsoft.easyreport.membership.domain.User;
import com.easytoolsoft.easyreport.membership.service.MembershipFacadeService;
import com.easytoolsoft.easyreport.membership.service.ModuleService;
import com.easytoolsoft.easyreport.membership.service.PermissionService;
import com.easytoolsoft.easyreport.membership.service.RoleService;
import com.easytoolsoft.easyreport.membership.service.UserService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 用户权限服务外观类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Service("MembershipFacadeService")
public class MembershipFacadeServiceImpl implements MembershipFacadeService {
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private ModuleService moduleService;
    @Resource
    private PermissionService permissionService;

    public MembershipFacadeServiceImpl() {
    }

    @Override
    public void loadCache() {
        this.permissionService.reloadCache();
    }

    @Override
    public List<EasyUITreeNode<Module>> getModuleTree(final List<Module> modules, final Predicate<Module> predicate) {
        return this.moduleService.getModuleTree(modules, predicate);
    }

    @Override
    public User getUser(final String account) {
        return this.userService.getUserByAccount(account);
    }

    @Override
    public String getRoleNames(final String roleIds) {
        return this.roleService.getNames(roleIds);
    }

    @Override
    public Set<String> getRoleSet(final String roleIds) {
        final String[] roleIdSplit = StringUtils.split(roleIds, ',');
        if (roleIdSplit == null || roleIdSplit.length == 0) {
            return Collections.emptySet();
        }

        final Set<String> roleSet = new HashSet<>(roleIdSplit.length);
        for (final String roleId : roleIdSplit) {
            if (!roleSet.contains(roleId.trim())) {
                roleSet.add(roleId);
            }
        }
        return roleSet;
    }

    @Override
    public Set<String> getPermissionSet(final String roleIds) {
        final String permissionIds = this.roleService.getPermissionIds(roleIds);
        if (StringUtils.isBlank(permissionIds)) {
            return Collections.emptySet();
        }

        final Map<String, String> permissionMap = this.permissionService.getIdCodeMap();
        final String[] permissionIdSplit = StringUtils.split(permissionIds, ',');
        final Set<String> permSet = new HashSet<>();
        for (final String permId : permissionIdSplit) {
            final String perm = permissionMap.get(StringUtils.trim(permId));
            if (StringUtils.isNotBlank(perm)) {
                permSet.add(perm);
            }
        }
        return permSet;
    }

    @Override
    public boolean hasPermission(final String roleIds, final String... codes) {
        if (this.isAdministrator(roleIds)) {
            return true;
        }

        if (StringUtils.isBlank(roleIds) || ArrayUtils.isEmpty(codes)) {
            return false;
        }

        final String permissionIds = this.roleService.getPermissionIds(roleIds);
        if (StringUtils.isBlank(permissionIds)) {
            return false;
        }

        final String[] permissionIdSplit = StringUtils.split(permissionIds, ',');
        final String codePermissionIds = this.permissionService.getPermissionIds(codes);
        final String[] codePermissionIdSplit = StringUtils.split(codePermissionIds, ',');

        return this.hasPermission(codePermissionIdSplit, permissionIdSplit);
    }

    private boolean hasPermission(final String[] codePermissionIdSplit, final String[] permissionIdSplit) {
        if (codePermissionIdSplit == null || permissionIdSplit == null) {
            return false;
        }

        for (final String permId : codePermissionIdSplit) {
            if (!ArrayUtils.contains(permissionIdSplit, permId)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isAdministrator(final String roleIds) {
        if (StringUtils.isBlank(roleIds)) {
            return false;
        }
        return this.roleService.isSuperAdminRole(roleIds);
    }

    @Override
    public List<Module> getModules(final String roleIds) {
        if (this.isAdministrator(roleIds)) {
            return this.moduleService.getAll();
        }
        final String moduleIds = this.roleService.getModuleIds(roleIds);
        return this.moduleService.getModules(moduleIds);
    }
}
