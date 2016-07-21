package com.easytoolsoft.easyreport.common.form;

import com.easytoolsoft.easyreport.common.form.control.HtmlFormElement;
import com.easytoolsoft.easyreport.common.pair.NameTextPair;

import java.util.List;
import java.util.Map;

/**
 * 报表的查询参数表单视图接口
 */
public interface QueryParamFormView {

    List<NameTextPair> getTextList(List<HtmlFormElement> formElements);

    Map<String, String> getTextMap(List<HtmlFormElement> formElements);

    String getFormHtmlText(HtmlFormElement formElement);

    String getFormHtmlText(List<HtmlFormElement> formElements);
}
