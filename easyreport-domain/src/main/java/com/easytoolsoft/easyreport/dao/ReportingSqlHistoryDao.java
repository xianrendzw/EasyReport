package com.easytoolsoft.easyreport.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.easytoolsoft.easyreport.data.PageInfo;
import com.easytoolsoft.easyreport.data.criterion.Restrictions;
import com.easytoolsoft.easyreport.data.jdbc.BaseDao;
import com.easytoolsoft.easyreport.po.ReportingSqlHistoryPo;

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