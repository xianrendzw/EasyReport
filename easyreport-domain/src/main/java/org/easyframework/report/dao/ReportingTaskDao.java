package org.easyframework.report.dao;

import org.easyframework.report.data.jdbc.BaseDao;
import org.easyframework.report.entity.ReportingTask;
import org.springframework.stereotype.Repository;

/**
 * ReportingTaskDao提供报表任务信息表表(reporting_task)的相关数据访问操作的类。
 * 
 * @author Tom Deng
 */
@Repository
public class ReportingTaskDao extends BaseDao<ReportingTask> {

	public ReportingTaskDao() {
		super(ReportingTask.EntityName, ReportingTask.Id);
	}
}