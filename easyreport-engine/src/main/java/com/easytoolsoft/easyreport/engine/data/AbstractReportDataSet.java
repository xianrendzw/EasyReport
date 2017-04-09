package com.easytoolsoft.easyreport.engine.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.easytoolsoft.easyreport.engine.exception.NotFoundLayoutColumnException;
import com.easytoolsoft.easyreport.engine.util.AviatorExprUtils;
import com.easytoolsoft.easyreport.engine.util.ComparatorUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 抽象报表数据集类,包含生成报表所需的数据集，配置及元数据。
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public abstract class AbstractReportDataSet implements ReportDataSet {
    protected final static String PATH_SEPARATOR = "$";
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
    protected ColumnTree leftFixedColumnTree;
    protected ColumnTree layoutColumnTree;
    protected ColumnTree dimColumnTree;
    protected ColumnTree statColumnTree;

    /**
     * @param metaDataSet
     * @param layout
     * @param statColumnLayout
     */
    public AbstractReportDataSet(ReportMetaDataSet metaDataSet, LayoutType layout, LayoutType statColumnLayout) {
        this.metaDataSet = metaDataSet;
        this.layout = layout;
        this.statColumnLayout = statColumnLayout;
    }

    @Override
    public String getPathSeparator() {
        return PATH_SEPARATOR;
    }

    @Override
    public ReportMetaDataSet getMetaData() {
        return this.metaDataSet;
    }

    @Override
    public ColumnTree getLayoutColumnTree() {
        if (this.layoutColumnTree != null) {
            return this.layoutColumnTree;
        }
        this.layoutColumnTree = this.buildColumnTreeByLevel(this.getLayoutColumns(), true);
        return this.layoutColumnTree;
    }

    @Override
    public ColumnTree getDimColumnTree() {
        if (this.dimColumnTree != null) {
            return this.dimColumnTree;
        }

        int depth = this.getDimColumnCount();
        // 无维度列则直接设置树只有一个节点
        if (depth == 0) {
            List<ColumnTreeNode> roots = new ArrayList<>();
            ReportDataColumn column = new ReportDataColumn(
                new ReportMetaDataColumn("stat_value", "值", ColumnType.DIMENSION));
            roots.add(new ColumnTreeNode(column));
            this.dimColumnTree = new ColumnTree(roots, 1);
            this.dimColumnTree.setLeafNodes(roots);
            return this.dimColumnTree;
        }

        this.dimColumnTree = this.buildColumnTreeByLevel(this.getDimColumns(), true);
        return this.dimColumnTree;
    }

    @Override
    public ColumnTree getStatColumnTree() {
        if (this.statColumnTree != null) {
            return this.statColumnTree;
        }

        List<ColumnTreeNode> treeNodes = new ArrayList<>();
        if (this.isHideStatColumn()) {
            this.statColumnTree = new ColumnTree(treeNodes, 0);
            return this.statColumnTree;
        }

        List<ReportDataColumn> statColumns = this.getStatColumns();
        treeNodes.addAll(statColumns.stream().filter(
            statColumn -> !statColumn.getMetaData().isHidden())
            .map(this::createStatColumnTreeNode)
            .collect(Collectors.toList()));
        this.statColumnTree = new ColumnTree(treeNodes, 1);
        return this.statColumnTree;
    }

    @Override
    public List<ReportDataColumn> getLayoutColumns() {
        if (this.layoutColumns == null) {
            List<ReportMetaDataColumn> metaDataColumns = this.metaDataSet.getLayoutColumns();
            if (metaDataColumns.size() == 0) {
                throw new NotFoundLayoutColumnException();
            }

            this.layoutColumns = new ArrayList<>();
            this.layoutColumns.addAll(metaDataColumns.stream()
                .map(ReportDataColumn::new)
                .collect(Collectors.toList()));
        }
        return this.layoutColumns;
    }

    @Override
    public List<ReportDataColumn> getDimColumns() {
        if (this.dimColumns != null) {
            return this.dimColumns;
        }

        this.dimColumns = new ArrayList<>();
        List<ReportMetaDataColumn> metaDataColumns = this.metaDataSet.getDimColumns();
        this.dimColumns.addAll(metaDataColumns.stream()
            .map(ReportDataColumn::new)
            .collect(Collectors.toList()));
        return this.dimColumns;
    }

    @Override
    public List<ReportDataColumn> getEnabledStatColumns() {
        if (this.enabledStatColumns != null) {
            return this.enabledStatColumns;
        }

        this.enabledStatColumns = new ArrayList<>();
        this.enabledStatColumns.addAll(this.getStatColumns().stream()
            .filter(statColumn -> !statColumn.getMetaData().isHidden()).collect(Collectors.toList()));
        return this.enabledStatColumns;
    }

    @Override
    public List<ReportDataColumn> getComputedColumns() {
        if (this.computedColumns != null) {
            return this.computedColumns;
        }

        this.computedColumns = new ArrayList<>();
        this.computedColumns.addAll(this.getStatColumns().stream()
            .filter(statColumn -> statColumn.getMetaData().getType() == ColumnType.COMPUTED)
            .collect(Collectors.toList()));
        return this.computedColumns;
    }

    @Override
    public List<ReportDataColumn> getStatColumns() {
        if (this.statColumns != null) {
            return this.statColumns;
        }

        this.statColumns = new ArrayList<>();
        List<ReportMetaDataColumn> metaDataColumns = this.metaDataSet.getStatColumns();
        this.statColumns.addAll(metaDataColumns.stream()
            .map(ReportDataColumn::new)
            .collect(Collectors.toList()));
        return this.statColumns;
    }

    @Override
    public List<ReportDataColumn> getNonStatColumns() {
        if (this.nonStatColumns != null) {
            return this.nonStatColumns;
        }

        this.nonStatColumns = new ArrayList<>();
        nonStatColumns.addAll(this.getLayoutColumns());
        nonStatColumns.addAll(this.getDimColumns());
        return nonStatColumns;
    }

    @Override
    public int getDimColumnCount() {
        return this.getDimColumns().size();
    }

    @Override
    public Map<String, List<String>> getUnduplicatedNonStatColumnDataMap() {
        Map<String, List<String>> nonStatColumnDataMap = new HashMap<>();
        List<ReportMetaDataRow> metaDataRows = this.metaDataSet.getRows();
        List<ReportDataColumn> nonStatColumns = this.getNonStatColumns();

        for (ReportDataColumn column : nonStatColumns) {
            Set<String> columnValueSet = new HashSet<>();
            List<String> valueList = new ArrayList<>();
            for (ReportMetaDataRow metaDataRow : metaDataRows) {
                String value = this.getMetaCellValue(metaDataRow, column);
                if (!columnValueSet.contains(value)) {
                    columnValueSet.add(value);
                    valueList.add(value);
                }
            }
            final ColumnSortType sortType = column.getMetaData().getSortType();
            Collections.sort(valueList, (o1, o2) -> {
                if (sortType == ColumnSortType.DIGIT_ASCENDING) {
                    return ComparatorUtils.compareByDigitPriority(o1, o2);
                }
                if (sortType == ColumnSortType.DIGIT_DESCENDING) {
                    return ComparatorUtils.compareByDigitPriority(o2, o1);
                }
                if (sortType == ColumnSortType.CHAR_ASCENDING) {
                    return o1.compareTo(o2);
                }
                if (sortType == ColumnSortType.CHAR_DESCENDING) {
                    return o2.compareTo(o1);
                }
                return 0;
            });
            nonStatColumnDataMap.put(column.getName(), valueList);
        }
        return nonStatColumnDataMap;
    }

    @Override
    public Map<String, ReportDataRow> getRowMap() {
        Map<String, ReportDataRow> dataRowMap = new HashMap<>();
        List<ReportDataColumn> statColumns = this.getStatColumns();
        List<ReportDataColumn> computedColumns = this.getComputedColumns();
        List<ReportDataColumn> nonStatColumns = this.getNonStatColumns();
        List<ReportMetaDataRow> metaDataRows = this.metaDataSet.getRows();

        for (ReportMetaDataRow metaDataRow : metaDataRows) {
            String key = this.getDataRowMapKey(metaDataRow, nonStatColumns);
            ReportDataRow dataRow = new ReportDataRow();
            Map<String, Object> exprContext = new HashMap<>();
            for (ReportDataColumn statColumn : statColumns) {
                Object value = metaDataRow.getCellValue(statColumn.getName());
                dataRow.add(new ReportDataCell(statColumn, statColumn.getName(), value));
                exprContext.put("c" + statColumn.getMetaData().getOrdinal(), value);
                exprContext.put(statColumn.getName(), value);
            }
            for (ReportDataColumn column : computedColumns) {
                Object value = AviatorExprUtils.execute(column.getMetaData().getExpression(), exprContext);
                dataRow.getCell(column.getName()).setValue(value);
                exprContext.put("c" + column.getMetaData().getOrdinal(), value);
                exprContext.put(column.getName(), value);
            }
            dataRowMap.put(key, dataRow);
        }

        return dataRowMap;
    }

    /**
     * 按列的先后顺序做为树的层次，构建出一棵层次树，
     * 其中层次数为key(根层次为0),当前层次列的值(即对应的行列Cell中的值)为树节点集合
     *
     * @param columns             列集合
     * @param isInitSpansAndDepth 是否初始树的spans与深度属性
     * @return ColumnTree
     */
    protected ColumnTree buildColumnTreeByLevel(List<ReportDataColumn> columns, boolean isInitSpansAndDepth) {
        Map<Integer, List<ColumnTreeNode>> levelNodeMap = new HashMap<>();

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
     * @param depth        树的层次深度（根层次为0)
     * @return {@link List<ColumnTreeNode>}
     */
    protected List<ColumnTreeNode> getAllLeafNodes(Map<Integer, List<ColumnTreeNode>> levelNodeMap, int depth) {
        List<ColumnTreeNode> leafNodes = (depth > 1) ? new ArrayList<>() : levelNodeMap.get(0);
        for (int level = 0; level < depth - 1; level++) {
            List<ColumnTreeNode> parentNodes = levelNodeMap.get(level);
            for (ColumnTreeNode parentNode : parentNodes) {
                List<ColumnTreeNode> subNodes = levelNodeMap.get(level + 1);
                for (ColumnTreeNode subNode : subNodes) {
                    if (subNode.getParent().getPath().equals(parentNode.getPath())) {
                        parentNode.getChildren().add(subNode);
                        if (level == (depth - 2)) {
                            leafNodes.add(subNode);
                        }
                    }
                }
            }
        }
        return leafNodes;
    }

    protected String getDataRowMapKey(ReportMetaDataRow metaDataRow, List<ReportDataColumn> nonStatColumns) {
        StringBuilder rowMapKeyBuilder = new StringBuilder("");
        for (ReportDataColumn nonStatColumn : nonStatColumns) {
            String value = this.getMetaCellValue(metaDataRow, nonStatColumn);
            rowMapKeyBuilder.append(StringUtils.replace(value, PATH_SEPARATOR, "*"));
            rowMapKeyBuilder.append(PATH_SEPARATOR);
        }
        return rowMapKeyBuilder.toString();
    }

    protected List<ColumnTreeNode> getTreeNodesByLevel(List<ReportDataColumn> columns, int level) {
        List<ReportMetaDataRow> metaDataRows = this.metaDataSet.getRows();
        Set<String> pathSet = new HashSet<>();
        List<ColumnTreeNode> depthTreeNodes = new ArrayList<>();

        for (ReportMetaDataRow metaDataRow : metaDataRows) {
            String path = this.getLevelPath(metaDataRow, columns, level);
            if (!pathSet.contains(path)) {
                pathSet.add(path);
                depthTreeNodes.add(this.createColumnTreeNode(metaDataRow, columns, level, path));
            }
        }
        return depthTreeNodes;
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
        StringBuilder pathBuilder = new StringBuilder();
        for (int i = 0; i <= level; i++) {
            ReportDataColumn column = columns.get(i);
            String value = this.getMetaCellValue(metaDataRow, column);
            pathBuilder.append(StringUtils.replace(value, PATH_SEPARATOR, "*"));
            pathBuilder.append(PATH_SEPARATOR);
        }
        return pathBuilder.toString();
    }

    protected String getMetaCellValue(ReportMetaDataRow metaDataRow, ReportDataColumn column) {
        Object cellValue = metaDataRow.getCellValue(column.getName());
        return (cellValue == null) ? "" : cellValue.toString().trim();
    }

    protected void setTreeNodeSpansAndDepth(List<ColumnTreeNode> roots, List<ReportDataColumn> columns) {
        this.setTreeNodeSpansAndDepth(roots, true, columns);
    }

    protected void setTreeNodeSpansAndDepth(List<ColumnTreeNode> roots, boolean isSort,
                                            List<ReportDataColumn> columns) {
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
        if (parentNode.getChildren().size() == 0) {
            return 1;
        }

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
        if (sortType == ColumnSortType.DEFAULT ||
            columnType == ColumnType.STATISTICAL ||
            columnType == ColumnType.COMPUTED) {
            return;
        }

        Collections.sort(treeNodes, (o1, o2) -> {
            if (sortType == ColumnSortType.DIGIT_ASCENDING) {
                return ComparatorUtils.compareByDigitPriority(o1.getValue(), o2.getValue());
            }
            if (sortType == ColumnSortType.DIGIT_DESCENDING) {
                return ComparatorUtils.compareByDigitPriority(o2.getValue(), o1.getValue());
            }
            if (sortType == ColumnSortType.CHAR_ASCENDING) {
                return o1.getValue().compareTo(o2.getValue());
            }
            if (sortType == ColumnSortType.CHAR_DESCENDING) {
                return o2.getValue().compareTo(o1.getValue());
            }
            return 0;
        });
    }

    protected ColumnTreeNode createStatColumnTreeNode(ReportDataColumn column) {
        return this.createStatColumnTreeNode(column.getName(), column.getText(), column);
    }

    protected ColumnTreeNode createStatColumnTreeNode(String name, String text, ReportDataColumn column) {
        ColumnTreeNode treeNode = new ColumnTreeNode(name, text, text);
        treeNode.setColumn(column);
        treeNode.setPath(text + PATH_SEPARATOR);
        return treeNode;
    }
}
