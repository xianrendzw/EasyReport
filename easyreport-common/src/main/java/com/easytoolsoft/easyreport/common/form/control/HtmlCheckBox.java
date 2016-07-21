package com.easytoolsoft.easyreport.common.form.control;

public class HtmlCheckBox extends HtmlFormElement {
    private final String value;
    private boolean checked;

    public HtmlCheckBox(String name, String text, String value) {
        super(name, text);
        this.type = "checkbox";
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
