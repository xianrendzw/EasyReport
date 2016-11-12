package com.easytoolsoft.easyreport.domain.schedule.service;

import com.easytoolsoft.easyreport.data.service.ICrudService;
import com.easytoolsoft.easyreport.domain.schedule.example.TaskExample;
import com.easytoolsoft.easyreport.domain.schedule.po.Task;

/**
 * 报表调度任务服务类
 *
 * @author Tom Deng
 */
public interface ITaskService extends ICrudService<Task, TaskExample> {
}
