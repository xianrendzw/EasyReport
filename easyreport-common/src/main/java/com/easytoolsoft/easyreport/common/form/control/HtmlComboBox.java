package com.easytoolsoft.easyreport.common.form.control;

import java.util.List;

public class HtmlComboBox extends HtmlFormElement {
    private final List<HtmlSelectOption> value;
    private boolean multipled;
    private boolean autoComplete;

    public HtmlComboBox(String name, String text, List<HtmlSelectOption> value) {
        super(name, text);
        this.type = "combobox";
        this.value = value;
    }

    public boolean isMultipled() {
        return this.multipled;
    }

    public void setMultipled(boolean multipled) {
        this.multipled = multipled;
    }

    public boolean isAutoComplete() {
        return autoComplete;
    }

    public void setAutoComplete(boolean autoComplete) {
        this.autoComplete = autoComplete;
    }

    public List<HtmlSelectOption> getValue() {
        return this.value;
    }
}
