package org.easyframework.report.engine.query;

import java.util.List;

import org.easyframework.report.engine.data.ReportMetaDataColumn;
import org.easyframework.report.engine.data.ReportMetaDataRow;

/**
 * 报表查询器接口
 * 
 *
 */
public interface Queryer {

	/**
	 * 获取报表原始数据行集合
	 * 
	 * @return List<ReportMetaDataRow>
	 */
	List<ReportMetaDataRow> getMetaDataRows();

	/**
	 * 获取报表原始数据列集合
	 * 
	 * @return List<ReportMetaDataColumn>
	 */
	List<ReportMetaDataColumn> getMetaDataColumns();
}
