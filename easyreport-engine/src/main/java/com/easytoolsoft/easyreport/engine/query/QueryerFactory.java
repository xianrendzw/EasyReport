package com.easytoolsoft.easyreport.engine.query;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;
import org.apache.commons.lang3.StringUtils;

public class QueryerFactory {
    public static Queryer create(String jdbcUrl, String user, String password) {
        ReportDataSource dataSource = new ReportDataSource(jdbcUrl, user, password);
        return create(dataSource, null);
    }

    public static Queryer create(ReportDataSource dataSource, ReportParameter parameter) {
        if (dataSource != null) {
            if (StringUtils.containsIgnoreCase(dataSource.getJdbcUrl(), "jdbc:mysql")) {
                return new MySqlQueryer(dataSource, parameter);
            }
            if (StringUtils.containsIgnoreCase(dataSource.getJdbcUrl(), "jdbc:oracle")) {
                return new OracleQueryer(dataSource, parameter);
            }
            if (StringUtils.containsIgnoreCase(dataSource.getJdbcUrl(), "jdbc:sqlserver")) {
                return new SqlServerQueryer(dataSource, parameter);
            }
            if (StringUtils.containsIgnoreCase(dataSource.getJdbcUrl(), "jdbc:sqlite")) {
                return new SQLiteQueryer(dataSource, parameter);
            }
            if (StringUtils.containsIgnoreCase(dataSource.getJdbcUrl(), "jdbc:phoenix")) {
                return new HBaseQueryer(dataSource, parameter);
            }
        }
        return null;
    }
}
