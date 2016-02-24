package com.easytoolsoft.easyreport.viewmodel;

public class HtmlTextBox extends HtmlFormElement {
    private final String value;

    public HtmlTextBox(String name, String text, String value) {
        super(name, text);
        this.type = "textbox";
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
