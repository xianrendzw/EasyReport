package com.easytoolsoft.easyreport.engine.query;

import java.sql.Connection;
import java.sql.DriverManager;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;

public class OracleQueryer extends AbstractQueryer implements Queryer {

	public OracleQueryer(ReportDataSource dataSource, ReportParameter parameter) {
		super(dataSource, parameter);

	}

	@Override
	protected Connection getJdbcConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			return DriverManager.getConnection(this.dataSource.getJdbcUrl(), this.dataSource.getUser(), this.dataSource.getPassword());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
