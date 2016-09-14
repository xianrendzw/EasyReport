package com.easytoolsoft.easyreport.engine;

import com.easytoolsoft.easyreport.engine.data.ColumnTree;
import com.easytoolsoft.easyreport.engine.data.ColumnTreeNode;
import com.easytoolsoft.easyreport.engine.data.ReportDataColumn;
import com.easytoolsoft.easyreport.engine.data.ReportDataSet;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;
import com.easytoolsoft.easyreport.engine.data.ReportTable;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractReportBuilder {
    protected final ReportDataSet reportDataSet;
    protected final ReportParameter reportParameter;
    protected final StringBuilder tableRows = new StringBuilder();

    protected AbstractReportBuilder(ReportDataSet reportDataSet, ReportParameter reportParameter) {
        this.reportDataSet = reportDataSet;
        this.reportParameter = reportParameter;
    }

    public ReportTable getTable() {
        StringBuilder table = new StringBuilder();
        table.append("<table id=\"easyreport\" class=\"easyreport\">");
        table.append(this.tableRows.toString());
        table.append("</table>");
        return new ReportTable(table.toString(),
                this.reportParameter.getSqlText(),
                reportDataSet.getMetaData().getRows().size(), reportDataSet.getMetaData().getColumns().size());
    }

    public void drawTableHeaderRows() {
        List<ReportDataColumn> leftFixedColumns = this.reportDataSet.getHeaderLeftFixedColumns();
        ColumnTree rightColumnTree = this.reportDataSet.getHeaderRightColumnTree();
        int rowCount = rightColumnTree.getDepth();
        String rowSpan = rowCount > 1 ? String.format(" rowspan=\"%s\"", rowCount) : "";

        this.tableRows.append("<thead>");
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            this.tableRows.append("<tr class=\"easyreport-header\">");
            if (rowIndex == 0) {
                for (ReportDataColumn leftColumn : leftFixedColumns) {
                    this.tableRows.append(String.format("<th title=\"%s\"%s>%s</th>",
                            leftColumn.getMetaData().getComment(), rowSpan, leftColumn.getText()));
                }
            }
            for (ColumnTreeNode rightColumn : rightColumnTree.getNodesByLevel(rowIndex)) {
                String colSpan = rightColumn.getSpans() > 1 ? String.format(" colspan=\"%s\"", rightColumn.getSpans()) : "";
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
    protected String[] drawLeftFixedColumn(Map<String, ColumnTreeNode> treeNodePathMap,
                                           String[] lastNodePaths, ColumnTreeNode rowNode, boolean isRowSpan) {
        if (isRowSpan) {
            return this.drawLeftRowSpanColumn(treeNodePathMap, lastNodePaths, rowNode);
        }

        String[] paths = StringUtils.splitPreserveAllTokens(rowNode.getPath(), this.reportDataSet.getPathSeparator());
        if (paths == null || paths.length == 0) {
            return null;
        }

        int level = paths.length > 1 ? paths.length - 1 : 1;
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
    protected Map<String, ColumnTreeNode> getTreeNodePathMap(ColumnTree columnTree) {
        Map<String, ColumnTreeNode> treeNodePathMap = new HashMap<>();
        for (int level = 0; level < columnTree.getDepth(); level++) {
            for (ColumnTreeNode treeNode : columnTree.getNodesByLevel(level)) {
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
    protected String[] drawLeftRowSpanColumn(Map<String, ColumnTreeNode> treeNodePathMap, String[] lastNodePaths, ColumnTreeNode rowNode) {
        String[] paths = StringUtils.splitPreserveAllTokens(rowNode.getPath(), this.reportDataSet.getPathSeparator());
        if (paths == null || paths.length == 0) {
            return null;
        }

        int level = paths.length > 1 ? paths.length - 1 : 1;
        String[] currNodePaths = new String[level];
        for (int i = 0; i < level; i++) {
            String currPath = paths[i] + this.reportDataSet.getPathSeparator();
            currNodePaths[i] = (i > 0 ? currNodePaths[i - 1] + currPath : currPath);
            if (lastNodePaths != null && lastNodePaths[i].equals(currNodePaths[i])) {
                continue;
            }
            ColumnTreeNode treeNode = treeNodePathMap.get(currNodePaths[i]);
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
