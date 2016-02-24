package com.easytoolsoft.easyreport.service;

import com.alibaba.fastjson.JSON;
import com.easytoolsoft.easyreport.common.util.DateUtils;
import com.easytoolsoft.easyreport.dao.ReportingDao;
import com.easytoolsoft.easyreport.data.jdbc.BaseService;
import com.easytoolsoft.easyreport.engine.ReportGenerator;
import com.easytoolsoft.easyreport.engine.data.*;
import com.easytoolsoft.easyreport.engine.query.Queryer;
import com.easytoolsoft.easyreport.engine.util.VelocityUtils;
import com.easytoolsoft.easyreport.po.DataSourcePo;
import com.easytoolsoft.easyreport.po.QueryParameterPo;
import com.easytoolsoft.easyreport.po.ReportingPo;
import com.easytoolsoft.easyreport.viewmodel.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 报表生成服务类
 */
@Service
public class ReportingGenerationService extends BaseService<ReportingDao, ReportingPo> {
    @Resource
    private DataSourceService dataSourceService;
    @Resource
    private ConfigDictService configDictService;
    @Resource
    private ReportingService reportingService;

    @Autowired
    public ReportingGenerationService(ReportingDao dao) {
        super(dao);
    }

    public ReportDataSource getReportDataSource(ReportingPo report) {
        DataSourcePo ds = this.dataSourceService.getById(report.getDsId());
        return new ReportDataSource(ds.getJdbcUrl(), ds.getUser(), ds.getPassword());
    }

    public ReportParameter getReportParameter(ReportingPo report, Map<?, ?> parameters) {
        Map<String, Object> formParams = this.getFormParameters(parameters, report.getDataRange());
        return this.createReportParameter(report, formParams);
    }

    public ReportTable getReportTable(int id, Map<String, Object> formParams) {
        ReportingPo report = this.dao.queryByKey(id);
        return this.getReportTable(report, formParams);
    }

    public ReportTable getReportTable(String uid, Map<String, Object> formParams) {
        ReportingPo report = this.dao.queryByUid(uid);
        return this.getReportTable(report, formParams);
    }

    public ReportTable getReportTable(ReportingPo report, Map<String, Object> formParams) {
        DataSourcePo ds = this.dataSourceService.getById(report.getDsId());
        ReportDataSource reportDs = new ReportDataSource(ds.getJdbcUrl(), ds.getUser(), ds.getPassword());
        return ReportGenerator.generate(reportDs, this.createReportParameter(report, formParams));
    }

    public ReportTable getReportTable(Queryer queryer, ReportParameter reportParameter) {
        return ReportGenerator.generate(queryer, reportParameter);
    }

    public ReportTable getReportTable(ReportMetaDataSet metaDataSet, ReportParameter reportParameter) {
        return ReportGenerator.generate(metaDataSet, reportParameter);
    }

    public ReportDataSet getReportDataSet(ReportingPo report, Map<String, Object> parameters) {
        DataSourcePo ds = this.dataSourceService.getById(report.getDsId());
        ReportDataSource reportDs = new ReportDataSource(ds.getJdbcUrl(), ds.getUser(), ds.getPassword());
        return ReportGenerator.getDataSet(reportDs, this.createReportParameter(report, parameters));
    }

    private ReportParameter createReportParameter(ReportingPo report, Map<String, Object> formParams) {
        String sqlText = new ReportSqlTemplate(report.getSqlText(), formParams).execute();
        Set<String> enabledStatColumn = this.getEnabledStatColumns(formParams);
        List<ReportMetaDataColumn> metaColumns = JSON.parseArray(report.getMetaColumns(), ReportMetaDataColumn.class);
        return new ReportParameter(report.getId().toString(), report.getName(),
                report.getLayout(), report.getStatColumnLayout(), metaColumns,
                enabledStatColumn, Boolean.valueOf(formParams.get("isRowSpan").toString()), sqlText);
    }

