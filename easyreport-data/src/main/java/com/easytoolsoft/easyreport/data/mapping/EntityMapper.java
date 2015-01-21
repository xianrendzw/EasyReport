package com.easytoolsoft.easyreport.data.mapping;

import java.util.HashMap;

/**
 * 实体映射器类,实现把实体转换成对应的数据库字段的相关操作.
 */
public final class EntityMapper {

	private EntityMapper() {
	}

	public static <TEntity> ColumnMap getColumnMap(TEntity entity, boolean isFilter, String... columnNames) {
		return getColumnMap(entity, "", isFilter, columnNames);
	}

	public static <TEntity> ColumnMap getColumnMap(TEntity entity, String entityName, boolean isFilter,
			String... columnNames) {
		MetaDataTable metaDataTable = new MetaDataTable(entity.getClass(), entityName);
		if (columnNames != null && columnNames.length > 0) {
			return getColumnMapByColumnNames(entity, metaDataTable, isFilter, columnNames);
		}

		ColumnMap mapTable = new ColumnMap(metaDataTable.getMetaDataColumns().size());
		for (String colName : metaDataTable.getMetaDataColumns().keySet()) {
			MetaDataColumn metaColumn = metaDataTable.getMetaDataColumns().get(colName);
			if (isFilter && isFilterColumn(metaColumn)) {
				continue;
			}
			ColumnProperty colProperty = new ColumnProperty(metaColumn.getValue(entity), metaColumn.getAnnotation()
					.sqlType());
			mapTable.put(metaColumn.getName(), colProperty);
		}

		return mapTable;
	}

	private static <TEntity> ColumnMap getColumnMapByColumnNames(TEntity entity, MetaDataTable metaDataTable,
			boolean isFilter, String... columnNames) {
		ColumnMap mapTable = new ColumnMap(columnNames.length);
		HashMap<String, MetaDataColumn> metaColumns = metaDataTable.getMetaDataColumns();

		for (String columnName : columnNames) {
			String colName = columnName.trim().toLowerCase();
			MetaDataColumn metaColumn = metaColumns.get(colName);
			if (isFilter && (!metaColumns.containsKey(colName) || isFilterColumn(metaColumn))) {
				continue;
			}
			ColumnProperty colProperty = new ColumnProperty(metaColumn.getValue(entity), metaColumn.getAnnotation()
					.sqlType());
			mapTable.put(metaColumn.getName(), colProperty);
		}

		return mapTable;
	}

	private static boolean isFilterColumn(MetaDataColumn metaColumn) {
		return (metaColumn.getAnnotation().isIgnored() || metaColumn.getAnnotation().isIdentity());
	}
}
