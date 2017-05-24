package com.easytoolsoft.easyreport.web.controller.member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.easytoolsoft.easyreport.common.pair.IdNamePair;
import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.membership.domain.Module;
import com.easytoolsoft.easyreport.membership.domain.Permission;
import com.easytoolsoft.easyreport.membership.domain.Role;
import com.easytoolsoft.easyreport.membership.domain.User;
import com.easytoolsoft.easyreport.membership.domain.example.RoleExample;
import com.easytoolsoft.easyreport.membership.service.ModuleService;
import com.easytoolsoft.easyreport.membership.service.PermissionService;
import com.easytoolsoft.easyreport.membership.service.RoleService;
import com.easytoolsoft.easyreport.mybatis.pager.PageInfo;
import com.easytoolsoft.easyreport.support.annotation.CurrentUser;
import com.easytoolsoft.easyreport.support.annotation.OpLog;
import com.easytoolsoft.easyreport.support.model.ResponseResult;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.model.DataGridPager;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tom Deng
 * @date 2017-03-25
 */
@RestController
@RequestMapping(value = "/rest/member/role")
public class RoleController
    extends BaseController<RoleService, Role, RoleExample, Integer> {

    @Resource
    private ModuleService moduleService;
    @Resource
    private PermissionService permissionService;

    @GetMapping(value = "/isSuperAdmin")
    @OpLog(name = "是否为超级管理角色")
    @RequiresPermissions("membership.role:view")
    public ResponseResult isSuperAdmin(@CurrentUser final User loginUser) {
        return ResponseResult.success(this.service.isSuperAdminRole(loginUser.getRoles()));
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取角色列表")
    @RequiresPermissions("membership.role:view")
    public Map<String, Object> list(@CurrentUser final User loginUser, final DataGridPager pager,
                                    final String fieldName, final String keyword) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<Role> list = this.service.getByPage(pageInfo, loginUser, fieldName, "%" + keyword + "%");
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @PostMapping(value = "/add")
    @OpLog(name = "增加角色")
    @RequiresPermissions("membership.role:add")
    public ResponseResult add(@CurrentUser final User loginUser, final Role po) {
        po.setModules("");
        po.setPermissions("");
        po.setCreateUser(loginUser.getAccount());
        po.setGmtCreated(new Date());
        po.setGmtModified(new Date());
        this.service.add(po);
        return ResponseResult.success("");
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "修改角色")
    @RequiresPermissions("membership.role:edit")
    public ResponseResult edit(final Role po) {
        this.service.editById(po);
        return ResponseResult.success("");
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除角色")
    @RequiresPermissions("membership.role:remove")
    public ResponseResult remove(final Integer id) {
        this.service.removeById(id);
        return ResponseResult.success("");
    }

    @GetMapping(value = "/getRoleList")
    @OpLog(name = "获取当前的角色列表")
    @RequiresPermissions("membership.role:view")
    public List<IdNamePair> getRoleList(@CurrentUser final User loginUser) {
        final List<IdNamePair> list = new ArrayList<>(10);
        final List<Role> roleList = this.service.getRolesList(loginUser);
        if (CollectionUtils.isEmpty(roleList)) {
            return list;
        }
        list.addAll(roleList.stream()
            .map(role -> new IdNamePair(String.valueOf(role.getId()), role.getName()))
            .collect(Collectors.toList()));
        return list;
    }

    @PostMapping(value = "/authorize")
    @OpLog(name = "给角色授权")
    @RequiresPermissions("membership.role:authorize")
    public ResponseResult authorize(final Role po) {
        po.setPermissions(StringUtils.stripEnd(po.getPermissions(), ","));
        po.setModules(this.permissionService.getModuleIds(po.getPermissions()));
        this.service.editById(po);
        return ResponseResult.success("");
    }

    @GetMapping(value = "/getRoleById")
    @OpLog(name = "获取指定id的角色信息")
    @RequiresPermissions("membership.role:view")
    public Role getRoleById(final Integer id) {
        return this.service.getById(id);
    }

    @GetMapping(value = "/listPermissionTree")
    @OpLog(name = "获取当前用户所拥有的权限列表")
    @RequiresPermissions("membership.role:view")
    public List<EasyUITreeNode<String>> listPermissionTree(@CurrentUser final User loginUser,
                                                           final Integer roleId) {
        final Map<String, String[]> roleModuleAndPermissionMap
            = this.service.getRoleModulesAndPermissions(roleId);
        if (roleModuleAndPermissionMap == null) {
            return new ArrayList<>(0);
        }
        return this.buildTree(this.getModulePermissions(
            loginUser.getRoles(),
            roleModuleAndPermissionMap.get("modules"),
            roleModuleAndPermissionMap.get("permissions")));
    }

    private List<EasyUITreeNode<String>> getModulePermissions(final String userRoles,
                                                              final String[] moduleSplit,
                                                              final String[] operationSplit) {
        final boolean isSuperAdminRole = this.service.isSuperAdminRole(userRoles);
        final List<Module> modules = isSuperAdminRole
            ? this.moduleService.getAll()
            : this.moduleService.getModules(this.service.getModuleIds(userRoles));

        final List<EasyUITreeNode<String>> moduleNodes = new ArrayList<>(modules.size());
        moduleNodes.addAll(modules.stream()
            .map(module -> new EasyUITreeNode<>(
                String.valueOf(-module.getId()),
                String.valueOf(-module.getParentId()),
                module.getName(), "open", "", false,
                String.valueOf(module.getId())
            )).collect(Collectors.toList()));

        final List<Permission> permissions = this.permissionService.getAll();
        final List<EasyUITreeNode<String>> permNodes = new ArrayList<>(permissions.size());
        permNodes.addAll(permissions.stream()
            .map(perm -> new EasyUITreeNode<>(
                String.valueOf(perm.getId()),
                String.valueOf(-perm.getModuleId()),
                perm.getName(), "open", "",
                ArrayUtils.contains(operationSplit, perm.getId().toString()),
                String.valueOf(perm.getId())
            )).collect(Collectors.toList()));
        moduleNodes.addAll(permNodes);
        return moduleNodes;
    }

    private List<EasyUITreeNode<String>> buildTree(final Collection<EasyUITreeNode<String>> nodes) {
        if (CollectionUtils.isEmpty(nodes)) {
            return new ArrayList<>(0);
        }

        final List<EasyUITreeNode<String>> rootNodes = new ArrayList<>(20);
        rootNodes.addAll(nodes.stream()
            .filter(treeNode -> "0".equals(treeNode.getPId()))
            .collect(Collectors.toList()));
        for (final EasyUITreeNode<String> rootNode : rootNodes) {
            getChildNodes(nodes, rootNode);
        }
        return rootNodes;
    }

    private void getChildNodes(final Collection<EasyUITreeNode<String>> nodes, final EasyUITreeNode<String> node) {
        final List<EasyUITreeNode<String>> childNodes = new ArrayList<>(nodes.size());
        childNodes.addAll(nodes.stream()
            .filter(treeNode -> treeNode.getPId().equals(node.getId()))
            .collect(Collectors.toList()));
        for (final EasyUITreeNode<String> childNode : childNodes) {
            node.setState("closed");
            node.getChildren().add(childNode);
            getChildNodes(nodes, childNode);
        }
    }

}
