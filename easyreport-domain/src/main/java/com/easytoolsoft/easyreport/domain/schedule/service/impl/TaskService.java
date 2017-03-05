package com.easytoolsoft.easyreport.domain.schedule.service.impl;

import com.easytoolsoft.easyreport.data.service.AbstractCrudService;
import com.easytoolsoft.easyreport.domain.schedule.dao.ITaskDao;
import com.easytoolsoft.easyreport.domain.schedule.example.TaskExample;
import com.easytoolsoft.easyreport.domain.schedule.po.Task;
import com.easytoolsoft.easyreport.domain.schedule.service.ITaskService;
import org.springframework.stereotype.Service;

/**
 * @author tomdeng
 */
@Service("EzrptScheduleTaskService")
public class TaskService
        extends AbstractCrudService<ITaskDao, Task, TaskExample>
        implements ITaskService {
    @Override
    protected TaskExample getPageExample(String fieldName, String keyword) {
        TaskExample example = new TaskExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}