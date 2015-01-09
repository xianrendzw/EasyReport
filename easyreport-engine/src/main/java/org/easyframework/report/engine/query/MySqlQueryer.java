package org.easyframework.report.engine.query;

import java.sql.Connection;
import java.sql.DriverManager;

import org.easyframework.report.engine.data.ReportDataSource;
import org.easyframework.report.engine.data.ReportParameter;

public class MySqlQueryer extends AbstractQueryer implements Queryer {

	public MySqlQueryer(ReportDataSource dataSource, ReportParameter parameter) {
		super(dataSource, parameter);
	}

	@Override
	protected Connection getConnection() {
		try {
			return DriverManager.getConnection(this.dataSource.getJdbcUrl(), this.dataSource.getUser(), this.dataSource.getPassword());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
