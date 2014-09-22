package org.easyframework.report.entity;

import java.io.Serializable;

/**
 * 报表查询参数类
 */
public class ReportingQueryParam implements Serializable {
	private static final long serialVersionUID = 7975880105664108114L;
	private String name;
	private String text;
	private String formElement;
	private String sqlText;
	private String defaultValue;
	private String defaultText;
	private int optionType;

	/**
	 * 获取报表查询参数名称
	 * 
	 * @return 报表查询参数名称
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置报表查询参数名称
	 * 
	 * @param name 参数名称
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * 获取报表查询参数对应的标题(中文名)
	 * 
	 * @return 报表查询参数对应的标题(中文名)
	 */
	public String getText() {
		if (this.text == null || this.text.trim().length() == 0) {
			return this.name;
		}
		return this.text.trim();
	}

	/**
	 * 设置报表查询参数对应的标题(中文名)
	 * 
	 * @param text 报表查询参数名称对应的标题(中文名)
	 */
	public void setText(final String text) {
		this.text = text;
	}

	/**
	 * 获取报表查询参数对应的html表单input元素
	 * 
	 * @return html表单input元素(select,text等)
	 */
	public String getFormElement() {
		return this.formElement == null ? "" : this.formElement.trim();
	}

	/**
	 * 设置报表查询参数对应的html表单input元素
	 * 
	 * @param formElement html表单input元素(select,text等)
	 */
	public void setFormElement(String formElement) {
		this.formElement = formElement;
	}

	/**
	 * 获报表查询参数对应的sql查询语句
	 * 
	 * @return 报表查询参数对应的sql查询语句
	 */
	public String getSqlText() {
		return this.sqlText == null ? "" : this.sqlText.trim();
	}

	/**
	 * 设置报表查询参数对应的sql查询语句
	 * 
	 * @param sqlText 报表查询参数对应的sql查询语句
	 */
	public void setSqlText(final String sqlText) {
		this.sqlText = sqlText;
	}

	/**
	 * 获取报表查询参数对应的默认值
	 * 
	 * @return 报表查询参数对应的默认值
	 */
	public String getDefaultValue() {
		return (this.defaultValue == null || this.defaultValue.trim().length() == 0)
				? "" : this.defaultValue.trim();
	}

	/**
	 * 设置报表查询参数对应的默认值
	 * 
	 * @param defaultValue 报表查询参数对应的默认值
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * 获取报表查询参数的默认值对应的标题
	 * 
	 * @return 报表查询参数的默认值对应的标题
	 */
	public String getDefaultText() {
		return (this.defaultText == null || this.defaultText.trim().length() == 0)
				? "全部" : this.defaultText.trim();
	}

	/**
	 * 设置报表查询参数的默认值对应的标题
	 * 
	 * @param defaultText 报表查询参数的默认值对应的标题
	 */
	public void setDefaultText(String defaultText) {
		this.defaultText = defaultText;
	}

	/**
	 * 获取报表查询参数下拉控件中选项数据来源类型
	 * 
	 * @return 0表示从sql语句中查询得出,1表示从文本内容得出,2表示无内容
	 */
	public int getOptionType() {
		return optionType;
	}

	/**
	 * 设置报表查询参数下拉控件中选项数据来源类型
	 * 
	 * @param optionType 0表示从sql语句中查询得出,1表示从文本内容得出,2表示无内容
	 */
	public void setOptionType(int optionType) {
		this.optionType = optionType;
	}
}
