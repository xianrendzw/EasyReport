package com.easytoolsoft.easyreport.engine.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表数据行类
 */
public class ReportDataRow {
    private final Map<String, ReportDataCell> cells = new HashMap<>();

    public ReportDataRow() {
    }

    public ReportDataRow add(ReportDataCell cell) {
        this.cells.put(cell.getName(), cell);
        return this;
    }

    public ReportDataRow addAll(List<ReportDataCell> cells) {
        cells.forEach(this::add);
        return this;
    }

    public Map<String, ReportDataCell> getCells() {
        return this.cells;
    }

    public ReportDataCell getCell(String name) {
        return this.cells.get(name);
    }

    public Object getCellValue(String name) {
        ReportDataCell cell = this.cells.get(name);
        return (cell == null) ? "" : cell.getValue();
    }
}
