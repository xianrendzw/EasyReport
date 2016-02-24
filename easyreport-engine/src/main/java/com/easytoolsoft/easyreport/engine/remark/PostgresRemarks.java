package com.easytoolsoft.easyreport.engine.remark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class PostgresRemarks implements IRemarks {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostgresRemarks.class);

    // SELECT description FROM pg_description d INNER JOIN pg_class C ON C .oid = d.objoid WHERE
    // relname = 'tablename'
    @Override
    public String getTableRemark(Connection conn, String tableName) {
        Statement st = null;
        ResultSet rs = null;
        String result = "";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(getRemarkSql(tableName));
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            close(st, rs);
        }
        return result;
    }

    private String getRemarkSql(String tableName) {
        return "SELECT description FROM pg_description d INNER JOIN pg_class C ON C .oid = d.objoid WHERE relname='"
                + tableName + "'";
    }

    private void close(Statement st, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public String getColumnRemark(Connection conn, String tableSchema, String tableName, String columnName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, String> getColumnRemarks(Connection conn, String tableSchema, String tableName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, String> getColumnRemarksBySql(Connection conn, String sqlText) {
        return null;
    }

}
