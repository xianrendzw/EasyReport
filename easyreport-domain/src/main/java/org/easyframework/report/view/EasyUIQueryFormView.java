package org.easyframework.report.view;

import org.easyframework.report.viewmodel.HtmlCheckBox;
import org.easyframework.report.viewmodel.HtmlCheckBoxList;
import org.easyframework.report.viewmodel.HtmlComboBox;
import org.easyframework.report.viewmodel.HtmlDateBox;
import org.easyframework.report.viewmodel.HtmlSelectOption;
import org.easyframework.report.viewmodel.HtmlTextBox;

/**
 * JQueryEasyUI控件报表查询参数表单视图
 *
 */
public class EasyUIQueryFormView extends AbstractQueryParamFormView implements QueryParamFormView {
	@Override
	protected String getDateBoxText(HtmlDateBox dateBox) {
		String template = "<input name=\"%s\" type=\"text\" class=\"easyui-datebox\" required=\"true\" value=\"%s\" />";
		String easyuiText = String.format(template, dateBox.getName(), dateBox.getValue());
		return String.format("<span class=\"j-item\"><label style=\"width: 120px;\">%s:</label>%s</span>", dateBox.getText(), easyuiText);
	}

	@Override
	protected String getTexBoxText(HtmlTextBox textBox) {
		String template = "<input name=\"%s\" type=\"text\" value=\"%s\" />";
		String easyuiText = String.format(template, textBox.getName(), textBox.getValue());
		return String.format("<span class=\"j-item\"><label style=\"width: 120px;\">%s:</label>%s</span>", textBox.getText(), easyuiText);
	}

	@Override
	protected String getCheckBoxText(HtmlCheckBox checkBox) {
		String checked = checkBox.isChecked() ? "" : "checked=\"checked\"";
		return String.format("<input name=\"%s\" type=\"checkbox\" value=\"%s\" %s />%s",
				checkBox.getName(), checkBox.getValue(), checked, checkBox.getText());
	}

	@Override
	protected String getComboBoxText(HtmlComboBox comboBox) {
		String multiple = comboBox.isMultipled() ? "data-options=\"multiple:true\"" : "";
		StringBuilder htmlText = new StringBuilder("");
		htmlText.append(String.format("<span class=\"j-item\"><label style=\"width: 120px;\">%s:</label>", comboBox.getText()));
		htmlText.append(String.format("<select class=\"easyui-combobox\" style=\"width: 200px;\" name=\"%s\" %s>", comboBox.getName(), multiple));
		for (HtmlSelectOption option : comboBox.getValue()) {
			String selected = option.isSelected() ? "selected=\"selected\"" : "";
			htmlText.append(String.format("<option value=\"%s\" %s>%s</option>", option.getValue(), selected, option.getText()));
		}
		htmlText.append("</select>");
		htmlText.append("</span>");
		return htmlText.toString();
	}

	@Override
	protected String getCheckboxListText(HtmlCheckBoxList checkBoxList) {
		StringBuilder htmlText = new StringBuilder("");
		htmlText.append(String.format("<span class=\"j-item\" data-type=\"checkbox\"><label style=\"width: 120px;\">%s:</label>",
				checkBoxList.getText()));
		for (HtmlCheckBox checkBox : checkBoxList.getValue()) {
			String checked = checkBox.isChecked() ? "checked=\"checked\"" : "";
			htmlText.append(String.format("<input name=\"%s\" type=\"checkbox\" value=\"%s\" data-name=\"%s\" %s/>%s &nbsp;",
					checkBoxList.getName(), checkBox.getName(), checkBox.getText(), checked, checkBox.getText()));
		}
		htmlText.append("<input id=\"checkAllStatColumn\" name=\"checkAllStatColumn\" type=\"checkbox\" />全选</span>");
		return htmlText.toString();
	}
}