    private Set<String> getEnabledStatColumns(Map<String, Object> formParams) {
        Set<String> checkedSet = new HashSet<String>();
        String checkedColumnNames = formParams.get("statColumns").toString();
        if (StringUtils.isBlank(checkedColumnNames)) {
            return checkedSet;
        }
        String[] columnNames = StringUtils.split(checkedColumnNames, ',');
        for (String columnName : columnNames) {
            if (!checkedSet.contains(columnName)) {
                checkedSet.add(columnName);
            }
        }
        return checkedSet;
    }

    public Map<String, Object> getBuildInParameters(Map<?, ?> httpReqParamMap, int dataRange) {
        Map<String, Object> formParams = new HashMap<String, Object>();
        this.setBuildInParams(formParams, httpReqParamMap, dataRange);
        return formParams;
    }

    public Map<String, Object> getFormParameters(Map<?, ?> httpReqParamMap) {
        return this.getFormParameters(httpReqParamMap, 7);
    }

    public Map<String, Object> getFormParameters(Map<?, ?> httpReqParamMap, int dataRange) {
        Map<String, Object> formParams = new HashMap<String, Object>();
        this.setBuildInParams(formParams, httpReqParamMap, 7);
        this.setQueryParams(formParams, httpReqParamMap);
        return formParams;
    }

    private void setBuildInParams(Map<String, Object> formParams, Map<?, ?> httpReqParamMap, int dataRange) {
        dataRange = (dataRange - 1) < 0 ? 0 : dataRange - 1;
        // 判断是否设置报表开始时间与结束时期
        if (httpReqParamMap.containsKey("startTime")) {
            String[] values = (String[]) httpReqParamMap.get("startTime");
            formParams.put("startTime", values[0]);
        } else {
            formParams.put("startTime", DateUtils.add(-dataRange, "yyyy-MM-dd"));
        }
        if (httpReqParamMap.containsKey("endTime")) {
            String[] values = (String[]) httpReqParamMap.get("endTime");
            formParams.put("endTime", values[0]);
        } else {
            formParams.put("endTime", DateUtils.getNow("yyyy-MM-dd"));
        }
        // 判断是否设置报表统计列
        if (httpReqParamMap.containsKey("statColumns")) {
            String[] values = (String[]) httpReqParamMap.get("statColumns");
            formParams.put("statColumns", StringUtils.join(values, ','));
        } else {
            formParams.put("statColumns", "");
        }
        // 判断是否设置报表表格行跨行显示
        if (httpReqParamMap.containsKey("isRowSpan")) {
            String[] values = (String[]) httpReqParamMap.get("isRowSpan");
            formParams.put("isRowSpan", values[0]);
        } else {
            formParams.put("isRowSpan", "true");
        }

        String startTime = formParams.get("startTime").toString();
        String endTime = formParams.get("endTime").toString();
        formParams.put("intStartTime", Integer.valueOf(DateUtils.getDate(startTime, "yyyyMMdd")));
        formParams.put("utcStartTime", DateUtils.getUtcDate(startTime, "yyyy-MM-dd"));
        formParams.put("utcIntStartTime", Integer.valueOf(DateUtils.getUtcDate(startTime, "yyyyMMdd")));
        formParams.put("intEndTime", Integer.valueOf(DateUtils.getDate(endTime, "yyyyMMdd")));
        formParams.put("utcEndTime", DateUtils.getUtcDate(endTime, "yyyy-MM-dd"));
        formParams.put("utcIntEndTime", Integer.valueOf(DateUtils.getUtcDate(endTime, "yyyyMMdd")));
    }

    private void setQueryParams(Map<String, Object> formParams, Map<?, ?> httpReqParamMap) {
        String[] values = (String[]) httpReqParamMap.get("uid");
        if (values == null || values.length == 0 || "".equals(values[0].trim())) {
            return;
        }

        String uid = values[0].trim();
        ReportingPo report = this.reportingService.getByUid(uid);
        List<QueryParameterPo> queryParams = report.getQueryParamList();
        for (QueryParameterPo queryParam : queryParams) {
            String value = "";
            values = (String[]) httpReqParamMap.get(queryParam.getName());
            if (values != null && values.length > 0) {
                value = this.getQueryParamValue(queryParam.getDataType(), values);
            }
            formParams.put(queryParam.getName(), value);
        }
    }

