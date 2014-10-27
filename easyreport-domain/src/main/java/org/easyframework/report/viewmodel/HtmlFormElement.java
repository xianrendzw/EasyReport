package org.easyframework.report.viewmodel;

public abstract class HtmlFormElement {
	protected final String name;
	protected final String text;
	protected String type;
	protected String dataType = "string";
	protected int width = 100;
	protected int height = 25;
	protected boolean isRequired;

	protected HtmlFormElement(String name, String text) {
		this.name = name;
		this.text = text;
	}

	/**
	 * 获取报表查询参数名称
	 * 
	 * @return 报表查询参数名称
	 */
	public String getName() {
		return this.name;
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
	 * 获取报表查询参数对应的控件类型
	 * 
	 * @return text|select|checkbox|datebox|checkboxlist
	 */
	public String getType() {
		return type;
	}

	/**
	 * 获取报表查询参数表单控件的宽度(单位：像素)
	 * 
	 * @return
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * 获取报表查询参数的数据来源
	 * 
	 * @param dataType
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * 获取报表查询参数的数据来源
	 * 
	 * @return 宽度的像素值,默认为100
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * 设置报表查询参数表单控件的宽度(单位：像素)
	 * 
	 * @param width
	 *            宽度的像素值
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * 获取报表查询参数表单控件的高度(单位：像素)
	 * 
	 * @return 高度的像素值,默认为25
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * 设置报表查询参数表单控件的高度(单位：像素)
	 * 
	 * @param height
	 *            高度的像素
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * 获取报表查询参数的是否必选
	 * 
	 * @return
	 */
	public boolean isRequired() {
		return this.isRequired;
	}

	/**
	 * 设置报表查询参数是否必选
	 * 
	 * @param isRequired
	 *            true|false
	 */
	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

}
