package com.easyreport.engine.query;

import java.sql.Connection;
import java.sql.DriverManager;

import com.easyreport.engine.data.ReportDataSource;
import com.easyreport.engine.data.ReportParameter;

public class SqlServerQueryer extends AbstractQueryer implements Queryer {

	public SqlServerQueryer(ReportDataSource dataSource, ReportParameter parameter) {
		super(dataSource, parameter);

	}

	@Override
	protected Connection getJdbcConnection() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			return DriverManager.getConnection(this.dataSource.getJdbcUrl(), this.dataSource.getUser(), this.dataSource.getPassword());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
