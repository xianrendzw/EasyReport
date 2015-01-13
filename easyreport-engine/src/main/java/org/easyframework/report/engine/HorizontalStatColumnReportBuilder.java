package org.easyframework.report.engine;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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

	/**
	 * 横向展示统计列的报表生成类
	 * 
	 * @param reportDataSet
	 *            报表数据集
	 * @param reportParameter
	 *            报表参数
	 */
	public HorizontalStatColumnReportBuilder(ReportDataSet reportDataSet, ReportParameter reportParameter) {
		super(reportDataSet, reportParameter);
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

	@Override
	protected String[] drawLeftRowSpanColumn(Map<String, ColumnTreeNode> pathTreeNodeMap, String[] lastNodePaths, ColumnTreeNode rowNode) {
		String[] paths = StringUtils.splitPreserveAllTokens(rowNode.getPath(), this.reportDataSet.getPathSeparator());
		if (paths == null || paths.length == 0) {
			return null;
		}

		int level = paths.length > 1 ? paths.length - 1 : 1;
		String[] currNodePaths = new String[level];
		for (int i = 0; i < level; i++) {
			String currPath = paths[i] + this.reportDataSet.getPathSeparator();
			currNodePaths[i] = (i > 0 ? currNodePaths[i - 1] + currPath : currPath);
			if (lastNodePaths != null && lastNodePaths[i].equals(currNodePaths[i]))
				continue;
			ColumnTreeNode treeNode = pathTreeNodeMap.get(currNodePaths[i]);
			if (treeNode == null) {
				this.tableRows.append("<td class=\"easyreport-fixed-column\"></td>");
			} else {
				String rowspan = treeNode.getSpans() > 1 ? String.format(" rowspan=\"%s\"", treeNode.getSpans()) : "";
				this.tableRows.append(String.format("<td class=\"easyreport-fixed-column\"%s>%s</td>", rowspan, treeNode.getValue()));
			}
		}
		lastNodePaths = currNodePaths;
		return lastNodePaths;
	}
}
