package com.easytoolsoft.easyreport.common.tree;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * dhtmxTree(http://dhtmlx.com/docs/products/dhtmlxTree/)控件的工具类
 */
public class DhtmlXTreeUtils {
    public static DhtmlXTreeNode getRootNode(String id, List<DhtmlXTreeNode> nodes) {
        DhtmlXTreeNode rootNode = new DhtmlXTreeNode();
        rootNode.setId(id);
        rootNode.getItem().addAll(nodes);
        return rootNode;
    }

    public static JSONObject getJSONObject(String id, List<DhtmlXTreeNode> nodes) {
        return JSONObject.parseObject(getJSONString(id, nodes));
    }

    public static String getJSONString(String id, List<DhtmlXTreeNode> nodes) {
        String JSONTextFormat = "{id:\"%1$s\", item:[%2$s]}";
        String JSONNodeFormat = "{id:\"%1$s\",text:\"%2$s\",tooltip:\"%3$s\",child:%4$s}";

        StringBuilder jsonString = new StringBuilder();
        int count = nodes.size();
        for (int i = 0; i < count; i++) {
            DhtmlXTreeNode node = nodes.get(i);
            String JOSNNodeStr = String.format(JSONNodeFormat, node.getId(), node.getText(), node.getTooltip(), node.getChild());
            jsonString.append(JOSNNodeStr).append(i < count - 1 ? "," : "");
        }
        return String.format(JSONTextFormat, id, jsonString);
    }

    public static List<DhtmlXTreeNode> getNodes(Collection<DhtmlXTreeNode> nodes, String rootId) {
        if (nodes == null || nodes.size() == 0) {
            return new ArrayList<>(0);
        }

        List<DhtmlXTreeNode> rootNodes = nodes.stream()
                .filter(x -> x.getPid().equals(rootId))
                .sorted((x, y) -> x.getSequence() > y.getSequence() ? 1 : -1)
                .collect(Collectors.toList());

        for (DhtmlXTreeNode rootNode : rootNodes) {
            getChildNodes(nodes, rootNode);
        }
        return rootNodes;
    }

    private static void getChildNodes(Collection<DhtmlXTreeNode> nodes, DhtmlXTreeNode node) {
        List<DhtmlXTreeNode> childNodes = nodes.stream()
                .filter(x -> x.getPid().equals(node.getId()))
                .sorted((x, y) -> x.getSequence() > y.getSequence() ? 1 : -1)
                .collect(Collectors.toList());

        for (DhtmlXTreeNode childNode : childNodes) {
            node.getItem().add(childNode);
            getChildNodes(nodes, childNode);
        }
    }
}
