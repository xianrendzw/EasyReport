package com.easytoolsoft.easyreport.meta.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Resource;

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
import com.easytoolsoft.easyreport.engine.util.DateUtils;
import com.easytoolsoft.easyreport.engine.util.VelocityUtils;
import com.easytoolsoft.easyreport.meta.domain.Report;
import com.easytoolsoft.easyreport.meta.domain.options.QueryParameterOptions;
import com.easytoolsoft.easyreport.meta.domain.options.ReportOptions;
import com.easytoolsoft.easyreport.meta.form.control.HtmlCheckBox;
import com.easytoolsoft.easyreport.meta.form.control.HtmlCheckBoxList;
import com.easytoolsoft.easyreport.meta.form.control.HtmlComboBox;
import com.easytoolsoft.easyreport.meta.form.control.HtmlDateBox;
import com.easytoolsoft.easyreport.meta.form.control.HtmlFormElement;
import com.easytoolsoft.easyreport.meta.form.control.HtmlSelectOption;
import com.easytoolsoft.easyreport.meta.form.control.HtmlTextBox;
import com.easytoolsoft.easyreport.meta.service.ReportService;
import com.easytoolsoft.easyreport.meta.service.TableReportService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 报表生成服务类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Service("TableReportService")
public class TableReportServiceImpl implements TableReportService {
    @Resource
    private ReportService reportService;

    @Override
    public ReportParameter getReportParameter(final Report report, final Map<?, ?> parameters) {
        final ReportOptions options = this.reportService.parseOptions(report.getOptions());
        final Map<String, Object> formParams = this.getFormParameters(parameters, options.getDataRange());
        return this.createReportParameter(report, formParams);
    }

    @Override
    public ReportTable getReportTable(final int id, final Map<String, Object> formParams) {
        final Report report = this.reportService.getById(id);
        return this.getReportTable(report, formParams);
    }

    @Override
    public ReportTable getReportTable(final String uid, final Map<String, Object> formParams) {
        final Report report = this.reportService.getByUid(uid);
        return this.getReportTable(report, formParams);
    }

    @Override
    public ReportTable getReportTable(final Report report, final Map<String, Object> formParams) {
        final ReportDataSource reportDs = this.reportService.getReportDataSource(report.getDsId());
        return ReportGenerator.generate(reportDs, this.createReportParameter(report, formParams));
    }

    @Override
    public ReportTable getReportTable(final Queryer queryer, final ReportParameter reportParameter) {
        return ReportGenerator.generate(queryer, reportParameter);
    }

    @Override
    public ReportTable getReportTable(final ReportMetaDataSet metaDataSet, final ReportParameter reportParameter) {
        return ReportGenerator.generate(metaDataSet, reportParameter);
    }

    @Override
    public ReportDataSet getReportDataSet(final Report report, final Map<String, Object> parameters) {
        final ReportDataSource reportDs = this.reportService.getReportDataSource(report.getDsId());
        return ReportGenerator.getDataSet(reportDs, this.createReportParameter(report, parameters));
    }

    private ReportParameter createReportParameter(final Report report, final Map<String, Object> formParams) {
        final String sqlText = new ReportSqlTemplate(report.getSqlText(), formParams).execute();
        final Set<String> enabledStatColumn = this.getEnabledStatColumns(formParams);
        final ReportOptions options = this.reportService.parseOptions(report.getOptions());
        final List<ReportMetaDataColumn> metaColumns = this.reportService.parseMetaColumns(report.getMetaColumns());
        return new ReportParameter(report.getId().toString(), report.getName(),
            options.getLayout(), options.getStatColumnLayout(), metaColumns,
            enabledStatColumn, Boolean.valueOf(formParams.get("isRowSpan").toString()), sqlText);
    }

