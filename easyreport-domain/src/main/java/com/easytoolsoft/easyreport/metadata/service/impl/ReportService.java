package com.easytoolsoft.easyreport.metadata.service.impl;

import com.alibaba.fastjson.JSON;
import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import com.easytoolsoft.easyreport.data.common.service.AbstractCrudService;
import com.easytoolsoft.easyreport.data.metadata.dao.IReportDao;
import com.easytoolsoft.easyreport.data.metadata.example.ReportExample;
import com.easytoolsoft.easyreport.data.metadata.po.Category;
import com.easytoolsoft.easyreport.data.metadata.po.DataSource;
import com.easytoolsoft.easyreport.data.metadata.po.Report;
import com.easytoolsoft.easyreport.data.metadata.po.ReportOptions;
import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.engine.data.ReportQueryParamItem;
import com.easytoolsoft.easyreport.engine.query.QueryerFactory;
import com.easytoolsoft.easyreport.metadata.service.ICategoryService;
import com.easytoolsoft.easyreport.metadata.service.IConfService;
import com.easytoolsoft.easyreport.metadata.service.IDataSourceService;
import com.easytoolsoft.easyreport.metadata.service.IReportService;
import com.easytoolsoft.easyreport.metadata.vo.QueryParameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("EzrptMetaReportService")
public class ReportService
        extends AbstractCrudService<IReportDao, Report, ReportExample>
        implements IReportService {
    @Resource
    private IDataSourceService dsService;
    @Resource
    private IConfService confService;
    @Resource
    private ICategoryService categoryService;

    @Override
    protected ReportExample getPageExample(String fieldName, String keyword) {
        ReportExample example = new ReportExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public int add(Report po) {
        Category category = this.categoryService.getById(po.getCategoryId());
        po.setUid(UUID.randomUUID().toString());
        this.dao.insert(po);
        po = Report.builder()
                .id(po.getId())
                .path(category.getPath() + "|" + po.getId())
                .build();
        this.dao.updateById(po);
        return po.getId();
    }

    @Override
    public List<Report> getByPage(PageInfo page, Integer categoryId, String fieldName, String keyword) {
        ReportExample example = new ReportExample();
        example.or()
                .andCategoryIdEqualTo(categoryId)
                .andFieldLike(fieldName, keyword);
        return this.getByPage(page, example);
    }

    @Override
    public boolean saveQueryParams(int id, String json) {
        Report po = Report.builder().id(id).queryParams(json).build();
        return this.dao.updateById(po) > 0;
    }

    @Override
    public Report getByUid(String uid) {
        ReportExample example = new ReportExample();
        example.or().andUidEqualTo(uid);
        return this.dao.selectOneByExample(example);
    }

    @Override
    public String getSqlText(int id) {
        Report po = this.dao.selectById(id);
        return po == null ? "" : po.getSqlText();
    }

    @Override
    public List<ReportMetaDataColumn> getMetaDataColumns(int dsId, String sqlText) {
        List<ReportMetaDataColumn> metaDataColumns = this.executeSqlText(dsId, sqlText);
        Map<String, ReportMetaDataColumn> commonColumnMap = this.confService.getCommonColumns();
        Map<String, ReportMetaDataColumn> commonOptionColumnMap = this.confService.getCommonOptionalColumns();

        for (ReportMetaDataColumn column : metaDataColumns) {
            String columnName = column.getName();
            if (commonColumnMap.containsKey(columnName)) {
                ReportMetaDataColumn commonColumn = commonColumnMap.get(columnName);
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
    public void explainSqlText(int dsId, String sqlText) {
        this.executeSqlText(dsId, sqlText);
    }

    @Override
    public List<ReportMetaDataColumn> executeSqlText(int dsId, String sqlText) {
        ReportDataSource reportDataSource = getReportDataSource(dsId);
        return QueryerFactory.create(reportDataSource).parseMetaDataColumns(sqlText);
    }

    @Override
    public List<ReportQueryParamItem> executeQueryParamSqlText(int dsId, String sqlText) {
        ReportDataSource reportDataSource = getReportDataSource(dsId);
        return QueryerFactory.create(reportDataSource).parseQueryParamItems(sqlText);
    }

    @Override
    public List<ReportMetaDataColumn> parseMetaColumns(String json) {
        if (StringUtils.isBlank(json)) {
            return new ArrayList<>(0);
        }
        return JSON.parseArray(json, ReportMetaDataColumn.class);
    }

    @Override
    public List<QueryParameter> parseQueryParams(String json) {
        if (StringUtils.isBlank(json)) {
            return new ArrayList<>(0);
        }
        return JSON.parseArray(json, QueryParameter.class);
    }

    @Override
    public ReportOptions parseOptions(String json) {
        if (StringUtils.isBlank(json)) {
            return ReportOptions.builder().dataRange(7).layout(1).statColumnLayout(1).build();
        }
        return JSON.parseObject(json, ReportOptions.class);
    }

    @Override
    public ReportDataSource getReportDataSource(int dsId) {
        DataSource ds = this.dsService.getById(dsId);
        Map<String, Object> options = new HashMap<>(3);
        if (StringUtils.isNotEmpty(ds.getOptions())) {
            options = JSON.parseObject(ds.getOptions());
        }
        return new ReportDataSource(
                ds.getUid(),
                ds.getDriverClass(),
                ds.getJdbcUrl(), ds.getUser(), ds.getPassword(),
                options);
    }
}
