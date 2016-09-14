package com.easytoolsoft.easyreport.engine.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 报表参数类
 */
public class ReportParameter {
    private String id;
    private String name;
    private LayoutType layout;
    private LayoutType statColumnLayout;
    private String sqlText;
    private List<ReportMetaDataColumn> metaColumns;
    private Set<String> enabledStatColumns;
    private boolean isRowSpan = true;

    public ReportParameter() {
    }

    /**
     * 报表参数构造函数
     *
     * @param id                 报表唯一id
     * @param name               报表名称
     * @param layout             报表布局形式 (1:横向;2:纵向)
     * @param statColumnLayout   报表统计列或计算列布局形式 (1:横向;2:纵向)
     * @param metaColumns        报表元数据列集合
     * @param enabledStatColumns 报表中启用的统计(含计算)列名集合
     * @param isRowSpan          是否生成rowspan（跨行)的表格,默认为true
     * @param sqlText            报表sql查询语句
     */
    public ReportParameter(String id, String name, int layout, int statColumnLayout,
                           List<ReportMetaDataColumn> metaColumns, Set<String> enabledStatColumns,
                           boolean isRowSpan, String sqlText) {
        this.id = id;
        this.name = name;
        this.layout = LayoutType.valueOf(layout);
        this.statColumnLayout = LayoutType.valueOf(statColumnLayout);
        this.metaColumns = metaColumns;
        this.enabledStatColumns = enabledStatColumns;
        this.isRowSpan = isRowSpan;
        this.sqlText = sqlText;
    }

    /**
     * 获取报表唯一id
     *
     * @return
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置报表唯一id
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取报表名称
     *
     * @return 报表名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置报表名称
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取报表布局形式(1:横向;2:纵向)
     *
     * @return Layout
     */
    public LayoutType getLayout() {
        return this.layout;
    }

    /**
     * 设置报表布局形式(1:横向;2:纵向)
     *
     * @param layout 报表布局形式(1:横向;2:纵向)
     */
    public void setLayout(int layout) {
        this.layout = LayoutType.valueOf(layout);
    }

    /**
     * 获取报表统计列或计算列布局形式 (1:横向;2:纵向)
     *
     * @return (1:横向;2:纵向)
     */
    public LayoutType getStatColumnLayout() {
        return statColumnLayout;
    }

    /**
     * 设置报表统计列或计算列布局形式 (1:横向;2:纵向)
     *
     * @param statColumnLayout (1:横向;2:纵向)
     */
    public void setStatColumnLayout(LayoutType statColumnLayout) {
        this.statColumnLayout = statColumnLayout;
    }

    /**
     * 获取报表SQL语句
     *
     * @return 报表SQL语句
     */
    public String getSqlText() {
        return this.sqlText;
    }

    /**
     * 设置报表SQL语句
     *
     * @param sqlText
     */
    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    /**
     * 获取报表元数据列集合
     *
     * @return 报表元数据列集合
     */
    public List<ReportMetaDataColumn> getMetaColumns() {
        return this.metaColumns;
    }

    /**
     * 设置报表元数据列集合
     *
     * @param metaColumns 报表元数据列集合
     */
    public void setMetaColumns(List<ReportMetaDataColumn> metaColumns) {
        this.metaColumns = metaColumns;
    }

    /**
     * 获取报表中启用的统计(含计算)列名集合。
     * <p/>
     * 如果未设置任何列名，则在报表中启用全部统计统计(含计算)列
     *
     * @return 报表中启用的统计(含计算)列名集合
     */
    public Set<String> getEnabledStatColumns() {
        return enabledStatColumns == null ? new HashSet<>(0) : this.enabledStatColumns;
    }

    /**
     * 设置报表中启用的统计(含计算)列名集合。
     *
     * @param enabledStatColumns 报表中启用的统计(含计算)列名集合
     */
    public void setEnabledStatColumns(Set<String> enabledStatColumns) {
        this.enabledStatColumns = enabledStatColumns;
    }

    /**
     * 获取是否生成rowspan（跨行)的表格,默认为true
     *
     * @return true|false
     */
    public boolean isRowSpan() {
        return isRowSpan;
    }

    /**
     * 设置是否生成rowspan（跨行)的表格,默认为true
     *
     * @param isRowSpan true|false
     */
    public void setRowSpan(boolean isRowSpan) {
        this.isRowSpan = isRowSpan;
    }
}
