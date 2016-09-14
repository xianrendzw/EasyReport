package com.easytoolsoft.easyreport.engine.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 纵向展示报表统计列的报表数据集类。
 */
public class VerticalStatColumnDataSet extends ReportDataSet {
    /**
     * @param metaDataSet
     * @param layout
     * @param statColumnLayout
     */
    public VerticalStatColumnDataSet(ReportMetaDataSet metaDataSet, LayoutType layout, LayoutType statColumnLayout) {
        super(metaDataSet, layout, statColumnLayout);
    }

    @Override
    public String getRowKey(ColumnTreeNode rowNode, ColumnTreeNode columnNode) {
        String rowNodePath;
        if (this.isHideStatColumn()) {
            rowNodePath = rowNode.getPath();
        } else if (rowNode.getParent() == null) {
            rowNodePath = "";
        } else {
            rowNodePath = rowNode.getParent().getPath();
        }
        return layout == LayoutType.HORIZONTAL ? (columnNode.getPath() + rowNodePath) : (rowNodePath + columnNode.getPath());
    }

    @Override
    public List<ReportDataColumn> getHeaderLeftFixedColumns() {
        List<ReportDataColumn> columns = (layout == LayoutType.HORIZONTAL) ? this.getDimColumns() : this.getLayoutColumns();
        if (this.isHideStatColumn()) {
            return columns;
        }
        List<ReportDataColumn> leftFixedColumns = new ArrayList<>(columns.size() + 1);
        leftFixedColumns.addAll(columns);
        leftFixedColumns.add(new ReportDataColumn(new ReportMetaDataColumn("stat_column", "统计列", ColumnType.STATISTICAL)));
        return leftFixedColumns;
    }

    @Override
    public ColumnTree getHeaderRightColumnTree() {
        if (this.layout == LayoutType.HORIZONTAL) {
            return this.getLayoutColumnTree();
        }
        return this.getDimColumnTree();
    }

    @Override
    public ColumnTree getBodyLeftFixedColumnTree() {
        return layout == LayoutType.HORIZONTAL ?
                this.getHorizontalLayoutLeftFixedColumnTree() : this.getVerticalLayoutLeftFixedColumnTree();
    }

    @Override
    public List<ColumnTreeNode> getBodyRightColumnNodes() {
        return layout == LayoutType.HORIZONTAL ?
                this.getLayoutColumnTree().getLastLevelNodes() : this.getDimColumnTree().getLastLevelNodes();
    }

    @Override
    public boolean isHideStatColumn() {
        // 如果布局列纵向显示
        // 或者布局列模向显示（即显示在表头右边)且有维度列（即表体左边)
        return this.getEnabledStatColumns().size() == 1 &&
                (this.layout == LayoutType.VERTICAL || this.getDimColumnCount() > 0);
    }

    private ColumnTree getHorizontalLayoutLeftFixedColumnTree() {
        ColumnTree statColumnTree = this.getStatColumnTree();
        // 无维度列则表体左边固定列直接设置为统计列
        if (this.getDimColumnCount() == 0) {
            this.setTreeNodeSpansAndDepth(statColumnTree.getRoots(), false, this.getEnabledStatColumns());
            this.leftFixedColumnTree = new ColumnTree(statColumnTree.getRoots(), statColumnTree.getDepth());
            this.leftFixedColumnTree.setLeafNodes(statColumnTree.getRoots());
            return this.leftFixedColumnTree;
        }

        ColumnTree dimColumnTree = this.getDimColumnTree();
        if (this.isHideStatColumn()) {
            return dimColumnTree;
        }

        this.appendStatColumnToLeafNode(statColumnTree, dimColumnTree);
        this.setTreeNodeSpansAndDepth(dimColumnTree.getRoots(), this.getDimColumns());
        int depth = dimColumnTree.getDepth() + statColumnTree.getDepth();
        this.leftFixedColumnTree = new ColumnTree(dimColumnTree.getRoots(), depth);
        return this.leftFixedColumnTree;
    }

    private ColumnTree getVerticalLayoutLeftFixedColumnTree() {
        ColumnTree statColumnTree = this.getStatColumnTree();
        ColumnTree layoutTree = this.getLayoutColumnTree();
        if (this.isHideStatColumn()) {
            return layoutTree;
        }

        this.appendStatColumnToLeafNode(statColumnTree, layoutTree);
        this.setTreeNodeSpansAndDepth(layoutTree.getRoots(), this.getLayoutColumns());
        int depth = layoutTree.getDepth() + statColumnTree.getDepth();
        this.leftFixedColumnTree = new ColumnTree(layoutTree.getRoots(), depth);
        return this.leftFixedColumnTree;
    }

    /**
     * @param statColumnTree
     * @param dimColumnTree
     */
    private void appendStatColumnToLeafNode(ColumnTree statColumnTree, ColumnTree dimColumnTree) {
        for (ColumnTreeNode leafNode : dimColumnTree.getLeafNodes()) {
            for (ColumnTreeNode statColumnNode : statColumnTree.getRoots()) {
                ColumnTreeNode treeNode = statColumnNode.copyToNew();
                treeNode.setParent(leafNode);
                treeNode.setPath(leafNode.getPath() + treeNode.getPath());
                leafNode.getChildren().add(treeNode);
            }
        }
    }
}
