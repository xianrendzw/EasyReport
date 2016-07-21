package com.easytoolsoft.easyreport.engine.remark;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hongliangpan@gmail.com
 * 根据sql 语句  获取 字段 的中文注释
 */
public class TableRemarkUtils {
    public static final Logger logger = LoggerFactory.getLogger(TableRemarkUtils.class);
    public static final String TMP_TABLE_PREFIX = "tmp_";
    public static final String VIEW_PREFIX = "v_";
    public static final String TMP_TABLE_VIEW_PREFIX = TMP_TABLE_PREFIX + VIEW_PREFIX;

    private static boolean isViewName(String viewName) {
        return viewName.startsWith(VIEW_PREFIX);
    }

    private static boolean isTableName(String tableName) {
        return tableName.startsWith(TMP_TABLE_VIEW_PREFIX);
    }

    public static String parseSelectTableName(String sql) {
        sql = sql.replaceAll("\n", " ");
        Pattern p = Pattern.compile(
                "(?i)(?<=(?:from)\\s{1,1000})(\\w+)"
        );
        Matcher m = p.matcher(sql);
        if (m.find()) {
            return m.group();
        }
        return "";
    }

    protected static void dropTable(Connection conn, String tableName) {
        if (StringUtils.isEmpty(tableName)) {
            return;
        }
        String dropSql = "DROP TABLE IF EXISTS " + tableName;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(dropSql);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    protected static String createTable(Connection conn, String sqlText) throws SQLException {
        String tableName = parseSelectTableName(sqlText);
        if (tableName.length() == 0) {
            return "";
        }
        tableName = TMP_TABLE_PREFIX + tableName + System.currentTimeMillis();
        String sql = "CREATE TABLE " + tableName + " AS SELECT * FROM (" + sqlText + ") t LIMIT 0";
        String dropSql = "DROP TABLE IF EXISTS " + tableName;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(dropSql);
            stmt.executeUpdate(sql);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
            }
        }
        return tableName;
    }
}
