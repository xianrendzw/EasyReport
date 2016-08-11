package com.easytoolsoft.easyreport.schedule.service.impl;

import com.easytoolsoft.easyreport.data.common.service.AbstractCrudService;
import com.easytoolsoft.easyreport.data.schedule.dao.ITaskDao;
import com.easytoolsoft.easyreport.data.schedule.example.TaskExample;
import com.easytoolsoft.easyreport.data.schedule.po.Task;
import com.easytoolsoft.easyreport.schedule.service.ITaskService;
import org.springframework.stereotype.Service;

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