package com.easytoolsoft.easyreport.meta.form.control;

/**
 * @author Tom Deng
 * @date 2017-03-25
 **/
public abstract class HtmlFormElement {
    protected final String name;
    protected final String text;
    protected String type;
    protected String dataType = "string";
    protected String comment;
    protected int width = 100;
    protected int height = 25;
    protected boolean isRequired;
    private String defaultText;
    private String defaultValue;

    protected HtmlFormElement(final String name, final String text) {
        this.name = name;
        this.text = text;
    }

    /**
     * 获取Html表单(Form)元素名称
     *
     * @return Html表单(Form)元素名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 获取Html表单(Form)元素对应的标题(中文名)
     *
     * @return Html表单(Form)元素对应的标题(中文名)
     */
    public String getText() {
        if (this.text == null || this.text.trim().length() == 0) {
            return this.name;
        }
        return this.text.trim();
    }

    /**
     * 获取Html表单(Form)元素对应的控件类型
     *
     * @return text|select|checkbox|datebox|checkboxlist
     */
    public String getType() {
        return this.type;
    }

    /**
     * 获取Html表单(Form)元素表单控件的宽度(单位：像素)
     *
     * @return
     */
    public String getDataType() {
        return this.dataType;
    }

    /**
     * 获取Html表单(Form)元素的数据来源
     *
     * @param dataType
     */
    public void setDataType(final String dataType) {
        this.dataType = dataType;
    }

    /**
     * 获取Html表单(Form)元素的备注
     *
     * @return
     */
    public String getComment() {
        return this.comment == null ? "" : this.comment;
    }

    /**
     * 设置Html表单(Form)元素的备注
     *
     * @param comment
     */
    public void setComment(final String comment) {
        this.comment = comment;
    }

    /**
     * 获取Html表单(Form)元素的数据来源
     *
     * @return 宽度的像素值, 默认为100
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * 设置Html表单(Form)元素表单控件的宽度(单位：像素)
     *
     * @param width 宽度的像素值
     */
    public void setWidth(final int width) {
        this.width = width;
    }

    /**
     * 获取Html表单(Form)元素表单控件的高度(单位：像素)
     *
     * @return 高度的像素值, 默认为25
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * 设置Html表单(Form)元素表单控件的高度(单位：像素)
     *
     * @param height 高度的像素
     */
    public void setHeight(final int height) {
        this.height = height;
    }

    /**
     * 获取Html表单(Form)元素的是否必选
     *
     * @return
     */
    public boolean isRequired() {
        return this.isRequired;
    }

    /**
     * 设置Html表单(Form)元素是否必选
     *
     * @param isRequired true|false
     */
    public void setRequired(final boolean isRequired) {
        this.isRequired = isRequired;
    }

    /**
     * 获取Html表单(Form)元素对应的默认值
     *
     * @return Html表单(Form)元素对应的默认值
     */
    public String getDefaultValue() {
        return this.defaultValue;
    }

    /**
     * 设置Html表单(Form)元素对应的默认值
     *
     * @param defaultValue Html表单(Form)元素对应的默认值
     */
    public void setDefaultValue(final String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * 获取Html表单(Form)元素的默认值对应的标题
     *
     * @return Html表单(Form)元素的默认值对应的标题
     */
    public String getDefaultText() {
        return this.defaultText;
    }

    /**
     * 设置Html表单(Form)元素的默认值对应的标题
     *
     * @param defaultText Html表单(Form)元素的默认值对应的标题
     */
    public void setDefaultText(final String defaultText) {
        this.defaultText = defaultText;
    }
}
