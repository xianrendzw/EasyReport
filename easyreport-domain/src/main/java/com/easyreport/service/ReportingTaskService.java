package com.easyreport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyreport.dao.ReportingTaskDao;
import com.easyreport.data.jdbc.BaseService;
import com.easyreport.po.ReportingTaskPo;

/**
 * 报表任务信息表服务类
 */
@Service
public class ReportingTaskService extends BaseService<ReportingTaskDao, ReportingTaskPo> {
	@Autowired
	public ReportingTaskService(ReportingTaskDao dao) {
		super(dao);
	}
}