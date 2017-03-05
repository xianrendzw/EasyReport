package com.easytoolsoft.easyreport.engine;

/**
 * @author tomdeng
 */
public class ReportDirector {
    private ReportBuilder builder;

    public ReportDirector(ReportBuilder builder) {
        this.builder = builder;
    }

    public void build() {
        this.builder.drawTableHeaderRows();
        this.builder.drawTableBodyRows();
        this.builder.drawTableFooterRows();
    }
}
