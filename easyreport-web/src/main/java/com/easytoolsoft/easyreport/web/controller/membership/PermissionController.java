package com.easytoolsoft.easyreport.web.controller.membership;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.membership.example.PermissionExample;
import com.easytoolsoft.easyreport.data.membership.po.Permission;
import com.easytoolsoft.easyreport.membership.service.IPermissionService;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.spring.aop.OpLog;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/membership/permission")
public class PermissionController
        extends BaseController<IPermissionService, Permission, PermissionExample> {

    @GetMapping(value = "/list")
    @OpLog(name = "获取权限列表")
    @RequiresPermissions("membership.permission:view")
    public Map<String, Object> list(DataGridPager pager, Integer id) {
        int moduleId = (id == null ? 0 : id);
        PageInfo pageInfo = pager.toPageInfo();
        List<Permission> list = this.service.getByPage(pageInfo, moduleId);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", list.size());
        modelMap.put("rows", list);
        return modelMap;
    }

    @PostMapping(value = "/add")
    @OpLog(name = "增加权限")
    @RequiresPermissions("membership.permission:add")
    public JsonResult add(Permission po) {
        JsonResult<String> result = new JsonResult<>();
        po.setGmtCreated(new Date());
        po.setGmtModified(new Date());
        this.service.add(po);
        this.service.reloadCache();
        return result;
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "修改权限")
    @RequiresPermissions("membership.permission:edit")
    public JsonResult edit(Permission po) {
        JsonResult<String> result = new JsonResult<>();
        this.service.editById(po);
        this.service.reloadCache();
        return result;
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除权限")
    @RequiresPermissions("membership.permission:remove")
    public JsonResult remove(int id) {
        JsonResult<String> result = new JsonResult<>();
        this.service.removeById(id);
        this.service.reloadCache();
        return result;
    }
}
