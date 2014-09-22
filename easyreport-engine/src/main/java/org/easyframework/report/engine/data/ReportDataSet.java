package org.easyframework.report.engine.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.easyframework.report.engine.exception.NotFoundLayoutColumnException;
import org.easyframework.report.engine.util.AviatorExprUtils;
import org.easyframework.report.engine.util.ComparatorUtils;

/**
 * 报表数据类,包含生成报表所需的数据集，配置及元数据。
 */
public class ReportDataSet {
	private final String separatorChar = "$";
	private final ReportMetaDataSet metaDataSet;
	private final LayoutType layout;
	private final LayoutType statColumnLayout;
	private List<ReportDataColumn> layoutColumns;
	private List<ReportDataColumn> dimColumns;
	private List<ReportDataColumn> displayStatColumns;
	private List<ReportDataColumn> computedColumns;
	private List<ReportDataColumn> statColumns;
	private List<ReportDataColumn> nonStatColumns;
	private ColumnTree headerColumnTree;
	private ColumnTree layoutColumnTree;
	private ColumnTree dimColumnTree;
	private ColumnTree statColumnTree;

	/**
	 * 
	 * @param metaDataSet
	 * @param layout
	 * @param statColumnLayout
	 */
	public ReportDataSet(ReportMetaDataSet metaDataSet, LayoutType layout, LayoutType statColumnLayout) {
		this.metaDataSet = metaDataSet;
		this.layout = layout;
		this.statColumnLayout = statColumnLayout;
	}

	/**
	 * 获取报表数据集中行数据中的列分隔字符串
	 * <sample>layoutValue$col1value$col2value$col3value$...</sample>
	 * 
	 * @return
	 */
	public String getSeparatorChars() {
		return this.separatorChar;
	}

	/**
	 * 获取报表元数据对象
	 * 
	 * @return {@link ReportMetaDataSet}
	 */
	public ReportMetaDataSet getMetaData() {
		return this.metaDataSet;
	}

	/**
	 * 获取表头列树
	 * 
	 * @return ColumnTree
	 */
	public ColumnTree getHeaderColumnTree() {
		if (this.headerColumnTree != null) {
			return this.headerColumnTree;
		}
		if (this.layout == LayoutType.VERTICAL) {
			return this.getVerticalTableHeaderTree();
		}
		return this.getHorizontalTableHeaderTree();
	}

	/**
	 * 获取布局列树
	 * 
	 * @return ColumnTree
	 */
	public ColumnTree getLayoutColumnTree() {
		if (this.layoutColumnTree != null) {
			return this.layoutColumnTree;
		}
		this.layoutColumnTree = this.buildColumnTreeByLevel(this.getLayoutColumns(), true);
		return this.layoutColumnTree;
	}

	/**
	 * 获取维度列树
	 * 
	 * @return {@link List<ColumnTreeNode>}
	 */
	public ColumnTree getDimColumnTree() {
		if (this.dimColumnTree != null) {
			return this.dimColumnTree;
		}

		int depth = this.getDimColumnCount();
		// 无维度列则直接设置树只有一个节点
		if (depth == 0) {
			List<ColumnTreeNode> roots = new ArrayList<ColumnTreeNode>();
			roots.add(new ColumnTreeNode("", "", ""));
			this.dimColumnTree = new ColumnTree(roots, 1);
			this.dimColumnTree.setLeafNodes(roots);
			return this.dimColumnTree;
		}

		this.dimColumnTree = this.buildColumnTreeByLevel(this.getDimColumns(),
				this.layout == LayoutType.HORIZONTAL);
		return this.dimColumnTree;
	}