    private Set<String> getEnabledStatColumns(final Map<String, Object> formParams) {
        final Set<String> checkedSet = new HashSet<>();
        final String checkedColumnNames = formParams.get("statColumns").toString();
        if (StringUtils.isBlank(checkedColumnNames)) {
            return checkedSet;
        }
        final String[] columnNames = StringUtils.split(checkedColumnNames, ',');
        for (final String columnName : columnNames) {
            if (!checkedSet.contains(columnName)) {
                checkedSet.add(columnName);
            }
        }
        return checkedSet;
    }

    @Override
    public Map<String, Object> getBuildInParameters(final Map<?, ?> httpReqParamMap, final int dataRange) {
        final Map<String, Object> formParams = new HashMap<>();
        this.setBuildInParams(formParams, httpReqParamMap, dataRange);
        return formParams;
    }

    @Override
    public Map<String, Object> getFormParameters(final Map<?, ?> httpReqParamMap) {
        return this.getFormParameters(httpReqParamMap, 7);
    }

    @Override
    public Map<String, Object> getFormParameters(final Map<?, ?> httpReqParamMap, final int dataRange) {
        final Map<String, Object> formParams = new HashMap<>();
        this.setBuildInParams(formParams, httpReqParamMap, 7);
        this.setQueryParams(formParams, httpReqParamMap);
        return formParams;
    }

    private void setBuildInParams(final Map<String, Object> formParams, final Map<?, ?> httpReqParamMap,
                                  int dataRange) {
        dataRange = (dataRange - 1) < 0 ? 0 : dataRange - 1;
        // 判断是否设置报表开始时间与结束时期
        if (httpReqParamMap.containsKey("startTime")) {
            final String[] values = (String[])httpReqParamMap.get("startTime");
            formParams.put("startTime", values[0]);
        } else {
            formParams.put("startTime", DateUtils.add(-dataRange, "yyyy-MM-dd"));
        }
        if (httpReqParamMap.containsKey("endTime")) {
            final String[] values = (String[])httpReqParamMap.get("endTime");
            formParams.put("endTime", values[0]);
        } else {
            formParams.put("endTime", DateUtils.getNow("yyyy-MM-dd"));
        }
        // 判断是否设置报表统计列
        if (httpReqParamMap.containsKey("statColumns")) {
            final String[] values = (String[])httpReqParamMap.get("statColumns");
            formParams.put("statColumns", StringUtils.join(values, ','));
        } else {
            formParams.put("statColumns", "");
        }
        // 判断是否设置报表表格行跨行显示
        if (httpReqParamMap.containsKey("isRowSpan")) {
            final String[] values = (String[])httpReqParamMap.get("isRowSpan");
            formParams.put("isRowSpan", values[0]);
        } else {
            formParams.put("isRowSpan", "true");
        }

        final String startTime = formParams.get("startTime").toString();
        final String endTime = formParams.get("endTime").toString();
        formParams.put("intStartTime", Integer.valueOf(DateUtils.getDate(startTime, "yyyyMMdd")));
        formParams.put("utcStartTime", DateUtils.getUtcDate(startTime, "yyyy-MM-dd"));
        formParams.put("utcIntStartTime", Integer.valueOf(DateUtils.getUtcDate(startTime, "yyyyMMdd")));
        formParams.put("intEndTime", Integer.valueOf(DateUtils.getDate(endTime, "yyyyMMdd")));
        formParams.put("utcEndTime", DateUtils.getUtcDate(endTime, "yyyy-MM-dd"));
        formParams.put("utcIntEndTime", Integer.valueOf(DateUtils.getUtcDate(endTime, "yyyyMMdd")));
    }

    private void setQueryParams(final Map<String, Object> formParams, final Map<?, ?> httpReqParamMap) {
        String[] values = (String[])httpReqParamMap.get("uid");
        if (ArrayUtils.isEmpty(values) || "".equals(values[0].trim())) {
            return;
        }

        final String uid = values[0].trim();
        final Report report = this.reportService.getByUid(uid);
        final List<QueryParameterOptions> queryParams = this.reportService.parseQueryParams(report.getQueryParams());
        for (final QueryParameterOptions queryParam : queryParams) {
            String value = "";
            values = (String[])httpReqParamMap.get(queryParam.getName());
            if (values != null && values.length > 0) {
                value = this.getQueryParamValue(queryParam.getDataType(), values);
            }
            formParams.put(queryParam.getName(), value);
        }
    }

