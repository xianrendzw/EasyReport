package com.easytoolsoft.easyreport.engine;

import com.easytoolsoft.easyreport.engine.data.ColumnTree;
import com.easytoolsoft.easyreport.engine.data.ColumnTreeNode;
import com.easytoolsoft.easyreport.engine.data.ReportDataRow;
import com.easytoolsoft.easyreport.engine.data.ReportDataSet;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;

import java.util.List;
import java.util.Map;

/**
 * 纵向展示统计列的报表生成类
 */
public class VerticalStatColumnReportBuilder extends AbstractReportBuilder implements ReportBuilder {

    /**
     * 纵向展示统计列的报表生成类
     *
     * @param reportDataSet   报表数据集
     * @param reportParameter 报表参数
     */
    public VerticalStatColumnReportBuilder(ReportDataSet reportDataSet, ReportParameter reportParameter) {
        super(reportDataSet, reportParameter);
    }

    @Override
    public void drawTableBodyRows() {
        ColumnTree leftFixedColumnTree = this.reportDataSet.getBodyLeftFixedColumnTree();
        List<ColumnTreeNode> rowNodes = leftFixedColumnTree.getLastLevelNodes();
        List<ColumnTreeNode> columnNodes = this.reportDataSet.getBodyRightColumnNodes();
        Map<String, ReportDataRow> statRowMap = reportDataSet.getRowMap();
        Map<String, ColumnTreeNode> treeNodePathMap = this.getTreeNodePathMap(leftFixedColumnTree);
        String defaultColumName = this.reportDataSet.getEnabledStatColumns().get(0).getName();
        boolean isHideStatColumn = this.reportDataSet.isHideStatColumn();

        int rowIndex = 0;
        String[] lastNodePaths = null;
        this.tableRows.append("<tbody>");
        for (ColumnTreeNode rowNode : rowNodes) {
            String columnName = isHideStatColumn ? defaultColumName : rowNode.getName();
            this.tableRows.append("<tr").append(rowIndex % 2 == 0 ? " class=\"easyreport-row\"" : "").append(">");
            lastNodePaths = this.drawLeftFixedColumn(treeNodePathMap, lastNodePaths, rowNode, this.reportParameter.isRowSpan());
            for (ColumnTreeNode columnNode : columnNodes) {
                String rowKey = this.reportDataSet.getRowKey(rowNode, columnNode);
                ReportDataRow statRow = statRowMap.get(rowKey);
                if (statRow == null) {
                    statRow = new ReportDataRow();
                }
                Object cell = statRow.getCell(columnName);
                String value = (cell == null) ? "" : cell.toString();
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
