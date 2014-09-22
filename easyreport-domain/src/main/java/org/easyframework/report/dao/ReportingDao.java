package org.easyframework.report.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.easyframework.report.data.PageInfo;
import org.easyframework.report.data.SortType;
import org.easyframework.report.data.criterion.Restrictions;
import org.easyframework.report.data.criterion.operands.Bracket;
import org.easyframework.report.data.jdbc.BaseDao;
import org.easyframework.report.engine.data.ReportMetaDataColumn;
import org.easyframework.report.entity.OptionItem;
import org.easyframework.report.entity.Reporting;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * ReportingDao提供报表信息表表(reporting)的相关数据访问操作的类。
 * 
 * @author Tom Deng
 */
@Repository
public class ReportingDao extends BaseDao<Reporting> {
	private ComboPooledDataSource reportSqlDataSource;

	public ReportingDao() {
		super(Reporting.EntityName, Reporting.Id);
	}

	@Resource
	public void setReportSqlDataSource(ComboPooledDataSource reportSqlDataSource) {
		this.reportSqlDataSource = reportSqlDataSource;
	}

	public boolean exists(int dsId) {
		String condition = Restrictions.equal(Reporting.DsId, "?").toString();
		Object[] args = new Object[] { dsId };
		return this.count(condition, args) > 0;
	}

	public boolean exists(String createUser, int id) {
		String condition = Restrictions.equal(Reporting.CreateUser, "?")
				.append(Restrictions.And)
				.append(Restrictions.equal(Reporting.Id, "?")).toString();
		Object[] args = new Object[] { createUser, id };
		return this.count(condition, args) > 0;
	}

	public boolean exists(int pid, int id, String name) {
		String condition = Restrictions.equal(Reporting.Pid, "?")
				.append(Restrictions.And)
				.append(Restrictions.equal(Reporting.Name, "?"))
				.append(Restrictions.And)
				.append(Restrictions.notEqual(Reporting.Id, "?")).toString();
		Object[] args = new Object[] { pid, name, id };
		return this.count(condition, args) > 0;
	}

	public boolean existsByUid(String uid) {
		String condition = Restrictions.equal(Reporting.Uid, "?").toString();
		Object[] args = new Object[] { uid };
		return this.count(condition, args) > 0;
	}

	public int countChildBy(int pid) {
		String condition = Restrictions.equal(Reporting.Pid, pid).toString();
		return this.count(condition);
	}

	public int countChildBy(int pid, String name) {
		String condition = Restrictions.equal(Reporting.Pid, "?")
				.append(Restrictions.And)
				.append(Restrictions.bracket(Bracket.Left))
				.append(Restrictions.equal(Reporting.Name, "?"))
				.append(Restrictions.Or)
				.append(Restrictions.like(Reporting.Name, String.format("'%s_复件%'", name)))
				.append(Restrictions.bracket(Bracket.Rgiht)).toString();
		Object[] args = new Object[] { pid, name };
		return this.count(condition, args);
	}

	public int queryStatus(int id) {
		Reporting entity = this.queryByKey(id, Reporting.Status);
		return entity == null ? -1 : entity.getStatus();
	}

	public String querySqlText(int id) {
		Reporting entity = this.queryByKey(id, Reporting.Status);
		return entity == null ? "" : entity.getSqlText();
	}

	public Reporting queryByUid(String uid) {
		String condition = Restrictions.equal(Reporting.Uid, "?").toString();
		Object[] args = new Object[] { uid };
		return this.queryOne(condition, args);
	}

	public String queryPath(int pid) {
		Reporting entity = this.queryByKey(pid, Reporting.Path);
		return entity == null ? "" : entity.getPath();
	}

	public int queryId(String uid) {
		String condition = Restrictions.equal(Reporting.Uid, "?").toString();
		Object[] args = new Object[] { uid };
		Reporting entity = this.queryOne(condition, args, Reporting.Id);
		return entity == null ? 0 : entity.getId();
	}

	public List<Reporting> queryAllChild(int id) {
		String condition = Restrictions.equal(Reporting.Id, "?")
				.append(Restrictions.like(Reporting.Path, "'" + id + ",%'")).toString();
		Object[] args = new Object[] { id };
		return this.query(condition, args);
	}

	public List<Reporting> queryByPid(int pid) {
		String condition = Restrictions.equal(Reporting.Pid, "?")
				.append(Restrictions.orderBy(SortType.ASC, Reporting.Sequence))
				.toString();
		Object[] args = new Object[] { pid };
		return this.query(condition, args);
	}

	public List<Reporting> queryByPage(String fieldName, String keyword, PageInfo page) {
		String condition = "";
		if (keyword != null && keyword.trim().length() > 0) {
			condition = Restrictions.like(fieldName, "'%" + keyword + "%'").toString();
		}
		String[] columnNames = new String[] {
				Reporting.Id, Reporting.Pid, Reporting.Uid, Reporting.Path,
				Reporting.Name, Reporting.CreateUser, Reporting.CreateTime
		};
		return this.query(condition, page, columnNames);
	}

