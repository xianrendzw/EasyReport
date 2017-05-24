package com.easytoolsoft.easyreport.web.controller.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easytoolsoft.easyreport.membership.domain.Event;
import com.easytoolsoft.easyreport.membership.domain.example.EventExample;
import com.easytoolsoft.easyreport.membership.service.EventService;
import com.easytoolsoft.easyreport.mybatis.pager.PageInfo;
import com.easytoolsoft.easyreport.support.annotation.OpLog;
import com.easytoolsoft.easyreport.support.model.ResponseResult;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.model.DataGridPager;
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
@RequestMapping(value = "/rest/member/event")
public class EventController
    extends BaseController<EventService, Event, EventExample, Integer> {
    @GetMapping(value = "/list")
    @OpLog(name = "分页获取系统日志列表")
    @RequiresPermissions("membership.event:view")
    public Map<String, Object> list(final DataGridPager pager, final String fieldName, final String keyword) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<Event> list = this.service.getByPage(pageInfo, fieldName, "%" + keyword + "%");
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除系统日志")
    @RequiresPermissions("membership.event:remove")
    public ResponseResult remove(final Integer id) {
        this.service.removeById(id);
        return ResponseResult.success("");
    }

    @GetMapping(value = "/clear")
    @OpLog(name = "清除系统日志")
    @RequiresPermissions("membership.event:clear")
    public ResponseResult clear() {
        this.service.clear();
        return ResponseResult.success("");
    }
}