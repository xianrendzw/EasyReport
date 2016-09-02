package com.easytoolsoft.easyreport.engine.dbpool;


import com.alibaba.druid.pool.DruidDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import org.apache.commons.collections4.MapUtils;

import javax.sql.DataSource;

/**
 * Druid数据源连接池包装类
 * <a href="https://github.com/alibaba/druid/wiki>Druid</a>
 */
public class DruidDataSourcePool implements DataSourcePoolWrapper {
    @Override
    public DataSource wrap(ReportDataSource rptDs) {
        try {
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setDriverClassName(rptDs.getDriverClass());
            dataSource.setUrl(rptDs.getJdbcUrl());
            dataSource.setUsername(rptDs.getUser());
            dataSource.setPassword(rptDs.getPassword());
            dataSource.setBreakAfterAcquireFailure(MapUtils.getBoolean(rptDs.getOptions(), "breakAfterAcquireFailure", false));
            return dataSource;
        } catch (Exception ex) {
            throw new RuntimeException("C3p0DataSourcePool Create Error", ex);
        }
    }
}
