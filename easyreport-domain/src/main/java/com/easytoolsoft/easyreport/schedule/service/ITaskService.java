package com.easytoolsoft.easyreport.schedule.service;

import com.easytoolsoft.easyreport.data.common.service.ICrudService;
import com.easytoolsoft.easyreport.data.schedule.example.TaskExample;
import com.easytoolsoft.easyreport.data.schedule.po.Task;

/**
 * 报表调度任务服务类
 *
 * @author Tom Deng
 */
public interface ITaskService extends ICrudService<Task, TaskExample> {
}
