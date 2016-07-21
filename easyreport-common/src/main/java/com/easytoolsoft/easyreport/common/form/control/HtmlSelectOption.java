package com.easytoolsoft.easyreport.common.form.control;

public class HtmlSelectOption {
    private String text;
    private String value;
    private boolean selected;

    public HtmlSelectOption(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public HtmlSelectOption(String text, String value, boolean selected) {
        this(text, value);
        this.selected = selected;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
