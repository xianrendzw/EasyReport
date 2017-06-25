package com.easytoolsoft.easyreport.engine.query;

import java.sql.Connection;
import java.sql.DriverManager;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;

/**
 * @author tomdeng
 * @see https://prestodb.io/docs/current/installation/jdbc.html
 */
public class PrestoQueryer extends AbstractQueryer implements Queryer {
    public PrestoQueryer(ReportDataSource dataSource, ReportParameter parameter) {
        super(dataSource, parameter);

    }

    @Override
    protected Connection getJdbcConnection() {
        try {
            Class.forName(this.dataSource.getDriverClass());
            return DriverManager.getConnection(this.dataSource.getJdbcUrl(), this.dataSource.getUser(),
                this.dataSource.getPassword());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}