package com.easytoolsoft.easyreport.report.impl;

import com.easytoolsoft.easyreport.common.form.control.HtmlCheckBox;
import com.easytoolsoft.easyreport.common.form.control.HtmlCheckBoxList;
import com.easytoolsoft.easyreport.common.form.control.HtmlComboBox;
import com.easytoolsoft.easyreport.common.form.control.HtmlDateBox;
import com.easytoolsoft.easyreport.common.form.control.HtmlFormElement;
import com.easytoolsoft.easyreport.common.form.control.HtmlSelectOption;
import com.easytoolsoft.easyreport.common.form.control.HtmlTextBox;
import com.easytoolsoft.easyreport.common.util.DateUtils;
import com.easytoolsoft.easyreport.engine.ReportGenerator;
import com.easytoolsoft.easyreport.engine.data.ColumnType;
import com.easytoolsoft.easyreport.engine.data.ReportDataSet;
import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataSet;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;
import com.easytoolsoft.easyreport.engine.data.ReportQueryParamItem;
import com.easytoolsoft.easyreport.engine.data.ReportSqlTemplate;
import com.easytoolsoft.easyreport.engine.data.ReportTable;
import com.easytoolsoft.easyreport.engine.query.Queryer;
import com.easytoolsoft.easyreport.engine.util.VelocityUtils;
import com.easytoolsoft.easyreport.data.metadata.po.Report;
import com.easytoolsoft.easyreport.data.metadata.po.ReportOptions;
import com.easytoolsoft.easyreport.report.ITableReportService;
import com.easytoolsoft.easyreport.metadata.service.IReportService;
import com.easytoolsoft.easyreport.metadata.vo.QueryParameter;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 报表生成服务类
 */
@Service("EzrptMetaReportingGenerationService")
public class TableReportService implements ITableReportService {
    @Resource
    private IReportService reportService;

    @Override
    public ReportParameter getReportParameter(Report report, Map<?, ?> parameters) {
        ReportOptions options = this.reportService.parseOptions(report.getOptions());
        Map<String, Object> formParams = this.getFormParameters(parameters, options.getDataRange());
        return this.createReportParameter(report, formParams);
    }

    @Override
    public ReportTable getReportTable(int id, Map<String, Object> formParams) {
        Report report = this.reportService.getById(id);
        return this.getReportTable(report, formParams);
    }

    @Override
    public ReportTable getReportTable(String uid, Map<String, Object> formParams) {
        Report report = this.reportService.getByUid(uid);
        return this.getReportTable(report, formParams);
    }

    @Override
    public ReportTable getReportTable(Report report, Map<String, Object> formParams) {
        ReportDataSource reportDs = this.reportService.getReportDataSource(report.getDsId());
        return ReportGenerator.generate(reportDs, this.createReportParameter(report, formParams));
    }

    @Override
    public ReportTable getReportTable(Queryer queryer, ReportParameter reportParameter) {
        return ReportGenerator.generate(queryer, reportParameter);
    }

    @Override
    public ReportTable getReportTable(ReportMetaDataSet metaDataSet, ReportParameter reportParameter) {
        return ReportGenerator.generate(metaDataSet, reportParameter);
    }

    @Override
    public ReportDataSet getReportDataSet(Report report, Map<String, Object> parameters) {
        ReportDataSource reportDs = this.reportService.getReportDataSource(report.getDsId());
        return ReportGenerator.getDataSet(reportDs, this.createReportParameter(report, parameters));
    }

    private ReportParameter createReportParameter(Report report, Map<String, Object> formParams) {
        String sqlText = new ReportSqlTemplate(report.getSqlText(), formParams).execute();
        Set<String> enabledStatColumn = this.getEnabledStatColumns(formParams);
        ReportOptions options = this.reportService.parseOptions(report.getOptions());
        List<ReportMetaDataColumn> metaColumns = this.reportService.parseMetaColumns(report.getMetaColumns());
        return new ReportParameter(report.getId().toString(), report.getName(),
                options.getLayout(), options.getStatColumnLayout(), metaColumns,
                enabledStatColumn, Boolean.valueOf(formParams.get("isRowSpan").toString()), sqlText);
    }

