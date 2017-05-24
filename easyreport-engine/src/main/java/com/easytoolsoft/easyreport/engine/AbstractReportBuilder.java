package com.easytoolsoft.easyreport.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easytoolsoft.easyreport.engine.data.AbstractReportDataSet;
import com.easytoolsoft.easyreport.engine.data.ColumnTree;
import com.easytoolsoft.easyreport.engine.data.ColumnTreeNode;
import com.easytoolsoft.easyreport.engine.data.ReportDataColumn;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;
import com.easytoolsoft.easyreport.engine.data.ReportTable;
import org.apache.commons.lang3.StringUtils;

/**
 * @author tomdeng
 */
public abstract class AbstractReportBuilder implements ReportBuilder {
    protected final AbstractReportDataSet reportDataSet;
    protected final ReportParameter reportParameter;
    protected final StringBuilder tableRows = new StringBuilder();

    protected AbstractReportBuilder(final AbstractReportDataSet reportDataSet, final ReportParameter reportParameter) {
        this.reportDataSet = reportDataSet;
        this.reportParameter = reportParameter;
    }

    @Override
    public ReportTable getTable() {
        final StringBuilder table = new StringBuilder();
        table.append("<table id=\"easyreport\" class=\"easyreport\">");
        table.append(this.tableRows.toString());
        table.append("</table>");
        return new ReportTable(table.toString(),
            this.reportParameter.getSqlText(),
            this.reportDataSet.getMetaData().getRows().size(), this.reportDataSet.getMetaData().getColumns().size());
    }

    @Override
    public void drawTableHeaderRows() {
        final List<ReportDataColumn> leftFixedColumns = this.reportDataSet.getHeaderLeftFixedColumns();
        final ColumnTree rightColumnTree = this.reportDataSet.getHeaderRightColumnTree();
        final int rowCount = rightColumnTree.getDepth();
        final String rowSpan = rowCount > 1 ? String.format(" rowspan=\"%s\"", rowCount) : "";

        this.tableRows.append("<thead>");
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            this.tableRows.append("<tr class=\"easyreport-header\">");
            if (rowIndex == 0) {
                for (final ReportDataColumn leftColumn : leftFixedColumns) {
                    this.tableRows.append(String.format("<th title=\"%s\"%s>%s</th>",
                        leftColumn.getMetaData().getComment(), rowSpan, leftColumn.getText()));
                }
            }
            for (final ColumnTreeNode rightColumn : rightColumnTree.getNodesByLevel(rowIndex)) {
                final String colSpan = rightColumn.getSpans() > 1 ? String.format(" colspan=\"%s\"",
                    rightColumn.getSpans())
                    : "";
                this.tableRows.append(String.format("<th title=\"%s\"%s>%s</th>",
                    rightColumn.getColumn().getMetaData().getComment(), colSpan, rightColumn.getValue()));
            }
            this.tableRows.append("</tr>");
        }
        this.tableRows.append("</thead>");
    }

    /**
     * 生成表体左边每一行的单元格
     *
     * @param treeNodePathMap 树中每个节点的path属性为key,treeNode属性为value的map对象
     * @param lastNodePaths   上一个跨行结点的树路径
     * @param rowNode         当前行结点
     * @param isRowSpan       是否跨行(rowspan)
     * @return
     */
    protected String[] drawLeftFixedColumn(final Map<String, ColumnTreeNode> treeNodePathMap,
                                           final String[] lastNodePaths, final ColumnTreeNode rowNode,
                                           final boolean isRowSpan) {
        if (isRowSpan) {
            return this.drawLeftRowSpanColumn(treeNodePathMap, lastNodePaths, rowNode);
        }

        final String[] paths = StringUtils.splitPreserveAllTokens(rowNode.getPath(),
            this.reportDataSet.getPathSeparator());
        if (paths == null || paths.length == 0) {
            return null;
        }

        final int level = paths.length > 1 ? paths.length - 1 : 1;
        for (int i = 0; i < level; i++) {
            this.tableRows.append(String.format("<td class=\"easyreport-fixed-column\">%s</td>", paths[i]));
        }
        return null;
    }

    /**
     * 按层次遍历报表列树中每个结点，然后以结点path为key,treeNode属性为value，生成一个Map对象
     *
     * @param columnTree 报表列树对象
     * @return 树中每个节点的path属性为key, treeNode属性为value的map对象
     */
    protected Map<String, ColumnTreeNode> getTreeNodePathMap(final ColumnTree columnTree) {
        final Map<String, ColumnTreeNode> treeNodePathMap = new HashMap<>();
        for (int level = 0; level < columnTree.getDepth(); level++) {
            for (final ColumnTreeNode treeNode : columnTree.getNodesByLevel(level)) {
                treeNodePathMap.put(treeNode.getPath(), treeNode);
            }
        }
        return treeNodePathMap;
    }

    /**
     * 生成表体左边每一行的跨行(rowspan)单元格
     *
     * @param treeNodePathMap 树中每个节点的path属性为key,treeNode属性为value的map对象
     * @param lastNodePaths   上一个跨行结点的树路径
     * @param rowNode         当前行结点
     * @return 当前跨行结点的树路径
     */
    protected String[] drawLeftRowSpanColumn(final Map<String, ColumnTreeNode> treeNodePathMap, String[] lastNodePaths,
                                             final ColumnTreeNode rowNode) {
        final String[] paths = StringUtils.splitPreserveAllTokens(rowNode.getPath(),
            this.reportDataSet.getPathSeparator());
        if (paths == null || paths.length == 0) {
            return null;
        }

        final int level = paths.length > 1 ? paths.length - 1 : 1;
        final String[] currNodePaths = new String[level];
        for (int i = 0; i < level; i++) {
            final String currPath = paths[i] + this.reportDataSet.getPathSeparator();
            currNodePaths[i] = (i > 0 ? currNodePaths[i - 1] + currPath : currPath);
            if (lastNodePaths != null && lastNodePaths[i].equals(currNodePaths[i])) {
                continue;
            }
            final ColumnTreeNode treeNode = treeNodePathMap.get(currNodePaths[i]);
            if (treeNode == null) {
                this.tableRows.append("<td class=\"easyreport-fixed-column\"></td>");
            } else {
                final String rowspan = treeNode.getSpans() > 1 ? String.format(" rowspan=\"%s\"", treeNode.getSpans())
                    : "";
                this.tableRows.append(
                    String.format("<td class=\"easyreport-fixed-column\"%s>%s</td>", rowspan, treeNode.getValue()));
            }
        }
        lastNodePaths = currNodePaths;
        return lastNodePaths;
    }
}
