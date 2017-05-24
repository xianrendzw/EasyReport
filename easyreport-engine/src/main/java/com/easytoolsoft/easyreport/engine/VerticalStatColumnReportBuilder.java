package com.easytoolsoft.easyreport.engine;

import java.util.List;
import java.util.Map;

import com.easytoolsoft.easyreport.engine.data.AbstractReportDataSet;
import com.easytoolsoft.easyreport.engine.data.ColumnTree;
import com.easytoolsoft.easyreport.engine.data.ColumnTreeNode;
import com.easytoolsoft.easyreport.engine.data.ReportDataRow;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;

/**
 * 纵向展示统计列的报表生成类
 *
 * @author tomdeng
 */
public class VerticalStatColumnReportBuilder extends AbstractReportBuilder implements ReportBuilder {

    /**
     * 纵向展示统计列的报表生成类
     *
     * @param reportDataSet   报表数据集
     * @param reportParameter 报表参数
     */
    public VerticalStatColumnReportBuilder(final AbstractReportDataSet reportDataSet,
                                           final ReportParameter reportParameter) {
        super(reportDataSet, reportParameter);
    }

    @Override
    public void drawTableBodyRows() {
        final ColumnTree leftFixedColumnTree = this.reportDataSet.getBodyLeftFixedColumnTree();
        final List<ColumnTreeNode> rowNodes = leftFixedColumnTree.getLastLevelNodes();
        final List<ColumnTreeNode> columnNodes = this.reportDataSet.getBodyRightColumnNodes();
        final Map<String, ReportDataRow> statRowMap = this.reportDataSet.getRowMap();
        final Map<String, ColumnTreeNode> treeNodePathMap = this.getTreeNodePathMap(leftFixedColumnTree);
        final String defaultColumName = this.reportDataSet.getEnabledStatColumns().get(0).getName();
        final boolean isHideStatColumn = this.reportDataSet.isHideStatColumn();

        int rowIndex = 0;
        String[] lastNodePaths = null;
        this.tableRows.append("<tbody>");
        for (final ColumnTreeNode rowNode : rowNodes) {
            final String columnName = isHideStatColumn ? defaultColumName : rowNode.getName();
            this.tableRows.append("<tr").append(rowIndex % 2 == 0 ? " class=\"easyreport-row\"" : "").append(">");
            lastNodePaths = this.drawLeftFixedColumn(treeNodePathMap, lastNodePaths, rowNode,
                this.reportParameter.isRowSpan());
            for (final ColumnTreeNode columnNode : columnNodes) {
                final String rowKey = this.reportDataSet.getRowKey(rowNode, columnNode);
                ReportDataRow statRow = statRowMap.get(rowKey);
                if (statRow == null) {
                    statRow = new ReportDataRow();
                }
                final Object cell = statRow.getCell(columnName);
                final String value = (cell == null) ? "" : cell.toString();
                this.tableRows.append("<td>").append(value).append("</td>");
            }
            this.tableRows.append("</tr>");
            rowIndex++;
        }
        this.tableRows.append("</tbody>");
    }

    @Override
    public void drawTableFooterRows() {
    }
}
