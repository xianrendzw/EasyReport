package com.easytoolsoft.easyreport.engine.query;

import java.sql.Connection;
import java.sql.DriverManager;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;

/**
 * <see>http://phoenix.apache.org/ support hbase1.0 or later
 * <see>http://phoenix.apache.org/installation.html
 *
 */
public class HBaseQueryer extends AbstractQueryer implements Queryer {

	public HBaseQueryer(ReportDataSource dataSource, ReportParameter parameter) {
		super(dataSource, parameter);

	}

	@Override
	protected Connection getJdbcConnection() {
		try {
			// Connection conn =
			// DriverManager.getConnection("jdbc:phoenix:server1,server2:3333");
			return DriverManager.getConnection(this.dataSource.getJdbcUrl(), this.dataSource.getUser(),
					this.dataSource.getPassword());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
