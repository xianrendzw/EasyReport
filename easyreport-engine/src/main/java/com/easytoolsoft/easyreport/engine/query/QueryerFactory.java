package com.easytoolsoft.easyreport.engine.query;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;
import org.apache.commons.lang3.StringUtils;

/**
 * 报表查询器工厂方法类
 */
public class QueryerFactory {
    public static Queryer create(ReportDataSource dataSource) {
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
            if (StringUtils.containsIgnoreCase(dataSource.getJdbcUrl(), "jdbc:postgresql")) {
                return new PostgresqlQueryer(dataSource, parameter);
            }
        }
        return null;
    }
}
