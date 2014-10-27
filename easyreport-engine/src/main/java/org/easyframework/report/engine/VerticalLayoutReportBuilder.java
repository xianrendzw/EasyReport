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
 * 纵向布局形式的报表生成类
 */
public class VerticalLayoutReportBuilder extends AbstractReportBuilder implements ReportBuilder {

	public VerticalLayoutReportBuilder(ReportDataSet reportData, ReportParameter reportParameter) {
		super(reportData, reportParameter);
	}

	@Override
	public String getTable() {
		StringBuilder table = new StringBuilder();
		table.append("<table id=\"report\" class=\"grid\">");
		table.append(this.tableRows.toString());
		table.append("</table>");
		return table.toString();
	}

	@Override
	public void drawTableHeaderRows() {
		ColumnTree headerColumnTree = this.reportData.getHeaderColumnTree();
		List<ReportDataColumn> layoutColumns = reportData.getLayoutColumns();
		int rowCount = headerColumnTree.getDepth();

		this.tableRows.append("<thead>");
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			this.tableRows.append("<tr class=\"caption\">");
			if (rowIndex == 0) {
				for (ReportDataColumn layoutColumn : layoutColumns) {
					String rowspan = String.format("rowspan=\"%s\"", rowCount);
					this.tableRows.append("<th ").append(rowspan).append(">");
					this.tableRows.append(layoutColumn.getText()).append("</th>");
				}
			}
			for (ColumnTreeNode treeNode : headerColumnTree.getNodesByLevel(rowIndex)) {
				this.tableRows.append(String.format("<th colspan=\"%s\">", treeNode.getSpans()));
				this.tableRows.append(treeNode.getValue()).append("</th>");
			}
			this.tableRows.append("</tr>");
		}
		this.tableRows.append("</thead>");
	}

	@Override
	public void drawTableBodyRows() {
		ColumnTree layoutColumnTree = reportData.getLayoutColumnTree();
		List<ColumnTreeNode> layoutLeafNodes = layoutColumnTree.getLastLevelNodes();
		List<ColumnTreeNode> dimLeafNodes = reportData.getDimColumnTree().getLastLevelNodes();
		Map<String, ReportDataRow> statRowMap = reportData.getDataRowMap();
		List<ReportDataColumn> statColumns = reportData.getDisplayStatColumns();
		Map<String, ColumnTreeNode> pathTreeNodeMap = this.getPathTreeNodeMap(layoutColumnTree);

		int rowIndex = 0;
		String[] lastNodePaths = null;
		this.tableRows.append("<tbody>");
		for (ColumnTreeNode layoutLeafNode : layoutLeafNodes) {
			this.tableRows.append("<tr").append(rowIndex % 2 == 0 ? " class=\"c\"" : "").append(">");
			lastNodePaths = this.drawRowSpanColumn(pathTreeNodeMap, lastNodePaths, layoutLeafNode);
			for (ColumnTreeNode dimLeafNode : dimLeafNodes) {
				String rowKey = layoutLeafNode.getPath() + dimLeafNode.getPath();
				ReportDataRow statRow = statRowMap.get(rowKey);
				if (statRow == null)
					statRow = new ReportDataRow();
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
		// TODO Auto-generated method stub
	}
}
