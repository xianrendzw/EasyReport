package org.easyframework.report.data.jdbc;

import java.util.List;

import org.easyframework.report.data.PageInfo;
import org.easyframework.report.data.SortType;

public interface IBaseDao<T> {
	/**
	 * 向数据库中添加一条记录
	 *
	 * @param entity 实体对象
	 * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
	 */
	int insert(T entity);

	/**
	 * 向数据库中添加一条记录，并返回插入记录的ID值。
	 *
	 * @param entity 实体对象
	 * @return 插入记录的数据库自增标识
	 */
	int insertWithId(T entity);

	/**
	 * 批量向表中插入记录
	 *
	 * @param entities 插入的对象集合
	 */
	int batchInsert(List<T> entities);

	/**
	 * 根据指定条件,从数据库中删除记录
	 *
	 * @param condition 删除记录的条件语句,不需要带SQL语句的Where关键字
	 * @param args SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
	 * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
	 */
	int delete(String condition, Object... args);

	/**
	 * 清空表中的所有数据
	 *
	 * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
	 */
	int deleteAll();

	/**
	 * 清空表中的所有数据,且不记录数据库日志(调用truncate语句)。
	 */
	void truncate();

	/**
	 * 根据指定记录的ID,从数据库中删除指定记录(用于整型主键)。
	 *
	 * @param keyValue 指定记录的ID值
	 * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
	 */
	int deleteByKey(int keyValue);

	/**
	 * 根据指定记录的ID,从数据库中删除指定记录(用于整型主键)。
	 *
	 * @param keyValue 指定记录的ID值
	 * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
	 */
	int deleteByKey(String keyValue);

	/**
	 * 从数据库中删除一个或多个指定标识的记录。
	 *
	 * @param keyValues 记录标识数组
	 * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
	 */
	int deleteByKey(int[] keyValues);

	/**
	 * 从数据库中删除一个或多个指定标识的记录。
	 *
	 * @param keyValues 记录标识数组
	 * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
	 */
	int deleteByKey(String[] keyValues);

	/**
	 * 删除多条数据 条件为符合多个指定列的值 使用 IN 匹配
	 *
	 * @param keyValues 主键ID值集合 以,号分割
	 * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
	 */
	int deleteInByKey(String keyValues);

	/**
	 * 更新数据库表中指定主键值的记录
	 *
	 * @param entity
	 * @param columnNames 指定数据库表中需要更新的列名集合
	 * @return
	 */
	int update(T entity, String... columnNames);

	/**
	 * 更新数据库表中指定主键值的记录
	 *
	 * @param entity 实体对象
	 * @param keyValue 表中主键的值
	 * @param columnNames 指定数据库表中需要更新的列名集合
	 * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
	 */
	int update(T entity, int keyValue, String... columnNames);

	/**
	 * 更新数据库表中指定主键值的记录
	 *
	 * @param entity 实体对象
	 * @param keyValue 表中主键的值
	 * @param columnNames 指定数据库表中需要更新的列名集合
	 * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
	 */
	int update(T entity, String keyValue, String... columnNames);

	/**
	 * 更新数据库表中的记录
	 *
	 * @param condition 不带Where的更新条件
	 * @param args SQL参数对应值的集合,该数组的值必须与columnNames按顺序对应
	 * @param columnNames 指定数据库表中需要更新的列名集合
	 * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
	 */
	int update(String condition, Object[] args, String[] columnNames);

	/**
	 * 更新数据库表中的记录
	 *
	 * @param entity 实体对象
	 * @param condition 不带Where的更新条件
	 * @param args SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
	 * @param columnNames 指定数据库表中需要更新的列名集合
	 * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
	 */
	int update(T entity, String condition, Object[] args, String... columnNames);

	/**
	 * 更新一条数据 条件为符合指定列的值 使用 IN 匹配 提示：In的条件仅用于主键列
	 *
	 * @param entity 实体对象
	 * @param keyValues 主键ID值 以,号分割
	 * @param columnNames 指定数据库表中需要更新的列名集合
	 * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
	 */
	int updateInByKey(T entity, String keyValues, String... columnNames);

