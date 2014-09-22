package org.easyframework.report.dao;

import org.easyframework.report.data.jdbc.BaseDao;
import org.easyframework.report.entity.ReportingContacts;
import org.springframework.stereotype.Repository;

/**
 * ReportingContactsDao提供报表与联系人关系表表(reporting_contacts)的相关数据访问操作的类。
 * 
 * @author Tom Deng
 */
@Repository
public class ReportingContactsDao extends BaseDao<ReportingContacts> {

	public ReportingContactsDao() {
		super(ReportingContacts.EntityName, ReportingContacts.Id);
	}
}