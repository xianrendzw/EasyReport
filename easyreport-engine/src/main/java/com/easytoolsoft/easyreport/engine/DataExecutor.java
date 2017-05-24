package com.easytoolsoft.easyreport.engine;

import java.util.List;

import com.easytoolsoft.easyreport.engine.data.AbstractReportDataSet;
import com.easytoolsoft.easyreport.engine.data.HorizontalStatColumnDataSet;
import com.easytoolsoft.easyreport.engine.data.LayoutType;
import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataRow;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataSet;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;
import com.easytoolsoft.easyreport.engine.data.VerticalStatColumnDataSet;
import com.easytoolsoft.easyreport.engine.query.Queryer;
import com.easytoolsoft.easyreport.engine.query.QueryerFactory;

/**
 * 数据执行器类，负责选择正确的报表查询器并获取数据，最终转化为成报表的数据集
 *
 * @author tomdeng
 */
public class DataExecutor {
    private final ReportParameter parameter;
    private final ReportDataSource dataSource;
    private final Queryer queryer;

    /**
     * 数据执行器
     *
     * @param parameter 报表参数对象
     */
    public DataExecutor(final ReportParameter parameter) {
        this.parameter = parameter;
        this.dataSource = null;
        this.queryer = null;
    }

    /**
     * 数据执行器
     *
     * @param dataSource 报表数据源配置对象
     * @param parameter  报表参数对象
     */
    public DataExecutor(final ReportDataSource dataSource, final ReportParameter parameter) {
        this.dataSource = dataSource;
        this.parameter = parameter;
        this.queryer = null;
    }

    /**
     * 数据执行器
     *
     * @param queryer   报表查询器对象
     * @param parameter 报表参数对象
     */
    public DataExecutor(final Queryer queryer, final ReportParameter parameter) {
        this.dataSource = null;
        this.parameter = parameter;
        this.queryer = queryer;
    }

    /**
     * 选择正确的报表查询器并获取数据，最终转化为成报表的数据集
     *
     * @return ReportDataSet报表数据集对象
     */
    public AbstractReportDataSet execute() {
        final Queryer queryer = this.getQueryer();
        if (queryer == null) {
            throw new RuntimeException("未指定报表查询器对象!");
        }
        final List<ReportMetaDataColumn> metaDataColumns = queryer.getMetaDataColumns();
        final List<ReportMetaDataRow> metaDataRows = queryer.getMetaDataRows();
        final ReportMetaDataSet metaDataSet = new ReportMetaDataSet(metaDataRows, metaDataColumns,
            this.parameter.getEnabledStatColumns());
        return this.parameter.getStatColumnLayout() == LayoutType.VERTICAL ?
            new VerticalStatColumnDataSet(metaDataSet, this.parameter.getLayout(), this.parameter.getStatColumnLayout())
            :
                new HorizontalStatColumnDataSet(metaDataSet, this.parameter.getLayout(),
                    this.parameter.getStatColumnLayout());
    }

    /**
     * 选择正确的报表查询器并获取数据，最终转化为成报表的数据集
     *
     * @param metaDataSet
     * @return ReportDataSet报表数据集对象
     */
    public AbstractReportDataSet execute(final ReportMetaDataSet metaDataSet) {
        if (metaDataSet == null) {
            throw new RuntimeException("报表元数据集不能为null!");
        }
        return this.parameter.getStatColumnLayout() == LayoutType.VERTICAL ?
            new VerticalStatColumnDataSet(metaDataSet, this.parameter.getLayout(), this.parameter.getStatColumnLayout())
            :
                new HorizontalStatColumnDataSet(metaDataSet, this.parameter.getLayout(),
                    this.parameter.getStatColumnLayout());
    }

    private Queryer getQueryer() {
        if (this.queryer != null) {
            return this.queryer;
        }
        return QueryerFactory.create(this.dataSource, this.parameter);
    }
}
