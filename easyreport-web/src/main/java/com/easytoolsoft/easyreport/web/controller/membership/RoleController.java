package com.easytoolsoft.easyreport.web.controller.membership;

import com.easytoolsoft.easyreport.common.pair.IdNamePair;
import com.easytoolsoft.easyreport.common.tree.EasyUITreeNode;
import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.membership.example.RoleExample;
import com.easytoolsoft.easyreport.data.membership.po.Module;
import com.easytoolsoft.easyreport.data.membership.po.Permission;
import com.easytoolsoft.easyreport.data.membership.po.Role;
import com.easytoolsoft.easyreport.data.membership.po.User;
import com.easytoolsoft.easyreport.membership.common.CurrentUser;
import com.easytoolsoft.easyreport.membership.service.IModuleService;
import com.easytoolsoft.easyreport.membership.service.IPermissionService;
import com.easytoolsoft.easyreport.membership.service.IRoleService;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.spring.aop.OpLog;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest/membership/role")
public class RoleController
        extends BaseController<IRoleService, Role, RoleExample> {

    @Resource
    private IModuleService moduleService;
    @Resource
    private IPermissionService permissionService;

    @RequestMapping(value = {"", "/", "/index"})
    public ModelAndView index(@CurrentUser User loginUser) {
        ModelAndView modelAndView = new ModelAndView("membership/role");
        modelAndView.addObject("isSuperAdmin", this.service.isSuperAdminRole(loginUser.getRoles()));
        return modelAndView;
    }

    @RequestMapping(value = "/list")
    @OpLog(name = "分页获取角色列表")
    public Map<String, Object> list(@CurrentUser User loginUser, DataGridPager pager) {
        PageInfo pageInfo = pager.toPageInfo();
        List<Role> list = this.service.getByPage(pageInfo, loginUser, null, null);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/find")
    @OpLog(name = "查找角色并分页列表显示")
    public Map<String, Object> find(@CurrentUser User loginUser,
                                    DataGridPager pager, String fieldName, String keyword) {
        PageInfo pageInfo = pager.toPageInfo();
        List<Role> list = this.service.getByPage(pageInfo, loginUser, fieldName, keyword);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/add")
    @OpLog(name = "增加角色")
    public JsonResult add(@CurrentUser User loginUser, Role po) {
        JsonResult<String> result = new JsonResult<>();
        po.setModules("");
        po.setPermissions("");
        po.setCreateUser(loginUser.getAccount());
        po.setGmtCreated(new Date());
        po.setGmtModified(new Date());
        this.service.add(po);
        return result;
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改角色")
    public JsonResult edit(Role po) {
        JsonResult<String> result = new JsonResult<>();
        this.service.editById(po);
        return result;
    }

    @RequestMapping(value = "/remove")
    @OpLog(name = "删除角色")
    public JsonResult remove(int id) {
        JsonResult<String> result = new JsonResult<>();
        this.service.removeById(id);
        return result;
    }

    @RequestMapping(value = "/getRoleList")
    @OpLog(name = "获取当前的角色列表")
    public List<IdNamePair> getRoleList(@CurrentUser User loginUser) {
        List<IdNamePair> list = new ArrayList<>(10);
        List<Role> roleList = this.service.getRolesList(loginUser);
        if (CollectionUtils.isEmpty(roleList)) {
            return list;
        }
        list.addAll(roleList.stream()
                .map(role -> new IdNamePair(String.valueOf(role.getId()), role.getName()))
                .collect(Collectors.toList()));
        return list;
    }

    @RequestMapping(value = "/authorize")
    @OpLog(name = "给角色授权")
    public JsonResult authorize(Role po) {
        JsonResult<String> result = new JsonResult<>();
        po.setPermissions(StringUtils.stripEnd(po.getPermissions(), ","));
        po.setModules(permissionService.getModuleIds(po.getPermissions()));
        this.service.editById(po);
        return result;
    }

    @RequestMapping(value = "/getRoleById")
    @OpLog(name = "获取指定id的角色信息")
    public Role getRoleById(Integer id) {
        return this.service.getById(id);
    }

    @RequestMapping(value = "/listPermissionTree")
    @OpLog(name = "获取当前用户所拥有的权限列表")
    public List<EasyUITreeNode<String>> listPermissionTree(@CurrentUser User loginUser, Integer roleId) {
        Map<String, String[]> roleModuleAndPermissionMap
                = this.service.getRoleModulesAndPermissions(roleId);
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
        boolean isSuperAdminRole = this.service.isSuperAdminRole(userRoles);
        List<Module> modules = isSuperAdminRole
                ? this.moduleService.getAll()
                : this.moduleService.getModules(this.service.getModuleIds(userRoles));

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
