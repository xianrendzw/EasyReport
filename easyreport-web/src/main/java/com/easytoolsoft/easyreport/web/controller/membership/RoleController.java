package com.easytoolsoft.easyreport.web.controller.membership;

import com.easytoolsoft.easyreport.common.pair.IdNamePair;
import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.membership.common.CurrentUser;
import com.easytoolsoft.easyreport.data.membership.po.Module;
import com.easytoolsoft.easyreport.data.membership.po.Permission;
import com.easytoolsoft.easyreport.data.membership.po.Role;
import com.easytoolsoft.easyreport.data.membership.po.User;
import com.easytoolsoft.easyreport.membership.service.IModuleService;
import com.easytoolsoft.easyreport.membership.service.IPermissionService;
import com.easytoolsoft.easyreport.membership.service.IRoleService;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import com.easytoolsoft.easyreport.web.controller.common.AbstractController;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest/membership/role")
public class RoleController extends AbstractController {
    @Resource
    private IRoleService roleService;
    @Resource
    private IModuleService moduleService;
    @Resource
    private IPermissionService permissionService;

    @RequestMapping(value = {"", "/", "/index"})
    public ModelAndView index(@CurrentUser User loginUser) {
        ModelAndView modelAndView = new ModelAndView("membership/role");
        modelAndView.addObject("isSuperAdmin", this.roleService.isSuperAdminRole(loginUser.getRoles()));
        return modelAndView;
    }

    @RequestMapping(value = "/list")
    public Map<String, Object> list(@CurrentUser User loginUser, DataGridPager pager) {
        PageInfo pageInfo = pager.toPageInfo();
        List<Role> list = this.roleService.getByPage(pageInfo, loginUser, null, null);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);

        return modelMap;
    }

    @RequestMapping(value = "/find")
    public Map<String, Object> find(@CurrentUser User loginUser,
                                    DataGridPager pager, String fieldName, String keyword) {
        PageInfo pageInfo = pager.toPageInfo();
        List<Role> list = this.roleService.getByPage(pageInfo, loginUser, fieldName, keyword);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);

        return modelMap;
    }

    @RequestMapping(value = "/add")
    public JsonResult add(@CurrentUser User loginUser, Role po, HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            po.setModules("");
            po.setPermissions("");
            po.setCreateUser(loginUser.getAccount());
            po.setGmtCreated(new Date());
            po.setGmtModified(new Date());
            this.roleService.add(po);
            this.logSuccessResult(result, String.format("增加角色[ID:%s]操作成功!", po.getId()), req);
        } catch (Exception ex) {
            result.setMsg(String.format("增加角色:[%s]操作失败!", po.getName()));
            this.logExceptionResult(result, ex, req);
        }
        return result;
    }

    @RequestMapping(value = "/edit")
    public JsonResult edit(Role po, HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            this.roleService.editById(po);
            this.logSuccessResult(result, String.format("修改角色[ID:%s]操作成功!", po.getId()), req);
        } catch (Exception ex) {
            result.setMsg(String.format("修改角色:[%s]操作失败!", po.getId()));
            this.logExceptionResult(result, ex, req);
        }
        return result;
    }

    @RequestMapping(value = "/remove")
    public JsonResult remove(int id, HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            this.roleService.removeById(id);
            this.logSuccessResult(result, String.format("删除角色[ID:%s]操作成功!", id), req);
        } catch (Exception ex) {
            result.setMsg(String.format("删除角色[ID:%s]操作失败!", id));
            this.logExceptionResult(result, ex, req);
        }
        return result;
    }

    @RequestMapping(value = "/getRoleList")
    public List<IdNamePair> getRoleList(@CurrentUser User loginUser, HttpServletRequest req) {
        List<IdNamePair> list = new ArrayList<>(10);
        List<Role> roleList = this.roleService.getRolesList(loginUser);
        if (CollectionUtils.isEmpty(roleList)) {
            return list;
        }
        list.addAll(roleList.stream()
                .map(role -> new IdNamePair(String.valueOf(role.getId()), role.getName()))
                .collect(Collectors.toList()));
        return list;
    }

    @RequestMapping(value = "/authorize")
    public JsonResult authorize(Role po, HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            po.setPermissions(StringUtils.stripEnd(po.getPermissions(), ","));
            po.setModules(permissionService.getModuleIds(po.getPermissions()));
            this.roleService.editById(po);
            this.logSuccessResult(result, String.format("给角色[ID:%s]授权成功!", po.getId()), req);
        } catch (Exception ex) {
            result.setMsg(String.format("给角色[ID:%s]授权失败!", po.getId()));
            this.logExceptionResult(result, ex, req);
        }
        return result;
    }

    @RequestMapping(value = "/getRoleById")
    public Role getRoleById(Integer id, HttpServletRequest req) {
        return this.roleService.getById(id);

    }

    @RequestMapping(value = "/listPermissionTree")
    public List<EasyUITreeNode<String>> listPermissionTree(@CurrentUser User loginUser, Integer roleId) {
        Map<String, String[]> roleModuleAndPermissionMap
                = this.roleService.getRoleModulesAndPermissions(roleId);
        if (roleModuleAndPermissionMap == null) {
            return new ArrayList<>(0);
        }
        return this.buildTree(this.getModulePermissions(
                loginUser.getRoles(),
                roleModuleAndPermissionMap.get("modules"),
                roleModuleAndPermissionMap.get("permissions")));
    }

    private List<EasyUITreeNode<String>> getModulePermissions(String userRoles,
                                                              String[] moduleSplit, String[] operationSplit) {
        boolean isSuperAdminRole = this.roleService.isSuperAdminRole(userRoles);
        List<Module> modules = isSuperAdminRole
                ? this.moduleService.getAll()
                : this.moduleService.getModules(this.roleService.getModuleIds(userRoles));

        List<EasyUITreeNode<String>> moduleNodes = new ArrayList<>(modules.size());
        moduleNodes.addAll(modules.stream()
                .map(module -> new EasyUITreeNode<>(
                        String.valueOf(-module.getId()),
                        String.valueOf(-module.getParentId()),
                        module.getName(), "open", "", false,
                        String.valueOf(module.getId())
                )).collect(Collectors.toList()));

        List<Permission> permissions = this.permissionService.getAll();
        List<EasyUITreeNode<String>> permNodes = new ArrayList<>(permissions.size());
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

    private List<EasyUITreeNode<String>> buildTree(Collection<EasyUITreeNode<String>> nodes) {
        if (CollectionUtils.isEmpty(nodes)) {
            return new ArrayList<>(0);
        }

        List<EasyUITreeNode<String>> rootNodes = new ArrayList<>(20);
        rootNodes.addAll(nodes.stream()
                .filter(treeNode -> treeNode.getPId().equals("0"))
                .collect(Collectors.toList()));
        for (EasyUITreeNode<String> rootNode : rootNodes) {
            getChildNodes(nodes, rootNode);
        }
        return rootNodes;
    }

    private void getChildNodes(Collection<EasyUITreeNode<String>> nodes, EasyUITreeNode<String> node) {
        List<EasyUITreeNode<String>> childNodes = new ArrayList<>(nodes.size());
        childNodes.addAll(nodes.stream()
                .filter(treeNode -> treeNode.getPId().equals(node.getId()))
                .collect(Collectors.toList()));
        for (EasyUITreeNode<String> childNode : childNodes) {
            node.setState("closed");
            node.getChildren().add(childNode);
            getChildNodes(nodes, childNode);
        }
    }

}
