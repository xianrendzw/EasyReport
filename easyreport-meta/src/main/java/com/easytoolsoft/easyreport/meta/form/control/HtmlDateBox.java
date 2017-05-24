package com.easytoolsoft.easyreport.meta.form.control;

/**
 * @author Tom Deng
 * @date 2017-03-25
 **/
public class HtmlDateBox extends HtmlFormElement {
    private final String value;

    public HtmlDateBox(final String name, final String text, final String value) {
        super(name, text);
        this.type = "datebox";
        this.value = value;
        this.dataType = "date";
    }

    public String getValue() {
        return this.value;
    }
}
