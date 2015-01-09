package org.easyframework.report.engine.query;

import java.sql.Connection;
import java.sql.DriverManager;

import org.easyframework.report.engine.data.ReportDataSource;
import org.easyframework.report.engine.data.ReportParameter;

public class HiveQueryer extends AbstractQueryer implements Queryer {

	public HiveQueryer(ReportDataSource dataSource, ReportParameter parameter) {
		super(dataSource, parameter);
	}

	@Override
	protected Connection getConnection() {
		try {
			Class.forName("org.apache.hive.jdbc.HiveDriver");
			return DriverManager.getConnection(this.dataSource.getJdbcUrl(), this.dataSource.getUser(), this.dataSource.getPassword());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