	public int updateUid(int id, String uid) {
		String condition = Restrictions.equal(Reporting.Id, "?").toString();
		String[] columnNames = new String[] { Reporting.Uid };
		Object[] args = new Object[] { uid, id };
		return this.update(condition, args, columnNames);
	}

	public int updateTreeNode(Reporting entity) {
		String[] columnNames = new String[] {
				Reporting.Name, Reporting.Flag,
				Reporting.HasChild, Reporting.Status,
				Reporting.Sequence, Reporting.Comment
		};
		return this.update(entity, columnNames);
	}

	public int updateQueryParams(int id, String queryParams) {
		String condition = Restrictions.equal(Reporting.Id, "?").toString();
		String[] columnNames = new String[] { Reporting.QueryParams };
		Object[] args = new Object[] { queryParams, id };
		return this.update(condition, args, columnNames);
	}

	public int updatePath(int id, String path) {
		String condition = Restrictions.equal(Reporting.Id, "?").toString();
		String[] columnNames = new String[] { Reporting.Path };
		Object[] args = new Object[] { path, id };
		return this.update(condition, args, columnNames);
	}

	public int updateHasChild(int pid, boolean hasChild) {
		String condition = Restrictions.equal(Reporting.Id, "?").toString();
		String[] columnNames = new String[] { Reporting.HasChild };
		Object[] args = new Object[] { hasChild, pid };
		return this.update(condition, args, columnNames);
	}

	public int updatePid(int id, int pid) {
		String condition = Restrictions.equal(Reporting.Id, "?").toString();
		String[] columnNames = new String[] { Reporting.Pid };
		Object[] args = new Object[] { pid, id };
		return this.update(condition, args, columnNames);
	}

	public int updateCreateUser(int id, String createUser, String comment) {
		String condition = Restrictions.equal(Reporting.Id, "?").toString();
		String[] columnNames = new String[] { Reporting.CreateUser, Reporting.Comment };
		Object[] args = new Object[] { createUser, comment, id };
		return this.update(condition, args, columnNames);
	}

	public int updateByUid(Reporting entity) {
		String[] columnNames = new String[] {
				Reporting.DsId, Reporting.Name, Reporting.DataRange, Reporting.Layout,
				Reporting.SqlText, Reporting.MetaColumns, Reporting.QueryParams, Reporting.Comment
		};
		String condition = Restrictions.equal(Reporting.Uid, "?").toString();
		Object[] args = new Object[] { entity.getUid() };
		return this.update(entity, condition, args, columnNames);
	}

	public List<ReportMetaDataColumn> executeSqlText(String url, String user, String password, String sqlText) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement stmt = null;
		java.sql.ResultSet rs = null;
		List<ReportMetaDataColumn> columns = null;

		try {
			this.reportSqlDataSource.setJdbcUrl(url);
			this.reportSqlDataSource.setUser(user);
			this.reportSqlDataSource.setPassword(password);
			conn = DataSourceUtils.getConnection(this.reportSqlDataSource);
			stmt = conn.prepareStatement(this.appendLimit(sqlText));
			rs = stmt.executeQuery();
			ResultSetMetaData rsMataData = rs.getMetaData();
			int count = rsMataData.getColumnCount();
			columns = new ArrayList<ReportMetaDataColumn>(count);
			for (int i = 1; i <= count; i++) {
				ReportMetaDataColumn column = new ReportMetaDataColumn();
				column.setName(rsMataData.getColumnLabel(i));
				column.setDataType(rsMataData.getColumnTypeName(i));
				column.setWidth(rsMataData.getColumnDisplaySize(i));
				columns.add(column);
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			this.releaseJdbcResource(conn, stmt, rs);
		}

		return columns == null ? new ArrayList<ReportMetaDataColumn>(0) : columns;
	}

	public List<OptionItem> executeQueryParamSqlText(String url, String user, String password, String sqlText) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement stmt = null;
		java.sql.ResultSet rs = null;
		HashSet<String> set = new HashSet<String>();
		List<OptionItem> rows = new ArrayList<OptionItem>();

		try {
			this.reportSqlDataSource.setJdbcUrl(url);
			this.reportSqlDataSource.setUser(user);
			this.reportSqlDataSource.setPassword(password);
			conn = DataSourceUtils.getConnection(this.reportSqlDataSource);
			stmt = conn.prepareStatement(sqlText);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String text = rs.getString("text");
				String value = rs.getString("value");
				text = (text == null) ? "" : text.trim();
				value = (value == null) ? "" : value.trim();
				if (!set.contains(value)) {
					set.add(value);
				}
				rows.add(new OptionItem(text, value));
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			this.releaseJdbcResource(conn, stmt, rs);
		}

		set.clear();
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
			throw new RuntimeException(ex);
		}
	}

	private String appendLimit(String sqlText) {
		sqlText = StringUtils.stripEnd(sqlText.trim(), ";");
		Pattern pattern = Pattern.compile("limit.*?$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(sqlText);
		if (matcher.find()) {
			sqlText = matcher.replaceFirst("");
		}
		return sqlText + " limit 1";
	}
}