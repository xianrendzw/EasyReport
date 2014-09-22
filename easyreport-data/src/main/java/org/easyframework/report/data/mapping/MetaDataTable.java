package org.easyframework.report.data.mapping;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.easyframework.report.data.annotations.Column;

/**
 * 映射实体(对应于数据库中的表)的元数据类。
 */
public class MetaDataTable {

	private String tableName;
	private Class<?> entityType;
	private HashMap<String, MetaDataColumn> metaDataColumns;

	public MetaDataTable(Class<?> entityType, String tableName) {
		this.entityType = entityType;
		this.tableName = tableName;
		this.metaDataColumns = this.getMetaDataColumns(entityType);
	}

	private HashMap<String, MetaDataColumn> getMetaDataColumns(Class<?> entityType) {
		Field[] fields = entityType.getDeclaredFields();
		if (fields == null || fields.length == 0) {
			return this.metaDataColumns;
		}

		this.metaDataColumns = new HashMap<>(fields.length);
		for (Field field : fields) {
			Column column = field.getAnnotation(Column.class);
			if (column == null) {
				continue;
			}

			MetaDataColumn metacolumn = new MetaDataColumn(field, column);
			this.metaDataColumns.put(metacolumn.getName().toLowerCase(), metacolumn);
		}

		return this.metaDataColumns;
	}

	public String getTableName() {
		return this.tableName;
	}

	public Class<?> getEntityType() {
		return this.entityType;
	}

	public HashMap<String, MetaDataColumn> getMetaDataColumns() {
		return this.metaDataColumns;
	}
}
