package com.easytoolsoft.easyreport.engine.dbpool;

import javax.sql.DataSource;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.collections4.MapUtils;

/**
 * c3p0数据源连接池包装类
 * <a href="http://www.mchange.com/projects/c3p0/#quickstart>c3po</a>
 *
 * @author tomdeng
 */
public class C3p0DataSourcePool implements DataSourcePoolWrapper {
    @Override
    public DataSource wrap(ReportDataSource rptDs) {
        try {
            ComboPooledDataSource dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass(rptDs.getDriverClass());
            dataSource.setJdbcUrl(rptDs.getJdbcUrl());
            dataSource.setUser(rptDs.getUser());
            dataSource.setPassword(rptDs.getPassword());
            dataSource.setInitialPoolSize(MapUtils.getInteger(rptDs.getOptions(), "initialPoolSize", 3));
            dataSource.setMinPoolSize(MapUtils.getInteger(rptDs.getOptions(), "minPoolSize", 1));
            dataSource.setMaxPoolSize(MapUtils.getInteger(rptDs.getOptions(), "maxPoolSize", 20));
            dataSource.setMaxStatements(MapUtils.getInteger(rptDs.getOptions(), "maxStatements", 50));
            dataSource.setMaxIdleTime(MapUtils.getInteger(rptDs.getOptions(), "maxIdleTime", 1800));
            dataSource.setAcquireIncrement(MapUtils.getInteger(rptDs.getOptions(), "acquireIncrement", 3));
            dataSource.setAcquireRetryAttempts(MapUtils.getInteger(rptDs.getOptions(), "acquireRetryAttempts", 30));
            dataSource.setIdleConnectionTestPeriod(
                MapUtils.getInteger(rptDs.getOptions(), "idleConnectionTestPeriod", 60));
            dataSource.setBreakAfterAcquireFailure(
                MapUtils.getBoolean(rptDs.getOptions(), "breakAfterAcquireFailure", false));
            dataSource.setTestConnectionOnCheckout(
                MapUtils.getBoolean(rptDs.getOptions(), "testConnectionOnCheckout", false));
            return dataSource;
        } catch (Exception ex) {
            throw new RuntimeException("C3p0DataSourcePool Create Error", ex);
        }
    }
}
