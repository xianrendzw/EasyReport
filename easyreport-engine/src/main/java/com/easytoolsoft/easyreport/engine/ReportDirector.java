package com.easytoolsoft.easyreport.engine;

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