    private String getQueryParamValue(final String dataType, final String[] values) {
        if (values.length == 1) {
            return values[0];
        }
        if ("float".equals(dataType) || "integer".equals(dataType)) {
            return StringUtils.join(values, ",");
        }
        return StringUtils.join(values, "','");
    }

    @Override
    public Map<String, HtmlFormElement> getFormElementMap(final String uid, final Map<String, Object> buildinParams,
                                                          final int minDisplayedStatColumn) {
        final Report report = this.reportService.getByUid(uid);
        return this.getFormElementMap(report, buildinParams, minDisplayedStatColumn);
    }

    @Override
    public Map<String, HtmlFormElement> getFormElementMap(final int id, final Map<String, Object> buildinParams,
                                                          final int minDisplayedStatColumn) {
        final Report report = this.reportService.getById(id);
        return this.getFormElementMap(report, buildinParams, minDisplayedStatColumn);
    }

    @Override
    public Map<String, HtmlFormElement> getFormElementMap(final Report report, final Map<String, Object> buildinParams,
                                                          final int minDisplayedStatColumn) {
        final List<HtmlFormElement> formElements = this.getFormElements(report, buildinParams, minDisplayedStatColumn);
        final Map<String, HtmlFormElement> formElementMap = new HashMap<>(formElements.size());
        for (final HtmlFormElement element : formElements) {
            formElementMap.put(element.getName(), element);
        }
        return formElementMap;
    }

    @Override
    public List<HtmlFormElement> getFormElements(final String uid, final Map<String, Object> buildinParams,
                                                 final int minDisplayedStatColumn) {
        final Report report = this.reportService.getByUid(uid);
        return this.getFormElements(report, buildinParams, minDisplayedStatColumn);
    }

    @Override
    public List<HtmlFormElement> getFormElements(final int id, final Map<String, Object> buildinParams,
                                                 final int minDisplayedStatColumn) {
        final Report report = this.reportService.getById(id);
        return this.getFormElements(report, buildinParams, minDisplayedStatColumn);
    }

    @Override
    public List<HtmlFormElement> getFormElements(final Report report, final Map<String, Object> buildinParams,
                                                 final int minDisplayedStatColumn) {
        final List<HtmlFormElement> formElements = new ArrayList<>(15);
        formElements.addAll(this.getDateFormElements(report, buildinParams));
        formElements.addAll(this.getQueryParamFormElements(report, buildinParams));
        formElements.add(this.getStatColumnFormElements(this.reportService.parseMetaColumns(report.getMetaColumns()),
            minDisplayedStatColumn));
        return formElements;
    }

    @Override
    public List<HtmlFormElement> getDateAndQueryParamFormElements(final Report report,
                                                                  final Map<String, Object> buildinParams) {
        final List<HtmlFormElement> formElements = new ArrayList<>(15);
        formElements.addAll(this.getDateFormElements(report, buildinParams));
        formElements.addAll(this.getQueryParamFormElements(report, buildinParams));
        return formElements;
    }

    @Override
    public List<HtmlDateBox> getDateFormElements(final String uid, final Map<String, Object> buildinParams) {
        final Report report = this.reportService.getByUid(uid);
        return this.getDateFormElements(report, buildinParams);
    }

    @Override
    public List<HtmlDateBox> getDateFormElements(final int id, final Map<String, Object> buildinParams) {
        final Report report = this.reportService.getById(id);
        return this.getDateFormElements(report, buildinParams);
    }

