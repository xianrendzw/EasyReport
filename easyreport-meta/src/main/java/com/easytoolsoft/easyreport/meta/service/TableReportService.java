package com.easytoolsoft.easyreport.meta.service;

import java.util.List;
import java.util.Map;

import com.easytoolsoft.easyreport.engine.data.ReportDataSet;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataSet;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;
import com.easytoolsoft.easyreport.engine.data.ReportTable;
import com.easytoolsoft.easyreport.engine.query.Queryer;
import com.easytoolsoft.easyreport.meta.domain.Report;
import com.easytoolsoft.easyreport.meta.form.control.HtmlCheckBoxList;
import com.easytoolsoft.easyreport.meta.form.control.HtmlDateBox;
import com.easytoolsoft.easyreport.meta.form.control.HtmlFormElement;

/**
 * 表格报表生成服务类,包括报表生成,转换,报表数据集处理等
 *
 * @author Tom Deng
 */
public interface TableReportService {
    /**
     * @param report
     * @param parameters
     * @return
     */
    ReportParameter getReportParameter(Report report, Map<?, ?> parameters);

    /**
     * @param id
     * @param formParams
     * @return
     */
    ReportTable getReportTable(int id, Map<String, Object> formParams);

    /**
     * @param uid
     * @param formParams
     * @return
     */
    ReportTable getReportTable(String uid, Map<String, Object> formParams);

    /**
     * @param report
     * @param formParams
     * @return
     */
    ReportTable getReportTable(Report report, Map<String, Object> formParams);

    /**
     * @param queryer
     * @param reportParameter
     * @return
     */
    ReportTable getReportTable(Queryer queryer, ReportParameter reportParameter);

    /**
     * @param metaDataSet
     * @param reportParameter
     * @return
     */
    ReportTable getReportTable(ReportMetaDataSet metaDataSet, ReportParameter reportParameter);

    /**
     * @param report
     * @param parameters
     * @return
     */
    ReportDataSet getReportDataSet(Report report, Map<String, Object> parameters);

    /**
     * @param httpReqParamMap
     * @param dataRange
     * @return
     */
    Map<String, Object> getBuildInParameters(Map<?, ?> httpReqParamMap, int dataRange);

    /**
     * @param httpReqParamMap
     * @return
     */
    Map<String, Object> getFormParameters(Map<?, ?> httpReqParamMap);

    /**
     * @param httpReqParamMap
     * @param dataRange
     * @return
     */
    Map<String, Object> getFormParameters(Map<?, ?> httpReqParamMap, int dataRange);

    /**
     * @param uid
     * @param buildinParams
     * @param minDisplayedStatColumn
     * @return
     */
    Map<String, HtmlFormElement> getFormElementMap(String uid, Map<String, Object> buildinParams,
                                                   int minDisplayedStatColumn);

    /**
     * @param id
     * @param buildinParams
     * @param minDisplayedStatColumn
     * @return
     */
    Map<String, HtmlFormElement> getFormElementMap(int id, Map<String, Object> buildinParams,
                                                   int minDisplayedStatColumn);

    /**
     * @param report
     * @param buildinParams
     * @param minDisplayedStatColumn
     * @return
     */
    Map<String, HtmlFormElement> getFormElementMap(Report report, Map<String, Object> buildinParams,
                                                   int minDisplayedStatColumn);

    /**
     * @param uid
     * @param buildinParams
     * @param minDisplayedStatColumn
     * @return
     */
    List<HtmlFormElement> getFormElements(String uid, Map<String, Object> buildinParams,
                                          int minDisplayedStatColumn);

    /**
     * @param id
     * @param buildinParams
     * @param minDisplayedStatColumn
     * @return
     */
    List<HtmlFormElement> getFormElements(int id, Map<String, Object> buildinParams,
                                          int minDisplayedStatColumn);

    /**
     * @param report
     * @param buildinParams
     * @param minDisplayedStatColumn
     * @return
     */
    List<HtmlFormElement> getFormElements(Report report, Map<String, Object> buildinParams,
                                          int minDisplayedStatColumn);

    /**
     * @param report
     * @param buildinParams
     * @return
     */
    List<HtmlFormElement> getDateAndQueryParamFormElements(Report report, Map<String, Object> buildinParams);

    /**
     * @param uid
     * @param buildinParams
     * @return
     */
    List<HtmlDateBox> getDateFormElements(String uid, Map<String, Object> buildinParams);

    /**
     * @param id
     * @param buildinParams
     * @return
     */
    List<HtmlDateBox> getDateFormElements(int id, Map<String, Object> buildinParams);

    /**
     * @param report
     * @param buildinParams
     * @return
     */
    List<HtmlDateBox> getDateFormElements(Report report, Map<String, Object> buildinParams);

    /**
     * @param uid
     * @param buildinParams
     * @param minDisplayedStatColumn
     * @return
     */
    List<HtmlFormElement> getQueryParamFormElements(String uid, Map<String, Object> buildinParams,
                                                    int minDisplayedStatColumn);

    /**
     * @param id
     * @param buildinParams
     * @param minDisplayedStatColumn
     * @return
     */
    List<HtmlFormElement> getQueryParamFormElements(int id, Map<String, Object> buildinParams,
                                                    int minDisplayedStatColumn);

    /**
     * @param report
     * @param buildinParams
     * @return
     */
    List<HtmlFormElement> getQueryParamFormElements(Report report, Map<String, Object> buildinParams);

    /**
     * @param uid
     * @param minDisplayedStatColumn
     * @return
     */
    HtmlCheckBoxList getStatColumnFormElements(String uid, int minDisplayedStatColumn);

    /**
     * @param id
     * @param minDisplayedStatColumn
     * @return
     */
    HtmlCheckBoxList getStatColumnFormElements(int id, int minDisplayedStatColumn);

    /**
     * @param columns
     * @param minDisplayedStatColumn
     * @return
     */
    HtmlCheckBoxList getStatColumnFormElements(List<ReportMetaDataColumn> columns,
                                               int minDisplayedStatColumn);

    /**
     * @param columns
     * @return
     */
    List<HtmlFormElement> getNonStatColumnFormElements(List<ReportMetaDataColumn> columns);
}
