package com.easytoolsoft.easyreport.meta.data;

import com.easytoolsoft.easyreport.meta.domain.Task;
import com.easytoolsoft.easyreport.meta.domain.example.TaskExample;
import com.easytoolsoft.easyreport.mybatis.data.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 报表任务(_rpt_task表)数据访问类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Repository("TaskRepository")
public interface TaskRepository extends CrudRepository<Task, TaskExample, Integer> {
}