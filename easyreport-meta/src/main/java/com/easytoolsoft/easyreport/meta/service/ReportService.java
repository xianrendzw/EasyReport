package com.easytoolsoft.easyreport.meta.service;

import java.util.List;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.engine.data.ReportQueryParamItem;
import com.easytoolsoft.easyreport.meta.domain.Report;
import com.easytoolsoft.easyreport.meta.domain.example.ReportExample;
import com.easytoolsoft.easyreport.meta.domain.options.QueryParameterOptions;
import com.easytoolsoft.easyreport.meta.domain.options.ReportOptions;
import com.easytoolsoft.easyreport.mybatis.pager.PageInfo;
import com.easytoolsoft.easyreport.mybatis.service.CrudService;

/**
 * 报表服务类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface ReportService extends CrudService<Report, ReportExample, Integer> {
    /**
     * @param page
     * @param fieldName
     * @param categoryId
     * @return
     */
    List<Report> getByPage(PageInfo page, String fieldName, Integer categoryId);

    /**
     * @param id
     * @param json
     * @return
     */
    boolean saveQueryParams(int id, String json);

    /**
     * @param uid
     * @return
     */
    Report getByUid(String uid);

    /**
     * @param id
     * @return
     */
    String getSqlText(int id);

    /**
     * @param dsId
     * @param sqlText
     * @return
     */
    List<ReportMetaDataColumn> getMetaDataColumns(int dsId, String sqlText);

    /**
     * @param dsId
     * @param sqlText
     */
    void explainSqlText(int dsId, String sqlText);

    /**
     * @param dsId
     * @param sqlText
     * @return
     */
    List<ReportMetaDataColumn> executeSqlText(int dsId, String sqlText);

    /**
     * @param dsId
     * @param sqlText
     * @return
     */
    List<ReportQueryParamItem> executeQueryParamSqlText(int dsId, String sqlText);

    /**
     * 解析json格式的报表元数据列为ReportMetaDataColumn对象集合
     *
     * @return List<ReportMetaDataColumn>
     */
    List<ReportMetaDataColumn> parseMetaColumns(String json);

    /**
     * 解析json格式的报表查询参数为QueryParameter对象集合
     *
     * @return List<QueryParameterOptions>
     */
    List<QueryParameterOptions> parseQueryParams(String json);

    /**
     * 解析json格式的报表选项ReportOptions
     *
     * @return ReportOptions
     */
    ReportOptions parseOptions(String json);

    /**
     * @param dsId
     * @return
     */
    ReportDataSource getReportDataSource(int dsId);
}
