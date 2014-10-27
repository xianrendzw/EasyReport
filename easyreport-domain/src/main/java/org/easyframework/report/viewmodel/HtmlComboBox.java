package org.easyframework.report.viewmodel;

import java.util.List;

public class HtmlComboBox extends HtmlFormElement {
	private final List<HtmlSelectOption> value;
	private boolean multipled;

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

	public List<HtmlSelectOption> getValue() {
		return this.value;
	}
}
