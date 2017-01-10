package com.easytoolsoft.easyreport.domain.metadata.service;

import com.easytoolsoft.easyreport.data.helper.PageInfo;
import com.easytoolsoft.easyreport.data.service.ICrudService;
import com.easytoolsoft.easyreport.domain.metadata.example.ReportExample;
import com.easytoolsoft.easyreport.domain.metadata.po.Report;
import com.easytoolsoft.easyreport.domain.metadata.po.ReportOptions;
import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.engine.data.ReportQueryParamItem;
import com.easytoolsoft.easyreport.domain.metadata.vo.QueryParameter;

import java.util.List;

/**
 * 报表服务类
 *
 * @author Tom Deng
 */
public interface IReportService extends ICrudService<Report, ReportExample> {
    List<Report> getByPage(PageInfo page, String fieldName, Integer categoryId);

    boolean saveQueryParams(int id, String json);

    Report getByUid(String uid);

    String getSqlText(int id);

    List<ReportMetaDataColumn> getMetaDataColumns(int dsId, String sqlText);

    void explainSqlText(int dsId, String sqlText);

    List<ReportMetaDataColumn> executeSqlText(int dsId, String sqlText);

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
     * @return List<QueryParameterPo>
     */
    List<QueryParameter> parseQueryParams(String json);

    /**
     * 解析json格式的报表选项ReportOptions
     *
     * @return ReportOptions
     */
    ReportOptions parseOptions(String json);

    ReportDataSource getReportDataSource(int dsId);
}
