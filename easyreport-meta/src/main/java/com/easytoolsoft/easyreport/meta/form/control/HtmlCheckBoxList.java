package com.easytoolsoft.easyreport.meta.form.control;

import java.util.List;

/**
 * @author Tom Deng
 * @date 2017-03-25
 **/
public class HtmlCheckBoxList extends HtmlFormElement {
    private final List<HtmlCheckBox> value;

    public HtmlCheckBoxList(final String name, final String text, final List<HtmlCheckBox> value) {
        super(name, text);
        this.type = "checkboxlist";
        this.value = value;
    }

    public List<HtmlCheckBox> getValue() {
        return this.value;
    }
}
