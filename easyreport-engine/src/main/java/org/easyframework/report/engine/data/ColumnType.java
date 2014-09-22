package org.easyframework.report.engine.data;

/**
 * 
 * 报表列类型
 */
public enum ColumnType {
	/**
	 * 布局列
	 */
	LAYOUT(1),

	/**
	 * 维度列
	 */
	DIMENSION(2),

	/**
	 * 统计列
	 */
	STATISTICAL(3),

	/**
	 * 计算列
	 */
	COMPUTED(4);

	private final int value;

	private ColumnType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static ColumnType valueOf(int arg) {
		if (arg == 1)
			return LAYOUT;
		if (arg == 2)
			return DIMENSION;
		if (arg == 3)
			return STATISTICAL;
		if (arg == 4)
			return COMPUTED;
		return DIMENSION;
	}
}
