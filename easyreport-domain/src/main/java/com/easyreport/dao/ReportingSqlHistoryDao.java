package com.easyreport.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.easyreport.data.PageInfo;
import com.easyreport.data.criterion.Restrictions;
import com.easyreport.data.jdbc.BaseDao;
import com.easyreport.po.ReportingSqlHistoryPo;

/**
 * ReportingSqlHistoryDao提供表(reporting_sql_history)的相关数据访问操作的类。
 * 
 * @author Tom Deng
 */
@Repository
public class ReportingSqlHistoryDao extends BaseDao<ReportingSqlHistoryPo> {

	public ReportingSqlHistoryDao() {
		super(ReportingSqlHistoryPo.EntityName, ReportingSqlHistoryPo.Id);
	}

	public List<ReportingSqlHistoryPo> queryByPage(PageInfo page, int reportId) {
		String condition = Restrictions.equal(ReportingSqlHistoryPo.ReportId, "?").toString();
		Object[] args = new Object[] { reportId };
		return this.query(condition, page, args);
	}
}