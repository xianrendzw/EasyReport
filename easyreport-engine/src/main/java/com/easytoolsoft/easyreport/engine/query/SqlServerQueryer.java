package com.easytoolsoft.easyreport.engine.query;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;
import com.easytoolsoft.easyreport.engine.util.JdbcUtils;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqlServerQueryer extends AbstractQueryer implements Queryer {

    public SqlServerQueryer(ReportDataSource dataSource, ReportParameter parameter) {
        super(dataSource, parameter);

    }

    @Override
    protected Connection getJdbcConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return JdbcUtils.getDataSource(this.dataSource).getConnection();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
