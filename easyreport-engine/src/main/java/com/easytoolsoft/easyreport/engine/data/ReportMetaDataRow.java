package com.easytoolsoft.easyreport.engine.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表元数据行类
 *
 * @author tomdeng
 */
public class ReportMetaDataRow {
    private final Map<String, ReportMetaDataCell> cells = new HashMap<>();
    private final StringBuilder rowKeyBuilder = new StringBuilder("");

    public ReportMetaDataRow() {
    }

    public ReportMetaDataRow add(final ReportMetaDataCell cell) {
        this.cells.put(cell.getName(), cell);
        if (cell.getColumn().getType() != ColumnType.STATISTICAL) {
            final Object cellValue = cell.getValue();
            this.rowKeyBuilder.append((cellValue == null) ? "" : cellValue.toString().trim());
            this.rowKeyBuilder.append("$");
        }
        return this;
    }

    public ReportMetaDataRow addAll(final List<ReportMetaDataCell> cells) {
        cells.forEach(this::add);
        return this;
    }

    public Map<String, ReportMetaDataCell> getCells() {
        return this.cells;
    }

    public ReportMetaDataCell getCell(final String name) {
        return this.cells.get(name);
    }

    public Object getCellValue(final String name) {
        final ReportMetaDataCell cell = this.cells.get(name);
        return (cell == null) ? null : cell.getValue();
    }

    public String getRowKey() {
        return this.rowKeyBuilder.toString();
    }
}
