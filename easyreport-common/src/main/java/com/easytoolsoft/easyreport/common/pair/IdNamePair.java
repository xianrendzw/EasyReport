package com.easytoolsoft.easyreport.common.pair;

public class IdNamePair {
    private String id;
    private String name;
    private boolean selected;

    public IdNamePair() {
    }

    public IdNamePair(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public IdNamePair(String id, String name, boolean selected) {
        this(id, name);
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
