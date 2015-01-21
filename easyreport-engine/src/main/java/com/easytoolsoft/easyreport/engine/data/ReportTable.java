package com.easytoolsoft.easyreport.engine.data;

/**
 * 报表类
 *
 */
public class ReportTable {
	private final String htmlText;
	private final String sqlText;
	private final int metaDataRowCount;

	public ReportTable(String htmlText, String sqlText, int metaDataRowCount) {
		this.htmlText = htmlText;
		this.sqlText = sqlText;
		this.metaDataRowCount = metaDataRowCount;
	}

	public String getHtmlText() {
		return htmlText;
	}

	public String getSqlText() {
		return sqlText;
	}

	public long getMetaDataRowCount() {
		return this.metaDataRowCount;
	}
}