	/**
	 * 获取报表统计列树
	 * 
	 * @return ColumnTree
	 */
	public ColumnTree getStatColumnTree() {
		if (this.statColumnTree != null) {
			return this.statColumnTree;
		}

		List<ColumnTreeNode> treeNodes = new ArrayList<ColumnTreeNode>();
		// 当统计列只有一列时,如果维度列大于0
		// 则表头列不显示出统计列，只显示布局列或维度列
		if (this.getDisplayStatColumns().size() == 1) {
			if (this.layout == LayoutType.HORIZONTAL || this.getDimColumnCount() > 0) {
				this.statColumnTree = new ColumnTree(treeNodes, 0);
				return this.statColumnTree;
			}
			if (this.statColumnLayout == LayoutType.HORIZONTAL) {
				// todo
			}
		}

		int depth = 1;
		List<ReportDataStatColumn> roots = this.getReportDataStatColumns();
		for (ReportDataStatColumn root : roots) {
			if (root.getReportDataColumn().getMetaData().isHidden()) {
				continue;
			}
			ColumnTreeNode treeNode = this.createColumnTreeNode(root.getReportDataColumn());
			for (ReportDataColumn child : root.getChildren()) {
				depth = 2;
				ColumnTreeNode childNode = this.createColumnTreeNode(child);
				if (root.getChildren().size() == 1) {
					childNode.setValue("");
				}
				treeNode.getChildren().add(childNode);
			}
			treeNodes.add(treeNode);
		}

		this.statColumnTree = new ColumnTree(treeNodes, depth);
		return this.statColumnTree;
	}

	/**
	 * 获取报表布局列
	 * 
	 * @return {@link List<ReportDataColumn}
	 */
	public List<ReportDataColumn> getLayoutColumns() {
		if (this.layoutColumns == null) {
			List<ReportMetaDataColumn> metaDataColumns = this.metaDataSet.getLayoutColumns();
			if (metaDataColumns.size() == 0) {
				throw new NotFoundLayoutColumnException();
			}

			this.layoutColumns = new ArrayList<ReportDataColumn>();
			for (ReportMetaDataColumn metaDataColumn : metaDataColumns) {
				this.layoutColumns.add(this.createColumn(metaDataColumn));
			}
		}
		return this.layoutColumns;
	}

	/**
	 * 获取报表所有维度列列表
	 * 
	 * @return {@link List<ReportDataColumn>}
	 */
	public List<ReportDataColumn> getDimColumns() {
		if (this.dimColumns != null) {
			return this.dimColumns;
		}

		this.dimColumns = new ArrayList<ReportDataColumn>();
		List<ReportMetaDataColumn> metaDataColumns = this.metaDataSet.getDimColumns();
		for (ReportMetaDataColumn metaDataColumn : metaDataColumns) {
			this.dimColumns.add(this.createColumn(metaDataColumn));
		}
		return this.dimColumns;
	}

	/**
	 * 获取报表所有显示的统计列列表
	 * 
	 * @return {@link List<ReportDataColumn>}
	 */
	public List<ReportDataColumn> getDisplayStatColumns() {
		if (this.displayStatColumns != null) {
			return this.displayStatColumns;
		}

		this.displayStatColumns = new ArrayList<ReportDataColumn>();
		for (ReportDataColumn statColumn : this.getStatColumns()) {
			if (!statColumn.getMetaData().isHidden()) {
				this.displayStatColumns.add(statColumn);
			}
		}
		return this.displayStatColumns;
	}

	/**
	 * 获取报表所有的计算列列表
	 * 
	 * @return {@link List<ReportDataColumn>}
	 */
	public List<ReportDataColumn> getComputedColumns() {
		if (this.computedColumns != null) {
			return this.computedColumns;
		}

		this.computedColumns = new ArrayList<ReportDataColumn>();
		for (ReportDataColumn statColumn : this.getStatColumns()) {
			if (statColumn.getMetaData().getType() == ColumnType.COMPUTED) {
				this.computedColumns.add(statColumn);
			}
		}
		return this.computedColumns;
	}

	/**
	 * 获取报表所有统计列(含计算列)列表
	 * 
	 * @return {@link List<ReportDataColumn>}
	 */
	public List<ReportDataColumn> getStatColumns() {
		if (this.statColumns != null) {
			return this.statColumns;
		}

		this.statColumns = new ArrayList<ReportDataColumn>();
		List<ReportDataStatColumn> roots = this.getReportDataStatColumns();
		for (ReportDataStatColumn root : roots) {
			if (root.getChildren().size() == 0) {
				this.statColumns.add(root.getReportDataColumn());
				root.getReportDataColumn().setOrdinal(this.statColumns.size());
				continue;
			}
			for (ReportDataColumn child : root.getChildren()) {
				child.setParentName(root.getReportDataColumn().getName());
				this.statColumns.add(child);
				child.setOrdinal(this.statColumns.size());
			}
		}
		return this.statColumns;
	}

