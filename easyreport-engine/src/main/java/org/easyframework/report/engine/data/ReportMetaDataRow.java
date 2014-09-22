package org.easyframework.report.engine.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表元数据行类
 */
public class ReportMetaDataRow {
	private final Map<String, ReportMetaDataCell> cells = new HashMap<String, ReportMetaDataCell>();

	public ReportMetaDataRow() {
	}

	public ReportMetaDataRow add(ReportMetaDataCell cell) {
		this.cells.put(cell.getName(), cell);
		return this;
	}

	public ReportMetaDataRow addAll(List<ReportMetaDataCell> cells) {
		for (ReportMetaDataCell cell : cells) {
			this.add(cell);
		}
		return this;
	}

	public Map<String, ReportMetaDataCell> getCells() {
		return this.cells;
	}

	public ReportMetaDataCell getCell(String name) {
		return this.cells.get(name);
	}

	public Object getCellValue(String name) {
		ReportMetaDataCell cell = this.cells.get(name);
		return (cell == null) ? null : cell.getValue();
	}
}
