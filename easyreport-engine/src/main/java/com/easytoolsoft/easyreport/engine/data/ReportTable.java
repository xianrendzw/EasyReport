package com.easytoolsoft.easyreport.engine.data;

/**
 * 报表类
 *
 * @author tomdeng
 */
public class ReportTable {
    private final String htmlText;
    private final String sqlText;
    private final int metaDataRowCount;
    private final int metaDataColumnCount;

    public ReportTable(final String htmlText, final String sqlText, final int metaDataRowCount,
                       final int metaDataColumnCount) {
        this.htmlText = htmlText;
        this.sqlText = sqlText;
        this.metaDataRowCount = metaDataRowCount;
        this.metaDataColumnCount = metaDataColumnCount;
    }

    public String getHtmlText() {
        return this.htmlText;
    }

    public String getSqlText() {
        return this.sqlText;
    }

    public long getMetaDataRowCount() {
        return this.metaDataRowCount;
    }

    public int getMetaDataColumnCount() {
        return this.metaDataColumnCount;
    }
}
