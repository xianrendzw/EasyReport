package com.easyreport.engine.query;

import java.sql.Connection;

import org.apache.commons.lang.NotImplementedException;

import com.easyreport.engine.data.ReportDataSource;
import com.easyreport.engine.data.ReportParameter;

public class HBaseQueryer extends AbstractQueryer implements Queryer {

	public HBaseQueryer(ReportDataSource dataSource, ReportParameter parameter) {
		super(dataSource, parameter);

	}

	@Override
	protected Connection getJdbcConnection() {
		// <see>http://phoenix.apache.org/
		throw new NotImplementedException();
	}
}
