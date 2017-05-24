package com.easytoolsoft.easyreport.meta.form.control;

/**
 * @author Tom Deng
 * @date 2017-03-25
 **/
public class HtmlTextBox extends HtmlFormElement {
    private final String value;

    public HtmlTextBox(final String name, final String text, final String value) {
        super(name, text);
        this.type = "textbox";
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
