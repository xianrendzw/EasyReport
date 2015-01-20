package com.easyreport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyreport.dao.ReportingSqlHistoryDao;
import com.easyreport.data.PageInfo;
import com.easyreport.data.jdbc.BaseService;
import com.easyreport.po.ReportingSqlHistoryPo;

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