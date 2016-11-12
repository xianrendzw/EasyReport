package com.easytoolsoft.easyreport.domain.metadata.dao;

import com.easytoolsoft.easyreport.data.dao.ICrudDao;
import com.easytoolsoft.easyreport.domain.metadata.example.ReportHistoryExample;
import com.easytoolsoft.easyreport.domain.metadata.po.ReportHistory;
import org.springframework.stereotype.Repository;

/**
 * 报表历史记录(ezrpt_meta_report_history表)数据访问类
 *
 * @author Tom Deng
 */
@Repository("EzrptMetaIReportHistoryDao")
public interface IReportHistoryDao extends ICrudDao<ReportHistory, ReportHistoryExample> {
}
