package org.easyframework.report.engine.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.easyframework.report.engine.data.ColumnType;
import org.easyframework.report.engine.data.ReportDataSource;
import org.easyframework.report.engine.data.ReportMetaDataCell;
import org.easyframework.report.engine.data.ReportMetaDataColumn;
import org.easyframework.report.engine.data.ReportMetaDataRow;
import org.easyframework.report.engine.data.ReportParameter;
import org.easyframework.report.engine.exception.SQLQueryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public abstract class AbstractQueryer {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected final ReportDataSource dataSource;
	protected final ReportParameter parameter;

	protected AbstractQueryer(ReportDataSource dataSource, ReportParameter parameter) {
		this.dataSource = dataSource;
		this.parameter = parameter;
	}

	public List<ReportMetaDataRow> getMetaDataRows() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			logger.info(this.parameter.getSqlText());
			conn = this.getConnection();
			stmt = conn.prepareStatement(this.parameter.getSqlText());
			rs = stmt.executeQuery();
			return this.getMetaDataRows(rs, this.getSqlColumns(this.getMetaDataColumns()));
		} catch (Exception ex) {
			logger.error(String.format("SqlText:%s，Msg:%s", this.parameter.getSqlText(), ex));
			throw new SQLQueryException(ex);
		} finally {
			this.releaseJdbcResource(conn, stmt, rs);
		}
	}

	public List<ReportMetaDataColumn> getMetaDataColumns() {
		return JSON.parseArray(this.parameter.getMetaColumns(), ReportMetaDataColumn.class);
	}

	protected abstract Connection getConnection();

	protected List<ReportMetaDataRow> getMetaDataRows(ResultSet rs, List<ReportMetaDataColumn> sqlColumns)
			throws SQLException {
		List<ReportMetaDataRow> rows = new ArrayList<ReportMetaDataRow>();
		while (rs.next()) {
			ReportMetaDataRow row = new ReportMetaDataRow();
			for (ReportMetaDataColumn column : sqlColumns) {
				Object value = rs.getObject(column.getName());
				if (column.getDataType().contains("BINARY")) {
					value = new String((byte[]) value);
				}
				row.add(new ReportMetaDataCell(column, column.getName(), value));
			}
			rows.add(row);
		}
		return rows;
	}

	protected void releaseJdbcResource(Connection conn, PreparedStatement stmt, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException ex) {
			throw new RuntimeException("数据库资源释放异常", ex);
		}
	}

	protected List<ReportMetaDataColumn> getSqlColumns(List<ReportMetaDataColumn> metaDataColumns) {
		return metaDataColumns.stream()
				.filter(x -> x.getType() != ColumnType.COMPUTED)
				.collect(Collectors.toList());
	}
}
