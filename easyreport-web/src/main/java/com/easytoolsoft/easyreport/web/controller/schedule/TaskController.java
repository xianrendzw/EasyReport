package com.easytoolsoft.easyreport.web.controller.schedule;

import java.util.Date;
import java.util.Map;

import com.easytoolsoft.easyreport.meta.domain.Task;
import com.easytoolsoft.easyreport.meta.domain.example.TaskExample;
import com.easytoolsoft.easyreport.meta.domain.options.MailTaskOptions;
import com.easytoolsoft.easyreport.meta.domain.options.SMSTaskOptions;
import com.easytoolsoft.easyreport.meta.service.TaskService;
import com.easytoolsoft.easyreport.support.annotation.OpLog;
import com.easytoolsoft.easyreport.support.model.ResponseResult;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.model.DataGridPager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping(value = "/rest/schedule/task")
public class TaskController
    extends BaseController<TaskService, Task, TaskExample, Integer> {

    private final Integer MAIL_TASK = 1;
    private final Integer SMS_TASK = 2;

    @GetMapping(value = "/list")
    @OpLog(name = "获取任务列表")
    @RequiresPermissions("schedule.task:view")
    public Map<String, Object> list(final DataGridPager pager, final String fieldName, final String keyword) {
        return this.queryByPage(pager, fieldName, keyword);
    }

    @PostMapping(value = "/add")
    @OpLog(name = "增加任务")
    @RequiresPermissions("schedule.task:add")
    public ResponseResult add(final Task po) {
        po.setGmtCreated(new Date());
        po.setGmtModified(new Date());
        return this.create(po);
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "修改任务")
    @RequiresPermissions("schedule.task:edit")
    public ResponseResult edit(final Task po) {
        return this.update(po);
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除任务")
    @RequiresPermissions("schedule.task:remove")
    public ResponseResult remove(final Integer id) {
        return this.delete(id);
    }

    @GetMapping(value = "/getJsonOptions")
    @OpLog(name = "获取配置项JSON结构")
    @RequiresPermissions("schedule.task:view")
    public ResponseResult getJsonOptions(final Integer type) throws JsonProcessingException {
        if (this.MAIL_TASK.equals(type)) {
            return ResponseResult.success((new ObjectMapper().writeValueAsString(new MailTaskOptions())));
        }
        if (this.SMS_TASK.equals(type)) {
            return ResponseResult.success(new ObjectMapper().writeValueAsString(new SMSTaskOptions()));
        }
        return ResponseResult.success("{}");
    }
}
