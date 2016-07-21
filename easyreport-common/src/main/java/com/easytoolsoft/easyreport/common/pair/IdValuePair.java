package com.easytoolsoft.easyreport.common.pair;

public class IdValuePair {
    private String id;
    private String value;
    private boolean selected;

    public IdValuePair() {
    }

    public IdValuePair(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public IdValuePair(String id, String value, boolean selected) {
        this(id, value);
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
