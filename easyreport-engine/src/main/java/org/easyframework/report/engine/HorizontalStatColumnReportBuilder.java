package org.easyframework.report.engine;

import java.util.List;
import java.util.Map;

import org.easyframework.report.engine.data.ColumnTree;
import org.easyframework.report.engine.data.ColumnTreeNode;
import org.easyframework.report.engine.data.ReportDataColumn;
import org.easyframework.report.engine.data.ReportDataRow;
import org.easyframework.report.engine.data.ReportDataSet;
import org.easyframework.report.engine.data.ReportParameter;

/**
 * 横向展示统计列的报表生成类
 */
public class HorizontalStatColumnReportBuilder extends AbstractReportBuilder implements ReportBuilder {

	public HorizontalStatColumnReportBuilder(ReportDataSet reportDataSet, ReportParameter reportParameter) {
		super(reportDataSet, reportParameter);
	}

	@Override
	public String getTable() {
		StringBuilder table = new StringBuilder();
		table.append("<table id=\"easyreport\" class=\"easyreport\">");
		table.append(this.tableRows.toString());
		table.append("</table>");
		return table.toString();
	}

	@Override
	public void drawTableHeaderRows() {
		// 获取表头左边的固定列集合
		List<ReportDataColumn> leftFixedColumns = this.reportDataSet.getHeaderLeftFixedColumns();
		// 获取表头右边列树型结构
		ColumnTree rightColumnTree = this.reportDataSet.getHeaderRightColumnTree();
		int rowCount = rightColumnTree.getDepth();
		String rowSpan = rowCount > 1 ? String.format(" rowspan=\"%s\"", rowCount) : "";

		this.tableRows.append("<thead>");
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			this.tableRows.append("<tr class=\"easyreport-header\">");
			if (rowIndex == 0) {
				for (ReportDataColumn leftColumn : leftFixedColumns) {
					this.tableRows.append(String.format("<th%s>%s</th>", rowSpan, leftColumn.getText()));
				}
			}
			for (ColumnTreeNode rightColumn : rightColumnTree.getNodesByLevel(rowIndex)) {
				String colSpan = rightColumn.getSpans() > 1 ? String.format(" colspan=\"%s\"", rightColumn.getSpans()) : "";
				this.tableRows.append(String.format("<th%s>%s</th>", colSpan, rightColumn.getValue()));
			}
			this.tableRows.append("</tr>");
		}
		this.tableRows.append("</thead>");
	}

	@Override
	public void drawTableBodyRows() {
		ColumnTree leftFixedColumnTree = this.reportDataSet.getBodyLeftFixedColumnTree();
		List<ColumnTreeNode> rowNodes = leftFixedColumnTree.getLastLevelNodes();
		List<ColumnTreeNode> columnNodes = this.reportDataSet.getBodyRightColumnNodes();
		Map<String, ReportDataRow> statRowMap = reportDataSet.getRowMap();
		List<ReportDataColumn> statColumns = reportDataSet.getEnabledStatColumns();
		Map<String, ColumnTreeNode> pathTreeNodeMap = this.getPathTreeNodeMap(leftFixedColumnTree);

		int rowIndex = 0;
		String[] lastNodePaths = null;
		this.tableRows.append("<tbody>");
		for (ColumnTreeNode rowNode : rowNodes) {
			this.tableRows.append("<tr").append(rowIndex % 2 == 0 ? " class=\"easyreport-row\"" : "").append(">");
			lastNodePaths = this.drawLeftFixedColumn(pathTreeNodeMap, lastNodePaths, rowNode, this.reportParameter.isRowSpan());
			for (ColumnTreeNode columnNode : columnNodes) {
				String rowKey = this.reportDataSet.getRowKey(rowNode, columnNode);
				ReportDataRow statRow = statRowMap.get(rowKey);
				if (statRow == null) {
					statRow = new ReportDataRow();
				}
				for (ReportDataColumn statColumn : statColumns) {
					Object cell = statRow.getCell(statColumn.getName());
					String value = (cell == null) ? "" : cell.toString();
					this.tableRows.append("<td>").append(value).append("</td>");
				}
			}
			this.tableRows.append("</tr>");
			rowIndex++;
		}
		this.tableRows.append("</tbody>");
	}

	@Override
	public void drawTableFooterRows() {
	}
}