	/**
	 * 获取报表所有非统计列列表
	 * 
	 * @return {@link List<ReportDataColumn>}
	 */
	public List<ReportDataColumn> getNonStatColumns() {
		if (this.nonStatColumns != null) {
			return this.nonStatColumns;
		}

		this.nonStatColumns = new ArrayList<ReportDataColumn>();
		nonStatColumns.addAll(this.getLayoutColumns());
		nonStatColumns.addAll(this.getDimColumns());
		return nonStatColumns;
	}

	/**
	 * 获取维度列总数
	 * 
	 * @return 维度列总数
	 */
	public int getDimColumnCount() {
		return this.getDimColumns().size();
	}

	/**
	 * 获取所有非统计列去重数据集合
	 * 
	 * @return {@link Map<String, List<String>>}
	 */
	public Map<String, List<String>> getNonStatColumnData() {
		Map<String, List<String>> nonStatColumnDataMap = new HashMap<String, List<String>>();
		List<ReportMetaDataRow> metaDataRows = this.metaDataSet.getRows();
		List<ReportDataColumn> nonStatColumns = this.getNonStatColumns();

		for (ReportDataColumn column : nonStatColumns) {
			Set<String> columnValueSet = new HashSet<String>();
			List<String> valueList = new ArrayList<String>();
			for (ReportMetaDataRow metaDataRow : metaDataRows) {
				String columnValue = this.getMetaCellValue(metaDataRow, column);
				if (!columnValueSet.contains(columnValue)) {
					columnValueSet.add(columnValue);
					valueList.add(columnValue);
				}
			}
			final ColumnSortType sortType = column.getMetaData().getSortType();
			Collections.sort(valueList, (o1, o2) -> {
				if (sortType == ColumnSortType.DIGIT_ASCENDING)
					return ComparatorUtils.CompareByDigitPriority(o1, o2);
				if (sortType == ColumnSortType.DIGIT_DESCENDING)
					return ComparatorUtils.CompareByDigitPriority(o2, o1);
				if (sortType == ColumnSortType.CHAR_ASCENDING)
					return o1.compareTo(o2);
				if (sortType == ColumnSortType.CHAR_DESCENDING)
					return o2.compareTo(o1);
				return 0;
			});
			nonStatColumnDataMap.put(column.getName(), valueList);
		}
		return nonStatColumnDataMap;
	}

	/**
	 * 获取报表数据行Map,其中key由于布局列与维度列的值组成,value为统计列与计算列值的集合
	 * 
	 * @return Map<String, ReportDataRow>
	 */
	public Map<String, ReportDataRow> getDataRowMap() {
		Map<String, ReportDataRow> dataRowMap = new HashMap<String, ReportDataRow>();
		List<ReportDataColumn> statColumns = this.getStatColumns();
		List<ReportDataColumn> computedColumns = this.getComputedColumns();
		List<ReportDataColumn> nonStatColumns = this.getNonStatColumns();
		List<ReportMetaDataRow> metaDataRows = this.metaDataSet.getRows();

		for (ReportMetaDataRow metaDataRow : metaDataRows) {
			String key = this.getDataRowMapKey(metaDataRow, nonStatColumns);
			ReportDataRow dataRow = new ReportDataRow();
			Map<String, Object> exprContext = new HashMap<String, Object>();
			for (ReportDataColumn statColumn : statColumns) {
				Object value = this.getStatCellValue(metaDataRow, statColumn);
				dataRow.add(new ReportDataCell(statColumn, statColumn.getName(), value));
				exprContext.put("c" + statColumn.getOrdinal(), value);
				exprContext.put(statColumn.getName(), value);
			}
			for (ReportDataColumn column : computedColumns) {
				Object value = AviatorExprUtils.execute(column.getMetaData().getExpression(), exprContext);
				dataRow.getCell(column.getName()).setValue(value);
				exprContext.put("c" + column.getOrdinal(), value);
				exprContext.put(column.getName(), value);
			}
			dataRowMap.put(key, dataRow);
		}

		return dataRowMap;
	}

