package com.easytoolsoft.easyreport.engine.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 报表树型列节点类
 *
 * @author tomdeng
 */
public class ColumnTreeNode {
    private final String name;
    private final String text;
    private final List<ColumnTreeNode> children = new ArrayList<>(0);
    private String value;
    private ColumnTreeNode parent;
    private int width = 100;
    private int depth;
    private int spans;
    private String path;
    private ReportDataColumn column;

    public ColumnTreeNode(final ReportDataColumn column) {
        this(column.getName(), column.getText(), column.getText());
        this.column = column;
    }

    public ColumnTreeNode(final String name, final String text, final String value) {
        this(name, text, value, null);
    }

    public ColumnTreeNode(final String name, final String text, final String value, final ColumnTreeNode parent) {
        this.name = name;
        this.text = text;
        this.value = value;
        this.parent = parent;
    }

    /**
     * 获取报表树型列名
     *
     * @return 报表树型列名
     */
    public String getName() {
        return this.name;
    }

    /**
     * 获取报表树型列对应的标题
     *
     * @return 报表树型列对应的标题
     */
    public String getText() {
        return this.text;
    }

    /**
     * 获取报表树型列的值
     *
     * @return 报表树型列的值
     */
    public String getValue() {
        return this.value;
    }

    /**
     * 设置报表树型列的值
     *
     * @param value 报表树型列的值
     */
    public void setValue(final String value) {
        this.value = value;
    }

    /**
     * 获取报表树型列的父列节点
     *
     * @return {@link ColumnTreeNode}
     */
    public ColumnTreeNode getParent() {
        return this.parent;
    }

    /**
     * 设置报表树型列的父列节点
     *
     * @param parent 父列节点
     */
    public void setParent(final ColumnTreeNode parent) {
        this.parent = parent;
    }

    /**
     * 获取报表树型列宽(单位:像素)
     *
     * @return 报表树型列宽(单位:像素)
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * 设置报表树型列宽(单位:像素)
     *
     * @param width 列宽(单位:像素)
     */
    public void setWidth(final int width) {
        this.width = width;
    }

    /**
     * 获取报表树型列在当前树中的深度(0表示第一层)
     *
     * @return 报表树型列在当前树中的深度(0表示第一层)
     */
    public int getDepth() {
        return this.depth;
    }

    /**
     * 设置报表树型列在当前树中的深度(0表示第一层)
     *
     * @param depth 列在当前树中的深度(0表示第一层)
     */
    public void setDepth(final int depth) {
        this.depth = depth;
    }

    /**
     * 获取报表树型列对应的html表格中的colspan或rowspan的总数
     *
     * @return html表格中的colspan或rowspan的总数
     */
    public int getSpans() {
        return this.spans;
    }

    /**
     * 设置报表树型列对应的html表格中的colspan或rowspan的总数
     *
     * @param spans html表格中的colspan或rowspan的总数
     */
    public void setSpans(final int spans) {
        this.spans = spans;
    }

    /**
     * 获取从根点到当前结点的树路径
     *
     * @return 从根点到当前结点的树路径
     */
    public String getPath() {
        return (this.path == null) ? "" : this.path;
    }

    /**
     * 设置从根点到当前结点的树路径
     *
     * @param path
     */
    public void setPath(final String path) {
        this.path = path;
    }

    /**
     * 获取从根点到当前结点的树路径
     *
     * @return 从根点到当前结点的树路径
     */
    public ReportDataColumn getColumn() {
        return this.column;
    }

    /**
     * 设置当前报表树型列对应报表列对象
     *
     * @param column {@link ReportDataColumn}
     */
    public void setColumn(final ReportDataColumn column) {
        this.column = column;
    }

    /**
     * 获取当前报表树型列对应报表列对象
     *
     * @return {@link List<ColumnTreeNode>}
     */
    public List<ColumnTreeNode> getChildren() {
        return this.children;
    }

    public ColumnTreeNode copyToNew() {
        final ColumnTreeNode treeNode = new ColumnTreeNode(this.name, this.text, this.value);
        treeNode.setColumn(this.column);
        treeNode.setDepth(this.depth);
        treeNode.setParent(this.parent);
        treeNode.setPath(this.path);
        treeNode.setSpans(this.spans);
        return treeNode;
    }
}
