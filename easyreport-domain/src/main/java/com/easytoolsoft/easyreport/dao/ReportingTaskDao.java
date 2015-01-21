package com.easytoolsoft.easyreport.dao;

import org.springframework.stereotype.Repository;

import com.easytoolsoft.easyreport.data.jdbc.BaseDao;
import com.easytoolsoft.easyreport.po.ReportingTaskPo;

/**
 * ReportingTaskDao提供报表任务信息表表(reporting_task)的相关数据访问操作的类。
 * 
 * @author Tom Deng
 */
@Repository
public class ReportingTaskDao extends BaseDao<ReportingTaskPo> {

	public ReportingTaskDao() {
		super(ReportingTaskPo.EntityName, ReportingTaskPo.Id);
	}
}