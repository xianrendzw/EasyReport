package com.easytoolsoft.easyreport.web.controller.membership;

import com.easytoolsoft.easyreport.data.membership.po.Permission;
import com.easytoolsoft.easyreport.membership.service.IModuleService;
import com.easytoolsoft.easyreport.membership.service.IPermissionService;
import com.easytoolsoft.easyreport.membership.service.IRoleService;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import com.easytoolsoft.easyreport.web.controller.common.AbstractController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/membership/permission")
public class PermissionController extends AbstractController {
    @Resource
    private IPermissionService permissionService;
    @Resource
    private IRoleService roleService;
    @Resource
    private IModuleService moduleService;

    @RequestMapping(value = "/list")
    public Map<String, Object> list(Integer id) {
        int moduleId = (id == null ? 0 : id);
        List<Permission> list = this.permissionService.getByModuleId(moduleId);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", list.size());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/add")
    public JsonResult add(Permission po, HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            po.setGmtCreated(new Date());
            po.setGmtModified(new Date());
            this.permissionService.add(po);
            this.permissionService.reloadCache();
            this.logSuccessResult(result, String.format("增加权限[ID:%s]操作成功!", po.getId()), req);
        } catch (Exception ex) {
            result.setMsg(String.format("增加权限:[%s]操作失败!", po.getName()));
            this.logExceptionResult(result, ex, req);
        }

        return result;
    }

    @RequestMapping(value = "/edit")
    public JsonResult edit(Permission po, HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            this.permissionService.editById(po);
            this.permissionService.reloadCache();
            this.logSuccessResult(result, String.format("修改权限[ID:%s]操作成功!", po.getId()), req);
        } catch (Exception ex) {
            result.setMsg(String.format("修改权限:[%s]操作失败!", po.getId()));
            this.logExceptionResult(result, ex, req);
        }
        return result;
    }

    @RequestMapping(value = "/remove")
    public JsonResult remove(int id, HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        try {
            this.permissionService.removeById(id);
            this.permissionService.reloadCache();
            this.logSuccessResult(result, String.format("删除权限[ID:%s]操作成功!", id), req);
        } catch (Exception ex) {
            result.setMsg(String.format("删除权限[ID:%s]操作失败!", id));
            this.logExceptionResult(result, ex, req);
        }
        return result;
    }
}
