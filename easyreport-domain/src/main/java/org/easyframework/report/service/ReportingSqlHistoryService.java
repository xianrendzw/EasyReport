package org.easyframework.report.service;

import org.easyframework.report.dao.ReportingSqlHistoryDao;
import org.easyframework.report.data.jdbc.BaseService;
import org.easyframework.report.entity.ReportingSqlHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务类
 */
@Service
public class ReportingSqlHistoryService extends BaseService<ReportingSqlHistoryDao, ReportingSqlHistory> {
	@Autowired
	public ReportingSqlHistoryService(ReportingSqlHistoryDao dao) {
		super(dao);
	}
}