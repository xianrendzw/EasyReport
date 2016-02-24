package com.easytoolsoft.easyreport.view;

import com.easytoolsoft.easyreport.common.viewmodel.NameTextPair;
import com.easytoolsoft.easyreport.viewmodel.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表的查询参数表单视图基类
 */
public abstract class AbstractQueryParamFormView {

    public List<NameTextPair> getTextList(List<HtmlFormElement> formElements) {
        if (formElements == null || formElements.size() == 0) {
            return new ArrayList<>(0);
        }

        List<NameTextPair> list = new ArrayList<>(formElements.size());
        for (HtmlFormElement element : formElements) {
            NameTextPair nameTextPair = this.createNameTextPair(element);
            if (nameTextPair != null)
                list.add(nameTextPair);
        }
        return list;
    }

    public Map<String, String> getTextMap(List<HtmlFormElement> formElements) {
        if (formElements == null || formElements.size() == 0) {
            return new HashMap<String, String>(0);
        }

        Map<String, String> textMap = new HashMap<String, String>(formElements.size());
        for (HtmlFormElement element : formElements) {
            NameTextPair nameTextPair = this.createNameTextPair(element);
            if (nameTextPair != null)
                textMap.put(nameTextPair.getName(), nameTextPair.getText());
        }
        return textMap;
    }

    public String getFormHtmlText(HtmlFormElement formElement) {
        List<HtmlFormElement> formElements = new ArrayList<HtmlFormElement>(1);
        formElements.add(formElement);
        return this.getFormHtmlText(formElements);
    }

    public String getFormHtmlText(List<HtmlFormElement> formElements) {
        List<NameTextPair> list = this.getTextList(formElements);
        // int pageSize = 3;
        int count = list.size();
        StringBuilder htmlTextBuilder = new StringBuilder();
        for (int index = 1; index <= count; index++) {
            htmlTextBuilder.append(list.get(index - 1).getText());
            // htmlTextBuilder.append((index % pageSize == 0) ? "<br/><br/>" :
            // "&nbsp;&nbsp;");
        }
        return htmlTextBuilder.toString();
    }

    protected NameTextPair createNameTextPair(HtmlFormElement element) {
        if (element == null) {
            return null;
        }

        if ("datebox".equals(element.getType())) {
            return new NameTextPair(element.getName(), this.getDateBoxText((HtmlDateBox) element));
        }
        if ("textbox".equals(element.getType())) {
            return new NameTextPair(element.getName(), this.getTexBoxText((HtmlTextBox) element));
        }
        if ("checkbox".equals(element.getType())) {
            return new NameTextPair(element.getName(), this.getCheckBoxText((HtmlCheckBox) element));
        }
        if ("combobox".equals(element.getType())) {
            return new NameTextPair(element.getName(), this.getComboBoxText((HtmlComboBox) element));
        }
        if ("checkboxlist".equals(element.getType())) {
            return new NameTextPair(element.getName(), this.getCheckboxListText((HtmlCheckBoxList) element));
        }
        return null;
    }

    protected abstract String getDateBoxText(HtmlDateBox dateBox);

    protected abstract String getTexBoxText(HtmlTextBox textBox);

    protected abstract String getCheckBoxText(HtmlCheckBox checkBox);

    protected abstract String getComboBoxText(HtmlComboBox comboBox);

    protected abstract String getCheckboxListText(HtmlCheckBoxList checkBoxList);
}
