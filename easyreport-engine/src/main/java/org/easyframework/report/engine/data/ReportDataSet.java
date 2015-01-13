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
 * 报表数据集类,包含生成报表所需的数据集，配置及元数据。
 */
public abstract class ReportDataSet {
	protected final String separatorChar = "$";
	protected final ReportMetaDataSet metaDataSet;
	protected final LayoutType layout;
	protected final LayoutType statColumnLayout;
	protected List<ReportDataColumn> layoutColumns;
	protected List<ReportDataColumn> dimColumns;
	protected List<ReportDataColumn> enabledStatColumns;
	protected List<ReportDataColumn> computedColumns;
	protected List<ReportDataColumn> statColumns;
	protected List<ReportDataColumn> nonStatColumns;
	protected ColumnTree headerColumnTree;
	protected ColumnTree layoutColumnTree;
	protected ColumnTree dimColumnTree;
	protected ColumnTree statColumnTree;

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
	 * 获取报表数据集(RowMap)每一行的key
	 * 
	 * @param rowNode
	 *            行结点
	 * @param columnNode
	 *            列结点
	 * @return 行key
	 */
	public abstract String getRowKey(ColumnTreeNode rowNode, ColumnTreeNode columnNode);

	/**
	 * 获取报表表头(header)左边固定列集合
	 * 
	 * @return List<ReportDataColumn>
	 */
	public abstract List<ReportDataColumn> getHeaderLeftFixedColumns();

	/**
	 * 获取表头(header)右边列树型结构
	 * 
	 * @return ColumnTree
	 */
	public abstract ColumnTree getHeaderRightColumnTree();

	/**
	 * 获取报表表体(body)左边固定列树型结构
	 * 
	 * @return ColumnTree
	 */
	public abstract ColumnTree getBodyLeftFixedColumnTree();

	/**
	 * 获取报表表体(body)右边列集合
	 * 
	 * @return List<ColumnTreeNode>
	 */
	public abstract List<ColumnTreeNode> getBodyRightColumnNodes();

	/**
	 * 获取布局列树
	 * 
	 * @return ColumnTree
	 */
	public abstract ColumnTree getLayoutColumnTree();

	/**
	 * 获取维度列树
	 * 
	 * @return ColumnTree {@link ColumnTree}
	 */
	public abstract ColumnTree getDimColumnTree();

	/**
	 * 获取报表统计列树
	 * 
	 * @return ColumnTree
	 */
	public abstract ColumnTree getStatColumnTree();

	/**
	 * 获取报表所有维度列列表
	 * 
	 * @return {@link List<ReportDataColumn>}
	 */
	public abstract List<ReportDataColumn> getDimColumns();

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
	 * 获取报表所有启用的统计列列表
	 * 
	 * @return {@link List<ReportDataColumn>}
	 */
	public List<ReportDataColumn> getEnabledStatColumns() {
		if (this.enabledStatColumns != null) {
			return this.enabledStatColumns;
		}

		this.enabledStatColumns = new ArrayList<ReportDataColumn>();
		for (ReportDataColumn statColumn : this.getStatColumns()) {
			if (!statColumn.getMetaData().isHidden()) {
				this.enabledStatColumns.add(statColumn);
			}
		}
		return this.enabledStatColumns;
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
	public Map<String, ReportDataRow> getRowMap() {
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
	 * @param columns
	 *            列集合
	 * @param isInitSpansAndDepth
	 *            是否初始树的spans与深度属性
	 * @return ColumnTree
	 */
	protected ColumnTree buildColumnTreeByLevel(List<ReportDataColumn> columns, boolean isInitSpansAndDepth) {
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
	 * @param levelNodeMap
	 *            层次树HashMap集合
	 * @param depth
	 *            树的层次深度（根层次为0)
	 * @return {@link List<ColumnTreeNode>}
	 */
	protected List<ColumnTreeNode> getAllLeafNodes(Map<Integer, List<ColumnTreeNode>> levelNodeMap, int depth) {
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

	protected List<ReportDataStatColumn> getReportDataStatColumns() {
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

	protected List<ReportDataColumn> getChildStatColumns(ReportMetaDataColumn metaDataColumn) {
		List<ReportDataColumn> children = null;
		return children;
	}

	protected String getDataRowMapKey(ReportMetaDataRow metaDataRow, List<ReportDataColumn> nonStatColumns) {
		StringBuilder rowMapKeyBuilder = new StringBuilder("");
		for (ReportDataColumn nonStatColumn : nonStatColumns) {
			String value = this.getMetaCellValue(metaDataRow, nonStatColumn);
			value = StringUtils.replace(value, this.separatorChar, "*");
			rowMapKeyBuilder.append(value + this.separatorChar);
		}
		return rowMapKeyBuilder.toString();
	}

	protected List<ColumnTreeNode> getTreeNodesByLevel(List<ReportDataColumn> columns, int level) {
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

	protected ReportDataColumn createColumn(ReportMetaDataColumn metaDataColumn) {
		return new ReportDataColumn(metaDataColumn);
	}

	protected ColumnTreeNode createColumnTreeNode(ReportDataColumn column) {
		return this.createColumnTreeNode(column.getName(), column.getText(), column);
	}

	protected ColumnTreeNode createColumnTreeNode(String name, String text, ReportDataColumn column) {
		ColumnTreeNode treeNode = new ColumnTreeNode(name, text, text);
		treeNode.setColumn(column);
		return treeNode;
	}

	protected ColumnTreeNode createColumnTreeNode(ReportMetaDataRow metaDataRow, List<ReportDataColumn> columns,
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

	protected String getLevelPath(ReportMetaDataRow metaDataRow, List<ReportDataColumn> columns, int level) {
		String path = "";
		for (int i = 0; i <= level; i++) {
			ReportDataColumn column = columns.get(i);
			String columnValue = this.getMetaCellValue(metaDataRow, column);
			path += StringUtils.replace(columnValue, this.separatorChar, "*") + this.separatorChar;
		}
		return path;
	}

	protected Object getStatCellValue(ReportMetaDataRow metaDataRow, ReportDataColumn statColumn) {
		String columnName = statColumn.getName();
		if (columnName.equals("dayhuan"))
			return 0.10;
		if (columnName.equals("weektong"))
			return 0.20;
		if (columnName.equals("monthtong"))
			return 0.30;
		return metaDataRow.getCellValue(statColumn.getName());
	}

	protected String getMetaCellValue(ReportMetaDataRow metaDataRow, ReportDataColumn column) {
		Object cellValue = metaDataRow.getCellValue(column.getName());
		return (cellValue == null) ? "" : cellValue.toString().trim();
	}

	protected void setTreeNodeSpansAndDepth(List<ColumnTreeNode> roots, List<ReportDataColumn> columns) {
		this.setTreeNodeSpansAndDepth(roots, true, columns);
	}

	protected void setTreeNodeSpansAndDepth(List<ColumnTreeNode> roots, boolean isSort, List<ReportDataColumn> columns) {
		if (isSort) {
			this.sortTreeNodesByLevel(roots, 0, columns);
		}

		for (ColumnTreeNode root : roots) {
			root.setDepth(0);
			root.setSpans(this.setSpansAndDepthByRecursion(root, 0, isSort, columns));
		}
	}

	protected int setSpansAndDepthByRecursion(ColumnTreeNode parentNode, int depth, boolean isSort,
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

	protected void sortTreeNodesByLevel(List<ColumnTreeNode> treeNodes, int level, List<ReportDataColumn> columns) {
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
