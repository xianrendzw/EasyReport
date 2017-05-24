package com.easytoolsoft.easyreport.meta.form.control;

import java.util.List;

/**
 * @author Tom Deng
 * @date 2017-03-25
 **/
public class HtmlComboBox extends HtmlFormElement {
    private final List<HtmlSelectOption> value;
    private boolean multipled;
    private boolean autoComplete;

    public HtmlComboBox(final String name, final String text, final List<HtmlSelectOption> value) {
        super(name, text);
        this.type = "combobox";
        this.value = value;
    }

    public boolean isMultipled() {
        return this.multipled;
    }

    public void setMultipled(final boolean multipled) {
        this.multipled = multipled;
    }

    public boolean isAutoComplete() {
        return this.autoComplete;
    }

    public void setAutoComplete(final boolean autoComplete) {
        this.autoComplete = autoComplete;
    }

    public List<HtmlSelectOption> getValue() {
        return this.value;
    }
}
