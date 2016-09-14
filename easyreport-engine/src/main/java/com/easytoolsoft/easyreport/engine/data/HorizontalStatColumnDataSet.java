package com.easytoolsoft.easyreport.engine.data;

import java.util.List;

/**
 * 横向展示报表统计列的报表数据集类。
 */
public class HorizontalStatColumnDataSet extends ReportDataSet {
    /**
     * @param metaDataSet
     * @param layout
     * @param statColumnLayout
     */
    public HorizontalStatColumnDataSet(ReportMetaDataSet metaDataSet, LayoutType layout, LayoutType statColumnLayout) {
        super(metaDataSet, layout, statColumnLayout);
    }

    @Override
    public String getRowKey(ColumnTreeNode rowNode, ColumnTreeNode columnNode) {
        return layout == LayoutType.HORIZONTAL ?
                (columnNode.getPath() + rowNode.getPath()) : (rowNode.getPath() + columnNode.getPath());
    }

    @Override
    public List<ReportDataColumn> getHeaderLeftFixedColumns() {
        return layout == LayoutType.HORIZONTAL ?
                this.getDimColumns() : this.getLayoutColumns();
    }

    @Override
    public ColumnTree getHeaderRightColumnTree() {
        if (this.headerColumnTree != null) {
            return this.headerColumnTree;
        }
        // 如果布局列纵向展示
        if (this.layout == LayoutType.HORIZONTAL) {
            return this.getHorizontalLayoutHeaderColumnTree();
        }
        return this.getVerticalLayoutHeaderColumnTree();
    }

    @Override
    public ColumnTree getBodyLeftFixedColumnTree() {
        return layout == LayoutType.HORIZONTAL ?
                this.getDimColumnTree() : this.getLayoutColumnTree();
    }

    @Override
    public List<ColumnTreeNode> getBodyRightColumnNodes() {
        return layout == LayoutType.HORIZONTAL ?
                this.getLayoutColumnTree().getLastLevelNodes() : this.getDimColumnTree().getLastLevelNodes();
    }

    @Override
    public boolean isHideStatColumn() {
        // 如果布局列横向显示
        // 或者布局列纵向显示（即显示在表体左边)且表头有维度列
        return this.getEnabledStatColumns().size() == 1 &&
                (this.layout == LayoutType.HORIZONTAL || this.getDimColumnCount() > 0);
    }

    private ColumnTree getHorizontalLayoutHeaderColumnTree() {
        ColumnTree statColumnTree = this.getStatColumnTree();

        ColumnTree layoutColumnTree = this.getLayoutColumnTree();
        if (this.isHideStatColumn()) {
            return layoutColumnTree;
        }

        for (ColumnTreeNode leafNode : layoutColumnTree.getLeafNodes()) {
            leafNode.getChildren().addAll(statColumnTree.getRoots());
        }
        this.setTreeNodeSpansAndDepth(layoutColumnTree.getRoots(), this.getLayoutColumns());
        int depth = layoutColumnTree.getDepth() + statColumnTree.getDepth();
        this.headerColumnTree = new ColumnTree(layoutColumnTree.getRoots(), depth);
        return this.headerColumnTree;
    }

    private ColumnTree getVerticalLayoutHeaderColumnTree() {
        ColumnTree statColumnTree = this.getStatColumnTree();

        // 如果右边没有维度列则表头列直接设置为统计列
        if (this.getDimColumnCount() == 0) {
            this.setTreeNodeSpansAndDepth(statColumnTree.getRoots(), false, this.getEnabledStatColumns());
            this.headerColumnTree = new ColumnTree(statColumnTree.getRoots(), statColumnTree.getDepth());
            this.headerColumnTree.setLeafNodes(statColumnTree.getRoots());
            return this.headerColumnTree;
        }

        ColumnTree dimColumnTree = this.getDimColumnTree();
        if (this.isHideStatColumn()) {
            return dimColumnTree;
        }

        for (ColumnTreeNode leafNode : dimColumnTree.getLeafNodes()) {
            leafNode.getChildren().addAll(statColumnTree.getRoots());
        }
        this.setTreeNodeSpansAndDepth(dimColumnTree.getRoots(), this.getDimColumns());
        int depth = dimColumnTree.getDepth() + statColumnTree.getDepth();
        this.headerColumnTree = new ColumnTree(dimColumnTree.getRoots(), depth);
        return this.headerColumnTree;
    }
}