	/**
	 * 更新所有记录为指定entity中属性的值.(注：该方法懂用)
	 *
	 * @param entity 实体对象
	 * @param columnNames 指定数据库表中需要更新的列名集合
	 * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
	 */
	int updateAll(T entity, String... columnNames);

	/**
	 * 根据表中的主键批量向表中更新记录
	 *
	 * @param entities 更新的对象集合
	 * @param columnNames 指定数据库表中需要更新的列名集合
	 * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
	 */
	int batchUpdate(List<T> entities, String... columnNames);

	/**
	 * 批量向表中更新记录
	 *
	 * @param entities 更新的对象集合
	 * @param condition 指定的条件,不要求带SQL语句Where关键字的条件
	 * @param cndColumnNames 条件中所命名用的列名
	 * @param columnNames 指定数据库表中需要更新的列名集合
	 * @return 返回影响记录的行数,-1表示操作失败,大于-1表示成功
	 */
	int batchUpdate(List<T> entities, String condition, String[] cndColumnNames, String... columnNames);

	/**
	 * 从数据库中获取一条满足指定条件的实体对象集合(返回值需要判断是否为null)。
	 *
	 * @param condition 指定的条件,不要求带SQL语句Where关键字的条件
	 * @param args SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
	 * @param columnNames 需要从数据库中筛选的列名
	 * @return 实体对象或null
	 */
	T queryOne(String condition, Object[] args, String... columnNames);

	/**
	 * 从数据库中获取所有的实体对象集合(返回值不需判断是否为null)。
	 *
	 * @return 实体对象集合
	 */
	List<T> query();

	/**
	 * 从数据库中获取所有的实体对象集合(返回值不需判断是否为null)。
	 *
	 * @param columnNames 需要从数据库中筛选的列名
	 * @return 实体对象集合
	 */
	List<T> query(String[] columnNames);

	/**
	 * 从数据库中获取满足指定条件的实体对象集合(返回值不需判断是否为null)。
	 *
	 * @param sortItem
	 * @param sortType
	 * @param columnNames
	 * @return
	 */
	List<T> query(String sortItem, SortType sortType, String... columnNames);

	/**
	 * 从数据库中获取满足指定条件的实体对象集合(返回值不需判断是否为null)。
	 *
	 * @param condition 指定的条件,不要求带SQL语句Where关键字的条件
	 * @param columnNames 需要从数据库中筛选的列名
	 * @return 指定条件的表中的实体对象集合
	 */
	List<T> query(String condition, String... columnNames);

	/**
	 * 从数据库中获取满足指定条件的实体对象集合(返回值不需判断是否为null)。
	 *
	 * @param condition 指定的条件,不要求带SQL语句Where关键字的条件
	 * @param args SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
	 * @param columnNames 需要从数据库中筛选的列名
	 * @return 实体对象集合
	 */
	List<T> query(String condition, Object[] args, String... columnNames);

	/**
	 * 从数据库中获取满足指定条件的实体对象集合(返回值不需判断是否为null)。
	 *
	 * @param condition 指定的条件,不要求带SQL语句Where关键字的条件
	 * @param sortItem 排序字段名称，不要求带ORDER BY关键字,只要指定排序字段名称即可
	 * @param sortType SQL语句排序类型
	 * @param args SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
	 * @param columnNames 需要从数据库中筛选的列名
	 * @return 实体对象集合
	 */
	List<T> query(String condition, String sortItem, SortType sortType, Object[] args, String... columnNames);

	/**
	 * 利用数据库表的limit关键字属性对数据进行分页查询的方法(返回值不需判断是否为null),
	 *
	 * @param condition 指定的条件,不要求带SQL语句Where关键字的条件
	 * @param page 分页参数对象
	 * @param columnNames 需要从数据库中筛选的列名
	 * @return 当前分页的所有取实体对象集合
	 */
	List<T> query(String condition, PageInfo page, String... columnNames);

