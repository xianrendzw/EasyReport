package com.easytoolsoft.easyreport.membership.service;

import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.data.membership.po.Module;
import com.easytoolsoft.easyreport.data.membership.po.User;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * 用户权限服务外观类
 */
@Service
public class MembershipFacade {
    @Resource
    private IUserService userService;
    @Resource
    private IRoleService roleService;
    @Resource
    private IModuleService moduleService;
    @Resource
    private IPermissionService permissionService;

    public MembershipFacade() {
    }

    public void loadCache() {
        permissionService.reloadCache();
    }

    public List<EasyUITreeNode<Module>> getModuleTree(List<Module> modules, Predicate<Module> predicate) {
        return this.moduleService.getModuleTree(modules, predicate);
    }

    public User getUser(String account) {
        return this.userService.getUserByAccount(account);
    }

    public String getRoleNames(String roleIds) {
        return this.roleService.getNames(roleIds);
    }

    public Set<String> getRoleSet(String roleIds) {
        String[] roleIdSplit = StringUtils.split(roleIds, ',');
        if (roleIdSplit == null || roleIdSplit.length == 0) {
            return Collections.emptySet();
        }

        Set<String> roleSet = new HashSet<>(roleIdSplit.length);
        for (String roleId : roleIdSplit) {
            if (!roleSet.contains(roleId.trim())) {
                roleSet.add(roleId);
            }
        }
        return roleSet;
    }

    public Set<String> getPermissionSet(String roleIds) {
        String permissionIds = roleService.getPermissionIds(roleIds);
        if (StringUtils.isBlank(permissionIds)) {
            return Collections.emptySet();
        }

        Map<String, String> permissionMap = permissionService.getIdCodeMap();
        String[] permissionIdSplit = StringUtils.split(permissionIds, ',');
        Set<String> permSet = new HashSet<>();
        for (String permId : permissionIdSplit) {
            String perm = permissionMap.get(StringUtils.trim(permId));
            if (StringUtils.isNotBlank(perm)) {
                permSet.add(perm);
            }
        }
        return permSet;
    }

    public boolean hasPermission(String roleIds, String... codes) {
        if (this.isAdministrator(roleIds)) {
            return true;
        }

        if (StringUtils.isBlank(roleIds) || ArrayUtils.isEmpty(codes)) {
            return false;
        }

        String permissionIds = roleService.getPermissionIds(roleIds);
        if (StringUtils.isBlank(permissionIds)) {
            return false;
        }

        String[] permissionIdSplit = StringUtils.split(permissionIds, ',');
        String codePermissionIds = permissionService.getPermissionIds(codes);
        String[] codePermissionIdSplit = StringUtils.split(codePermissionIds, ',');

        return this.hasPermission(codePermissionIdSplit, permissionIdSplit);
    }

    private boolean hasPermission(String[] codePermissionIdSplit, String[] permissionIdSplit) {
        if (codePermissionIdSplit == null || permissionIdSplit == null) {
            return false;
        }

        for (String permId : codePermissionIdSplit) {
            if (!ArrayUtils.contains(permissionIdSplit, permId)) {
                return false;
            }
        }

        return true;
    }

    public boolean isAdministrator(String roleIds) {
        if (StringUtils.isBlank(roleIds)) {
            return false;
        }
        return this.roleService.isSuperAdminRole(roleIds);
    }

    public List<Module> getModules(String roleIds) {
        if (this.isAdministrator(roleIds)) {
            return moduleService.getAll();
        }
        String moduleIds = roleService.getModuleIds(roleIds);
        return moduleService.getModules(moduleIds);
    }
}
