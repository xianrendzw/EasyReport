package com.easytoolsoft.easyreport.data.metadata.dao;

import com.easytoolsoft.easyreport.data.common.dao.ICrudDao;
import com.easytoolsoft.easyreport.data.metadata.example.ReportExample;
import com.easytoolsoft.easyreport.data.metadata.po.Report;
import org.springframework.stereotype.Repository;

/**
 * 报表(ezrpt_meta_report表)数据访问类
 *
 * @author Tom Deng
 */
@Repository("EzrptMetaIReportDao")
public interface IReportDao extends ICrudDao<Report, ReportExample> {
}
