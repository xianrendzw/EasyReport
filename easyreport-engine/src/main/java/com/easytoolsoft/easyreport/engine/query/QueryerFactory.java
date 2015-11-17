package com.easytoolsoft.easyreport.engine.query;

import java.lang.reflect.Constructor;

import org.apache.commons.lang3.StringUtils;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;

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

			Class<?> clazz = null;
			String packageName = "com.easytoolsoft.easyreport.engine.query.";
			try {
				if (StringUtils.containsIgnoreCase(dataSource.getJdbcUrl(), "jdbc:oracle")) {
					clazz = Class.forName(packageName + "OracleDriver");
				}
				if (StringUtils.containsIgnoreCase(dataSource.getJdbcUrl(), "jdbc:sqlserver")) {
					clazz = Class.forName(packageName + "SqlServerQueryer");
				}
				if (StringUtils.containsIgnoreCase(dataSource.getJdbcUrl(), "jdbc:sqlite")) {
					clazz = Class.forName(packageName + "SQLiteQueryer");
				}
				if (StringUtils.containsIgnoreCase(dataSource.getJdbcUrl(), "jdbc:phoenix")) {
					clazz = Class.forName(packageName + "HBaseQueryer");
				}
				if (StringUtils.containsIgnoreCase(dataSource.getJdbcUrl(), "jdbc:hive2")) {
					clazz = Class.forName(packageName + "HiveQueryer");
				}
				if (StringUtils.containsIgnoreCase(dataSource.getJdbcUrl(), "jdbc:presto")) {
					clazz = Class.forName(packageName + "PrestoQueryer");
				}
				if (clazz != null) {
					Constructor<?> constructor = clazz.getConstructor(ReportDataSource.class, ReportParameter.class);
					return (Queryer) constructor.newInstance(dataSource, parameter);
				}
			} catch (Exception ex) {
				throw new RuntimeException("Create Queryer Exception", ex);
			}
		}
		return null;
	}
}
