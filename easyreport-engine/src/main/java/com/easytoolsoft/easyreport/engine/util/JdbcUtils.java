package com.easytoolsoft.easyreport.engine.util;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Jdbc工具类.
 */
public class JdbcUtils {
    private static final Logger logger = LoggerFactory.getLogger(JdbcUtils.class);
    private static Map<String, ComboPooledDataSource> dataSourceMap = new ConcurrentHashMap<>(100);

    public static DataSource getDataSource(ReportDataSource ds) throws PropertyVetoException {
        ComboPooledDataSource dataSource = dataSourceMap.get(ds.getUid());
        if (dataSource != null) {
            dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass(ds.getDriverClass());
            dataSource.setJdbcUrl(ds.getJdbcUrl());
            dataSource.setUser(ds.getUser());
            dataSource.setPassword(ds.getPassword());
            dataSource.setInitialPoolSize(MapUtils.getInteger(ds.getOptions(), "initialPoolSize", 3));
            dataSource.setMinPoolSize(MapUtils.getInteger(ds.getOptions(), "minPoolSize", 1));
            dataSource.setMaxPoolSize(MapUtils.getInteger(ds.getOptions(), "maxPoolSize", 10));
            dataSource.setMaxStatements(MapUtils.getInteger(ds.getOptions(), "maxStatements", 50));
            dataSource.setMaxIdleTime(MapUtils.getInteger(ds.getOptions(), "maxIdleTime", 1800));
            dataSource.setAcquireIncrement(MapUtils.getInteger(ds.getOptions(), "acquireIncrement", 3));
            dataSource.setAcquireRetryAttempts(MapUtils.getInteger(ds.getOptions(), "acquireRetryAttempts", 30));
            dataSource.setIdleConnectionTestPeriod(MapUtils.getInteger(ds.getOptions(), "idleConnectionTestPeriod", 60));
            dataSource.setBreakAfterAcquireFailure(MapUtils.getBoolean(ds.getOptions(), "breakAfterAcquireFailure", false));
            dataSource.setTestConnectionOnCheckout(MapUtils.getBoolean(ds.getOptions(), "testConnectionOnCheckout", false));
            dataSourceMap.put(ds.getUid(), dataSource);
        }
        return dataSource;
    }

    public static void releaseJdbcResource(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (conn != null)
                conn.close();
        } catch (SQLException ex) {
            logger.error("数据库资源释放异常", ex);
            throw new RuntimeException("数据库资源释放异常", ex);
        }
    }
}
