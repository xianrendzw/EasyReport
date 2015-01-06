package org.easyframework.report.engine;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.easyframework.report.engine.data.ColumnTree;
import org.easyframework.report.engine.data.ColumnTreeNode;
import org.easyframework.report.engine.data.ReportDataSet;
import org.easyframework.report.engine.data.ReportParameter;

public abstract class AbstractReportBuilder {
	protected final ReportDataSet reportDataSet;
	protected final ReportParameter reportParameter;
	protected final StringBuilder tableRows = new StringBuilder();

	protected AbstractReportBuilder(ReportDataSet reportDataSet, ReportParameter reportParameter) {
		this.reportDataSet = reportDataSet;
		this.reportParameter = reportParameter;
	}

	/**
	 * 生成表体左边每一行的单元格
	 * 
	 * @param pathTreeNodeMap
	 *            树中每个节点的path属性为key,treeNode属性为value的map对象
	 * @param lastNodePaths
	 *            上一个跨行结点的树路径
	 * @param rowNode
	 *            当前行结点
	 * @param isRowSpan
	 *            是否跨行(rowspan)
	 * @return
	 */
	protected String[] drawLeftFixedColumn(Map<String, ColumnTreeNode> pathTreeNodeMap,
			String[] lastNodePaths, ColumnTreeNode rowNode, boolean isRowSpan) {
		if (isRowSpan) {
			return this.drawLeftRowSpanColumn(pathTreeNodeMap, lastNodePaths, rowNode);
		}
		String[] paths = StringUtils.splitPreserveAllTokens(rowNode.getPath(), this.reportDataSet.getSeparatorChars());
		int level = paths.length > 1 ? paths.length - 1 : 1;
		for (int i = 0; i < level; i++) {
			this.tableRows.append(String.format("<td class=\"easyreport-fixed-column\">%s</td>", paths[i]));
		}
		return null;
	}

	/**
	 * 生成表体左边每一行的跨行(rowspan)单元格
	 * 
	 * @param pathTreeNodeMap
	 *            树中每个节点的path属性为key,treeNode属性为value的map对象
	 * @param lastNodePaths
	 *            上一个跨行结点的树路径
	 * @param rowNode
	 *            当前行结点
	 * @return 当前跨行结点的树路径
	 */
	protected String[] drawLeftRowSpanColumn(Map<String, ColumnTreeNode> pathTreeNodeMap, String[] lastNodePaths, ColumnTreeNode rowNode) {
		String[] paths = StringUtils.splitPreserveAllTokens(rowNode.getPath(), this.reportDataSet.getSeparatorChars());
		int level = paths.length > 1 ? paths.length - 1 : 1;
		String[] currNodePaths = new String[level];
		for (int i = 0; i < level; i++) {
			String currPath = paths[i] + this.reportDataSet.getSeparatorChars();
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

	/**
	 * 按层次遍历报表列树中每个结点，然后以结点path为key,treeNode属性为value，生成一个Map对象
	 * 
	 * @param columnTree
	 *            报表列树对象
	 * @return 树中每个节点的path属性为key,treeNode属性为value的map对象
	 */
	protected Map<String, ColumnTreeNode> getPathTreeNodeMap(ColumnTree columnTree) {
		Map<String, ColumnTreeNode> pathTreeNodeMap = new HashMap<String, ColumnTreeNode>();
		for (int level = 0; level < columnTree.getDepth(); level++) {
			for (ColumnTreeNode treeNode : columnTree.getNodesByLevel(level)) {
				pathTreeNodeMap.put(treeNode.getPath(), treeNode);
			}
		}
		return pathTreeNodeMap;
	}
}