    private String getQueryParamValue(String dataType, String[] values) {
        if (values.length == 1) {
            return values[0];
        }
        if (dataType.equals("float") || dataType.equals("integer")) {
            return StringUtils.join(values, ",");
        }
        return StringUtils.join(values, "','");
    }

    public Map<String, HtmlFormElement> getFormElementMap(String uid, Map<String, Object> buildinParams,
                                                          int minDisplayedStatColumn) {
        ReportingPo report = this.dao.queryByUid(uid);
        return this.getFormElementMap(report, buildinParams, minDisplayedStatColumn);
    }

    public Map<String, HtmlFormElement> getFormElementMap(int id, Map<String, Object> buildinParams,
                                                          int minDisplayedStatColumn) {
        ReportingPo report = this.dao.queryByKey(id);
        return this.getFormElementMap(report, buildinParams, minDisplayedStatColumn);
    }

    public Map<String, HtmlFormElement> getFormElementMap(ReportingPo report, Map<String, Object> buildinParams,
                                                          int minDisplayedStatColumn) {
        List<HtmlFormElement> formElements = this.getFormElements(report, buildinParams, minDisplayedStatColumn);
        Map<String, HtmlFormElement> formElementMap = new HashMap<>(formElements.size());
        for (HtmlFormElement element : formElements) {
            formElementMap.put(element.getName(), element);
        }
        return formElementMap;
    }

    public List<HtmlFormElement> getFormElements(String uid, Map<String, Object> buildinParams,
                                                 int minDisplayedStatColumn) {
        ReportingPo report = this.dao.queryByUid(uid);
        return this.getFormElements(report, buildinParams, minDisplayedStatColumn);
    }

    public List<HtmlFormElement> getFormElements(int id, Map<String, Object> buildinParams,
                                                 int minDisplayedStatColumn) {
        ReportingPo report = this.dao.queryByKey(id);
        return this.getFormElements(report, buildinParams, minDisplayedStatColumn);
    }

    public List<HtmlFormElement> getFormElements(ReportingPo report, Map<String, Object> buildinParams,
                                                 int minDisplayedStatColumn) {
        List<HtmlFormElement> formElements = new ArrayList<>(15);
        formElements.addAll(this.getDateFormElements(report, buildinParams));
        formElements.addAll(this.getQueryParamFormElements(report, buildinParams));
        formElements.add(this.getStatColumnFormElements(report.getMetaColumnList(), minDisplayedStatColumn));
        return formElements;
    }

    public List<HtmlFormElement> getDateAndQueryParamFormElements(ReportingPo report, Map<String, Object> buildinParams) {
        List<HtmlFormElement> formElements = new ArrayList<>(15);
        formElements.addAll(this.getDateFormElements(report, buildinParams));
        formElements.addAll(this.getQueryParamFormElements(report, buildinParams));
        return formElements;
    }

    public List<HtmlDateBox> getDateFormElements(String uid, Map<String, Object> buildinParams) {
        ReportingPo report = this.dao.queryByUid(uid);
        return this.getDateFormElements(report, buildinParams);
    }

    public List<HtmlDateBox> getDateFormElements(int id, Map<String, Object> buildinParams) {
        ReportingPo report = this.dao.queryByKey(id);
        return this.getDateFormElements(report, buildinParams);
    }

    public List<HtmlDateBox> getDateFormElements(ReportingPo report, Map<String, Object> buildinParams) {
        StringBuilder text = new StringBuilder(report.getSqlText());
        text.append(" ");
        text.append(report.getQueryParams());

        String regex = "\\$\\{.*?\\}";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text.toString());
        Set<String> set = new HashSet<String>(2);
        while (matcher.find()) {
            String group = matcher.group(0);
            String name = group.replaceAll("utc|int|Int|[\\$\\{\\}]", "");
            name = name.substring(0, 1).toLowerCase() + name.substring(1);
            if (!set.contains(name) && StringUtils.indexOfIgnoreCase(group, name) != -1) {
                set.add(name);
            }
        }

