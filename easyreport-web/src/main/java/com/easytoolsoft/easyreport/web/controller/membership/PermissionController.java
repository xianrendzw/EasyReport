package com.easytoolsoft.easyreport.web.controller.membership;

import com.easytoolsoft.easyreport.data.membership.example.PermissionExample;
import com.easytoolsoft.easyreport.data.membership.po.Permission;
import com.easytoolsoft.easyreport.membership.service.IPermissionService;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.spring.aop.OpLog;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
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

    @RequestMapping(value = "/list")
    @OpLog(name = "获取权限列表")
    public Map<String, Object> list(Integer id) {
        int moduleId = (id == null ? 0 : id);
        List<Permission> list = this.service.getByModuleId(moduleId);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", list.size());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/add")
    @OpLog(name = "增加权限")
    public JsonResult add(Permission po) {
        JsonResult<String> result = new JsonResult<>();
        po.setGmtCreated(new Date());
        po.setGmtModified(new Date());
        this.service.add(po);
        this.service.reloadCache();
        return result;
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改权限")
    public JsonResult edit(Permission po) {
        JsonResult<String> result = new JsonResult<>();
        this.service.editById(po);
        this.service.reloadCache();
        return result;
    }

    @RequestMapping(value = "/remove")
    @OpLog(name = "删除权限")
    public JsonResult remove(int id) {
        JsonResult<String> result = new JsonResult<>();
        this.service.removeById(id);
        this.service.reloadCache();
        return result;
    }
}
