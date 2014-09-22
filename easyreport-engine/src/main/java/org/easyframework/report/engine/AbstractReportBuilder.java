package org.easyframework.report.engine;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.easyframework.report.engine.data.ColumnTree;
import org.easyframework.report.engine.data.ColumnTreeNode;
import org.easyframework.report.engine.data.ReportDataSet;

public abstract class AbstractReportBuilder {
	protected final ReportDataSet reportData;
	protected final StringBuilder tableRows = new StringBuilder();

	protected AbstractReportBuilder(ReportDataSet reportData) {
		this.reportData = reportData;
	}

	/**
	 * 根据树结构生成table中的跨行列(rowspan)
	 * 
	 * @param pathTreeNodeMap 树中每个节点的path属性为key,treeNode属性为value的map对象
	 * @param lastNodePaths 上一个跨行结点的树路径
	 * @param rowSpanNode 当前跨行结点
	 * @return 当前跨行结点的树路径
	 */
	protected String[] drawRowSpanColumn(Map<String, ColumnTreeNode> pathTreeNodeMap, String[] lastNodePaths,
			ColumnTreeNode rowSpanNode) {
		String[] paths = StringUtils.splitPreserveAllTokens(rowSpanNode.getPath(), this.reportData.getSeparatorChars());
		int level = paths.length > 1 ? paths.length - 1 : 1;
		String[] currNodePaths = new String[level];
		for (int i = 0; i < level; i++) {
			String currPath = paths[i] + this.reportData.getSeparatorChars();
			currNodePaths[i] = (i > 0 ? currNodePaths[i - 1] + currPath : currPath);
			if (lastNodePaths != null && lastNodePaths[i].equals(currNodePaths[i]))
				continue;
			ColumnTreeNode treeNode = pathTreeNodeMap.get(currNodePaths[i]);
			if (treeNode == null) {
				this.tableRows.append("<td class=\"back\"></td>");
			} else {
				String rowspan = String.format("rowspan=\"%s\"", treeNode.getSpans());
				this.tableRows.append("<td class=\"back\" ").append(rowspan).append(">");
				this.tableRows.append(treeNode.getValue()).append("</td>");
			}
		}
		lastNodePaths = currNodePaths;
		return lastNodePaths;
	}

	/**
	 * 按层次遍历报表列树中每个结点，然后以结点path为key,treeNode属性为value，生成一个Map对象
	 * 
	 * @param columnTree 报表列树对象
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
