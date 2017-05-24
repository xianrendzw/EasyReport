package com.easytoolsoft.easyreport.meta.form;

import java.util.List;
import java.util.Map;

import com.easytoolsoft.easyreport.common.pair.NameTextPair;
import com.easytoolsoft.easyreport.meta.form.control.HtmlFormElement;

/**
 * 报表的查询参数表单视图接口
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface QueryParamFormView {

    List<NameTextPair> getTextList(List<HtmlFormElement> formElements);

    Map<String, String> getTextMap(List<HtmlFormElement> formElements);

    String getFormHtmlText(HtmlFormElement formElement);

    String getFormHtmlText(List<HtmlFormElement> formElements);
}
