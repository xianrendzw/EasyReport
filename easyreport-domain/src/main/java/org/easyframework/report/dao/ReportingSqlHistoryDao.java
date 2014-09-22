package org.easyframework.report.dao;

import java.util.List;

import org.easyframework.report.data.PageInfo;
import org.easyframework.report.data.criterion.Restrictions;
import org.easyframework.report.data.jdbc.BaseDao;
import org.easyframework.report.entity.ReportingSqlHistory;
import org.springframework.stereotype.Repository;

/**
 * ReportingSqlHistoryDao提供表(reporting_sql_history)的相关数据访问操作的类。
 * 
 * @author Tom Deng
 */
@Repository
public class ReportingSqlHistoryDao extends BaseDao<ReportingSqlHistory> {

	public ReportingSqlHistoryDao() {
		super(ReportingSqlHistory.EntityName, ReportingSqlHistory.Id);
	}

	public List<ReportingSqlHistory> queryByPage(PageInfo page, int reportId) {
		String condition = Restrictions.equal(ReportingSqlHistory.ReportId, "?").toString();
		Object[] args = new Object[] { reportId };
		return this.query(condition, page, args);
	}
}