	/**
	 * 按列的先后顺序做为树的层次，构建出一棵层次树，其中层次数为key(根层次为0),当前层次列的值(即对应的行列Cell中的值)为树节点集合
	 * 
	 * @param columns 列集合
	 * @param isInitSpansAndDepth 是否初始树的spans与深度属性
	 * @return ColumnTree
	 */
	private ColumnTree buildColumnTreeByLevel(List<ReportDataColumn> columns, boolean isInitSpansAndDepth) {
		Map<Integer, List<ColumnTreeNode>> levelNodeMap = new HashMap<Integer, List<ColumnTreeNode>>();
		int depth = columns.size();

		for (int level = 0; level < depth; level++) {
			levelNodeMap.put(level, this.getTreeNodesByLevel(columns, level));
		}

		List<ColumnTreeNode> leafNodes = this.getAllLeafNodes(levelNodeMap, depth);
		if (isInitSpansAndDepth) {
			this.setTreeNodeSpansAndDepth(levelNodeMap.get(0), columns);
		}

		ColumnTree tree = new ColumnTree(levelNodeMap.get(0), depth);
		tree.setLeafNodes(leafNodes);
		return tree;
	}

	/**
	 * 按层次遍历树并设置节点的父子关系, 同时返回树节点的所有叶子节点
	 * 
	 * @param levelNodeMap 层次树HashMap集合
	 * @param depth 树的层次深度（根层次为0)
	 * @return {@link List<ColumnTreeNode>}
	 */
	private List<ColumnTreeNode> getAllLeafNodes(Map<Integer, List<ColumnTreeNode>> levelNodeMap, int depth) {
		List<ColumnTreeNode> leafNodes = (depth > 1) ? new ArrayList<ColumnTreeNode>() : levelNodeMap.get(0);
		for (int level = 0; level < depth - 1; level++) {
			List<ColumnTreeNode> parentNodes = levelNodeMap.get(level);
			for (ColumnTreeNode parentNode : parentNodes) {
				List<ColumnTreeNode> subNodes = levelNodeMap.get(level + 1);
				for (ColumnTreeNode subNode : subNodes) {
					if (subNode.getParent().getPath().equals(parentNode.getPath())) {
						parentNode.getChildren().add(subNode);
						if (level == (depth - 2))
							leafNodes.add(subNode);
					}
				}
			}
		}
		return leafNodes;
	}

	private List<ReportDataStatColumn> getReportDataStatColumns() {
		int depth = 1;
		List<ReportDataStatColumn> roots = new ArrayList<ReportDataStatColumn>();
		List<ReportMetaDataColumn> metaDataStatColumns = this.metaDataSet.getStatColumns();

		for (ReportMetaDataColumn metaDataStatColumn : metaDataStatColumns) {
			ReportDataStatColumn statColumn = new ReportDataStatColumn(metaDataStatColumn);
			List<ReportDataColumn> children = this.getChildStatColumns(metaDataStatColumn);
			if (children != null) {
				depth = 2;
				statColumn.getChildren().addAll(children);
			}
			roots.add(statColumn);
		}
		if (depth == 2) {
			for (ReportDataStatColumn root : roots) {
				if (root.getChildren().size() == 0)
					root.getChildren().add(root.getReportDataColumn());
			}
		}

		return roots;
	}

	private List<ReportDataColumn> getChildStatColumns(ReportMetaDataColumn metaDataColumn) {
		List<ReportDataColumn> children = null;
		return children;
	}

