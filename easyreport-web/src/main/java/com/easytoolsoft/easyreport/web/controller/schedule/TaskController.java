package com.easytoolsoft.easyreport.web.controller.schedule;

import com.easytoolsoft.easyreport.domain.schedule.example.TaskExample;
import com.easytoolsoft.easyreport.domain.schedule.po.MailTaskOptions;
import com.easytoolsoft.easyreport.domain.schedule.po.SMSTaskOptions;
import com.easytoolsoft.easyreport.domain.schedule.po.Task;
import com.easytoolsoft.easyreport.domain.schedule.service.ITaskService;
import com.easytoolsoft.easyreport.domain.schedule.service.QuartzManager;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.spring.aop.OpLog;
import com.easytoolsoft.easyreport.web.viewmodel.DataGridPager;
import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/schedule/task")
public class TaskController
        extends BaseController<ITaskService, Task, TaskExample> {
	@Autowired
	private QuartzManager quartz;
	
    @GetMapping(value = "/list")
    @OpLog(name = "获取任务列表")
    @RequiresPermissions("schedule.task:view")
    public Map<String, Object> list(DataGridPager pager, String fieldName, String keyword) {
        return super.find(pager, fieldName, keyword);
    }

    @PostMapping(value = "/add")
    @OpLog(name = "增加任务")
    @RequiresPermissions("schedule.task:add")
    public JsonResult add(Task po) {
        po.setGmtCreated(new Date());
        po.setGmtModified(new Date());
        JsonResult result =  super.add(po);
        quartz.reScheduleJob();
        return result;
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "修改任务")
    @RequiresPermissions("schedule.task:edit")
    public JsonResult edit(Task po) {
        JsonResult result =  super.edit(po);
        quartz.reScheduleJob();
        return result;
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除任务")
    @RequiresPermissions("schedule.task:remove")
    public JsonResult remove(int id) {
        JsonResult result =  super.remove(id);
        quartz.reScheduleJob();
        return result;
    }

    @GetMapping(value = "/getJsonOptions")
    @OpLog(name = "获取配置项JSON结构")
    @RequiresPermissions("schedule.task:view")
    public JsonResult getJsonOptions(Integer type) throws JsonProcessingException {
        JsonResult<String> result = new JsonResult<>();
        if (type.equals(1)) {
            result.setData(new ObjectMapper().writeValueAsString(new MailTaskOptions()));
        } else if (type.equals(2)) {
            result.setData(new ObjectMapper().writeValueAsString(new SMSTaskOptions()));
        } else {
            result.setData("{}");
        }
        return result;
    }
    
    @GetMapping(value = "/schedule")
    @OpLog(name = "init schedule")
    @RequiresPermissions("schedule.task:view")
    public JsonResult schedule() {
    	quartz.reScheduleJob();
    	return new JsonResult("{}");
    }
    
    @RequestMapping("/task")
    public JsonResult task(@RequestParam(value="taskid", defaultValue="0") Integer taskid) {
    	quartz.triggerTask(taskid);
        return new JsonResult("{}");
    }
}
