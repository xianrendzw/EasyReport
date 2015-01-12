package org.easyframework.report.engine.query;

import org.apache.commons.lang3.StringUtils;
import org.easyframework.report.engine.data.ReportDataSource;
import org.easyframework.report.engine.data.ReportParameter;

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
			if (StringUtils.containsIgnoreCase(dataSource.getJdbcUrl(), "jdbc:hive")) {
				return new HiveQueryer(dataSource, parameter);
			}
			if (StringUtils.containsIgnoreCase(dataSource.getJdbcUrl(), "jdbc:phoenix")) {
				return new HBaseQueryer(dataSource, parameter);
			}
		}
		return null;
	}
}
