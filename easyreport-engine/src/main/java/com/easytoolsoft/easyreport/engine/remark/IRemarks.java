package com.easytoolsoft.easyreport.engine.remark;

import java.sql.Connection;
import java.util.Map;

public interface IRemarks {

    /**
     * 获取表注释
     */
    String getTableRemark(Connection conn, String tableName);

    /**
     * 获取列注释
     */
    String getColumnRemark(Connection conn, String tableSchema, String tableName, String columnName);

    /**
     * 获取表所有列的注释
     */
    Map<String, String> getColumnRemarks(Connection conn, String tableSchema, String tableName);

    /**
     * 获取sql 查询语句 获取所有列的注释
     * <p/>
     * sql 支持join连接
     */
    Map<String, String> getColumnRemarksBySql(Connection conn, String sqlText);
}
