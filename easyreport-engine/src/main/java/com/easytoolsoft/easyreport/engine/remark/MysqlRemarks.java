package com.easytoolsoft.easyreport.engine.remark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MysqlRemarks implements IRemarks {

    private static final Logger logger = LoggerFactory.getLogger(MysqlRemarks.class);

    @Override
    public String getTableRemark(Connection conn, String tableName) {
        String sql = "SELECT TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME='"
                + tableName + "'";
        Statement st = null;
        ResultSet rs = null;
        String result = "";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            close(st, rs);
        }
        return result;
    }


    @Override
    public String getColumnRemark(Connection conn, String tableSchema, String tableName, String columnName) {
        Map<String, String> result = getColumnRemarks(conn, tableSchema, tableName);
        return result.get(columnName);
    }

    @Override
    public Map<String, String> getColumnRemarks(Connection conn, String tableSchema, String tableName) {
        String sql = getRemarkSql(tableSchema, tableName);
        Statement st = null;
        ResultSet rs = null;
        Map<String, String> result = new HashMap<>();
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                result.put(rs.getString(1), rs.getString(2));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            close(st, rs);
        }
        return result;
    }

    private String getRemarkSql(String tableSchema, String tableName) {
        return "SELECT COLUMN_NAME,COLUMN_COMMENT FROM information_schema.COLUMNS WHERE TABLE_SCHEMA='"
                + tableSchema + "' AND TABLE_NAME='" + tableName + "'";
    }

    @Override
    public Map<String, String> getColumnRemarksBySql(Connection conn, String sqlText) {
        Map<String, String> result = new HashMap<>();
        String tableName = "";
        try {
            tableName = TableRemarkUtils.createTable(conn, sqlText);
            String schema = getSchema(conn, tableName);
            if (tableName.contains(".")) {
                tableName = tableName.split(".")[1];
            }
            result = getColumnRemarks(conn, schema, tableName);

            TableRemarkUtils.dropTable(conn, tableName);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            TableRemarkUtils.dropTable(conn, tableName);
        }
        return result;
    }

    private String getSchema(Connection conn, String tableName) {
        if (tableName.contains(".")) {
            return tableName.split(".")[0];
        }
        return getDefaultSchema(conn);
    }

    private String getDefaultSchema(Connection conn) {
        try {
            if (conn.getSchema() != null) {
                return conn.getSchema();
            }
            Field dbField = conn.getClass().getSuperclass().getDeclaredField("database");
            dbField.setAccessible(true);
            return (String) dbField.get(conn);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
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
            logger.error(e.getMessage(), e);
        }
    }
}
