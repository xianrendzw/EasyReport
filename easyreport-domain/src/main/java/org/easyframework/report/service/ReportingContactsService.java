package org.easyframework.report.service;

import org.easyframework.report.dao.ReportingContactsDao;
import org.easyframework.report.data.jdbc.BaseService;
import org.easyframework.report.entity.ReportingContacts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 报表与联系人关系表服务类
 */
@Service
public class ReportingContactsService extends BaseService<ReportingContactsDao, ReportingContacts> {
	@Autowired
	public ReportingContactsService(ReportingContactsDao dao) {
		super(dao);
	}
}