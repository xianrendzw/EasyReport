package com.easytoolsoft.easyreport.common.form;

import com.easytoolsoft.easyreport.common.form.control.HtmlCheckBox;
import com.easytoolsoft.easyreport.common.form.control.HtmlCheckBoxList;
import com.easytoolsoft.easyreport.common.form.control.HtmlComboBox;
import com.easytoolsoft.easyreport.common.form.control.HtmlDateBox;
import com.easytoolsoft.easyreport.common.form.control.HtmlSelectOption;
import com.easytoolsoft.easyreport.common.form.control.HtmlTextBox;

/**
 * Bootstrapper风格控件报表查询参数表单视图
 */
public class BootstrapQueryFormView extends AbstractQueryParamFormView implements QueryParamFormView {
    @Override
    protected String getDateBoxText(HtmlDateBox dateBox) {
        StringBuilder htmlText = new StringBuilder("");
        htmlText.append("<div class=\"form-group\">");
        htmlText.append(String.format("<label>%s:</label>", dateBox.getText()));
        htmlText.append("<label class=\"input\">");
        htmlText.append("<i class=\"icon-append fa fa-calendar\"></i> ");
        htmlText.append(String.format("<input id=\"%s\" name=\"%s\" class=\"hasDatepicker\" type=\"text\" value=\"%s\" readonly />",
                dateBox.getName(), dateBox.getName(), dateBox.getValue()));
        htmlText.append("</label>");
        htmlText.append("</div>");
        return htmlText.toString();
    }

    @Override
    protected String getTexBoxText(HtmlTextBox textBox) {
        StringBuilder htmlText = new StringBuilder("");
        htmlText.append("<div class=\"form-group\">");
        htmlText.append(String.format("<label>%s:</label>", textBox.getText()));
        htmlText.append("<label class=\"input\">");
        htmlText.append(String.format("<input id=\"%s\" name=\"%s\" type=\"text\" value=\"%s\" placeholder=\"%s\"/>",
                textBox.getName(), textBox.getName(), textBox.getValue(), textBox.getDefaultText()));
        htmlText.append("</label>");
        htmlText.append("</div>");
        return htmlText.toString();
    }

    @Override
    protected String getCheckBoxText(HtmlCheckBox checkBox) {
        String checked = checkBox.isChecked() ? "" : "checked=\"checked\"";
        StringBuilder htmlText = new StringBuilder("");
        htmlText.append("<div class=\"form-group\">");
        htmlText.append(String.format("<label class=\"checkbox\">%s", checkBox.getText()));
        htmlText.append(String.format("<input id=\"%s\" name=\"%s\" class=\"checkbox-item\" type=\"checkbox\" value=\"%s\" %s />",
                checkBox.getName(), checkBox.getName(), checkBox.getValue(), checked));
        htmlText.append("<i></i></label>");
        htmlText.append("</div>");
        return htmlText.toString();

    }

    @Override
    protected String getComboBoxText(HtmlComboBox comboBox) {
        if (comboBox.isAutoComplete()) {
            return this.getAutoCompleteComboBoxText(comboBox);
        }
        String multiple = comboBox.isMultipled() ? "multiple" : "";
        StringBuilder htmlText = new StringBuilder("");
        htmlText.append("<div class=\"form-group\">");
        htmlText.append(String.format("<label>%s:</label>", comboBox.getText()));
        htmlText.append(String.format("<select class=\"form-control input-sm\" id=\"%1$s\" name=\"%1$s\" %2$s>", comboBox.getName(), multiple));
        for (HtmlSelectOption option : comboBox.getValue()) {
            String selected = option.isSelected() ? "selected=\"selected\"" : "";
            htmlText.append(String.format("<option value=\"%s\" %s>%s</option>", option.getValue(), selected, option.getText()));
        }
        htmlText.append("</select>");
        htmlText.append("</div> ");
        return htmlText.toString();

    }

    @Override
    protected String getCheckboxListText(HtmlCheckBoxList checkBoxList) {
        boolean isCheckedAll = true;
        StringBuilder htmlText = new StringBuilder("");
        for (HtmlCheckBox checkBox : checkBoxList.getValue()) {
            if (!checkBox.isChecked())
                isCheckedAll = false;
            String checked = checkBox.isChecked() ? "checked=\"checked\"" : "";
            htmlText.append(String.format("<label class=\"checkbox\">%s", checkBox.getText()));
            htmlText.append(String.format("<input class=\"checkbox-item\" type=\"checkbox\" name=\"%s\" value=\"%s\" %s />",
                    checkBoxList.getName(), checkBox.getName(), checked));
            htmlText.append("<i></i></label>");
        }
        htmlText.append("<label class=\"checkbox\">全选 ");
        htmlText.append(String.format("<input type=\"checkbox\" name=\"checkAllStatColumn\" id=\"checkAllStatColumn\" %s /><i></i></label>",
                isCheckedAll ? "checked=\"checked\"" : ""));
        return htmlText.toString();
    }

    private String getAutoCompleteComboBoxText(HtmlComboBox comboBox) {
        // 如果下拉框的option在1000以内就用jquery select2 插件显示
        // 否则用bootstrap的下拉框显示
        // 因为jquery select2 在option很多时比较卡
        if (comboBox.getValue().size() <= 1000) {
            return this.getSelect2AutoCompleteComboBoxText(comboBox);
        }
        StringBuilder htmlText = new StringBuilder("");
        htmlText.append("<div class=\"form-group\">");
        htmlText.append(String.format("<label>%s:</label>", comboBox.getText()));
        htmlText.append("<label class=\"input\">");
        htmlText.append(String.format("<input type=\"text\" list=\"%1$s\" name=\"%1$s\" value=\"%2$s\">",
                comboBox.getName(), comboBox.getDefaultValue()));
        htmlText.append(String.format("<datalist id=\"%1$s\">", comboBox.getName()));
        for (HtmlSelectOption option : comboBox.getValue()) {
            htmlText.append(String.format("<option value=\"%s\">%s</option>", option.getValue(), option.getText()));
        }
        htmlText.append("</datalist>");
        htmlText.append("</label>");
        htmlText.append("</div> ");
        return htmlText.toString();
    }

    private String getSelect2AutoCompleteComboBoxText(HtmlComboBox comboBox) {
        StringBuilder htmlText = new StringBuilder("");
        htmlText.append("<div class=\"form-group\">");
        htmlText.append(String.format("<label>%s:</label>", comboBox.getText()));
        htmlText.append(String.format("<select class=\"select2AutoComplete\" id=\"%1$s\" name=\"%1$s\">", comboBox.getName()));
        for (HtmlSelectOption option : comboBox.getValue()) {
            String selected = option.isSelected() ? "selected=\"selected\"" : "";
            htmlText.append(String.format("<option value=\"%s\" %s>%s</option>", option.getValue(), selected, option.getText()));
        }
        htmlText.append("</select>");
        htmlText.append("</div> ");
        return htmlText.toString();
    }
}
