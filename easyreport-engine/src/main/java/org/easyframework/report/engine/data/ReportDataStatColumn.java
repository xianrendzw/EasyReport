package org.easyframework.report.engine.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 报表统计列树节点类
 */
public class ReportDataStatColumn extends ReportDataColumn {
	private final List<ReportDataColumn> children = new ArrayList<ReportDataColumn>(0);

	public ReportDataStatColumn(ReportMetaDataColumn metaDataColumn) {
		super(metaDataColumn);
	}

	/**
	 * 获取报表统计列
	 * 
	 * @return {@link ReportDataColumn}
	 */
	public ReportDataColumn getReportDataColumn() {
		return new ReportDataColumn(this.getMetaData());
	}

	/**
	 * 获取报表统计列的所有子统计列集合
	 * 
	 * @return {@link List<ReportDataColumn>}
	 */
	public List<ReportDataColumn> getChildren() {
		return this.children;
	}
}
