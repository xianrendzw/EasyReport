package com.easytoolsoft.easyreport.engine.query;

import java.sql.Connection;
import java.sql.DriverManager;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;

public class SQLiteQueryer extends AbstractQueryer implements Queryer {

	public SQLiteQueryer(ReportDataSource dataSource, ReportParameter parameter) {
		super(dataSource, parameter);

	}

	@Override
	protected Connection getJdbcConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			return DriverManager.getConnection(this.dataSource.getJdbcUrl(), this.dataSource.getUser(), this.dataSource.getPassword());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
