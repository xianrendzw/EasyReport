package com.easytoolsoft.easyreport.engine.query;

import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataRow;
import com.easytoolsoft.easyreport.engine.data.ReportQueryParamItem;

import java.util.List;

/**
 * 报表查询器接口
 */
public interface Queryer {
    /**
     * 从sql语句中解析出报表元数据列集合
     *
     * @param sqlText sql语句
     * @return List[ReportMetaDataColumn]
     */
    List<ReportMetaDataColumn> parseMetaDataColumns(String sqlText);

    /**
     * 从sql语句中解析出报表查询参数(如下拉列表参数）的列表项集合
     *
     * @param sqlText sql语句
     * @return List[ReportQueryParamItem]
     */
    List<ReportQueryParamItem> parseQueryParamItems(String sqlText);

    /**
     * 获取报表原始数据行集合
     *
     * @return List[ReportMetaDataRow]
     */
    List<ReportMetaDataRow> getMetaDataRows();

    /**
     * 获取报表原始数据列集合
     *
     * @return List[ReportMetaDataColumn]
     */
    List<ReportMetaDataColumn> getMetaDataColumns();
}
