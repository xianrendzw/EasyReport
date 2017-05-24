package com.easytoolsoft.easyreport.meta.form.control;

/**
 * @author Tom Deng
 * @date 2017-03-25
 **/
public class HtmlSelectOption {
    private String text;
    private String value;
    private boolean selected;

    public HtmlSelectOption(final String text, final String value) {
        this.text = text;
        this.value = value;
    }

    public HtmlSelectOption(final String text, final String value, final boolean selected) {
        this(text, value);
        this.selected = selected;
    }

    public String getText() {
        return this.text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(final boolean selected) {
        this.selected = selected;
    }
}
