package org.easyframework.report.engine.query;

import java.sql.Connection;

import org.apache.commons.lang.NotImplementedException;
import org.easyframework.report.engine.data.ReportDataSource;
import org.easyframework.report.engine.data.ReportParameter;

public class HBaseQueryer extends AbstractQueryer implements Queryer {

	public HBaseQueryer(ReportDataSource dataSource, ReportParameter parameter) {
		super(dataSource, parameter);

	}

	@Override
	public Connection getJdbcConnection() {
		throw new NotImplementedException();
	}
}
