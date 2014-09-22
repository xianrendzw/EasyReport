package org.easyframework.report.engine.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 报表元数据类
 */
public class ReportMetaDataSet {
	private final List<ReportMetaDataRow> rows;
	private final List<ReportMetaDataColumn> columns;
	private List<ReportMetaDataColumn> sqlColumns;
	private List<ReportMetaDataColumn> layoutColumns;
	private List<ReportMetaDataColumn> dimColumns;
	private List<ReportMetaDataColumn> statColumns;

	/**
	 * 构造函数
	 * 
	 * @param rows 报表元数据行集合
	 * @param columns 报表元数据列集合
	 * @param displayedStatColumns 报表中需要显示统计列
	 */
	public ReportMetaDataSet(List<ReportMetaDataRow> rows, List<ReportMetaDataColumn> columns,
			Set<String> displayedStatColumns) {
		this.rows = rows;
		this.columns = columns;
		this.initilizeColumn(displayedStatColumns);
	}

	/**
	 * 获取报表的元数据行集合
	 * 
	 * @return List<ReportMetaDataRow>
	 */
	public List<ReportMetaDataRow> getRows() {
		return this.rows;
	}

	/**
	 * 获取报表的所有元数据列
	 * 
	 * @return List<ReportMetaDataColumn>
	 */
	public List<ReportMetaDataColumn> getColumns() {
		return this.columns;
	}

	/**
	 * 获取报表的SQL语句对应的元数据列(不包含计算列)
	 */
	public List<ReportMetaDataColumn> getSqlColumns() {
		return this.sqlColumns;
	}

	/**
	 * 获取报表的布局元数据列
	 */
	public List<ReportMetaDataColumn> getLayoutColumns() {
		return this.layoutColumns;
	}

	/**
	 * 获取报表的维度元数据列
	 */
	public List<ReportMetaDataColumn> getDimColumns() {
		return this.dimColumns;
	}

	/**
	 * 获取报表的统计(含计算)元数据列
	 */
	public List<ReportMetaDataColumn> getStatColumns() {
		return this.statColumns;
	}

	private void initilizeColumn(Set<String> displayedStatColumns) {
		this.sqlColumns = new ArrayList<ReportMetaDataColumn>();
		this.layoutColumns = new ArrayList<ReportMetaDataColumn>();
		this.dimColumns = new ArrayList<ReportMetaDataColumn>();
		this.statColumns = new ArrayList<ReportMetaDataColumn>();

		for (ReportMetaDataColumn column : this.columns) {
			if (column.getType() != ColumnType.COMPUTED) {
				this.sqlColumns.add(column);
			}
			if (column.getType() == ColumnType.LAYOUT) {
				this.layoutColumns.add(column);
			}
			else if (column.getType() == ColumnType.DIMENSION) {
				this.dimColumns.add(column);
			}
			else if (column.getType() == ColumnType.STATISTICAL ||
					column.getType() == ColumnType.COMPUTED) {
				if (displayedStatColumns.size() > 0 && !displayedStatColumns.contains(column.getName())) {
					column.setHidden(true);
				}
				this.statColumns.add(column);
			}
		}
	}
}
