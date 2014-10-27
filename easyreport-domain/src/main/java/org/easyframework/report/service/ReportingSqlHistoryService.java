package org.easyframework.report.service;

import java.util.List;

import org.easyframework.report.dao.ReportingSqlHistoryDao;
import org.easyframework.report.data.PageInfo;
import org.easyframework.report.data.jdbc.BaseService;
import org.easyframework.report.po.ReportingSqlHistoryPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务类
 */
@Service
public class ReportingSqlHistoryService extends BaseService<ReportingSqlHistoryDao, ReportingSqlHistoryPo> {
	@Autowired
	public ReportingSqlHistoryService(ReportingSqlHistoryDao dao) {
		super(dao);
	}

	public List<ReportingSqlHistoryPo> getByPage(PageInfo page, int reportId) {
		return this.dao.queryByPage(page, reportId);
	}
}