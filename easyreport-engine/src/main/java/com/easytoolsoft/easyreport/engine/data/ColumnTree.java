package com.easytoolsoft.easyreport.engine.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表列树型结构类
 *
 * @author tomdeng
 */
public class ColumnTree {
    private final List<ColumnTreeNode> roots;
    private final int depth;
    private List<ColumnTreeNode> leafNodes;
    private Map<Integer, List<ColumnTreeNode>> levelNodesMap;

    public ColumnTree(final List<ColumnTreeNode> roots, final int depth) {
        this.roots = roots;
        this.depth = depth;
    }

    public List<ColumnTreeNode> getRoots() {
        return this.roots;
    }

    public int getDepth() {
        return this.depth;
    }

    public List<ColumnTreeNode> getLeafNodes() {
        return this.leafNodes;
    }

    public void setLeafNodes(final List<ColumnTreeNode> leafNodes) {
        this.leafNodes = leafNodes;
    }

    public Map<Integer, List<ColumnTreeNode>> getLevelNodesMap() {
        if (this.levelNodesMap == null) {
            this.levelNodesMap = new HashMap<>();
            this.roots.forEach(this::buildLevelNodesMapByRecursion);
        }
        return this.levelNodesMap;
    }

    public List<ColumnTreeNode> getLastLevelNodes() {
        return this.getNodesByLevel(this.getDepth() - 1);
    }

    public List<ColumnTreeNode> getNodesByLevel(final int level) {
        if (this.levelNodesMap == null) {
            this.getLevelNodesMap();
        }
        if (this.levelNodesMap.containsKey(level)) {
            return this.levelNodesMap.get(level);
        }
        return new ArrayList<>(0);
    }

    private void buildLevelNodesMapByRecursion(final ColumnTreeNode parentNode) {
        final int level = parentNode.getDepth();
        if (!this.levelNodesMap.containsKey(level)) {
            final List<ColumnTreeNode> treeNodes = new ArrayList<>();
            treeNodes.add(parentNode);
            this.levelNodesMap.put(level, treeNodes);
        } else {
            this.levelNodesMap.get(level).add(parentNode);
        }

        parentNode.getChildren().forEach(this::buildLevelNodesMapByRecursion);
    }
}
