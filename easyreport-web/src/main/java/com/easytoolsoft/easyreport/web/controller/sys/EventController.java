package com.easytoolsoft.easyreport.web.controller.sys;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.sys.example.EventExample;
import com.easytoolsoft.easyreport.data.sys.po.Event;
import com.easytoolsoft.easyreport.sys.service.IEventService;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/rest/sys/event")
public class EventController
        extends BaseController<IEventService, Event, EventExample> {

    @RequestMapping(value = {"", "/", "/index"})
    public String index() {
        return "sys/event";
    }

    @RequestMapping(value = "/list")
    public Map<String, Object> list(DataGridPager pager) {
        PageInfo pageInfo = pager.toPageInfo();
        List<Event> list = this.service.getByPage(pageInfo);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/find")
    public Map<String, Object> find(DataGridPager pager, String fieldName, String keyword) {
        PageInfo pageInfo = pager.toPageInfo();
        List<Event> list = this.service.getByPage(pageInfo, fieldName, keyword);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/remove")
    public JsonResult remove(int id) {
        JsonResult<String> result = new JsonResult<>();
        this.service.removeById(id);
        return result;
    }

    @RequestMapping(value = "/clear")
    public JsonResult clear(HttpServletRequest req) {
        JsonResult<String> result = new JsonResult<>();
        this.service.clear();
        return result;
    }
}