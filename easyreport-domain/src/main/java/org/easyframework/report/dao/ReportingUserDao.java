package org.easyframework.report.dao;

import org.easyframework.report.data.jdbc.BaseDao;
import org.easyframework.report.entity.ReportingUser;
import org.springframework.stereotype.Repository;

/**
 * ReportingUserDao提供报表管理员信息表表(reporting_user)的相关数据访问操作的类。
 * 
 * @author Tom Deng
 */
@Repository
public class ReportingUserDao extends BaseDao<ReportingUser> {

	public ReportingUserDao() {
		super(ReportingUser.EntityName, ReportingUser.Id);
	}
}