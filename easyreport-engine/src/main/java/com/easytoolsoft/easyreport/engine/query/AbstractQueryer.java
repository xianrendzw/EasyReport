package com.easytoolsoft.easyreport.engine.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.easytoolsoft.easyreport.engine.data.ColumnType;
import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataCell;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataRow;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;
import com.easytoolsoft.easyreport.engine.data.ReportQueryParamItem;
import com.easytoolsoft.easyreport.engine.exception.SQLQueryException;
import com.easytoolsoft.easyreport.engine.util.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tomdeng
 */
public abstract class AbstractQueryer {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final ReportDataSource dataSource;
    protected final ReportParameter parameter;
    protected final List<ReportMetaDataColumn> metaDataColumns;

    protected AbstractQueryer(final ReportDataSource dataSource, final ReportParameter parameter) {
        this.dataSource = dataSource;
        this.parameter = parameter;
        this.metaDataColumns = this.parameter == null ?
            new ArrayList<>(0) :
            new ArrayList<>(this.parameter.getMetaColumns());
    }

    public List<ReportMetaDataColumn> parseMetaDataColumns(final String sqlText) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<ReportMetaDataColumn> columns = null;

        try {
            this.logger.debug("Parse Report MetaDataColumns SQL:{},", sqlText);
            conn = this.getJdbcConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(this.preprocessSqlText(sqlText));
            final ResultSetMetaData rsMataData = rs.getMetaData();
            final int count = rsMataData.getColumnCount();
            columns = new ArrayList<>(count);
            for (int i = 1; i <= count; i++) {
                final ReportMetaDataColumn column = new ReportMetaDataColumn();
                column.setName(rsMataData.getColumnLabel(i));
                column.setDataType(rsMataData.getColumnTypeName(i));
                column.setWidth(rsMataData.getColumnDisplaySize(i));
                columns.add(column);
            }
        } catch (final SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            JdbcUtils.releaseJdbcResource(conn, stmt, rs);
        }
        return columns;
    }

    public List<ReportQueryParamItem> parseQueryParamItems(final String sqlText) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        final HashSet<String> set = new HashSet<>();
        final List<ReportQueryParamItem> rows = new ArrayList<>();

        try {
            this.logger.debug(sqlText);
            conn = this.getJdbcConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlText);
            while (rs.next()) {
                String name = rs.getString("name");
                String text = rs.getString("text");
                name = (name == null) ? "" : name.trim();
                text = (text == null) ? "" : text.trim();
                if (!set.contains(name)) {
                    set.add(name);
                }
                rows.add(new ReportQueryParamItem(name, text));
            }
        } catch (final SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            JdbcUtils.releaseJdbcResource(conn, stmt, rs);
        }
        set.clear();
        return rows;
    }

    public List<ReportMetaDataColumn> getMetaDataColumns() {
        return this.metaDataColumns;
    }

    public List<ReportMetaDataRow> getMetaDataRows() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            this.logger.debug(this.parameter.getSqlText());
            conn = this.getJdbcConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(this.parameter.getSqlText());
            return this.getMetaDataRows(rs, this.getSqlColumns(this.parameter.getMetaColumns()));
        } catch (final Exception ex) {
            this.logger.error(String.format("SqlText:%s，Msg:%s", this.parameter.getSqlText(), ex));
            throw new SQLQueryException(ex);
        } finally {
            JdbcUtils.releaseJdbcResource(conn, stmt, rs);
        }
    }

    protected List<ReportMetaDataRow> getMetaDataRows(final ResultSet rs, final List<ReportMetaDataColumn> sqlColumns)
        throws SQLException {
        final List<ReportMetaDataRow> rows = new ArrayList<>();
        while (rs.next()) {
            final ReportMetaDataRow row = new ReportMetaDataRow();
            for (final ReportMetaDataColumn column : sqlColumns) {
                Object value = rs.getObject(column.getName());
                if (column.getDataType().contains("BINARY")) {
                    value = new String((byte[])value);
                }
                row.add(new ReportMetaDataCell(column, column.getName(), value));
            }
            rows.add(row);
        }
        return rows;
    }

    protected List<ReportMetaDataColumn> getSqlColumns(final List<ReportMetaDataColumn> metaDataColumns) {
        return metaDataColumns.stream()
            .filter(x -> x.getType() != ColumnType.COMPUTED)
            .collect(Collectors.toList());
    }

    /**
     * 预处理获取报表列集合的sql语句，
     * 在这里可以拦截全表查询等sql， 因为如果表的数据量很大，将会产生过多的内存消耗，甚至性能问题
     *
     * @param sqlText 原sql语句
     * @return 预处理后的sql语句
     */
    protected String preprocessSqlText(final String sqlText) {
        return sqlText;
    }

    /**
     * 获取当前报表查询器的JDBC Connection对象
     *
     * @return Connection
     */
    protected Connection getJdbcConnection() {
        try {
            Class.forName(this.dataSource.getDriverClass());
            return JdbcUtils.getDataSource(this.dataSource).getConnection();
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
