package com.easytoolsoft.easyreport.meta.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.engine.data.ReportQueryParamItem;
import com.easytoolsoft.easyreport.engine.query.QueryerFactory;
import com.easytoolsoft.easyreport.meta.data.ReportRepository;
import com.easytoolsoft.easyreport.meta.domain.DataSource;
import com.easytoolsoft.easyreport.meta.domain.Report;
import com.easytoolsoft.easyreport.meta.domain.example.ReportExample;
import com.easytoolsoft.easyreport.meta.domain.options.QueryParameterOptions;
import com.easytoolsoft.easyreport.meta.domain.options.ReportOptions;
import com.easytoolsoft.easyreport.meta.service.ConfService;
import com.easytoolsoft.easyreport.meta.service.DataSourceService;
import com.easytoolsoft.easyreport.meta.service.ReportService;
import com.easytoolsoft.easyreport.mybatis.pager.PageInfo;
import com.easytoolsoft.easyreport.mybatis.service.AbstractCrudService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author Tom Deng
 * @date 2017-03-25
 */
@Service("ReportService")
public class ReportServiceImpl
    extends AbstractCrudService<ReportRepository, Report, ReportExample, Integer>
    implements ReportService {
    @Resource
    private DataSourceService dsService;
    @Resource
    private ConfService confService;

    @Override
    protected ReportExample getPageExample(final String fieldName, final String keyword) {
        final ReportExample example = new ReportExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<Report> getByPage(final PageInfo page, final String fieldName, final Integer categoryId) {
        final ReportExample example = new ReportExample();
        example.or().andOperand(fieldName, "=", categoryId);
        return this.getByPage(page, example);
    }

    @Override
    public boolean saveQueryParams(final int id, final String json) {
        final Report po = Report.builder().id(id).queryParams(json).build();
        return this.dao.updateById(po) > 0;
    }

    @Override
    public Report getByUid(final String uid) {
        final ReportExample example = new ReportExample();
        example.or().andUidEqualTo(uid);
        return this.dao.selectOneByExample(example);
    }

    @Override
    public String getSqlText(final int id) {
        final Report po = this.dao.selectById(id);
        return po == null ? "" : po.getSqlText();
    }

    @Override
    public List<ReportMetaDataColumn> getMetaDataColumns(final int dsId, final String sqlText) {
        final List<ReportMetaDataColumn> metaDataColumns = this.executeSqlText(dsId, sqlText);
        final Map<String, ReportMetaDataColumn> commonColumnMap = this.confService.getCommonColumns();
        final Map<String, ReportMetaDataColumn> commonOptionColumnMap = this.confService.getCommonOptionalColumns();

        for (final ReportMetaDataColumn column : metaDataColumns) {
            final String columnName = column.getName();
            if (commonColumnMap.containsKey(columnName)) {
                final ReportMetaDataColumn commonColumn = commonColumnMap.get(columnName);
                column.setType(commonColumn.getType().getValue());
                column.setText(commonColumn.getText());
            }
            if (commonOptionColumnMap.containsKey(columnName)) {
                column.setOptional(true);
                column.setType(commonOptionColumnMap.get(columnName).getType().getValue());
            }
        }
        return metaDataColumns;
    }

    @Override
    public void explainSqlText(final int dsId, final String sqlText) {
        this.executeSqlText(dsId, sqlText);
    }

    @Override
    public List<ReportMetaDataColumn> executeSqlText(final int dsId, final String sqlText) {
        final ReportDataSource reportDataSource = getReportDataSource(dsId);
        return QueryerFactory.create(reportDataSource).parseMetaDataColumns(sqlText);
    }

    @Override
    public List<ReportQueryParamItem> executeQueryParamSqlText(final int dsId, final String sqlText) {
        final ReportDataSource reportDataSource = getReportDataSource(dsId);
        return QueryerFactory.create(reportDataSource).parseQueryParamItems(sqlText);
    }

    @Override
    public List<ReportMetaDataColumn> parseMetaColumns(final String json) {
        if (StringUtils.isBlank(json)) {
            return new ArrayList<>(0);
        }
        return JSON.parseArray(json, ReportMetaDataColumn.class);
    }

    @Override
    public List<QueryParameterOptions> parseQueryParams(final String json) {
        if (StringUtils.isBlank(json)) {
            return new ArrayList<>(0);
        }
        return JSON.parseArray(json, QueryParameterOptions.class);
    }

    @Override
    public ReportOptions parseOptions(final String json) {
        if (StringUtils.isBlank(json)) {
            return ReportOptions.builder().dataRange(7).layout(1).statColumnLayout(1).build();
        }
        return JSON.parseObject(json, ReportOptions.class);
    }

    @Override
    public ReportDataSource getReportDataSource(final int dsId) {
        final DataSource ds = this.dsService.getById(dsId);
        Map<String, Object> options = new HashMap<>(3);
        if (StringUtils.isNotEmpty(ds.getOptions())) {
            options = JSON.parseObject(ds.getOptions());
        }
        return new ReportDataSource(
            ds.getUid(),
            ds.getDriverClass(),
            ds.getJdbcUrl(), ds.getUser(), ds.getPassword(),
            ds.getQueryerClass(),
            ds.getPoolClass(),
            options);
    }
}