	/**
	 * 利用数据库表的limit关键字属性对数据进行分页查询的方法(返回值不需判断是否为null),
	 *
	 * @param condition 指定的条件,不要求带SQL语句Where关键字的条件
	 * @param page 分页参数对象
	 * @param args SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
	 * @param columnNames 需要从数据库中筛选的列名
	 * @return 当前分页的所有取实体对象集合
	 */
	List<T> query(String condition, PageInfo page, Object[] args, String... columnNames);

	/**
	 * 从数据库中获取指定主键值的实体对象(返回值需要判断是否为null)。
	 *
	 * @param keyValue 主键ID值集合 以逗号分割(eg:1,2,3,4)
	 * @param columnNames 需要从数据库中筛选的列名
	 * @return 实体对象
	 */
	T queryByKey(String keyValue, String... columnNames);

	/**
	 * 从数据库中获取指定主键值的实体对象(返回值需要判断是否为null)。
	 *
	 * @param keyValue 主键ID值
	 * @param columnNames 需要从数据库中筛选的列名
	 * @return 实体对象
	 */
	T queryByKey(int keyValue, String... columnNames);

	/**
	 * 从数据库中获取所有的实体对象集合(返回值不需判断是否为null)。
	 * 
	 * @param keyValues
	 * @param columnNames 需要从数据库中筛选的列名
	 * @return 实体对象集合
	 */
	List<T> queryInByKey(String keyValues, String... columnNames);

	/**
	 * 获取数据库中表的最大ID值(没有记录的时候返回0)。
	 *
	 * @return 最大ID值
	 */
	int queryMaxId();

	/**
	 * 获取数据库中该对象指定属性的最大值(没有记录的时候返回0)。
	 *
	 * @param condition 要求带SQL语句Where关键字的条件
	 * @param columnName 表中的字段(列)名称,字段的值必需是整型数据
	 * @param args SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
	 * @return 指定属性的最大值
	 */
	int queryMaxValue(String condition, String columnName, Object... args);

	/**
	 * 获取数据库中表的记录总数
	 *
	 * @return 表的记录总数
	 */
	int count();

	/**
	 * 获取数据库表中指定条件的记录总数
	 *
	 * @param condition 要求带SQL语句Where关键字的条件，如果不带Where关键字该方法将对表中所有记录执行操作
	 * @param args SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
	 * @return 指定条件的记录总数
	 */
	int count(String condition, Object... args);

	/**
	 * 查询数据库,判断指定条件的记录是否存在。
	 *
	 * @param condition 指定的条件,不需要带SQL语句的Where关键字
	 * @param args SQL参数对应值的集合,如果条件字符串中含有参数标记,则必须设置该数组的值
	 * @return 存在则返回true,否则为false。
	 */
	boolean isExist(String condition, Object... args);

	/**
	 * 查询数据库,判断指定标识的记录是否存在。
	 *
	 * @param keyValue 主键ID值
	 * @return 存在则返回true,否则还false
	 */
	boolean isExistByKey(int keyValue);

	/**
	 * 查询数据库,判断指定标识的记录是否存在。
	 *
	 * @param keyValues 主键ID值集合
	 * @return 存在则返回true,否则还false
	 */
	boolean isExistByKey(int[] keyValues);

	/**
	 * 查询数据库,判断指定标识的记录是否存在。
	 *
	 * @param keyValue 主键ID值
	 * @return 存在则返回true,否则还false
	 */
	boolean isExistByKey(String keyValue);

	/**
	 * 查询数据库,判断指定标识的记录是否存在。
	 *
	 * @param keyValues 主键ID值集合
	 * @return 存在则返回true,否则还false
	 */
	boolean isExistByKey(String[] keyValues);

	/**
	 * 查询数据库,判断指定标识的记录是否存在，使用 IN 匹配
	 *
	 * @param keyValues 主键ID值 以,号分割
	 * @return 存在则返回true,否则还false
	 */
	boolean isExistInByKey(String keyValues);
}
