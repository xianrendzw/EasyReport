package com.easytoolsoft.easyreport.web.controller.metadata;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easytoolsoft.easyreport.data.helper.PageInfo;
import com.easytoolsoft.easyreport.domain.metadata.example.GlobalParamExample;
import com.easytoolsoft.easyreport.domain.metadata.po.GlobalParam;
import com.easytoolsoft.easyreport.domain.metadata.service.IGlobalParamService;
import com.easytoolsoft.easyreport.membership.common.CurrentUser;
import com.easytoolsoft.easyreport.membership.po.User;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.spring.aop.OpLog;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;

/**
 * Global Parameter
 */
@RestController("MetaDataGlobalParamController")
@RequestMapping(value = "/rest/metadata/GlobalParam")
public class GlobalParamController
        extends BaseController<IGlobalParamService, GlobalParam, GlobalParamExample> {

    @RequestMapping(value = "/list")
    @OpLog(name = "List global parameter")
    @RequiresPermissions("report.conf:view")
    public Map<String, Object> list(DataGridPager pager) {
        PageInfo pageInfo = pager.toPageInfo();
        List<GlobalParam> list = this.service.getByPage(pageInfo);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", list.size());
        modelMap.put("rows", list);
        return modelMap;
    }
    
    @RequestMapping(value = "/add")
    @OpLog(name = "Add global parameter")
    @RequiresPermissions("report.conf:add")
    public JsonResult add(@CurrentUser User loginUser,GlobalParam po) {
        JsonResult<String> result = new JsonResult<>();
        po.setCreateUser(loginUser.getAccount());
        po.setGmtCreated(new Date());
        po.setGmtModified(new Date());
        this.service.add(po);
        return result;
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "Edit global parameter")
    @RequiresPermissions("report.conf:edit")
    public JsonResult edit(GlobalParam po) {
        JsonResult<String> result = new JsonResult<>();
        this.service.editById(po);
        return result;
    }

    @RequestMapping(value = "/remove")
    @OpLog(name = "Remove global parameter")
    @RequiresPermissions("report.conf:remove")
    public JsonResult remove(int id) {
        JsonResult<String> result = new JsonResult<>();
        this.service.removeById(id);
        return result;
    }
}