        List<HtmlDateBox> dateboxes = new ArrayList<>(2);
        String name = "startTime";
        if (set.contains(name)) {
            dateboxes.add(new HtmlDateBox(name, "开始日期", buildinParams.get(name).toString()));
        }
        name = "endTime";
        if (set.contains(name)) {
            dateboxes.add(new HtmlDateBox(name, "结束日期", buildinParams.get(name).toString()));
        }
        return dateboxes;
    }

    public List<HtmlFormElement> getQueryParamFormElements(String uid, Map<String, Object> buildinParams,
                                                           int minDisplayedStatColumn) {
        ReportingPo report = this.dao.queryByUid(uid);
        return this.getFormElements(report, buildinParams, minDisplayedStatColumn);
    }

    public List<HtmlFormElement> getQueryParamFormElements(int id, Map<String, Object> buildinParams,
                                                           int minDisplayedStatColumn) {
        ReportingPo report = this.dao.queryByKey(id);
        return this.getFormElements(report, buildinParams, minDisplayedStatColumn);
    }

    public List<HtmlFormElement> getQueryParamFormElements(ReportingPo report, Map<String, Object> buildinParams) {
        DataSourcePo ds = this.dataSourceService.getById(report.getDsId());
        List<QueryParameterPo> queryParams = report.getQueryParamList();
        List<HtmlFormElement> formElements = new ArrayList<HtmlFormElement>(3);

        for (QueryParameterPo queryParam : queryParams) {
            HtmlFormElement htmlFormElement = null;
            queryParam.setDefaultText(VelocityUtils.parse(queryParam.getDefaultText(), buildinParams));
            queryParam.setDefaultValue(VelocityUtils.parse(queryParam.getDefaultValue(), buildinParams));
            queryParam.setContent(VelocityUtils.parse(queryParam.getContent(), buildinParams));
            String formElement = queryParam.getFormElement().toLowerCase();
            if (formElement.equals("select") || formElement.equalsIgnoreCase("selectMul")) {
                htmlFormElement = this.getComboBoxFormElements(queryParam, ds, buildinParams);
            } else if (formElement.equals("checkbox")) {
                htmlFormElement = new HtmlCheckBox(queryParam.getName(), queryParam.getText(), queryParam.getRealDefaultValue());
            } else if (formElement.equals("text")) {
                htmlFormElement = new HtmlTextBox(queryParam.getName(), queryParam.getText(), queryParam.getRealDefaultValue());
            } else if (formElement.equals("date")) {
                htmlFormElement = new HtmlDateBox(queryParam.getName(), queryParam.getText(), queryParam.getRealDefaultValue());
            }
            if (htmlFormElement != null) {
                this.setElementCommonProperities(queryParam, htmlFormElement);
                formElements.add(htmlFormElement);
            }
        }
        return formElements;
    }

    private HtmlComboBox getComboBoxFormElements(QueryParameterPo queryParam, DataSourcePo ds, Map<String, Object> buildinParams) {
        List<ReportQueryParamItem> options = this.getOptions(queryParam, ds, buildinParams);
        List<HtmlSelectOption> htmlSelectOptions = new ArrayList<HtmlSelectOption>(options.size());

        if (queryParam.hasDefaultValue()) {
            htmlSelectOptions.add(new HtmlSelectOption(queryParam.getDefaultText(), queryParam.getDefaultValue(), true));
        }
        for (int i = 0; i < options.size(); i++) {
            ReportQueryParamItem option = options.get(i);
            if (!option.getName().equals(queryParam.getDefaultValue())) {
                htmlSelectOptions.add(new HtmlSelectOption(option.getText(), option.getName(), (!queryParam.hasDefaultValue() && i == 0)));
            }
        }

        HtmlComboBox htmlComboBox = new HtmlComboBox(queryParam.getName(), queryParam.getText(), htmlSelectOptions);
        htmlComboBox.setMultipled(queryParam.getFormElement().equals("selectMul"));
        htmlComboBox.setAutoComplete(queryParam.isAutoComplete());
        return htmlComboBox;
    }

    private void setElementCommonProperities(QueryParameterPo queryParam, HtmlFormElement htmlFormElement) {
        htmlFormElement.setDataType(queryParam.getDataType());
        htmlFormElement.setHeight(queryParam.getHeight());
        htmlFormElement.setWidth(queryParam.getWidth());
        htmlFormElement.setRequired(queryParam.isRequired());
        htmlFormElement.setDefaultText(queryParam.getRealDefaultText());
        htmlFormElement.setDefaultValue(queryParam.getRealDefaultValue());
        htmlFormElement.setComment(queryParam.getComment());
    }

    private List<ReportQueryParamItem> getOptions(QueryParameterPo queryParam, DataSourcePo ds,
                                                  Map<String, Object> buildinParams) {
        if (queryParam.getDataSource().equals("sql")) {
            return this.reportingService.getDao().executeQueryParamSqlText(ds.getJdbcUrl(), ds.getUser(), ds.getPassword(), queryParam.getContent());
        }

        List<ReportQueryParamItem> options = new ArrayList<ReportQueryParamItem>();
        if (queryParam.getDataSource().equals("text") && StringUtils.isNoneBlank(queryParam.getContent())) {
            HashSet<String> set = new HashSet<String>();
            String[] optionSplits = StringUtils.split(queryParam.getContent(), '|');
            for (String option : optionSplits) {
                String[] namevalueSplits = StringUtils.split(option, ',');
                String name = namevalueSplits[0];
                String text = namevalueSplits.length > 1 ? namevalueSplits[1] : name;
                if (!set.contains(name)) {
                    set.add(name);
                    options.add(new ReportQueryParamItem(name, text));
                }
            }
        }
        return options;
    }

    public HtmlCheckBoxList getStatColumnFormElements(String uid, int minDisplayedStatColumn) {
        ReportingPo report = this.dao.queryByUid(uid);
        return this.getStatColumnFormElements(report.getMetaColumnList(), minDisplayedStatColumn);
    }

    public HtmlCheckBoxList getStatColumnFormElements(int id, int minDisplayedStatColumn) {
        ReportingPo report = this.dao.queryByKey(id);
        return this.getStatColumnFormElements(report.getMetaColumnList(), minDisplayedStatColumn);
    }

    public HtmlCheckBoxList getStatColumnFormElements(List<ReportMetaDataColumn> columns, int minDisplayedStatColumn) {
        List<ReportMetaDataColumn> statColumns = new ArrayList<ReportMetaDataColumn>();
        for (ReportMetaDataColumn column : columns) {
            if (column.getType() == ColumnType.STATISTICAL ||
                    column.getType() == ColumnType.COMPUTED) {
                statColumns.add(column);
            }
        }

        if (statColumns.size() <= minDisplayedStatColumn) {
            return null;
        }

        List<HtmlCheckBox> checkBoxes = new ArrayList<HtmlCheckBox>(statColumns.size());
        for (ReportMetaDataColumn column : statColumns) {
            HtmlCheckBox checkbox = new HtmlCheckBox(column.getName(), column.getText(), column.getName());
            checkbox.setChecked(!column.isOptional());
            checkBoxes.add(checkbox);
        }
        return new HtmlCheckBoxList("statColumns", "统计列", checkBoxes);
    }

    public List<HtmlFormElement> getNonStatColumnFormElements(List<ReportMetaDataColumn> columns) {
        List<HtmlFormElement> formElements = new ArrayList<HtmlFormElement>(10);
        for (ReportMetaDataColumn column : columns) {
            if (column.getType() == ColumnType.LAYOUT || column.getType() == ColumnType.DIMENSION) {
                HtmlComboBox htmlComboBox =
                        new HtmlComboBox("dim_" + column.getName(), column.getText(), new ArrayList<HtmlSelectOption>(0));
                htmlComboBox.setAutoComplete(true);
                formElements.add(htmlComboBox);
            }
        }
        return formElements;
    }
}