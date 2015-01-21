package com.easytoolsoft.easyreport.data.mapping;

import java.util.HashMap;

@SuppressWarnings("serial")
public class ColumnMap extends HashMap<String, ColumnProperty> {

	public ColumnMap() {
	}

	public ColumnMap(int initialCapacity) {
		super(initialCapacity);
	}

	public ColumnMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}
}
