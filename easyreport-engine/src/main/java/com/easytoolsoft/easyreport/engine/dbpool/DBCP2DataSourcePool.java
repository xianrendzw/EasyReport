package com.easytoolsoft.easyreport.engine.dbpool;

import javax.sql.DataSource;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * c3p0数据源连接池包装类
 * <a href="http://www.mchange.com/projects/c3p0/#quickstart>c3po</a>
 *
 * @author tomdeng
 */
public class DBCP2DataSourcePool implements DataSourcePoolWrapper {
    @Override
    public DataSource wrap(final ReportDataSource rptDs) {
        try {
            final BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(rptDs.getDriverClass());
            dataSource.setUrl(rptDs.getJdbcUrl());
            dataSource.setUsername(rptDs.getUser());
            dataSource.setPassword(rptDs.getPassword());
            dataSource.setInitialSize(MapUtils.getInteger(rptDs.getOptions(), "initialSize", 3));
            dataSource.setMaxIdle(MapUtils.getInteger(rptDs.getOptions(), "maxIdle", 20));
            dataSource.setMinIdle(MapUtils.getInteger(rptDs.getOptions(), "minIdle", 1));
            dataSource.setLogAbandoned(MapUtils.getBoolean(rptDs.getOptions(), "logAbandoned", true));
            dataSource.setRemoveAbandonedTimeout(
                MapUtils.getInteger(rptDs.getOptions(), "removeAbandonedTimeout", 180));
            dataSource.setMaxWaitMillis(MapUtils.getInteger(rptDs.getOptions(), "maxWait", 1000));
            return dataSource;
        } catch (final Exception ex) {
            throw new RuntimeException("C3p0DataSourcePool Create Error", ex);
        }
    }
}
