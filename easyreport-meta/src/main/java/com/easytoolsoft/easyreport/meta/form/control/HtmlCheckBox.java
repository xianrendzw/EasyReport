package com.easytoolsoft.easyreport.meta.form.control;

/**
 * @author Tom Deng
 * @date 2017-03-25
 **/
public class HtmlCheckBox extends HtmlFormElement {
    private final String value;
    private boolean checked;

    public HtmlCheckBox(final String name, final String text, final String value) {
        super(name, text);
        this.type = "checkbox";
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(final boolean checked) {
        this.checked = checked;
    }
}
