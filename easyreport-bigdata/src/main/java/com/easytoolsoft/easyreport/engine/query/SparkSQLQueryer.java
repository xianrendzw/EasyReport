package com.easytoolsoft.easyreport.engine.query;

import java.sql.Connection;

import org.apache.commons.lang.NotImplementedException;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;

/*
 * <see>http://spark.apache.org/sql/
 */
public class SparkSQLQueryer extends AbstractQueryer implements Queryer {
	public SparkSQLQueryer(ReportDataSource dataSource, ReportParameter parameter) {
		super(dataSource, parameter);

	}

	@Override
	protected Connection getJdbcConnection() {
		throw new NotImplementedException();
	}
}
