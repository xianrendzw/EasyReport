package com.easytoolsoft.easyreport.engine;

import com.easytoolsoft.easyreport.engine.data.ReportTable;

/**
 * 报表生成接口。
 *
 * @author tomdeng
 */
public interface ReportBuilder {

    /**
     * 生成报表表头
     */
    void drawTableHeaderRows();

    /**
     * 生成报表表体中的每一行
     */
    void drawTableBodyRows();

    /**
     * 生成报表表尾
     */
    void drawTableFooterRows();

    /**
     * 获取生成的报表对象
     *
     * @return ReportTable
     */
    ReportTable getTable();
}