    private Set<String> getEnabledStatColumns(Map<String, Object> formParams) {
        Set<String> checkedSet = new HashSet<>();
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

    @Override
    public Map<String, Object> getBuildInParameters(Map<?, ?> httpReqParamMap, int dataRange) {
        Map<String, Object> formParams = new HashMap<>();
        this.setBuildInParams(formParams, httpReqParamMap, dataRange);
        return formParams;
    }

    @Override
    public Map<String, Object> getFormParameters(Map<?, ?> httpReqParamMap) {
        return this.getFormParameters(httpReqParamMap, 7);
    }

    @Override
    public Map<String, Object> getFormParameters(Map<?, ?> httpReqParamMap, int dataRange) {
        Map<String, Object> formParams = new HashMap<>();
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
        if (ArrayUtils.isEmpty(values) || "".equals(values[0].trim())) {
            return;
        }

        String uid = values[0].trim();
        Report report = this.reportService.getByUid(uid);
        List<QueryParameter> queryParams = this.reportService.parseQueryParams(report.getQueryParams());
        for (QueryParameter queryParam : queryParams) {
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

    @Override
    public Map<String, HtmlFormElement> getFormElementMap(String uid, Map<String, Object> buildinParams,
                                                          int minDisplayedStatColumn) {
        Report report = this.reportService.getByUid(uid);
        return this.getFormElementMap(report, buildinParams, minDisplayedStatColumn);
    }

    @Override
    public Map<String, HtmlFormElement> getFormElementMap(int id, Map<String, Object> buildinParams,
                                                          int minDisplayedStatColumn) {
        Report report = this.reportService.getById(id);
        return this.getFormElementMap(report, buildinParams, minDisplayedStatColumn);
    }

    @Override
    public Map<String, HtmlFormElement> getFormElementMap(Report report, Map<String, Object> buildinParams,
                                                          int minDisplayedStatColumn) {
        List<HtmlFormElement> formElements = this.getFormElements(report, buildinParams, minDisplayedStatColumn);
        Map<String, HtmlFormElement> formElementMap = new HashMap<>(formElements.size());
        for (HtmlFormElement element : formElements) {
            formElementMap.put(element.getName(), element);
        }
        return formElementMap;
    }

    @Override
    public List<HtmlFormElement> getFormElements(String uid, Map<String, Object> buildinParams,
                                                 int minDisplayedStatColumn) {
        Report report = this.reportService.getByUid(uid);
        return this.getFormElements(report, buildinParams, minDisplayedStatColumn);
    }

    @Override
    public List<HtmlFormElement> getFormElements(int id, Map<String, Object> buildinParams,
                                                 int minDisplayedStatColumn) {
        Report report = this.reportService.getById(id);
        return this.getFormElements(report, buildinParams, minDisplayedStatColumn);
    }

    @Override
    public List<HtmlFormElement> getFormElements(Report report, Map<String, Object> buildinParams,
                                                 int minDisplayedStatColumn) {
        List<HtmlFormElement> formElements = new ArrayList<>(15);
        formElements.addAll(this.getDateFormElements(report, buildinParams));
        formElements.addAll(this.getQueryParamFormElements(report, buildinParams));
        formElements.add(this.getStatColumnFormElements(this.reportService.parseMetaColumns(report.getMetaColumns()),
                minDisplayedStatColumn));
        return formElements;
    }

    @Override
    public List<HtmlFormElement> getDateAndQueryParamFormElements(Report report, Map<String, Object> buildinParams) {
        List<HtmlFormElement> formElements = new ArrayList<>(15);
        formElements.addAll(this.getDateFormElements(report, buildinParams));
        formElements.addAll(this.getQueryParamFormElements(report, buildinParams));
        return formElements;
    }

    @Override
    public List<HtmlDateBox> getDateFormElements(String uid, Map<String, Object> buildinParams) {
        Report report = this.reportService.getByUid(uid);
        return this.getDateFormElements(report, buildinParams);
    }

    @Override
    public List<HtmlDateBox> getDateFormElements(int id, Map<String, Object> buildinParams) {
        Report report = this.reportService.getById(id);
        return this.getDateFormElements(report, buildinParams);
    }

    @Override
    public List<HtmlDateBox> getDateFormElements(Report report, Map<String, Object> buildinParams) {
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

    @Override
    public List<HtmlFormElement> getQueryParamFormElements(String uid, Map<String, Object> buildinParams,
                                                           int minDisplayedStatColumn) {
        Report report = this.reportService.getByUid(uid);
        return this.getFormElements(report, buildinParams, minDisplayedStatColumn);
    }

    @Override
    public List<HtmlFormElement> getQueryParamFormElements(int id, Map<String, Object> buildinParams,
                                                           int minDisplayedStatColumn) {
        Report report = this.reportService.getById(id);
        return this.getFormElements(report, buildinParams, minDisplayedStatColumn);
    }

    @Override
    public List<HtmlFormElement> getQueryParamFormElements(Report report, Map<String, Object> buildinParams) {
        List<QueryParameter> queryParams = this.reportService.parseQueryParams(report.getQueryParams());
        List<HtmlFormElement> formElements = new ArrayList<>(3);

        for (QueryParameter queryParam : queryParams) {
            HtmlFormElement htmlFormElement = null;
            queryParam.setDefaultText(VelocityUtils.parse(queryParam.getDefaultText(), buildinParams));
            queryParam.setDefaultValue(VelocityUtils.parse(queryParam.getDefaultValue(), buildinParams));
            queryParam.setContent(VelocityUtils.parse(queryParam.getContent(), buildinParams));
            String formElement = queryParam.getFormElement().toLowerCase();
            if (formElement.equals("select") || formElement.equalsIgnoreCase("selectMul")) {
                htmlFormElement = this.getComboBoxFormElements(queryParam, report.getDsId(), buildinParams);
            } else if (formElement.equals("checkbox")) {
                htmlFormElement = new HtmlCheckBox(queryParam.getName(),queryParam.getText(),
                        queryParam.getRealDefaultValue());
            } else if (formElement.equals("text")) {
                htmlFormElement = new HtmlTextBox(queryParam.getName(),queryParam.getText(),
                        queryParam.getRealDefaultValue());
            } else if (formElement.equals("date")) {
                htmlFormElement = new HtmlDateBox(queryParam.getName(),queryParam.getText(),
                        queryParam.getRealDefaultValue());
            }
            if (htmlFormElement != null) {
                this.setElementCommonProperities(queryParam, htmlFormElement);
                formElements.add(htmlFormElement);
            }
        }
        return formElements;
    }

    private HtmlComboBox getComboBoxFormElements(QueryParameter queryParam, int dsId,
                                                 Map<String, Object> buildinParams) {
        List<ReportQueryParamItem> options = this.getOptions(queryParam, dsId, buildinParams);
        List<HtmlSelectOption> htmlSelectOptions = new ArrayList<>(options.size());

        if (queryParam.hasDefaultValue()) {
            htmlSelectOptions.add(new HtmlSelectOption(
                    queryParam.getDefaultText(),
                    queryParam.getDefaultValue(), true));
        }
        for (int i = 0; i < options.size(); i++) {
            ReportQueryParamItem option = options.get(i);
            if (!option.getName().equals(queryParam.getDefaultValue())) {
                htmlSelectOptions.add(new HtmlSelectOption(option.getText(),
                        option.getName(), (!queryParam.hasDefaultValue() && i == 0)));
            }
        }

        HtmlComboBox htmlComboBox = new HtmlComboBox(queryParam.getName(), queryParam.getText(), htmlSelectOptions);
        htmlComboBox.setMultipled(queryParam.getFormElement().equals("selectMul"));
        htmlComboBox.setAutoComplete(queryParam.isAutoComplete());
        return htmlComboBox;
    }

    private void setElementCommonProperities(QueryParameter queryParam, HtmlFormElement htmlFormElement) {
        htmlFormElement.setDataType(queryParam.getDataType());
        htmlFormElement.setHeight(queryParam.getHeight());
        htmlFormElement.setWidth(queryParam.getWidth());
        htmlFormElement.setRequired(queryParam.isRequired());
        htmlFormElement.setDefaultText(queryParam.getRealDefaultText());
        htmlFormElement.setDefaultValue(queryParam.getRealDefaultValue());
        htmlFormElement.setComment(queryParam.getComment());
    }

    private List<ReportQueryParamItem> getOptions(QueryParameter queryParam, int dsId,
                                                  Map<String, Object> buildinParams) {
        if (queryParam.getDataSource().equals("sql")) {
            return this.reportService.executeQueryParamSqlText(dsId, queryParam.getContent());
        }

        List<ReportQueryParamItem> options = new ArrayList<>();
        if (queryParam.getDataSource().equals("text") && StringUtils.isNoneBlank(queryParam.getContent())) {
            HashSet<String> set = new HashSet<>();
            String[] optionSplits = StringUtils.split(queryParam.getContent(), '|');
            for (String option : optionSplits) {
                String[] nameValuePairs = StringUtils.split(option, ',');
                String name = nameValuePairs[0];
                String text = nameValuePairs.length > 1 ? nameValuePairs[1] : name;
                if (!set.contains(name)) {
                    set.add(name);
                    options.add(new ReportQueryParamItem(name, text));
                }
            }
        }
        return options;
    }

    @Override
    public HtmlCheckBoxList getStatColumnFormElements(String uid, int minDisplayedStatColumn) {
        Report report = this.reportService.getByUid(uid);
        return this.getStatColumnFormElements(this.reportService.parseMetaColumns(report.getMetaColumns()),
                minDisplayedStatColumn);
    }

    @Override
    public HtmlCheckBoxList getStatColumnFormElements(int id, int minDisplayedStatColumn) {
        Report report = this.reportService.getById(id);
        return this.getStatColumnFormElements(this.reportService.parseMetaColumns(report.getMetaColumns()),
                minDisplayedStatColumn);
    }

    @Override
    public HtmlCheckBoxList getStatColumnFormElements(List<ReportMetaDataColumn> columns,
                                                      int minDisplayedStatColumn) {
        List<ReportMetaDataColumn> statColumns = columns.stream()
                .filter(column -> column.getType() == ColumnType.STATISTICAL ||
                        column.getType() == ColumnType.COMPUTED)
                .collect(Collectors.toList());
        if (statColumns.size() <= minDisplayedStatColumn) {
            return null;
        }

        List<HtmlCheckBox> checkBoxes = new ArrayList<>(statColumns.size());
        for (ReportMetaDataColumn column : statColumns) {
            HtmlCheckBox checkbox = new HtmlCheckBox(column.getName(), column.getText(), column.getName());
            checkbox.setChecked(!column.isOptional());
            checkBoxes.add(checkbox);
        }
        return new HtmlCheckBoxList("statColumns", "统计列", checkBoxes);
    }

    @Override
    public List<HtmlFormElement> getNonStatColumnFormElements(List<ReportMetaDataColumn> columns) {
        List<HtmlFormElement> formElements = new ArrayList<>(10);
        columns.stream()
                .filter(column -> column.getType() == ColumnType.LAYOUT ||
                        column.getType() == ColumnType.DIMENSION)
                .forEach(column -> {
                    HtmlComboBox htmlComboBox = new HtmlComboBox("dim_" + column.getName(),
                            column.getText(), new ArrayList<>(0));
                    htmlComboBox.setAutoComplete(true);
                    formElements.add(htmlComboBox);
                });
        return formElements;
    }
}