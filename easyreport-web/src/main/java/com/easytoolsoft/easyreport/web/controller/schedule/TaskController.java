package com.easytoolsoft.easyreport.web.controller.schedule;

import com.easytoolsoft.easyreport.data.schedule.example.TaskExample;
import com.easytoolsoft.easyreport.data.schedule.po.Task;
import com.easytoolsoft.easyreport.schedule.service.ITaskService;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.spring.aop.OpLog;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/schedule/task")
public class TaskController
        extends BaseController<ITaskService, Task, TaskExample> {

    @RequestMapping(value = "/list")
    @OpLog(name = "获取任务列表")
    public Map<String, Object> list(DataGridPager pager, String fieldName, String keyword) {
        return super.find(pager, fieldName, keyword);
    }

    @RequestMapping(value = "/add")
    @OpLog(name = "增加任务")
    public JsonResult add(Task po) {
        po.setGmtCreated(new Date());
        po.setGmtModified(new Date());
        return super.add(po);
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改任务")
    public JsonResult edit(Task po) {
        return super.edit(po);
    }

    @RequestMapping(value = "/remove")
    @OpLog(name = "删除任务")
    public JsonResult remove(int id) {
        return super.remove(id);
    }
}
