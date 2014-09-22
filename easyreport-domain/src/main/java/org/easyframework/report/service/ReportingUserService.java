package org.easyframework.report.service;

import org.easyframework.report.dao.ReportingUserDao;
import org.easyframework.report.data.jdbc.BaseService;
import org.easyframework.report.entity.ReportingUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 报表管理员信息表服务类
 */
@Service
public class ReportingUserService extends BaseService<ReportingUserDao, ReportingUser> {
	@Autowired
	public ReportingUserService(ReportingUserDao dao) {
		super(dao);
	}
}