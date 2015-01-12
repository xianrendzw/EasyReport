package org.easyframework.report.engine.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.easyframework.report.engine.data.ReportDataSource;
import org.easyframework.report.engine.data.ReportParameter;

public class MySqlQueryer extends AbstractQueryer implements Queryer {

	public MySqlQueryer(ReportDataSource dataSource, ReportParameter parameter) {
		super(dataSource, parameter);
	}

	@Override
	public Connection getJdbcConnection() {
		try {
			return DriverManager.getConnection(this.dataSource.getJdbcUrl(), this.dataSource.getUser(), this.dataSource.getPassword());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	protected String preprocessSqlText(String sqlText) {
		sqlText = StringUtils.stripEnd(sqlText.trim(), ";");
		Pattern pattern = Pattern.compile("limit.*?$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(sqlText);
		if (matcher.find()) {
			sqlText = matcher.replaceFirst("");
		}
		return sqlText + " limit 1";
	}
}
