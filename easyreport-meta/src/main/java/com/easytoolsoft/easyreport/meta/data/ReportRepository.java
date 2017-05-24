package com.easytoolsoft.easyreport.meta.data;

import com.easytoolsoft.easyreport.meta.domain.Report;
import com.easytoolsoft.easyreport.meta.domain.example.ReportExample;
import com.easytoolsoft.easyreport.mybatis.data.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 报表(_rpt_report表)数据访问类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Repository("ReportRepository")
public interface ReportRepository extends CrudRepository<Report, ReportExample, Integer> {
}
