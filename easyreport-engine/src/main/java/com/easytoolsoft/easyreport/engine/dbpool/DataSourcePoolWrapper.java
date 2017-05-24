package com.easytoolsoft.easyreport.engine.dbpool;

import javax.sql.DataSource;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;

/**
 * 数据源连接包装器
 *
 * @author tomdeng
 */
public interface DataSourcePoolWrapper {
    DataSource wrap(ReportDataSource rptDs);
}
