package com.easytoolsoft.easyreport.web.controller.sys;

import com.easytoolsoft.easyreport.data.helper.PageInfo;
import com.easytoolsoft.easyreport.sys.po.Event;
import com.easytoolsoft.easyreport.sys.service.IEventService;
import com.easytoolsoft.easyreport.web.common.DataGridPager;
import com.easytoolsoft.easyreport.web.controller.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/rest/sys/event")
public class EventController extends AbstractController {
    @Resource
    private IEventService eventService;

    @RequestMapping(value = {"", "/", "/index"})
    public String index() {
        return "sys/event";
    }

    @RequestMapping(value = "/list")
    public Map<String, Object> list(DataGridPager pager) {
        PageInfo pageInfo = pager.toPageInfo();
        List<Event> list = this.eventService.getByPage(pageInfo);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/find")
    public Map<String, Object> find(String fieldName, String keyword, DataGridPager pager, HttpServletRequest req) {
        pager.setDefaultSort(Event.getColumnName(""));
        pager.setSort(Event.getColumnName(pager.getSort()));
        PageInfo pageInfo = new PageInfo((pager.getPage() - 1) * pager.getRows(),
                pager.getRows(), pager.getSort(), pager.getOrder());
        List<Event> list = this.eventService.queryForPage(pageInfo, fieldName, keyword);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);

        return modelMap;
    }

    @RequestMapping(value = "/remove")
    public JsonResult remove(int id, HttpServletRequest req) {
        JsonResult result = new JsonResult();

        try {
            this.eventService.remove(id);
            this.logSuccessResult(result, String.format("删除日志记录[ID:%s]操作成功!", id), req);
        } catch (Exception ex) {
            result.setMsg(String.format("删除日志记录[ID:%s]操作失败!", id));
            this.logExceptionResult(result, ex, req);
        }

        return result;
    }

    @RequestMapping(value = "/clear")
    public JsonResult clear(HttpServletRequest req) {
        JsonResult result = new JsonResult();
        try {
            // this.eventService.removeAll();
            this.logSuccessResult(result, "清空日志成功！", req);
        } catch (Exception ex) {
            this.logExceptionResult(result, ex, req);
        }
        return result;
    }
}