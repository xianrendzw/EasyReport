package org.easyframework.report.engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.easyframework.report.engine.data.ColumnType;
import org.easyframework.report.engine.data.ReportDataSet;
import org.easyframework.report.engine.data.ReportDataSource;
import org.easyframework.report.engine.data.ReportMetaDataCell;
import org.easyframework.report.engine.data.ReportMetaDataColumn;
import org.easyframework.report.engine.data.ReportMetaDataRow;
import org.easyframework.report.engine.data.ReportMetaDataSet;
import org.easyframework.report.engine.data.ReportParameter;
import org.easyframework.report.engine.exception.SQLQueryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 *
 * 数据执行类，负责执行报表SQL查询并获取数据，最终转化为成报表的数据集
 *
 */
public class DataExecution {
	private final Logger logger = LoggerFactory.getLogger(DataExecution.class);
	private final ReportDataSource dataSource;
	private final ReportParameter parameter;

	public DataExecution(ReportDataSource dataSource, ReportParameter parameter) {
		this.dataSource = dataSource;
		this.parameter = parameter;
	}

	/**
	 * 
	 * 执行报表SQL查询并获取数据，最终转化为成报表的数据集
	 * 
	 * @return ReportDataSet报表数据集对象
	 */
	public ReportDataSet execute() {
		List<ReportMetaDataColumn> metaDataColumns = this.getMetaDataColumns();
		List<ReportMetaDataRow> metaDataRows = this.getMetaDataRows(this.getSqlColumns(metaDataColumns));
		ReportMetaDataSet metaDataSet = new ReportMetaDataSet(
				metaDataRows, metaDataColumns, this.parameter.getDisplayedStatColumns());
		return new ReportDataSet(metaDataSet, this.parameter.getLayout(), this.parameter.getStatColumnLayout());
	}

	private List<ReportMetaDataRow> getMetaDataRows(List<ReportMetaDataColumn> sqlCcolumns) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			logger.info(this.parameter.getSqlText());
			conn = DriverManager.getConnection(this.dataSource.getJdbcUrl(), this.dataSource.getUser(),
					this.dataSource.getPassword());
			stmt = conn.prepareStatement(this.parameter.getSqlText());
			rs = stmt.executeQuery();
			return this.getMetaDataRows(rs, sqlCcolumns);
		} catch (SQLException ex) {
			logger.error(String.format("SqlText:%s，Msg:%s", this.parameter.getSqlText(), ex));
			throw new SQLQueryException(ex);
		} finally {
			this.releaseJdbcResource(conn, stmt, rs);
		}
	}

	private List<ReportMetaDataRow> getMetaDataRows(ResultSet rs, List<ReportMetaDataColumn> sqlCcolumns)
			throws SQLException {
		List<ReportMetaDataRow> rows = new ArrayList<ReportMetaDataRow>();
		while (rs.next()) {
			ReportMetaDataRow row = new ReportMetaDataRow();
			for (ReportMetaDataColumn column : sqlCcolumns) {
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

	private void releaseJdbcResource(Connection conn, PreparedStatement stmt, ResultSet rs) {
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

	private List<ReportMetaDataColumn> getMetaDataColumns() {
		return JSON.parseArray(this.parameter.getMetaColumns(), ReportMetaDataColumn.class);
	}

	/**
	 * 获取sql语句的所有列信息
	 * 
	 * @param metaDataColumns
	 * @return List<ReportMetaDataColumn>
	 */
	private List<ReportMetaDataColumn> getSqlColumns(List<ReportMetaDataColumn> metaDataColumns) {
		return metaDataColumns.stream()
				.filter(x -> x.getType() != ColumnType.COMPUTED)
				.collect(Collectors.toList());
	}
}
