package com.easyreport.data.jdbc;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.easyreport.data.PageInfo;
import com.easyreport.data.SortType;
import com.easyreport.data.annotations.Column;
import com.easyreport.data.exceptions.SQLConditionException;
import com.easyreport.data.mapping.ColumnMap;
import com.easyreport.data.mapping.ColumnProperty;
import com.easyreport.data.mapping.EntityMapper;

public abstract class BaseDao<T> extends BaseJdbcDao implements IBaseDao<T> {

	protected String tableName = "";
	protected String primaryKey;

	protected BaseDao(String tableName, String primaryKey) {
		this.tableName = tableName;
		this.primaryKey = primaryKey;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPrimaryKey() {
		return this.primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Override
	public int insert(T entity) {
		SqlExpression sqlExpr = this.generateInsertSqlExpression(this.getColumnMap(entity), this.tableName);
		return this.update(sqlExpr.getCommandText(), sqlExpr.getParameters());
	}

	@Override
	public int insertWithId(T entity) {
		SqlExpression sqlExpr = this.generateInsertSqlExpression(this.getColumnMap(entity), this.tableName);
		return this.updateWithId(sqlExpr.getCommandText(), sqlExpr.getParameters());
	}

	@Override
	public int batchInsert(List<T> entities) {
		SqlExpression sqlExpr = this.generateBatchInsertSqlExpression(entities, this.tableName);
		return this.batchUpdate(sqlExpr.getCommandText(), sqlExpr.getBatchParameters());
	}

	@Override
	public int delete(String condition, Object... args) {
		if (StringUtils.isBlank(condition)) {
			throw new SQLConditionException("condition:条件表达式不能为空");
		}
		condition = this.checkCondition(condition);
		String sqlCmd = String.format("DELETE FROM %1$s %2$s", this.tableName, condition);
		return this.update(sqlCmd, args);
	}

	@Override
	public int deleteAll() {
		String sqlCmd = String.format("DELETE FROM %s", this.tableName);
		return this.update(sqlCmd);
	}

	@Override
	public void truncate() {
		String sqlCmd = String.format("TRUNCATE TABLE %s", this.tableName);
		this.update(sqlCmd);
	}

	@Override
	public int deleteByKey(int keyValue) {
		String condition = String.format("%1$s = %2$s", this.primaryKey, keyValue);
		return this.delete(condition);
	}

	@Override
	public int deleteByKey(String keyValue) {
		String condition = String.format("%s = ?", this.primaryKey);
		return this.delete(condition, keyValue);
	}

	@Override
	public int deleteByKey(int[] keyValues) {
		String[] keys = new String[keyValues.length];
		for (int i = 0; i < keyValues.length; i++) {
			keys[i] = Integer.toString(keyValues[0]);
		}
		return this.deleteByKey(keys);
	}

	@Override
	public int deleteByKey(String[] keyValues) {
		return this.deleteInByKey(StringUtils.join(keyValues, ','));
	}

	@Override
	public int deleteInByKey(String keyValues) {
		String condition = String.format("%1$s IN(%2$s)", this.primaryKey, keyValues);
		return this.delete(condition);
	}

	@Override
	public int update(T entity, String... columnNames) {
		String condition = String.format("%s = ?", this.primaryKey);
		Object keyValue = this.getPrimaryKeyValue(entity.getClass(), entity);
		Object[] args = new Object[] { keyValue };
		return this.update(entity, condition, args, columnNames);
	}

	@Override
	public int update(T entity, int keyValue, String... columnNames) {
		String condition = String.format("%1$s = %2$s", this.primaryKey, keyValue);
		return this.update(entity, condition, null, columnNames);
	}

	@Override
	public int update(T entity, String keyValue, String... columnNames) {
		String condition = String.format("%s = ?", this.primaryKey);
		Object[] args = new Object[] { keyValue };
		return this.update(entity, condition, args, columnNames);
	}

	@Override
	public int updateInByKey(T entity, String keyValues, String... columnNames) {
		String condition = String.format("%1$s IN(%1$s)", this.primaryKey, keyValues);
		return this.update(entity, condition, null, columnNames);
	}

	@Override
	public int update(String condition, Object[] args, String[] columnNames) {
		if (args == null || args.length == 0) {
			throw new NullPointerException("args:不能空null或没有值");
		}
		if (columnNames == null || columnNames.length == 0) {
			throw new NullPointerException("columnNames:不能空null或没有值");
		}
		if (args.length < columnNames.length) {
			throw new ArrayIndexOutOfBoundsException("参数：'args'长度不能小于'columnNames'的长度");
		}

		condition = this.checkCondition(condition);
		ArrayList<String> columns = new ArrayList<String>(columnNames.length);
		for (String columnName : columnNames) {
			columns.add(String.format("%s = ?", columnName));
		}

		String sqlCmd = String.format("UPDATE %1$s SET %2$s %3$s ", tableName,
				StringUtils.join(columns, ','), condition);
		return this.update(sqlCmd, args);
	}

	@Override
	public int update(T entity, String condition, Object[] args, String... columnNames) {
		if (entity == null) {
			throw new NullPointerException("entity:未将对象引用到实例");
		}
		if (StringUtils.isBlank(condition)) {
			throw new SQLConditionException("condition:条件表达式不能为空");
		}

		condition = this.checkCondition(condition);
		SqlExpression sqlExpr = this.generateUpdateSqlExpression(condition,
				this.getColumnMap(entity, columnNames), this.tableName, args);
		return this.update(sqlExpr.getCommandText(), sqlExpr.getParameters());
	}

	@Override
	public int updateAll(T entity, String... columnNames) {
		if (entity == null) {
			throw new NullPointerException("entity:未将对象引用到实例");
		}
		SqlExpression sqlExpr = this.generateUpdateSqlExpression("",
				this.getColumnMap(entity, columnNames), this.tableName);
		return this.update(sqlExpr.getCommandText(), sqlExpr.getParameters());
	}

	@Override
	public int batchUpdate(List<T> entities, String... columnNames) {
		String condition = String.format("WHERE %1$s = ?", this.primaryKey);
		String[] cndColumnNames = new String[] { this.primaryKey };
		return this.batchUpdate(entities, condition, cndColumnNames, columnNames);
	}

	@Override
	public int batchUpdate(List<T> entities, String condition, String[] cndColumnNames, String... columnNames) {
		SqlExpression sqlExpr = this.generateBatchUpdateSqlExpression(entities, condition, cndColumnNames,
				this.tableName, columnNames);
		return this.batchUpdate(sqlExpr.getCommandText(), sqlExpr.getBatchParameters());
	}

	@Override
	public T queryOne(String condition, Object[] args, String... columnNames) {
		List<T> list = this.query(condition, args, columnNames);
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}

	@Override
	public List<T> query() {
		return this.query("");
	}

	@Override
	public List<T> query(String[] columnNames) {
		return this.query("", "", SortType.ASC, null, columnNames);
	}

	@Override
	public List<T> query(String condition, String... columnNames) {
		return this.query(condition, "", SortType.ASC, null, columnNames);
	}

	@Override
	public List<T> query(String condition, Object[] args, String... columnNames) {
		return this.query(condition, "", SortType.ASC, args, columnNames);
	}

	@Override
	public List<T> query(String sortItem, SortType sortType, String... columnNames) {
		return this.query("", sortItem, sortType, null, columnNames);
	}

	@Override
	public List<T> query(String condition, PageInfo page, String... columnNames) {
		return this.query(condition, page, null, columnNames);
	}

	@Override
	public List<T> query(String condition, String sortItem, SortType sortType, Object[] args, String... columnNames) {
		PageInfo pageInfo = new PageInfo();
		pageInfo.setSortItem(sortItem);
		pageInfo.setSortType(sortType.toString());
		pageInfo.setPageSize(-1);
		return this.query(condition, pageInfo, args, columnNames);
	}

	@Override
	public List<T> query(String condition, PageInfo page, Object[] args, String... columnNames) {
		condition = this.checkCondition(condition);
		// 获取筛选列
		String columns = this.getColumns(columnNames);
		String orderByClause = StringUtils.isBlank(page.getSortItem()) ? "" :
				String.format("ORDER BY %1$s %2$s", page.getSortItem(), page.getSortType());
		String sqlCmd = String.format("SELECT %1$s FROM %2$s %3$s %4$s", columns, this.tableName, condition,
				orderByClause);

		Class<T> classz = this.getParameterizedType(this.getClass());
		// 不分页查询
		if (page.getPageSize() == -1) {
			return this.queryForList(sqlCmd, args, classz);
		}
		return this.queryByPage(sqlCmd, args, page, classz);
	}

	@Override
	public T queryByKey(String keyValue, String... columnNames) {
		String condition = String.format("%1$s = %2$s", this.primaryKey, keyValue);
		return this.queryOne(condition, null, columnNames);
	}

	@Override
	public T queryByKey(int keyValue, String... columnNames) {
		String condition = String.format("%1$s = ?", this.primaryKey);
		return this.queryOne(condition, new Object[] { keyValue }, columnNames);
	}

	@Override
	public List<T> queryInByKey(String keyValues, String... columnNames) {
		String condition = String.format("%1$s IN(%1$s)", this.primaryKey, keyValues);
		return this.query(condition, columnNames);
	}

	@Override
	public int queryMaxId() {
		return this.queryMaxValue("", this.primaryKey);
	}

	@Override
	public int queryMaxValue(String condition, String columnName, Object... args) {
		condition = this.checkCondition(condition);
		StringBuilder strSql = new StringBuilder();
		strSql.append("SELECT MAX(%1$s) AS MaxValue FROM %2$s %3$s");
		String sqlCmd = String.format(strSql.toString(), columnName, this.tableName, condition);
		return this.getJdbcTemplate().queryForObject(sqlCmd, args, Integer.class);
	}

	@Override
	public int count() {
		return this.count("");
	}

	@Override
	public int count(String condition, Object... args) {
		condition = this.checkCondition(condition);
		StringBuilder strSql = new StringBuilder();
		strSql.append("SELECT COUNT(0) AS total FROM %1$s %2$s");
		String sqlCmd = String.format(strSql.toString(), this.tableName, condition);
		return this.getJdbcTemplate().queryForObject(sqlCmd, args, Integer.class);
	}

	@Override
	public boolean isExist(String condition, Object... args) {
		condition = this.checkCondition(condition);
		StringBuilder strSql = new StringBuilder();
		strSql.append("SELECT COUNT(0) FROM %1$s %2$s");
		String sqlCmd = String.format(strSql.toString(), this.tableName, condition);
		return this.getJdbcTemplate().queryForObject(sqlCmd, args, Integer.class) > 0;
	}

	@Override
	public boolean isExistByKey(int keyValue) {
		String condition = String.format("%1$s = %2$s", this.primaryKey, keyValue);
		return this.isExist(condition);
	}

	@Override
	public boolean isExistByKey(int[] keyValues) {
		String[] keys = new String[keyValues.length];
		for (int i = 0; i < keyValues.length; i++) {
			keys[i] = Integer.toString(keyValues[i]);
		}
		return this.isExistByKey(keys);
	}

	@Override
	public boolean isExistByKey(String keyValue) {
		String condition = String.format("%s = ?", this.primaryKey);
		Object[] args = new Object[] { keyValue };
		return this.isExist(condition, args);
	}

	@Override
	public boolean isExistByKey(String[] keyValues) {
		return this.isExistByKey(StringUtils.join(keyValues, ','));
	}

	@Override
	public boolean isExistInByKey(String keyValues) {
		String condition = String.format("%1$s IN(%2$s)", this.primaryKey, keyValues);
		return this.isExist(condition);
	}

	protected SqlExpression generateInsertSqlExpression(ColumnMap columnMap, String tableName) {
		if (columnMap == null || columnMap.size() == 0) {
			throw new NullPointerException("columnMap:列映射对象不能为空或没有值");
		}

		String[] fields = new String[columnMap.size()];
		String[] argSymbols = new String[columnMap.size()];
		Object[] args = new Object[columnMap.size()];
		int[] argTypes = new int[columnMap.size()];

		int i = 0;
		for (String key : columnMap.keySet()) {
			fields[i] = key;
			argSymbols[i] = " ? ";
			args[i] = columnMap.get(key).getValue();
			argTypes[i] = columnMap.get(key).getSqlType();
			i++;
		}

		String commandText = String.format("INSERT INTO %1$s (%2$s) VALUES (%3$s)", tableName, StringUtils.join(fields,
				','), StringUtils.join(argSymbols, ','));
		return new SqlExpression(commandText, args, argTypes);
	}

	protected SqlExpression generateBatchInsertSqlExpression(List<T> entities, String tableName) {
		if (entities == null || entities.size() == 0) {
			throw new NullPointerException("entities:批量增加的数据集合不能为空.");
		}

		ColumnMap columnMap = this.getColumnMap(entities.get(0));
		String[] fields = new String[columnMap.size()];
		String[] argSymbols = new String[columnMap.size()];
		int i = 0;
		for (String key : columnMap.keySet()) {
			fields[i] = key;
			argSymbols[i] = " ? ";
			i++;
		}

		List<List<ColumnProperty>> batchParameters = new ArrayList<List<ColumnProperty>>(entities.size());
		for (T entity : entities) {
			columnMap = this.getColumnMap(entity);
			List<ColumnProperty> columnProperties = new ArrayList<ColumnProperty>(columnMap.size());
			for (String key : columnMap.keySet()) {
				columnProperties.add(columnMap.get(key));
			}
			batchParameters.add(columnProperties);
		}

		String commandText = String.format("INSERT INTO %1$s (%2$s) VALUES (%3$s)",
				tableName, StringUtils.join(fields, ','), StringUtils.join(argSymbols, ','));
		return new SqlExpression(commandText, batchParameters);
	}

	protected String generateInsertSql(ColumnMap columnMap, String tableName) {
		if (columnMap == null || columnMap.size() == 0) {
			throw new NullPointerException("columnMap:列映射对象为空或没有值");
		}

		String[] fields = new String[columnMap.size()];
		Object[] args = new Object[columnMap.size()];
		int i = 0;
		for (String key : columnMap.keySet()) {
			fields[i] = key;
			args[i] = columnMap.get(key).getValue().toString().replace("'", "''");
			i++;
		}

		return String.format("INSERT INTO %1$s (%2$s) VALUES (%3$s)",
				tableName, StringUtils.join(fields, ','), StringUtils.join(args, ','));
	}

	protected SqlExpression generateUpdateSqlExpression(String condition, ColumnMap columnMap, String tableName,
			Object... cndArgs) {
		if (columnMap == null || columnMap.size() == 0) {
			throw new NullPointerException("columnMap:列映射对象为空或没有值");
		}

		int initCapacity = columnMap.size() + (cndArgs == null ? 0 : cndArgs.length);
		ArrayList<Object> args = new ArrayList<Object>(initCapacity);
		ArrayList<String> columns = new ArrayList<String>(columnMap.size());

		for (String key : columnMap.keySet()) {
			columns.add(String.format("%s = ?", key));
			args.add(columnMap.get(key).getValue());
		}

		if (cndArgs != null && cndArgs.length > 0) {
			args.addAll(Arrays.asList(cndArgs));
		}

		String commandText = String.format("UPDATE %1$s SET %2$s %3$s ",
				tableName, StringUtils.join(columns, ','), condition);
		return new SqlExpression(commandText, args.toArray(), null);
	}

	protected SqlExpression generateBatchUpdateSqlExpression(List<T> entities, String condition,
			String[] cndColumnNames, String tableName, String... columnNames) {
		if (entities == null || entities.size() == 0) {
			throw new NullPointerException("entities:批量修改的数据集合不能为空.");
		}

		ColumnMap columnMap = this.getColumnMap(entities.get(0), columnNames);
		ArrayList<String> columns = new ArrayList<String>(columnMap.size());
		for (String key : columnMap.keySet()) {
			columns.add(String.format("%s = ?", key));
		}

		List<List<ColumnProperty>> batchParameters = new ArrayList<>(entities.size());
		for (T entity : entities) {
			columnMap = this.getColumnMap(entity, columnNames);
			int initCapacity = columnMap.size() + (cndColumnNames == null ? 0 : cndColumnNames.length);
			List<ColumnProperty> columnProperties = new ArrayList<>(initCapacity);
			for (String key : columnMap.keySet()) {
				columnProperties.add(columnMap.get(key));
			}
			if (cndColumnNames != null && cndColumnNames.length > 0) {
				columnMap = this.getColumnMap(entity, false, cndColumnNames);
				for (String cndColumnName : cndColumnNames) {
					columnProperties.add(columnMap.get(cndColumnName));
				}
			}
			batchParameters.add(columnProperties);
		}

		String commandText = String.format("UPDATE %1$s SET %2$s %3$s ",
				tableName, StringUtils.join(columns, ','), condition);
		return new SqlExpression(commandText, batchParameters);
	}

	protected String generateUpdateSql(String condition, ColumnMap columnMap, String tableName) {
		if (columnMap == null || columnMap.size() == 0) {
			throw new NullPointerException("columnMap:列映射对象不能为空或没有值");
		}

		String[] columns = new String[columnMap.size()];
		int i = 0;
		for (String key : columnMap.keySet()) {
			columns[i++] = String.format("%1$s = '%2$s'", key,
					columnMap.get(key).getValue().toString().replace("'", "''"));
		}

		return String.format("UPDATE %1$s SET %2$s %3$s ", tableName, StringUtils.join(columns, ','), condition);
	}

	protected boolean containWhere(String condition) {
		return (StringUtils.isNotBlank(condition) && condition.trim().toUpperCase().startsWith("WHERE"));
	}

	protected ColumnMap getColumnMap(T entity, String... columnNames) {
		return this.getColumnMap(entity, true, columnNames);
	}

	protected ColumnMap getColumnMap(T entity, boolean isFilter, String... columnNames) {
		if (entity == null) {
			throw new NullPointerException("entity:对象不能为空");
		}
		return EntityMapper.getColumnMap(entity, true, columnNames);
	}

	private String checkCondition(String condition) {
		if (this.containWhere(condition)) {
			return condition;
		}
		if (StringUtils.isNotBlank(condition)) {
			return "WHERE " + condition;
		}
		return StringUtils.EMPTY;
	}

	private Object getPrimaryKeyValue(Class<?> classz, T entity) {
		Object value = null;
		Field[] fields = classz.getDeclaredFields();
		if (fields == null || fields.length == 0) {
			return value;
		}

		try {
			for (Field field : fields) {
				Column column = field.getAnnotation(Column.class);
				if (column != null && column.isPrimaryKey()) {
					field.setAccessible(true);
					return field.get(entity);
				}
			}
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}

		return value;
	}

	private String getColumns(String... columnNames) {
		return (columnNames == null || columnNames.length == 0) ? "*" : StringUtils.join(columnNames, ',');
	}

	@SuppressWarnings("unchecked")
	private Class<T> getParameterizedType(Class<?> type) {
		return (Class<T>) ((ParameterizedType) type.getGenericSuperclass()).getActualTypeArguments()[0];
	}
}
