package com.easytoolsoft.easyreport.engine.util;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.dbpool.DataSourcePoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
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
    private static Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>(100);

    public static DataSource getDataSource(ReportDataSource rptDs) {
        //用数据源用户名,密码,jdbcUrl做为key
        String key = String.format("%s|%s|%s", rptDs.getUser(), rptDs.getPassword(), rptDs.getJdbcUrl()).toLowerCase();
        DataSource dataSource = dataSourceMap.get(key);
        if (dataSource == null) {
            dataSource = DataSourcePoolFactory.create(rptDs.getDbPoolClass()).wrap(rptDs);
            dataSourceMap.put(key, dataSource);
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
