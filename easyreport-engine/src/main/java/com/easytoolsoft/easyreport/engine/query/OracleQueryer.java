package com.easytoolsoft.easyreport.engine.query;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;
import com.easytoolsoft.easyreport.engine.util.JdbcUtils;

import java.sql.Connection;

public class OracleQueryer extends AbstractQueryer implements Queryer {

    public OracleQueryer(ReportDataSource dataSource, ReportParameter parameter) {
        super(dataSource, parameter);

    }

    @Override
    protected Connection getJdbcConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return JdbcUtils.getDataSource(this.dataSource).getConnection();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
