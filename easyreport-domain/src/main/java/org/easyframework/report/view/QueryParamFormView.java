package org.easyframework.report.view;

import java.util.List;
import java.util.Map;

import org.easyframework.report.common.viewmodel.NameTextPair;
import org.easyframework.report.viewmodel.HtmlFormElement;

/**
 * 
 * 报表的查询参数表单视图接口
 *
 */
interface QueryParamFormView {

	List<NameTextPair> getTextList(List<HtmlFormElement> formElements);

	Map<String, String> getTextMap(List<HtmlFormElement> formElements);

	String getFormHtmlText(HtmlFormElement formElement);

	String getFormHtmlText(List<HtmlFormElement> formElements);
}
