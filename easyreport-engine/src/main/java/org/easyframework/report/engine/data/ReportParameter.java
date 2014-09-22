package org.easyframework.report.engine.data;

import java.util.HashSet;
import java.util.Set;

/**
 * 报表参数类
 */
public class ReportParameter {
	private String name;
	private LayoutType layout;
	private LayoutType statColumnLayout;
	private String sqlText;
	private String jsonMetaColumns;
	private Set<String> displayedStatColumns;

	public ReportParameter() {
	}

	/**
	 * 报表参数构造函数
	 * 
	 * @param name 报表名称
	 * @param layout 报表布局形式 (1:横向;2:纵向)
	 * @param statColumnLayout 报表统计列或计算列布局形式 (1:横向;2:纵向)
	 * @param sqlText 报表sql查询语句
	 * @param jsonMetaColumns JSON格式的报表元数据列集合
	 * @param displayedStatColumns 报表中展示的统计(含计算)列名集合
	 */
	public ReportParameter(String name, int layout, int statColumnLayout,
			String sqlText, String jsonMetaColumns, Set<String> displayedStatColumns) {
		this.name = name;
		this.layout = LayoutType.valueOf(layout);
		this.statColumnLayout = LayoutType.valueOf(statColumnLayout);
		this.sqlText = sqlText;
		this.jsonMetaColumns = jsonMetaColumns;
		this.displayedStatColumns = displayedStatColumns;
	}

	/**
	 * 获取报表名称
	 * 
	 * @return 报表名称
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置报表名称
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取报表布局形式(1:横向;2:纵向)
	 * 
	 * @return Layout
	 */
	public LayoutType getLayout() {
		return this.layout;
	}

	/**
	 * 设置报表布局形式(1:横向;2:纵向)
	 * 
	 * @param layoutType 报表布局形式(1:横向;2:纵向)
	 */
	public void setLayout(int layout) {
		this.layout = LayoutType.valueOf(layout);
	}

	/**
	 * 获取报表统计列或计算列布局形式 (1:横向;2:纵向)
	 * 
	 * @return (1:横向;2:纵向)
	 */
	public LayoutType getStatColumnLayout() {
		return statColumnLayout;
	}

	/**
	 * 设置报表统计列或计算列布局形式 (1:横向;2:纵向)
	 * 
	 * @param statColumnLayout (1:横向;2:纵向)
	 */
	public void setStatColumnLayout(LayoutType statColumnLayout) {
		this.statColumnLayout = statColumnLayout;
	}

	/**
	 * 获取报表SQL语句
	 * 
	 * @return 报表SQL语句
	 */
	public String getSqlText() {
		return this.sqlText;
	}

	/**
	 * 设置报表SQL语句
	 * 
	 * @param sqlText
	 */
	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
	}

	/**
	 * 获取JSON格式的报表元数据列集合
	 * 
	 * @return JSON字符串格式的报表元数据列集合
	 */
	public String getJsonMetaColumns() {
		return jsonMetaColumns;
	}

	/**
	 * 设置JSON格式的报表元数据列集合
	 * 
	 * @param jsonMetaColumns JSON字符串格式的报表元数据列集合
	 */
	public void setJsonMetaColumns(String jsonMetaColumns) {
		this.jsonMetaColumns = jsonMetaColumns;
	}

	/**
	 * 获取报表中展示的统计(含计算)列名集合。
	 * 
	 * 如果未设置任何列名，则在报表中显示全部统计统计(含计算)列
	 * 
	 * @return 报表中展示的统计(含计算)列名集合
	 */
	public Set<String> getDisplayedStatColumns() {
		return displayedStatColumns == null ? new HashSet<String>(0) : this.displayedStatColumns;
	}

	/**
	 * 设置报表中展示的统计(含计算)列名集合。
	 * 
	 * @param displayedStatColumns 报表中展示的统计(含计算)列名集合
	 */
	public void setDisplayedStatColumns(Set<String> displayedStatColumns) {
		this.displayedStatColumns = displayedStatColumns;
	}
}
