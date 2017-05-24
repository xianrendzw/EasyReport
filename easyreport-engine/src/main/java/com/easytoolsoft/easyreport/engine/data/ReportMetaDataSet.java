package com.easytoolsoft.easyreport.engine.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 报表元数据集类
 *
 * @author tomdeng
 */
public class ReportMetaDataSet {
    private final List<ReportMetaDataRow> rows;
    private final List<ReportMetaDataColumn> columns;
    private List<ReportMetaDataColumn> nonComputeColumns;
    private List<ReportMetaDataColumn> layoutColumns;
    private List<ReportMetaDataColumn> dimColumns;
    private List<ReportMetaDataColumn> statColumns;

    /**
     * 构造函数
     *
     * @param rows               报表元数据行集合
     * @param columns            报表元数据列集合
     * @param enabledStatColumns 报表中启用的统计列
     */
    public ReportMetaDataSet(final List<ReportMetaDataRow> rows,
                             final List<ReportMetaDataColumn> columns,
                             final Set<String> enabledStatColumns) {
        this.rows = rows;
        this.columns = columns;
        this.initilizeColumn(enabledStatColumns);
    }

    /**
     * 获取报表的元数据行集合
     *
     * @return List<ReportMetaDataRow>
     */
    public List<ReportMetaDataRow> getRows() {
        return this.rows;
    }

    /**
     * 获取报表的所有元数据列
     *
     * @return List<ReportMetaDataColumn>
     */
    public List<ReportMetaDataColumn> getColumns() {
        return this.columns;
    }

    /**
     * 获取报表的所有非计算元数据列
     */
    public List<ReportMetaDataColumn> getNonComputeColumns() {
        return this.nonComputeColumns;
    }

    /**
     * 获取报表的布局元数据列
     */
    public List<ReportMetaDataColumn> getLayoutColumns() {
        return this.layoutColumns;
    }

    /**
     * 获取报表的维度元数据列
     */
    public List<ReportMetaDataColumn> getDimColumns() {
        return this.dimColumns;
    }

    /**
     * 获取报表的统计(含计算)元数据列
     */
    public List<ReportMetaDataColumn> getStatColumns() {
        return this.statColumns;
    }

    private void initilizeColumn(final Set<String> enabledStatColumns) {
        this.nonComputeColumns = new ArrayList<>();
        this.layoutColumns = new ArrayList<>();
        this.dimColumns = new ArrayList<>();
        this.statColumns = new ArrayList<>();

        for (int i = 0; i < this.columns.size(); i++) {
            final ReportMetaDataColumn column = this.columns.get(i);
            column.setOrdinal(i + 1);
            if (column.getType() != ColumnType.COMPUTED) {
                this.nonComputeColumns.add(column);
            }
            if (column.getType() == ColumnType.LAYOUT) {
                this.layoutColumns.add(column);
            } else if (column.getType() == ColumnType.DIMENSION) {
                this.dimColumns.add(column);
            } else if (column.getType() == ColumnType.STATISTICAL || column.getType() == ColumnType.COMPUTED) {
                if (enabledStatColumns.size() > 0 && !enabledStatColumns.contains(column.getName())) {
                    column.setHidden(true);
                }
                this.statColumns.add(column);
            }
        }
    }
}