	private String getDataRowMapKey(ReportMetaDataRow metaDataRow, List<ReportDataColumn> nonStatColumns) {
		StringBuilder rowMapKeyBuilder = new StringBuilder("");
		for (ReportDataColumn nonStatColumn : nonStatColumns) {
			String value = this.getMetaCellValue(metaDataRow, nonStatColumn);
			value = StringUtils.replace(value, this.separatorChar, "*");
			rowMapKeyBuilder.append(value + this.separatorChar);
		}
		return rowMapKeyBuilder.toString();
	}

	private List<ColumnTreeNode> getTreeNodesByLevel(List<ReportDataColumn> columns, int level) {
		List<ReportMetaDataRow> metaDataRows = this.metaDataSet.getRows();
		Set<String> columnValueSet = new HashSet<String>();
		List<ColumnTreeNode> depthTreeNodes = new ArrayList<ColumnTreeNode>();

		for (ReportMetaDataRow metaDataRow : metaDataRows) {
			String path = this.getLevelPath(metaDataRow, columns, level);
			if (!columnValueSet.contains(path)) {
				columnValueSet.add(path);
				depthTreeNodes.add(this.createColumnTreeNode(metaDataRow, columns, level, path));
			}
		}
		return depthTreeNodes;
	}

	private ReportDataColumn createColumn(ReportMetaDataColumn metaDataColumn) {
		return new ReportDataColumn(metaDataColumn);
	}

	private ColumnTreeNode createColumnTreeNode(ReportDataColumn column) {
		return this.createColumnTreeNode(column.getName(), column.getText(), column);
	}

	private ColumnTreeNode createColumnTreeNode(String name, String text, ReportDataColumn column) {
		ColumnTreeNode treeNode = new ColumnTreeNode(name, text, text);
		treeNode.setColumn(column);
		return treeNode;
	}

	private ColumnTreeNode createColumnTreeNode(ReportMetaDataRow metaDataRow, List<ReportDataColumn> columns,
			int depth, String path) {
		ColumnTreeNode parentNode = null;
		if (depth > 0) {
			ReportDataColumn parentColumn = columns.get(depth - 1);
			String parentValue = this.getMetaCellValue(metaDataRow, parentColumn);
			String parentPath = this.getLevelPath(metaDataRow, columns, depth - 1);
			parentNode = new ColumnTreeNode(parentColumn.getName(), parentColumn.getText(), parentValue, null);
			parentNode.setPath(parentPath);
			parentNode.setColumn(parentColumn);
		}

		ReportDataColumn column = columns.get(depth);
		String value = this.getMetaCellValue(metaDataRow, column);
		ColumnTreeNode treeNode = new ColumnTreeNode(column.getName(), column.getText(), value, parentNode);
		treeNode.setDepth(depth);
		treeNode.setPath(path);
		treeNode.setColumn(column);

		return treeNode;
	}

	private String getLevelPath(ReportMetaDataRow metaDataRow, List<ReportDataColumn> columns, int level) {
		String path = "";
		for (int i = 0; i <= level; i++) {
			ReportDataColumn column = columns.get(i);
			String columnValue = this.getMetaCellValue(metaDataRow, column);
			path += StringUtils.replace(columnValue, this.separatorChar, "*") + this.separatorChar;
		}
		return path;
	}

	private Object getStatCellValue(ReportMetaDataRow metaDataRow, ReportDataColumn statColumn) {
		String columnName = statColumn.getName();
		if (columnName.equals("dayhuan"))
			return 0.10;
		if (columnName.equals("weektong"))
			return 0.20;
		if (columnName.equals("monthtong"))
			return 0.30;
		return metaDataRow.getCellValue(statColumn.getName());
	}

	private String getMetaCellValue(ReportMetaDataRow metaDataRow, ReportDataColumn column) {
		Object cellValue = metaDataRow.getCellValue(column.getName());
		return (cellValue == null) ? "" : cellValue.toString().trim();
	}

	private void setTreeNodeSpansAndDepth(List<ColumnTreeNode> roots, List<ReportDataColumn> columns) {
		this.setTreeNodeSpansAndDepth(roots, true, columns);
	}

