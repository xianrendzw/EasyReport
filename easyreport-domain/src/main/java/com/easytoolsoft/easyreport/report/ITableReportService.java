package com.easytoolsoft.easyreport.report;

import com.easytoolsoft.easyreport.common.form.control.HtmlCheckBoxList;
import com.easytoolsoft.easyreport.common.form.control.HtmlDateBox;
import com.easytoolsoft.easyreport.common.form.control.HtmlFormElement;
import com.easytoolsoft.easyreport.engine.data.ReportDataSet;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataSet;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;
import com.easytoolsoft.easyreport.engine.data.ReportTable;
import com.easytoolsoft.easyreport.engine.query.Queryer;
import com.easytoolsoft.easyreport.data.metadata.po.Report;

import java.util.List;
import java.util.Map;

/**
 * 表格报表生成服务类,包括报表生成,转换,报表数据集处理等
 *
 * @author Tom Deng
 */
public interface ITableReportService {
    ReportParameter getReportParameter(Report report, Map<?, ?> parameters);

    ReportTable getReportTable(int id, Map<String, Object> formParams);

    ReportTable getReportTable(String uid, Map<String, Object> formParams);

    ReportTable getReportTable(Report report, Map<String, Object> formParams);

    ReportTable getReportTable(Queryer queryer, ReportParameter reportParameter);

    ReportTable getReportTable(ReportMetaDataSet metaDataSet, ReportParameter reportParameter);

    ReportDataSet getReportDataSet(Report report, Map<String, Object> parameters);

    Map<String, Object> getBuildInParameters(Map<?, ?> httpReqParamMap, int dataRange);

    Map<String, Object> getFormParameters(Map<?, ?> httpReqParamMap);

    Map<String, Object> getFormParameters(Map<?, ?> httpReqParamMap, int dataRange);

    Map<String, HtmlFormElement> getFormElementMap(String uid, Map<String, Object> buildinParams,
                                                   int minDisplayedStatColumn);

    Map<String, HtmlFormElement> getFormElementMap(int id, Map<String, Object> buildinParams,
                                                   int minDisplayedStatColumn);

    Map<String, HtmlFormElement> getFormElementMap(Report report, Map<String, Object> buildinParams,
                                                   int minDisplayedStatColumn);

    List<HtmlFormElement> getFormElements(String uid, Map<String, Object> buildinParams,
                                          int minDisplayedStatColumn);

    List<HtmlFormElement> getFormElements(int id, Map<String, Object> buildinParams,
                                          int minDisplayedStatColumn);

    List<HtmlFormElement> getFormElements(Report report, Map<String, Object> buildinParams,
                                          int minDisplayedStatColumn);

    List<HtmlFormElement> getDateAndQueryParamFormElements(Report report, Map<String, Object> buildinParams);

    List<HtmlDateBox> getDateFormElements(String uid, Map<String, Object> buildinParams);

    List<HtmlDateBox> getDateFormElements(int id, Map<String, Object> buildinParams);

    List<HtmlDateBox> getDateFormElements(Report report, Map<String, Object> buildinParams);

    List<HtmlFormElement> getQueryParamFormElements(String uid, Map<String, Object> buildinParams,
                                                    int minDisplayedStatColumn);

    List<HtmlFormElement> getQueryParamFormElements(int id, Map<String, Object> buildinParams,
                                                    int minDisplayedStatColumn);

    List<HtmlFormElement> getQueryParamFormElements(Report report, Map<String, Object> buildinParams);

    HtmlCheckBoxList getStatColumnFormElements(String uid, int minDisplayedStatColumn);

    HtmlCheckBoxList getStatColumnFormElements(int id, int minDisplayedStatColumn);

    HtmlCheckBoxList getStatColumnFormElements(List<ReportMetaDataColumn> columns,
                                               int minDisplayedStatColumn);

    List<HtmlFormElement> getNonStatColumnFormElements(List<ReportMetaDataColumn> columns);
}
