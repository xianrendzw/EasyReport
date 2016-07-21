package com.easytoolsoft.easyreport.schedule.service.impl;

import com.easytoolsoft.easyreport.data.service.AbstractCrudService;
import com.easytoolsoft.easyreport.schedule.dao.ITaskDao;
import com.easytoolsoft.easyreport.schedule.po.Task;
import com.easytoolsoft.easyreport.schedule.service.ITaskService;
import org.springframework.stereotype.Service;

@Service("EzrptScheduleTaskService")
public class TaskService extends AbstractCrudService<ITaskDao, Task> implements ITaskService {
}