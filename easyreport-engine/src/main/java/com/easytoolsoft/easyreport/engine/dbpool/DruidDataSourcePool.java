package com.easytoolsoft.easyreport.engine.dbpool;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import org.apache.commons.collections4.MapUtils;

/**
 * Druid数据源连接池包装类
 * <a href="https://github.com/alibaba/druid/wiki>Druid</a>
 *
 * @author tomdeng
 */
public class DruidDataSourcePool implements DataSourcePoolWrapper {
    @Override
    public DataSource wrap(final ReportDataSource rptDs) {
        try {
            final DruidDataSource dataSource = new DruidDataSource();
            dataSource.setDriverClassName(rptDs.getDriverClass());
            dataSource.setUrl(rptDs.getJdbcUrl());
            dataSource.setUsername(rptDs.getUser());
            dataSource.setPassword(rptDs.getPassword());
            dataSource.setInitialSize(MapUtils.getInteger(rptDs.getOptions(), "initialSize", 3));
            dataSource.setMaxActive(MapUtils.getInteger(rptDs.getOptions(), "maxActive", 20));
            dataSource.setMinIdle(MapUtils.getInteger(rptDs.getOptions(), "minIdle", 1));
            dataSource.setMaxWait(MapUtils.getInteger(rptDs.getOptions(), "maxWait", 60000));
            dataSource.setTimeBetweenEvictionRunsMillis(
                MapUtils.getInteger(rptDs.getOptions(), "timeBetweenEvictionRunsMillis", 60000));
            dataSource.setMinEvictableIdleTimeMillis(
                MapUtils.getInteger(rptDs.getOptions(), "minEvictableIdleTimeMillis", 300000));
            dataSource.setTestWhileIdle(MapUtils.getBoolean(rptDs.getOptions(), "testWhileIdle", true));
            dataSource.setTestOnBorrow(MapUtils.getBoolean(rptDs.getOptions(), "testOnBorrow", false));
            dataSource.setTestOnReturn(MapUtils.getBoolean(rptDs.getOptions(), "testOnReturn", false));
            dataSource.setMaxOpenPreparedStatements(
                MapUtils.getInteger(rptDs.getOptions(), "maxOpenPreparedStatements", 20));
            dataSource.setRemoveAbandoned(MapUtils.getBoolean(rptDs.getOptions(), "removeAbandoned", true));
            dataSource.setRemoveAbandonedTimeout(
                MapUtils.getInteger(rptDs.getOptions(), "removeAbandonedTimeout", 1800));
            dataSource.setLogAbandoned(MapUtils.getBoolean(rptDs.getOptions(), "logAbandoned", true));
            return dataSource;
        } catch (final Exception ex) {
            throw new RuntimeException("C3p0DataSourcePool Create Error", ex);
        }
    }
}
