package com.easytoolsoft.easyreport.common.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * dhtmxTree(http://dhtmlx.com/docs/products/dhtmlxTree/)控件的树结点数据模型类
 * http://docs.dhtmlx.com/tree__syntax_templates.html#jsonformattemplate
 *
 * @author tomdeng
 */
public class DhtmlXTreeNode {
    private final List<DhtmlXTreeNode> item;
    private String id;
    private String pid;
    private String text;
    private String tooltip;
    private int checked;
    private int child = 0;
    private int open;
    private int sequence = 10;

    public DhtmlXTreeNode() {
        this.item = new ArrayList<>(10);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getChild() {
        return child;
    }

    public void setChild(int child) {
        this.child = child;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public List<DhtmlXTreeNode> getItem() {
        return item;
    }
}
