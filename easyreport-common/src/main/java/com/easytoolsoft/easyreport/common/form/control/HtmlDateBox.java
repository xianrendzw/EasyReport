package com.easytoolsoft.easyreport.common.form.control;

public class HtmlDateBox extends HtmlFormElement {
    private final String value;

    public HtmlDateBox(String name, String text, String value) {
        super(name, text);
        this.type = "datebox";
        this.value = value;
        this.dataType = "date";
    }

    public String getValue() {
        return value;
    }
}