    @Override
    public List<HtmlDateBox> getDateFormElements(final Report report, final Map<String, Object> buildinParams) {
        final StringBuilder text = new StringBuilder(report.getSqlText());
        text.append(" ");
        text.append(report.getQueryParams());

        final String regex = "\\$\\{.*?\\}";
        final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(text.toString());
        final Set<String> set = new HashSet<>(2);
        while (matcher.find()) {
            final String group = matcher.group(0);
            String name = group.replaceAll("utc|int|Int|[\\$\\{\\}]", "");
            name = name.substring(0, 1).toLowerCase() + name.substring(1);
            if (!set.contains(name) && StringUtils.indexOfIgnoreCase(group, name) != -1) {
                set.add(name);
            }
        }

        final List<HtmlDateBox> dateboxes = new ArrayList<>(2);
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
    public List<HtmlFormElement> getQueryParamFormElements(final String uid, final Map<String, Object> buildinParams,
                                                           final int minDisplayedStatColumn) {
        final Report report = this.reportService.getByUid(uid);
        return this.getFormElements(report, buildinParams, minDisplayedStatColumn);
    }

    @Override
    public List<HtmlFormElement> getQueryParamFormElements(final int id, final Map<String, Object> buildinParams,
                                                           final int minDisplayedStatColumn) {
        final Report report = this.reportService.getById(id);
        return this.getFormElements(report, buildinParams, minDisplayedStatColumn);
    }

    @Override
    public List<HtmlFormElement> getQueryParamFormElements(final Report report,
                                                           final Map<String, Object> buildinParams) {
        final List<QueryParameterOptions> queryParams = this.reportService.parseQueryParams(report.getQueryParams());
        final List<HtmlFormElement> formElements = new ArrayList<>(3);

        for (final QueryParameterOptions queryParam : queryParams) {
            HtmlFormElement htmlFormElement = null;
            queryParam.setDefaultText(VelocityUtils.parse(queryParam.getDefaultText(), buildinParams));
            queryParam.setDefaultValue(VelocityUtils.parse(queryParam.getDefaultValue(), buildinParams));
            queryParam.setContent(VelocityUtils.parse(queryParam.getContent(), buildinParams));
            final String formElement = queryParam.getFormElement().toLowerCase();
            if ("select".equals(formElement) || "selectMul".equalsIgnoreCase(formElement)) {
                htmlFormElement = this.getComboBoxFormElements(queryParam, report.getDsId(), buildinParams);
            } else if ("checkbox".equals(formElement)) {
                htmlFormElement = new HtmlCheckBox(queryParam.getName(), queryParam.getText(),
                    queryParam.getRealDefaultValue());
            } else if ("text".equals(formElement)) {
                htmlFormElement = new HtmlTextBox(queryParam.getName(), queryParam.getText(),
                    queryParam.getRealDefaultValue());
            } else if ("date".equals(formElement)) {
                htmlFormElement = new HtmlDateBox(queryParam.getName(), queryParam.getText(),
                    queryParam.getRealDefaultValue());
            }
            if (htmlFormElement != null) {
                this.setElementCommonProperities(queryParam, htmlFormElement);
                formElements.add(htmlFormElement);
            }
        }
        return formElements;
    }

    private HtmlComboBox getComboBoxFormElements(final QueryParameterOptions queryParam, final int dsId,
                                                 final Map<String, Object> buildinParams) {
        final List<ReportQueryParamItem> options = this.getOptions(queryParam, dsId, buildinParams);
        final List<HtmlSelectOption> htmlSelectOptions = new ArrayList<>(options.size());

        if (queryParam.hasDefaultValue()) {
            htmlSelectOptions.add(new HtmlSelectOption(
                queryParam.getDefaultText(),
                queryParam.getDefaultValue(), true));
        }
        for (int i = 0; i < options.size(); i++) {
            final ReportQueryParamItem option = options.get(i);
            if (!option.getName().equals(queryParam.getDefaultValue())) {
                htmlSelectOptions.add(new HtmlSelectOption(option.getText(),
                    option.getName(), (!queryParam.hasDefaultValue() && i == 0)));
            }
        }

        final HtmlComboBox htmlComboBox = new HtmlComboBox(queryParam.getName(), queryParam.getText(),
            htmlSelectOptions);
        htmlComboBox.setMultipled("selectMul".equals(queryParam.getFormElement()));
        htmlComboBox.setAutoComplete(queryParam.isAutoComplete());
        return htmlComboBox;
    }

    private void setElementCommonProperities(final QueryParameterOptions queryParam,
                                             final HtmlFormElement htmlFormElement) {
        htmlFormElement.setDataType(queryParam.getDataType());
        htmlFormElement.setHeight(queryParam.getHeight());
        htmlFormElement.setWidth(queryParam.getWidth());
        htmlFormElement.setRequired(queryParam.isRequired());
        htmlFormElement.setDefaultText(queryParam.getRealDefaultText());
        htmlFormElement.setDefaultValue(queryParam.getRealDefaultValue());
        htmlFormElement.setComment(queryParam.getComment());
    }

    private List<ReportQueryParamItem> getOptions(final QueryParameterOptions queryParam, final int dsId,
                                                  final Map<String, Object> buildinParams) {
        if ("sql".equals(queryParam.getDataSource())) {
            return this.reportService.executeQueryParamSqlText(dsId, queryParam.getContent());
        }

        final List<ReportQueryParamItem> options = new ArrayList<>();
        if ("text".equals(queryParam.getDataSource()) && StringUtils.isNoneBlank(queryParam.getContent())) {
            final HashSet<String> set = new HashSet<>();
            final String[] optionSplits = StringUtils.split(queryParam.getContent(), '|');
            for (final String option : optionSplits) {
                final String[] nameValuePairs = StringUtils.split(option, ',');
                final String name = nameValuePairs[0];
                final String text = nameValuePairs.length > 1 ? nameValuePairs[1] : name;
                if (!set.contains(name)) {
                    set.add(name);
                    options.add(new ReportQueryParamItem(name, text));
                }
            }
        }
        return options;
    }

    @Override
    public HtmlCheckBoxList getStatColumnFormElements(final String uid, final int minDisplayedStatColumn) {
        final Report report = this.reportService.getByUid(uid);
        return this.getStatColumnFormElements(this.reportService.parseMetaColumns(report.getMetaColumns()),
            minDisplayedStatColumn);
    }

    @Override
    public HtmlCheckBoxList getStatColumnFormElements(final int id, final int minDisplayedStatColumn) {
        final Report report = this.reportService.getById(id);
        return this.getStatColumnFormElements(this.reportService.parseMetaColumns(report.getMetaColumns()),
            minDisplayedStatColumn);
    }

    @Override
    public HtmlCheckBoxList getStatColumnFormElements(final List<ReportMetaDataColumn> columns,
                                                      final int minDisplayedStatColumn) {
        final List<ReportMetaDataColumn> statColumns = columns.stream()
            .filter(column -> column.getType() == ColumnType.STATISTICAL ||
                column.getType() == ColumnType.COMPUTED)
            .collect(Collectors.toList());
        if (statColumns.size() <= minDisplayedStatColumn) {
            return null;
        }

        final List<HtmlCheckBox> checkBoxes = new ArrayList<>(statColumns.size());
        for (final ReportMetaDataColumn column : statColumns) {
            final HtmlCheckBox checkbox = new HtmlCheckBox(column.getName(), column.getText(), column.getName());
            checkbox.setChecked(!column.isOptional());
            checkBoxes.add(checkbox);
        }
        return new HtmlCheckBoxList("statColumns", "统计列", checkBoxes);
    }

    @Override
    public List<HtmlFormElement> getNonStatColumnFormElements(final List<ReportMetaDataColumn> columns) {
        final List<HtmlFormElement> formElements = new ArrayList<>(10);
        columns.stream()
            .filter(column -> column.getType() == ColumnType.LAYOUT ||
                column.getType() == ColumnType.DIMENSION)
            .forEach(column -> {
                final HtmlComboBox htmlComboBox = new HtmlComboBox("dim_" + column.getName(),
                    column.getText(), new ArrayList<>(0));
                htmlComboBox.setAutoComplete(true);
                formElements.add(htmlComboBox);
            });
        return formElements;
    }
}