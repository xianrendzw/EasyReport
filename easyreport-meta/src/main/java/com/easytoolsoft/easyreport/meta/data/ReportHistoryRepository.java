package com.easytoolsoft.easyreport.meta.data;

import com.easytoolsoft.easyreport.meta.domain.ReportHistory;
import com.easytoolsoft.easyreport.meta.domain.example.ReportHistoryExample;
import com.easytoolsoft.easyreport.mybatis.data.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 报表历史记录(_rpt_report_history表)数据访问类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Repository("ReportHistoryRepository")
public interface ReportHistoryRepository extends CrudRepository<ReportHistory, ReportHistoryExample, Integer> {
}
