package com.easytoolsoft.easyreport.common.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * jQueryEasyUI(http://www.jeasyui.com/)树节点视图数据模型类
 */
public class EasyUITreeNode<T> {
    private final String id;
    private final String pid;
    private final String text;
    private final String state;
    private final String iconCls;
    private final boolean checked;
    private final T attributes;
    private final List<EasyUITreeNode<T>> children = new ArrayList<>();

    public EasyUITreeNode(String id, String pid, String text) {
        this(id, pid, text, "closed", "", false, null);
    }

    public EasyUITreeNode(String id, String text, String state, T attributes) {
        this(id, "0", text, state, "", false, attributes);
    }

    public EasyUITreeNode(String id, String pid, String text, String state, T attributes) {
        this(id, pid, text, state, "", false, attributes);
    }

    public EasyUITreeNode(String id, String pid, String text, String state, String iconCls, boolean checked, T attributes) {
        this.id = id;
        this.pid = pid;
        this.text = text;
        this.state = state;
        this.iconCls = iconCls;
        this.checked = checked;
        this.attributes = attributes;
    }

    public String getId() {
        return this.id;
    }

    public String getPId() {
        return this.pid;
    }

    public String getText() {
        return this.text;
    }

    public String getIconCls() {
        return this.iconCls == null ? "" : this.iconCls;
    }

    public boolean getChecked() {
        return this.checked;
    }

    public String getState() {
        return this.state;
    }

    public T getAttributes() {
        return this.attributes;
    }

    public List<EasyUITreeNode<T>> getChildren() {
        return this.children;
    }
}
