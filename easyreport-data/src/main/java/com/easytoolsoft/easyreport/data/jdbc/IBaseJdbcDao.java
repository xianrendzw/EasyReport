package com.easytoolsoft.easyreport.data.jdbc;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.easytoolsoft.easyreport.data.PageInfo;
import com.easytoolsoft.easyreport.data.mapping.ColumnProperty;

/**
 * 基于JDBC的DAO的公共操作接口，对数据库表提供标准的增删改查功能
 *
 */
public interface IBaseJdbcDao {
	/**
	 * 执行更新SQL语句
	 *
	 * @param sql sql语句
	 * @return 返回成功更新的记录数
	 * @throws DataAccessException
	 */
	int update(String sql) throws DataAccessException;

	/**
	 *
	 * @param sql sql语句
	 * @param args 参数值
	 * @return
	 * @throws DataAccessException
	 */
	int update(String sql, Object[] args) throws DataAccessException;

	/**
	 *
	 * @param sql sql语句
	 * @param args 参数值
	 * @param argTypes 参数类型
	 * @return
	 * @throws DataAccessException
	 */
	int update(String sql, Object[] args, int[] argTypes) throws DataAccessException;

	/**
	 * 向表中插入一条记录并返回其自增id
	 *
	 * @param sql sql语句
	 * @param args sql参数
	 * @param argTypes sql参数对应的数据类型
	 * @return 自增id
	 * @throws DataAccessException
	 */
	int updateWithId(String sql, Object[] args, int[] argTypes) throws DataAccessException;

	/**
	 * 向表中插入一条记录并返回其自增id
	 *
	 * @param sql sql语句
	 * @param args sql参数
	 * @return 自增id
	 * @throws DataAccessException
	 */
	int updateWithId(String sql, Object[] args) throws DataAccessException;

	/**
	 * 批量向表中增加或更新记录
	 *
	 * @param sql sql语句
	 * @param list 等处理记录的列属性集合
	 */
	int batchUpdate(String sql, List<List<ColumnProperty>> list) throws DataAccessException;

	/**
	 * 执行查询SQL语句，返回String值
	 *
	 * @param sql
	 * @return
	 * @throws DaoException
	 */
	String queryForString(String sql) throws DataAccessException;

	/**
	 * 执行查询SQL语句，返回int值
	 *
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 */
	int queryForInt(String sql) throws DataAccessException;

	/**
	 * 执行查询SQL语句，返回long值
	 *
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 */
	long queryForLong(String sql) throws DataAccessException;

	/**
	 * 执行查询SQL语句
	 *
	 * @param sql
	 * @param objectClass
	 * @return 返回Class的对象
	 * @throws DataAccessException
	 */
	<T> Object queryForObject(String sql, Class<T> objectClass) throws DataAccessException;

	/**
	 * 执行查询SQL语句,验证是否存在满足whereClause条件的数据且是唯一
	 *
	 * @param tableName
	 * @param whereClause
	 * @return 返回boolean
	 * @throws DataAccessException
	 */
	boolean isExist(String tableName, String whereClause) throws DataAccessException;

	/**
	 * 执行查询SQL语句
	 *
	 * @param sql
	 * @param args
	 * @param poClass
	 * @return 返回结果列表
	 * @throws DataAccessException
	 */
	<T> List<T> queryForList(String sql, Object[] args, Class<T> poClass) throws DataAccessException;

	/**
	 * 执行查询SQL语句
	 *
	 * @param sql
	 * @return 返回结果列表
	 * @throws DataAccessException
	 */
	<T> List<T> queryForList(String sql, Object[] args, int[] argTypes, Class<T> poClass) throws DataAccessException;

	/**
	 * 执行查询SQL语句
	 *
	 * @param sql
	 * @return 返回结果列表
	 * @throws DataAccessException
	 */
	<T> List<T> queryForList(String sql, Class<T> poClass) throws DataAccessException;

	/**
	 * 执行查询SQL语句
	 *
	 * @param sql
	 * @return Object
	 * @throws DataAccessException
	 */
	<T> T queryForObject(String sql, Object[] args, int[] argTypes, Class<T> poClass) throws DataAccessException;

	/**
	 * 执行分页查询SQL语句
	 *
	 * @param sql
	 * @param args ,
	 * @param page
	 * @param poClass
	 * @return 返回结果列表
	 * @throws DataAccessException
	 */
	public <T> List<T> queryByPage(String sql, Object[] args, PageInfo page, Class<T> poClass);

	/**
	 * 执行分页查询SQL语句
	 *
	 * @param sql
	 * @param args ,
	 * @param argTypes
	 * @param page
	 * @param poClass
	 * @return 返回结果列表
	 * @throws DataAccessException
	 */
	<T> List<T> queryByPage(String sql, Object[] args, int[] argTypes, PageInfo page, Class<T> poClass)
			throws DataAccessException;

	/**
	 * 执行分页查询SQL语句
	 *
	 * @param sql
	 * @return 返回结果列表
	 * @throws DataAccessException
	 */
	<T> List<T> queryByPage(String sql, PageInfo page, Class<T> poClass) throws DataAccessException;

	/**
	 * 执行sql语句 得到记录总数
	 *
	 * @param sql
	 * @return long
	 * @throws DataAccessException
	 */
	long queryByCount(String sql) throws DataAccessException;

	/**
	 * 执行sql语句 得到记录总数
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws DataAccessException
	 */
	long queryByCount(String sql, Object[] args) throws DataAccessException;

	/**
	 * 执行sql语句 得到记录总数
	 *
	 * @param sql
	 * @param args
	 * @param argTypes
	 * @return long
	 * @throws DataAccessException
	 */
	long queryByCount(String sql, Object[] args, int[] argTypes) throws DataAccessException;

}
