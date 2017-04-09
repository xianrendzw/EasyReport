package com.easytoolsoft.easyreport.engine.data;

import java.util.List;
import java.util.Map;

/**
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface ReportDataSet {
    /**
     * 获取报表数据集中行key(或树路径)分隔字符串
     * <sample>layoutValue$col1value$col2value$col3value$...</sample>
     *
     * @return layoutValue$col1value$col2value$col3value$...
     */
    String getPathSeparator();

    /**
     * 获取报表元数据对象
     *
     * @return {@link ReportMetaDataSet}
     */
    ReportMetaDataSet getMetaData();

    /**
     * 获取报表数据集(RowMap)每一行的key
     *
     * @param rowNode    行结点
     * @param columnNode 列结点
     * @return 行key
     */
    String getRowKey(ColumnTreeNode rowNode, ColumnTreeNode columnNode);

    /**
     * 获取报表表头(header)左边固定列集合
     *
     * @return List<ReportDataColumn>
     */
    List<ReportDataColumn> getHeaderLeftFixedColumns();

    /**
     * 获取表头(header)右边列树型结构
     *
     * @return ColumnTree
     */
    ColumnTree getHeaderRightColumnTree();

    /**
     * 获取报表表体(body)左边固定列树型结构
     *
     * @return ColumnTree
     */
    ColumnTree getBodyLeftFixedColumnTree();

    /**
     * 获取报表表体(body)右边列集合
     *
     * @return List<ColumnTreeNode>
     */
    List<ColumnTreeNode> getBodyRightColumnNodes();

    /**
     * 是否在报表中隐藏统计列
     * 当统计列只有一列时,如果维度列大于0,则不显示出统计列，只显示布局列或维度列
     *
     * @return true|false
     */
    boolean isHideStatColumn();

    /**
     * 获取布局列树
     *
     * @return ColumnTree
     */
    ColumnTree getLayoutColumnTree();

    /**
     * 获取维度列树
     *
     * @return ColumnTree {@link ColumnTree}
     */
    ColumnTree getDimColumnTree();

    /**
     * 获取报表统计列树
     *
     * @return ColumnTree
     */
    ColumnTree getStatColumnTree();

    /**
     * 获取报表布局列
     *
     * @return {@link List<ReportDataColumn}
     */
    List<ReportDataColumn> getLayoutColumns();

    /**
     * 获取报表所有维度列列表
     *
     * @return {@link List<ReportDataColumn>}
     */
    List<ReportDataColumn> getDimColumns();

    /**
     * 获取报表所有启用的统计列列表
     *
     * @return {@link List<ReportDataColumn>}
     */
    List<ReportDataColumn> getEnabledStatColumns();

    /**
     * 获取报表所有的计算列列表
     *
     * @return {@link List<ReportDataColumn>}
     */
    List<ReportDataColumn> getComputedColumns();

    /**
     * 获取报表所有统计列(含计算列)列表
     *
     * @return {@link List<ReportDataColumn>}
     */
    List<ReportDataColumn> getStatColumns();

    /**
     * 获取报表所有非统计列列表
     *
     * @return {@link List<ReportDataColumn>}
     */
    List<ReportDataColumn> getNonStatColumns();

    /**
     * 获取维度列总数
     *
     * @return 维度列总数
     */
    int getDimColumnCount();

    /**
     * 获取所有非统计列去重数据集合
     *
     * @return {@link Map <String, List<String>>}
     */
    Map<String, List<String>> getUnduplicatedNonStatColumnDataMap();

    /**
     * 获取报表数据行Map,其中key由于布局列与维度列的值组成,value为统计列与计算列值的集合
     *
     * @return Map<String, ReportDataRow>
     */
    Map<String, ReportDataRow> getRowMap();
}
