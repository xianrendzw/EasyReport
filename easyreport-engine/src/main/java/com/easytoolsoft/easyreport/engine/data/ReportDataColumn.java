package com.easytoolsoft.easyreport.engine.data;

/**
 * 报表数据列
 */
public class ReportDataColumn {
    private final ReportMetaDataColumn metaDataColumn;
    private String parentName;

    public ReportDataColumn(ReportMetaDataColumn metaDataColumn) {
        this.metaDataColumn = metaDataColumn;
    }

    /**
     * 获取报表列的元数据
     *
     * @return ReportMetaDataColumn
     */
    public ReportMetaDataColumn getMetaData() {
        return this.metaDataColumn;
    }

    /**
     * 获取报表列名
     *
     * @return 报表列名
     */
    public String getName() {
        return this.metaDataColumn.getName();
    }

    /**
     * 获取报表列对应的标题
     *
     * @return 报表列对应的标题
     */
    public String getText() {
        return this.metaDataColumn.getText();
    }

    /**
     * 获取报表列类型(1：布局列,2:维度列，3:统计列)
     *
     * @return 列类型(1：布局列, 2:维度列，3:统计列)
     */
    public ColumnType getType() {
        return this.metaDataColumn.getType();
    }

    /**
     * 获取报表列名的所属的父列 如果当前列为子统计,则可能需要设置该属性，否则与name属性相同
     *
     * @return 报表列名
     */
    public String getParentName() {
        return (this.parentName == null) ? this.getName() : this.parentName;
    }

    /**
     * 设置报表列名的所属的父列 如果当前列为子统计,则可能需要设置该属性，否则与name属性相同
     *
     * @param parentName
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
