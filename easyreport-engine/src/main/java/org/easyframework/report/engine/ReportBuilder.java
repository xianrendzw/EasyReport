package org.easyframework.report.engine;

/**
 * 报表生成接口。
 */
public interface ReportBuilder {

	/**
	 * 画报表表头
	 */
	void drawTableHeaderRows();

	/**
	 * 画报表表体中的每一行
	 */
	void drawTableBodyRows();

	/**
	 * 画报表表尾
	 */
	void drawTableFooterRows();

	/**
	 * 获取最终生成的报表html table代码
	 * 
	 * @return 报表html table代码
	 */
	String getTable();
}
