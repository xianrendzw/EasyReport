package com.easytoolsoft.easyreport.engine.data;

/**
 * 报表元数据列类
 */
public class ReportMetaDataColumn {
    private int ordinal;
    private String name;
    private String text;
    private String dataType;
    private String expression;
    private String format;
    private String comment;
    private int width;
    private int decimals;
    private ColumnType type = ColumnType.DIMENSION;
    private ColumnSortType sortType = ColumnSortType.DEFAULT;
    private boolean isExtensions;
    private boolean isFootings;
    private boolean isPercent;
    private boolean isOptional;
    private boolean isDisplayInMail;
    private boolean isHidden;

    public ReportMetaDataColumn() {
    }

    public ReportMetaDataColumn(String name, String text, ColumnType type) {
        this.name = name;
        this.text = text;
        this.type = type;
    }

    /**
     * 获取报表元数据列的顺序(从1开始)
     *
     * @return 报表元数据列的顺序
     */
    public int getOrdinal() {
        return this.ordinal;
    }

    /**
     * 设置报表元数据列的顺序
     *
     * @return 报表元数据列的顺序(从1开始)
     */
    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    /**
     * 获取报表元数据列名
     *
     * @return 元数据列名
     */
    public String getName() {
        return this.name == null ? "" : this.name.trim().toLowerCase();
    }

    /**
     * 设置报表元数据列名
     *
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * 获取报表元数据列对应的标题文本
     *
     * @return 报表元数据列对应的标题文本
     */
    public String getText() {
        if (this.text == null || this.text.trim().length() == 0) {
            return this.name;
        }
        return this.text;
    }

    /**
     * 设置报表元数据列对应的标题文本
     *
     * @param text
     */
    public void setText(final String text) {
        this.text = text;
    }

    /**
     * 获取报表元数据列数据类型名称
     *
     * @return dataType java.sql.Types中的类型名称
     */
    public String getDataType() {
        return this.dataType;
    }

    /**
     * 设置报表元数据列数据类型名称
     *
     * @param dataType java.sql.Types中的类型名称
     */
    public void setDataType(final String dataType) {
        this.dataType = dataType;
    }

    /**
     * 获取报表元数据计算列的计算表达式
     *
     * @return 计算表达式
     */
    public String getExpression() {
        return this.expression == null ? "" : this.expression;
    }

    /**
     * 设置报表元数据计算列的计算表达式
     *
     * @param expression 计算表达式
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }

    /**
     * 获取报表元数据列的格式 .String.format(format,text)
     *
     * @return 格式化字符串
     */
    public String getFormat() {
        return format == null ? "" : this.format;
    }

    /**
     * 设置报表元数据列的格式
     *
     * @param format 格式化字符串
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * 获取报表元数据列宽(单位:像素)
     *
     * @return 列宽(单位:像素)
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * 设置报表元数据列宽(单位:像素)
     *
     * @param width
     */
    public void setWidth(final int width) {
        this.width = width;
    }

    /**
     * 获取报表元数据列的精度（即保留多少位小数,默认浮点数为4位，百分比为2位，其他为0)
     *
     * @return 小数精度, 默认浮点数为4位，百分比为2位
     */
    public int getDecimals() {
        return this.decimals;
    }

    /**
     * 设置报表元数据列的精度（即保留多少位小数,默认浮点数为4位，百分比为2位，其他为0)
     *
     * @param decimals 小数精度,默认浮点数为4位，百分比为2位，其他为0
     */
    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

    /**
     * 获取报表元数据列类型
     *
     * @return 列类型(1：布局列, 2:维度列，3:统计列, 4:计算列)
     */
    public ColumnType getType() {
        return this.type;
    }

    /**
     * 设置报表元数据列类型
     *
     * @param type (1：布局列,2:维度列，3:统计列,4:计算列)
     */
    public void setType(final int type) {
        this.type = ColumnType.valueOf(type);
    }

    /**
     * 设置列排序类型(0:默认,1：数字优先升序,2:数字优先降序,3：字符优先升序,4:字符优先降序)
     */
    public ColumnSortType getSortType() {
        return this.sortType;
    }

    /**
     * 设置列排序类型
     *
     * @param sortType (0:默认,1：数字优先升序,2:数字优先降序,3：字符优先升序,4:字符优先降序)
     */
    public void setSortType(int sortType) {
        this.sortType = ColumnSortType.valueOf(sortType);
    }

    /**
     * 获取元数据列是否支持小计
     *
     * @return true|false
     */
    public boolean isExtensions() {
        return this.isExtensions;
    }

    /**
     * 设置元数据列是否支持小计
     *
     * @param isExtensions
     */
    public void setExtensions(final boolean isExtensions) {
        this.isExtensions = isExtensions;
    }

    /**
     * 获取元数据列是否支持条件查询
     *
     * @return true|false
     */
    public boolean isFootings() {
        return this.isFootings;
    }

    /**
     * 设置元数据列是否支持合计
     *
     * @param isFootings
     */
    public void setFootings(final boolean isFootings) {
        this.isFootings = isFootings;
    }

    /**
     * 获取元数据列是否支持百分比显示
     *
     * @return true|false
     */
    public boolean isPercent() {
        return this.isPercent;
    }

    /**
     * 设置元数据列是否支持百分比显示
     *
     * @param isPercent
     */
    public void setPercent(boolean isPercent) {
        this.isPercent = isPercent;
    }

    /**
     * 获取配置列是否支持可选择显示
     *
     * @return true|false
     */
    public boolean isOptional() {
        return this.isOptional;
    }

    /**
     * 设置配置列是否支持可选择显示
     *
     * @param isOptional
     */
    public void setOptional(boolean isOptional) {
        this.isOptional = isOptional;
    }

    /**
     * 获取配置列中的统计列(含计算)是否支持在邮件中显示
     *
     * @return true|false
     */
    public boolean isDisplayInMail() {
        return this.isDisplayInMail;
    }

    /**
     * 设置配置列中的统计列(含计算)是否支持在邮件中显示
     *
     * @param isDisplayInMail
     */
    public void setDisplayInMail(boolean isDisplayInMail) {
        this.isDisplayInMail = isDisplayInMail;
    }

    /**
     * 获取元数据列是否隐藏
     *
     * @return true|false
     */
    public boolean isHidden() {
        return this.isHidden;
    }

    /**
     * 设置元数据列是否隐藏
     *
     * @param isHidden
     */
    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    /**
     * 获取元数据列备注
     *
     * @return
     */
    public String getComment() {
        return this.comment == null ? "" : this.comment;
    }

    /**
     * 设置元数据列备注
     *
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    public ReportMetaDataColumn copyToNew() {
        return this.copyToNew(this.name, this.text);
    }

    public ReportMetaDataColumn copyToNew(String name, String text) {
        return this.copyToNew(name, text, this.isPercent);
    }

    public ReportMetaDataColumn copyToNew(String name, String text, boolean isPercent) {
        ReportMetaDataColumn column = new ReportMetaDataColumn();
        column.setName(name);
        column.setText(text);
        column.setPercent(isPercent);
        column.setDataType(this.dataType);
        column.setExtensions(this.isExtensions);
        column.setFootings(this.isFootings);
        column.setHidden(this.isHidden);
        column.setSortType(this.sortType.getValue());
        column.setType(this.type.getValue());
        column.setWidth(this.width);
        column.setDecimals(this.decimals);
        column.setOptional(this.isOptional);
        column.setDisplayInMail(this.isDisplayInMail);
        column.setComment(this.comment);
        return column;
    }
}