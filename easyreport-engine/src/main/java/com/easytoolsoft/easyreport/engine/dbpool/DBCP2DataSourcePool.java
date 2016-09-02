package com.easytoolsoft.easyreport.engine.dbpool;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

/**
 * c3p0数据源连接池包装类
 * <a href="http://www.mchange.com/projects/c3p0/#quickstart>c3po</a>
 */
public class DBCP2DataSourcePool  implements DataSourcePoolWrapper{
    @Override
    public DataSource wrap(ReportDataSource rptDs) {
        try {
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(rptDs.getDriverClass());
            dataSource.setUrl(rptDs.getJdbcUrl());
            dataSource.setUsername(rptDs.getUser());
            dataSource.setPassword(rptDs.getPassword());
            return dataSource;
        } catch (Exception ex) {
            throw new RuntimeException("C3p0DataSourcePool Create Error", ex);
        }
    }
}
