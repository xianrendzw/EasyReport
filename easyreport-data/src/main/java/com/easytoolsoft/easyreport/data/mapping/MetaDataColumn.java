package com.easytoolsoft.easyreport.data.mapping;

import java.lang.reflect.Field;

import com.easytoolsoft.easyreport.data.annotations.Column;

/**
 * 映射列(对应于数据库中的列)的元数据类
 */
public class MetaDataColumn {

	private Field field;
	private Column columnAnnotation;

	public MetaDataColumn(Field field, Column columnAnnotation) {
		this.field = field;
		this.columnAnnotation = columnAnnotation;
		this.field.setAccessible(true);
	}

	public Column getAnnotation() {
		return this.columnAnnotation;
	}

	public Class<?> getDataType() {
		return this.field.getType();
	}

	public String getName() {
		return this.columnAnnotation.name();
	}

	public Field getField() {
		return this.field;
	}

	public <TEntity> Object getValue(TEntity entity) {
		try {
			return this.field.get(entity);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}
	}
}
