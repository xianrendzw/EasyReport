package com.easytoolsoft.easyreport.meta.service.impl;

import com.easytoolsoft.easyreport.meta.data.TaskRepository;
import com.easytoolsoft.easyreport.meta.domain.Task;
import com.easytoolsoft.easyreport.meta.domain.example.TaskExample;
import com.easytoolsoft.easyreport.meta.service.TaskService;
import com.easytoolsoft.easyreport.mybatis.service.AbstractCrudService;
import org.springframework.stereotype.Service;

/**
 * @author Tom Deng
 * @date 2017-03-25
 */
@Service("TaskService")
public class TaskServiceImpl
    extends AbstractCrudService<TaskRepository, Task, TaskExample, Integer>
    implements TaskService {
    @Override
    protected TaskExample getPageExample(final String fieldName, final String keyword) {
        final TaskExample example = new TaskExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}