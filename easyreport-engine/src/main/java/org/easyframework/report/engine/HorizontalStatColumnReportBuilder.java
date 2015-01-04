package org.easyframework.report.engine;

import java.util.List;
import java.util.Map;

import org.easyframework.report.engine.data.ColumnTree;
import org.easyframework.report.engine.data.ColumnTreeNode;
import org.easyframework.report.engine.data.LayoutType;
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
		// 获取表头左边的一般维度列(即非布局维度列)
		List<ReportDataColumn> headerDimColumns = reportParameter.getLayout() == LayoutType.HORIZONTAL ?
				reportDataSet.getDimColumns() : reportDataSet.getLayoutColumns();
		// 获取表头右边的布局维度列树型结构
		ColumnTree headerColumnTree = this.reportDataSet.getHeaderColumnTree();
		int rowCount = headerColumnTree.getDepth();
		String rowSpan = rowCount > 1 ? String.format(" rowspan=\"%s\"", rowCount) : "";

		this.tableRows.append("<thead>");
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			this.tableRows.append("<tr class=\"easyreport-header\">");
			if (rowIndex == 0) {
				for (ReportDataColumn column : headerDimColumns) {
					this.tableRows.append(String.format("<th%s>%s</th>", rowSpan, column.getText()));
				}
			}
			for (ColumnTreeNode treeNode : headerColumnTree.getNodesByLevel(rowIndex)) {
				String colSpan = treeNode.getSpans() > 1 ? String.format(" colspan=\"%s\"", treeNode.getSpans()) : "";
				this.tableRows.append(String.format("<th%s>%s</th>", colSpan, treeNode.getValue()));
			}
			this.tableRows.append("</tr>");
		}
		this.tableRows.append("</thead>");
	}

	@Override
	public void drawTableBodyRows() {
		ColumnTree dimColumnTree = reportParameter.getLayout() == LayoutType.HORIZONTAL ?
				reportDataSet.getDimColumnTree() : reportDataSet.getLayoutColumnTree();
		List<ColumnTreeNode> dimRowNodes = dimColumnTree.getLastLevelNodes();
		List<ColumnTreeNode> statColumnNodes = reportParameter.getLayout() == LayoutType.HORIZONTAL ?
				reportDataSet.getLayoutColumnTree().getLastLevelNodes() : reportDataSet.getDimColumnTree().getLastLevelNodes();
		Map<String, ReportDataRow> statRowMap = reportDataSet.getDataRowMap();
		List<ReportDataColumn> statColumns = reportDataSet.getDisplayStatColumns();
		Map<String, ColumnTreeNode> pathTreeNodeMap = this.getPathTreeNodeMap(dimColumnTree);

		int rowIndex = 0;
		String[] lastNodePaths = null;
		this.tableRows.append("<tbody>");
		for (ColumnTreeNode dimRowNode : dimRowNodes) {
			this.tableRows.append("<tr").append(rowIndex % 2 == 0 ? " class=\"easyreport-row\"" : "").append(">");
			lastNodePaths = this.drawRowSpanColumn(pathTreeNodeMap, lastNodePaths, dimRowNode);
			for (ColumnTreeNode statColumnNode : statColumnNodes) {
				String rowKey = reportParameter.getLayout() == LayoutType.HORIZONTAL ?
						(statColumnNode.getPath() + dimRowNode.getPath()) : (dimRowNode.getPath() + statColumnNode.getPath());
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
