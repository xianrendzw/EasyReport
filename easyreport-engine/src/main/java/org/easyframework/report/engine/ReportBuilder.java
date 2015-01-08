package org.easyframework.report.engine;

import org.easyframework.report.engine.data.ReportTable;

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
	 * 获取最终生成的报表
	 * 
	 * @return ReportTable
	 */
	ReportTable getTable();
}
