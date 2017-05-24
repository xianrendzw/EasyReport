package com.easytoolsoft.easyreport.meta.domain.options;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 报表查询参数类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public class QueryParameterOptions implements Serializable {
    private static final long serialVersionUID = 7975880105664108114L;
    private String name;
    private String text;
    private String formElement;
    private String content;
    private String defaultValue;
    private String defaultText;
    private String dataSource;
    private String dataType = "string";
    private String comment;
    private int width = 100;
    private int height = 25;
    private boolean isRequired;
    private boolean isAutoComplete;

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
            return "NoTitle";
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
     * @return html表单input元素(select, text等)
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
     * 获取报表查询参数对应的内容（sql语句|文本字符|空)
     *
     * @return 报表查询参数对应的内容（sql语句|文本字符|空)
     */
    public String getContent() {
        return this.content == null ? "" : this.content.trim();
    }

    /**
     * 设置报表查询参数对应的内容（sql语句|文本字符|空)
     *
     * @param content t报表查询参数对应的内容（sql语句|文本字符|空)
     */
    public void setContent(final String content) {
        this.content = content;
    }

    /**
     * 获取报表查询参数对应的默认值
     *
     * @return 报表查询参数对应的默认值
     */
    public String getDefaultValue() {
        return (this.defaultValue == null || this.defaultValue.trim().length() == 0)
            ? "noDefaultValue" : this.defaultValue.trim();
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
            ? "noDefaultText" : this.defaultText.trim();
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
     * 获取报表查询参数的数据来源
     * 取值 为[sql:根据sql语句查询得出,text:从文本内容得出,none:无数据来源]
     *
     * @return sql|text|none
     */
    public String getDataSource() {
        return this.dataSource;
    }

    /**
     * 设置报表查询参数数据来源
     *
     * @param dataSource 取值 为[sql:根据sql语句查询得出,text:从文本内容得出,none:无数据来源]
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取表查询参数的数据类型(string|float|integer|date)，默认是string
     *
     * @return
     */
    public String getDataType() {
        return (this.dataType == null || this.dataType.trim().length() == 0) ? "string" : this.dataType;
    }

    /**
     * 获取报表查询参数的数据类型(string|float|integer|date)，默认是string
     *
     * @param dataType(string|float|integer|date)
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * 获取报表查询参数备注
     *
     * @return
     */
    public String getComment() {
        return this.comment == null ? "" : this.comment;
    }

    /**
     * 设置报表查询参数备注
     *
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 获取报表查询参数的数据来源
     *
     * @return 宽度的像素值, 默认为100
     */
    public int getWidth() {
        return width;
    }

    /**
     * 设置报表查询参数表单控件的宽度(单位：像素)
     *
     * @param width 宽度的像素值
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * 获取报表查询参数表单控件的高度(单位：像素)
     *
     * @return 高度的像素值, 默认为25
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * 设置报表查询参数表单控件的高度(单位：像素)
     *
     * @param height 高度的像素
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
     * @param isRequired true|false
     */
    public void setRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    /**
     * 获取报表查询参数的是否自动提示(主要用于下拉列表控件中)
     *
     * @return true|false
     */
    public boolean isAutoComplete() {
        return this.isAutoComplete;
    }

    /**
     * 设置报表查询参数的是否自动提示(主要用于下拉列表控件中)
     *
     * @param isAutoComplete true|false
     */
    public void setAutoComplete(boolean isAutoComplete) {
        this.isAutoComplete = isAutoComplete;
    }

    /**
     * 获取当前查询参数是否设置了默认值
     *
     * @return true|false
     */
    @JsonIgnore
    public boolean hasDefaultValue() {
        return !"noDefaultValue".equals(this.getDefaultValue());
    }

    /**
     * 获取当前查询参数是否设置了默认标题
     *
     * @return true|false
     */
    @JsonIgnore
    public boolean hasDefaultText() {
        return !"noDefaultText".equals(this.getDefaultText());
    }

    /**
     * 获取当前查询参数的真实默认标题
     *
     * @return
     */
    @JsonIgnore
    public String getRealDefaultText() {
        return this.hasDefaultText() ? this.getDefaultText() : "";
    }

    /**
     * 获取当前查询参数的真实默认值
     *
     * @return
     */
    @JsonIgnore
    public String getRealDefaultValue() {
        return this.hasDefaultValue() ? this.getDefaultValue() : "";
    }
}
