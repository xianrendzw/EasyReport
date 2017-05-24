package com.easytoolsoft.easyreport.meta.service;

import com.easytoolsoft.easyreport.meta.domain.Task;
import com.easytoolsoft.easyreport.meta.domain.example.TaskExample;
import com.easytoolsoft.easyreport.mybatis.service.CrudService;

/**
 * 报表调度任务服务类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface TaskService extends CrudService<Task, TaskExample, Integer> {
}
