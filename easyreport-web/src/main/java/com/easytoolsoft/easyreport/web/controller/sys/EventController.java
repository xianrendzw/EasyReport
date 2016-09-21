package com.easytoolsoft.easyreport.web.controller.sys;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.sys.example.EventExample;
import com.easytoolsoft.easyreport.data.sys.po.Event;
import com.easytoolsoft.easyreport.domain.sys.service.IEventService;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.spring.aop.OpLog;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/sys/event")
public class EventController
        extends BaseController<IEventService, Event, EventExample> {
    @GetMapping(value = "/list")
    @OpLog(name = "分页获取系统日志列表")
    @RequiresPermissions("sys.event:view")
    public Map<String, Object> list(DataGridPager pager, String fieldName, String keyword) {
        PageInfo pageInfo = pager.toPageInfo();
        List<Event> list = this.service.getByPage(pageInfo, fieldName, "%" + keyword + "%");
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除系统日志")
    @RequiresPermissions("sys.event:remove")
    public JsonResult remove(int id) {
        JsonResult<String> result = new JsonResult<>();
        this.service.removeById(id);
        return result;
    }

    @GetMapping(value = "/clear")
    @OpLog(name = "清除系统日志")
    @RequiresPermissions("sys.event:clear")
    public JsonResult clear() {
        JsonResult<String> result = new JsonResult<>();
        this.service.clear();
        return result;
    }
}