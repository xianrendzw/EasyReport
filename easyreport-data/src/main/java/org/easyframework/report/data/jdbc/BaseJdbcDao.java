package org.easyframework.report.data.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.easyframework.report.data.PageInfo;
import org.easyframework.report.data.mapping.ColumnProperty;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public abstract class BaseJdbcDao extends JdbcDaoSupport implements IBaseJdbcDao {
	protected final Logger logger = Logger.getLogger(this.getClass());

	public BaseJdbcDao() {
		super();
	}

	@Resource
	public void setJDBCTemplate(JdbcTemplate jdbcTemplate) {
		this.setJdbcTemplate(jdbcTemplate);
	}

	/**
	 * 执行更新SQL语句
	 *
	 * @param sql
	 *            sql语句
	 * @return 返回成功更新的记录数
	 * @throws DataAccessException
	 */
	@Override
	public int update(String sql) throws DataAccessException {
		this.debugWrite(sql);
		return getJdbcTemplate().update(sql);
	}

	/**
	 * 执行更新SQL语句
	 *
	 * @param sql
	 * @param args
	 * @return 返回成功更新的记录数
	 * @throws DataAccessException
	 */
	@Override
	public int update(String sql, Object[] args) throws DataAccessException {
		this.debugWrite(sql);
		return getJdbcTemplate().update(sql, args);
	}

	/**
	 * 执行更新SQL语句
	 *
	 * @param sql
	 * @param args
	 * @param argTypes
	 * @return 返回成功更新的记录数
	 * @throws DataAccessException
	 */
	@Override
	public int update(String sql, Object[] args, int[] argTypes) throws DataAccessException {
		this.debugWrite(sql);
		return getJdbcTemplate().update(sql, args, argTypes);
	}

	/**
	 * 向表中插入一条记录并返回其自增id
	 *
	 * @param sql
	 *            sql语句
	 * @param args
	 *            sql参数
	 * @param argTypes
	 *            sql参数对应的数据类型
	 * @return 自增id
	 * @throws DataAccessException
	 */
	@Override
	public int updateWithId(final String sql, final Object[] args, final int[] argTypes) throws DataAccessException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getJdbcTemplate().update((PreparedStatementCreator) con -> {
			PreparedStatement pstat = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; i < args.length; i++) {
				if (args[i] == null) {
					pstat.setNull(i + 1, Types.NULL);
				} else {
					pstat.setObject(i + 1, args[i], argTypes[i]);
				}
			}
			return pstat;
		}, keyHolder);

		this.debugWrite(sql);
		return keyHolder.getKey().intValue();
	}

	/**
	 * 向表中插入一条记录并返回其自增id
	 *
	 * @param sql
	 *            sql语句
	 * @param args
	 *            sql参数
	 * @return 自增id
	 * @throws DataAccessException
	 */
	@Override
	public int updateWithId(final String sql, final Object[] args) throws DataAccessException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getJdbcTemplate().update((PreparedStatementCreator) con -> {
			PreparedStatement pstat = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; i < args.length; i++) {
				if (args[i] == null) {
					pstat.setNull(i + 1, Types.NULL);
				} else {
					pstat.setObject(i + 1, args[i]);
				}
			}
			return pstat;
		}, keyHolder);

		this.debugWrite(sql);
		return keyHolder.getKey().intValue();
	}

	/**
	 * 批量向表中插入记录
	 *
	 * @param sql
	 *            sql语句
	 * @param list
	 *            插入的对象的列属性集合
	 */
	@Override
	public int batchUpdate(final String sql, final List<List<ColumnProperty>> list) throws DataAccessException {
		int[] rowsAffecteds = this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(final PreparedStatement pstat, final int i) throws SQLException {
				List<ColumnProperty> columnProperties = list.get(i);
				for (int index = 0; index < columnProperties.size(); index++) {
					ColumnProperty columnProperty = columnProperties.get(index);
					pstat.setObject(index + 1, columnProperty.getValue());
				}
			}

			@Override
			public int getBatchSize() {
				return list.size();
			}
		});

		int rowsAffected = 0;
		for (int element : rowsAffecteds) {
			rowsAffected += element;
		}
		return rowsAffected;
	}

	/**
	 * 执行查询SQL语句
	 *
	 * @param sql
	 * @return 返回String值
	 * @throws DataAccessException
	 */
	@Override
	public String queryForString(String sql) throws DataAccessException {
		this.debugWrite(sql);
		return getJdbcTemplate().queryForObject(sql, String.class);
	}

	/**
	 * 执行查询SQL语句
	 *
	 * @param sql
	 * @return 返回int值
	 * @throws DataAccessException
	 */
	@Override
	public int queryForInt(String sql) throws DataAccessException {
		this.debugWrite(sql);
		return getJdbcTemplate().queryForObject(sql, Integer.class);
	}

	/**
	 * 执行查询SQL语句
	 *
	 * @param sql
	 * @return 返回long值
	 * @throws DataAccessException
	 */
	@Override
	public long queryForLong(String sql) throws DataAccessException {
		this.debugWrite(sql);
		return getJdbcTemplate().queryForObject(sql, Long.class);
	}

	/**
	 * 执行查询SQL语句
	 *
	 * @param sql
	 * @param objClass
	 * @return 返回Class的对象
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryForObject(String sql, Class<T> objClass) throws DataAccessException {
		this.debugWrite(sql);
		return getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<T>(objClass));
	}

	/**
	 * 执行查询SQL语句,验证是否存在满足whereClause条件的数据且是唯一
	 *
	 * @param tableName
	 * @param whereClause
	 * @return 返回boolean
	 * @throws DataAccessException
	 */
	@Override
	public boolean isExist(String tableName, String whereClause) throws DataAccessException {
		if (StringUtils.isBlank(tableName)) {
			throw new IllegalArgumentException("数据判存失败：表名参数不合法！");
		}
		StringBuffer sql = new StringBuffer(200);
		sql.append("SELECT 1 FROM ").append(tableName).append(" WHERE ROWNUM = 1 ");
		if (StringUtils.isNotBlank(whereClause)) {
			sql.append(" AND (").append(whereClause).append(")");
		}

		String sqlCmd = sql.toString();
		this.debugWrite(sqlCmd);
		return getJdbcTemplate().queryForList(sqlCmd).size() == 1;
	}

	/**
	 * 执行查询SQL语句
	 *
	 * @param sql
	 * @param args
	 * @param poClass
	 * @return 返回结果列表
	 * @throws DataAccessException
	 */
	@Override
	public <T> List<T> queryForList(String sql, Object[] args, Class<T> poClass) throws DataAccessException {
		List<T> list = getJdbcTemplate().query(sql, args, new BeanPropertyRowMapper<T>(poClass));
		this.debugWrite(sql, list.size());
		return list;
	}

	/**
	 * 执行查询SQL语句
	 *
	 * @param sql
	 * @return 返回结果列表
	 * @throws DataAccessException
	 */
	@Override
	public <T> List<T> queryForList(String sql, Object[] args, int[] argTypes, Class<T> poClass)
			throws DataAccessException {
		List<T> list = getJdbcTemplate().query(sql, args, argTypes, new BeanPropertyRowMapper<T>(poClass));
		this.debugWrite(sql, list.size());
		return list;
	}

	/**
	 * 执行查询SQL语句
	 *
	 * @param sql
	 * @return 返回结果列表
	 * @throws DataAccessException
	 *
	 */
	@Override
	public <T> List<T> queryForList(String sql, Class<T> poClass) throws DataAccessException {
		List<T> list = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<T>(poClass));
		this.debugWrite(sql, list.size());
		return list;
	}

	/**
	 * 执行查询SQL语句
	 *
	 * @param sql
	 * @return Object
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryForObject(String sql, Object[] args, int[] argTypes, Class<T> poClass)
			throws DataAccessException {
		this.debugWrite(sql);
		return getJdbcTemplate().queryForObject(sql, args, argTypes, new BeanPropertyRowMapper<T>(poClass));
	}

	/**
	 * 执行分页查询SQL语句
	 *
	 * @param sql
	 * @return 返回结果列表
	 * @throws DataAccessException
	 */
	@Override
	public <T> List<T> queryByPage(String sql, Object[] args, int[] argTypes, PageInfo page, Class<T> poClass)
			throws DataAccessException {
		return queryByPage(sql, args, argTypes, page, new BeanPropertyRowMapper<T>(poClass));
	}

	/**
	 * 执行分页查询SQL语句
	 *
	 * @param sql
	 * @return 返回结果列表
	 * @throws DataAccessException
	 */
	@Override
	public <T> List<T> queryByPage(String sql, PageInfo page, Class<T> poClass) throws DataAccessException {
		return queryByPage(sql, page, new BeanPropertyRowMapper<T>(poClass));
	}

	/**
	 * 执行分页查询SQL语句
	 *
	 * @param sql
	 * @return 返回结果列表
	 * @throws DataAccessException
	 */
	@Override
	public <T> List<T> queryByPage(String sql, Object[] args, PageInfo page, Class<T> poClass)
			throws DataAccessException {
		if (page != null) {
			page.setTotals(this.queryByCount(sql, args));
		}
		sql = this.preparePageSql(sql, page);
		List<T> list = getJdbcTemplate().query(sql, args, new BeanPropertyRowMapper<T>(poClass));
		this.debugWrite(sql, list.size());
		return list;
	}

	/**
	 * 执行分页查询SQL语句
	 *
	 * @param sql
	 * @return 返回结果列表
	 * @throws DataAccessException
	 */
	public <T> List<T> queryByPage(String sql, Object[] args, int[] argTypes, PageInfo page, RowMapper<T> mapper)
			throws DataAccessException {
		if (page != null) {
			page.setTotals(this.queryByCount(sql, args, argTypes));
		}
		sql = this.preparePageSql(sql, page);
		List<T> list = getJdbcTemplate().query(sql, args, argTypes, mapper);
		this.debugWrite(sql, list.size());
		return list;
	}

	/**
	 * 执行分页查询SQL语句
	 *
	 * @param sql
	 * @return 返回结果列表
	 * @throws DataAccessException
	 */
	public <T> List<T> queryByPage(String sql, PageInfo page, RowMapper<T> mapper) throws DataAccessException {
		if (page != null) {
			page.setTotals(this.queryByCount(sql));
		}
		sql = this.preparePageSql(sql, page);
		List<T> list = getJdbcTemplate().query(sql, mapper);
		this.debugWrite(sql, list.size());
		return list;
	}

	/**
	 * 执行sql语句 得到记录总数
	 *
	 * @param sql
	 * @return long
	 * @throws DataAccessException
	 */
	@Override
	public long queryByCount(String sql) throws DataAccessException {
		String sqlCmd = this.getQueryByCountSql(sql);
		return getJdbcTemplate().queryForObject(sqlCmd, Long.class);
	}

	/**
	 * 执行sql语句 得到记录总数
	 *
	 * @param sql
	 * @param args
	 * @return long
	 * @throws DataAccessException
	 */
	@Override
	public long queryByCount(String sql, Object[] args) throws DataAccessException {
		String sqlCmd = this.getQueryByCountSql(sql);
		return getJdbcTemplate().queryForObject(sqlCmd, args, Long.class);
	}

	/**
	 * 执行sql语句 得到记录总数
	 *
	 * @param sql
	 * @param args
	 * @param argTypes
	 * @return long
	 * @throws DataAccessException
	 */
	@Override
	public long queryByCount(String sql, Object[] args, int[] argTypes) throws DataAccessException {
		String sqlCmd = this.getQueryByCountSql(sql);
		return getJdbcTemplate().queryForObject(sqlCmd, args, argTypes, Long.class);
	}

	protected String preparePageSql(String sql, PageInfo page) {
		if (page == null) {
			return sql;
		}

		StringBuffer sqlBuf = new StringBuffer(30 + sql.length());
		sqlBuf.append(sql);
		sqlBuf.append(String.format(" LIMIT %s,%s", page.getStartIndex(), page.getPageSize()));
		/*
		 * sqlBuf.append("SELECT tt.* FROM").append(" (SELECT ROWNUM rid,t.* FROM ("
		 * ).append(sql).append( ") t) tt WHERE rid >").append(
		 * (PageTotalRecords > 0 && PageStartIndex > PageTotalRecords) ? 0 :
		 * PageStartIndex) .append(" AND rid <= ").append( (PageTotalRecords > 0
		 * && end > PageTotalRecords) ? PageTotalRecords : end);
		 */
		return sqlBuf.toString();
	}

	private String getQueryByCountSql(String sql) {
		StringBuffer countSql = new StringBuffer(30 + sql.length());
		countSql.append(" SELECT COUNT(*) FROM (").append(sql).append(") Z");
		String sqlCmd = countSql.toString();
		this.debugWrite(sqlCmd);
		return sqlCmd;
	}

	private void debugWrite(String sql) {
		if (logger.isDebugEnabled()) {
			logger.debug("执行更新SQL语句：" + sql);
		}
	}

	private void debugWrite(String sql, int resultCount) {
		if (logger.isDebugEnabled()) {
			logger.debug("执行查询SQL语句，SQL=[" + sql + "]");
			logger.debug("返回结果列表:" + resultCount);
		}
	}
}