	private void setTreeNodeSpansAndDepth(List<ColumnTreeNode> roots, boolean isSort, List<ReportDataColumn> columns) {
		if (isSort) {
			this.sortTreeNodesByLevel(roots, 0, columns);
		}

		for (ColumnTreeNode root : roots) {
			root.setDepth(0);
			root.setSpans(this.setSpansAndDepthByRecursion(root, 0, isSort, columns));
		}
	}

	private int setSpansAndDepthByRecursion(ColumnTreeNode parentNode, int depth, boolean isSort,
			List<ReportDataColumn> columns) {
		if (parentNode.getChildren().size() == 0)
			return 1;

		if (isSort) {
			this.sortTreeNodesByLevel(parentNode.getChildren(), depth + 1, columns);
		}

		int spans = 0;
		for (ColumnTreeNode treeNode : parentNode.getChildren()) {
			treeNode.setDepth(depth + 1);
			treeNode.setSpans(this.setSpansAndDepthByRecursion(treeNode, depth + 1, isSort, columns));
			spans += treeNode.getSpans();
		}
		return spans;
	}

	private ColumnTree getHorizontalTableHeaderTree() {
		ColumnTree statColumnTree = this.getStatColumnTree();

		ColumnTree layoutColumnTree = this.getLayoutColumnTree();
		for (ColumnTreeNode leafNode : layoutColumnTree.getLeafNodes()) {
			leafNode.getChildren().addAll(statColumnTree.getRoots());
		}

		this.setTreeNodeSpansAndDepth(layoutColumnTree.getRoots(), this.getLayoutColumns());
		int depth = layoutColumnTree.getDepth() + statColumnTree.getDepth();
		this.headerColumnTree = new ColumnTree(layoutColumnTree.getRoots(), depth);
		return this.headerColumnTree;
	}

	private ColumnTree getVerticalTableHeaderTree() {
		ColumnTree statColumnTree = this.getStatColumnTree();

		// 无维度列则表头列直接设置为统计列
		if (this.getDimColumnCount() == 0) {
			this.setTreeNodeSpansAndDepth(statColumnTree.getRoots(), false, this.getDisplayStatColumns());
			this.headerColumnTree = new ColumnTree(statColumnTree.getRoots(), statColumnTree.getDepth());
			this.headerColumnTree.setLeafNodes(statColumnTree.getRoots());
			return this.headerColumnTree;
		}

		ColumnTree dimColumnTree = this.getDimColumnTree();
		for (ColumnTreeNode leafNode : dimColumnTree.getLeafNodes()) {
			leafNode.getChildren().addAll(statColumnTree.getRoots());
		}

		this.setTreeNodeSpansAndDepth(dimColumnTree.getRoots(), this.getDimColumns());
		int depth = dimColumnTree.getDepth() + statColumnTree.getDepth();
		this.headerColumnTree = new ColumnTree(dimColumnTree.getRoots(), depth);
		return this.headerColumnTree;
	}

	private void sortTreeNodesByLevel(List<ColumnTreeNode> treeNodes, int level, List<ReportDataColumn> columns) {
		if (level >= columns.size()) {
			return;
		}

		ReportDataColumn column = columns.get(level);
		ColumnType columnType = column.getMetaData().getType();
		final ColumnSortType sortType = column.getMetaData().getSortType();
		if (sortType == ColumnSortType.DEFAULT || columnType == ColumnType.STATISTICAL
				|| columnType == ColumnType.COMPUTED) {
			return;
		}

		Collections.sort(treeNodes, (o1, o2) -> {
			if (sortType == ColumnSortType.DIGIT_ASCENDING)
				return ComparatorUtils.CompareByDigitPriority(o1.getValue(), o2.getValue());
			if (sortType == ColumnSortType.DIGIT_DESCENDING)
				return ComparatorUtils.CompareByDigitPriority(o2.getValue(), o1.getValue());
			if (sortType == ColumnSortType.CHAR_ASCENDING)
				return o1.getValue().compareTo(o2.getValue());
			if (sortType == ColumnSortType.CHAR_DESCENDING)
				return o2.getValue().compareTo(o1.getValue());
			return 0;
		});
	}
}
