package com.easyreport.data.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.sql.Types;

/**
 * 实体列注解类。
 */
@Target({ FIELD })
@Retention(RUNTIME)
public @interface Column {

	/**
	 * 是否为自增标识列
	 * 
	 * @return true|false
	 */
	boolean isIdentity() default false;

	/**
	 * 是否为可为空列
	 * 
	 * @return true|false
	 */
	boolean isNullable() default false;

	/**
	 * 是否为主键列
	 * 
	 * @return true|false
	 */
	boolean isPrimaryKey() default false;

	/**
	 * 是否为外键列
	 * 
	 * @return true|false
	 */
	boolean isForeignKey() default false;

	/**
	 * 是否为GUID键列
	 * 
	 * @return
	 */
	boolean isUnique() default false;

	/**
	 * 该列是否可以重写
	 * 
	 * @return true|false
	 */
	boolean isOverride() default false;

	/**
	 * 当进行插入与更新操作时是否忽略该列
	 * 
	 * @return true|false
	 */
	boolean isIgnored() default false;

	/**
	 * 列中数据类型的长度,用于服务端数据验证
	 * 
	 * @return
	 */
	int length() default 0;

	/**
	 * 列名称,对应数据库中的字段名称
	 * 
	 * @return
	 */
	String name() default "";

	/**
	 * 列所使用的公式(如果为计算列)
	 * 
	 * @return
	 */
	String formula() default "";

	/**
	 * 列对应java数据类型
	 * 
	 * @return
	 */
	String ColumnType() default "";

	/**
	 * 列对应的唯一键名称(如果该列为唯一键)
	 * 
	 * @return
	 */
	String uniqueKey() default "";

	/**
	 * 列对应的索引名称(如果该列为索引)
	 * 
	 * @return
	 */
	String index() default "";

	/**
	 * 列对应SQL数据类型
	 * 
	 * @return
	 */
	int sqlType() default Types.JAVA_OBJECT;

	/**
	 * 列对应数据库check检查
	 * 
	 * @return
	 */
	String check() default "";

	/**
	 * 列的默认值
	 * 
	 * @return
	 */
	String value() default "";

	/**
	 * 列名对应排序号
	 * 
	 * @return
	 */
	int seq() default -1;
}
