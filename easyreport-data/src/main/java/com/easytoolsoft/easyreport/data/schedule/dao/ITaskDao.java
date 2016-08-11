package com.easytoolsoft.easyreport.data.schedule.dao;

import com.easytoolsoft.easyreport.data.common.dao.ICrudDao;
import com.easytoolsoft.easyreport.data.schedule.example.TaskExample;
import com.easytoolsoft.easyreport.data.schedule.po.Task;
import org.springframework.stereotype.Repository;

/**
 * 报表任务(ezrpt_schedule_task表)数据访问类
 *
 * @author Tom Deng
 */
@Repository("EzrptScheduleITaskDao")
public interface ITaskDao extends ICrudDao<Task, TaskExample> {
}