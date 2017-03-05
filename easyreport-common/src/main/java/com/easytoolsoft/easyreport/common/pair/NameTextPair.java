package com.easytoolsoft.easyreport.common.pair;

/**
 * @author tomdeng
 */
public class NameTextPair {
    private String name;
    private String text;
    private boolean selected;

    public NameTextPair() {
    }

    public NameTextPair(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public NameTextPair(String name, String text, boolean selected) {
        this(name, text);
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
