package com.easytoolsoft.easyreport.common.pair;

/**
 * @author tomdeng
 */
public class IdTextPair {
    private String id;
    private String text;
    private boolean selected;

    public IdTextPair() {
    }

    public IdTextPair(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public IdTextPair(String id, String text, boolean selected) {
        this(id, text);
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
