package com.easytoolsoft.easyreport.meta.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easytoolsoft.easyreport.common.pair.NameTextPair;
import com.easytoolsoft.easyreport.meta.form.control.HtmlCheckBox;
import com.easytoolsoft.easyreport.meta.form.control.HtmlCheckBoxList;
import com.easytoolsoft.easyreport.meta.form.control.HtmlComboBox;
import com.easytoolsoft.easyreport.meta.form.control.HtmlDateBox;
import com.easytoolsoft.easyreport.meta.form.control.HtmlFormElement;
import com.easytoolsoft.easyreport.meta.form.control.HtmlTextBox;

/**
 * 报表的查询参数表单视图基类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public abstract class AbstractQueryParamFormView {

    public List<NameTextPair> getTextList(final List<HtmlFormElement> formElements) {
        if (formElements == null || formElements.size() == 0) {
            return new ArrayList<>(0);
        }

        final List<NameTextPair> list = new ArrayList<>(formElements.size());
        for (final HtmlFormElement element : formElements) {
            final NameTextPair nameTextPair = this.createNameTextPair(element);
            if (nameTextPair != null) {
                list.add(nameTextPair);
            }
        }
        return list;
    }

    public Map<String, String> getTextMap(final List<HtmlFormElement> formElements) {
        if (formElements == null || formElements.size() == 0) {
            return new HashMap<>(0);
        }

        final Map<String, String> textMap = new HashMap<>(formElements.size());
        for (final HtmlFormElement element : formElements) {
            final NameTextPair nameTextPair = this.createNameTextPair(element);
            if (nameTextPair != null) {
                textMap.put(nameTextPair.getName(), nameTextPair.getText());
            }
        }
        return textMap;
    }

    public String getFormHtmlText(final HtmlFormElement formElement) {
        final List<HtmlFormElement> formElements = new ArrayList<>(1);
        formElements.add(formElement);
        return this.getFormHtmlText(formElements);
    }

    public String getFormHtmlText(final List<HtmlFormElement> formElements) {
        final List<NameTextPair> list = this.getTextList(formElements);
        final int count = list.size();
        final StringBuilder htmlTextBuilder = new StringBuilder();
        for (int index = 1; index <= count; index++) {
            htmlTextBuilder.append(list.get(index - 1).getText());
        }
        return htmlTextBuilder.toString();
    }

    protected NameTextPair createNameTextPair(final HtmlFormElement element) {
        if (element == null) {
            return null;
        }

        if ("datebox".equals(element.getType())) {
            return new NameTextPair(element.getName(), this.getDateBoxText((HtmlDateBox)element));
        }
        if ("textbox".equals(element.getType())) {
            return new NameTextPair(element.getName(), this.getTexBoxText((HtmlTextBox)element));
        }
        if ("checkbox".equals(element.getType())) {
            return new NameTextPair(element.getName(), this.getCheckBoxText((HtmlCheckBox)element));
        }
        if ("combobox".equals(element.getType())) {
            return new NameTextPair(element.getName(), this.getComboBoxText((HtmlComboBox)element));
        }
        if ("checkboxlist".equals(element.getType())) {
            return new NameTextPair(element.getName(), this.getCheckboxListText((HtmlCheckBoxList)element));
        }
        return null;
    }

    protected abstract String getDateBoxText(HtmlDateBox dateBox);

    protected abstract String getTexBoxText(HtmlTextBox textBox);

    protected abstract String getCheckBoxText(HtmlCheckBox checkBox);

    protected abstract String getComboBoxText(HtmlComboBox comboBox);

    protected abstract String getCheckboxListText(HtmlCheckBoxList checkBoxList);
}
