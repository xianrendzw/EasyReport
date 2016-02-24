package com.easytoolsoft.easyreport.po;

import com.easytoolsoft.easyreport.common.serializer.CustomDateTimeSerializer;
import com.easytoolsoft.easyreport.data.annotations.Column;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * 持久类
 */
public class ReportingSqlHistoryPo implements Serializable {
    /**
     * 列名id,报表SQL语句版本历史id
     */
    public final static String Id = "id";
    /**
     * 列名report_id,报表id
     */
    public final static String ReportId = "report_id";
    /**
     * 列名sql_text,报表SQL语句
     */
    public final static String SqlText = "sql_text";
    /**
     * 列名author,报表SQL语句当前版本创建者
     */
    public final static String Author = "author";
    /**
     * 列名comment,报表SQL语句版本历史说明
     */
    public final static String Comment = "comment";
    /**
     * 列名create_time,报表SQL语句版本历史创建时间
     */
    public final static String CreateTime = "create_time";
    /**
     * 列名update_time,报表SQL语句版本历史修改时间
     */
    public final static String UpdateTime = "update_time";
    private static final long serialVersionUID = 1302676883262496522L;
    /**
     * 实体reporting_sql_history名称
     */
    public static String EntityName = "reporting_sql_history";
    @Column(name = "id", isIgnored = true, isPrimaryKey = true)
    private Integer id;
    @Column(name = "report_id")
    private Integer reportId;
    @Column(name = "sql_text")
    private String sqlText;
    @Column(name = "author")
    private String author = "";
    @Column(name = "comment")
    private String comment = "";
    @Column(name = "create_time")
    private Date createTime = Calendar.getInstance().getTime();
    @Column(name = "update_time")
    private Date updateTime = Calendar.getInstance().getTime();

    /**
     * 获取报表SQL语句版本历史id
     *
     * @return 报表SQL语句版本历史id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置报表SQL语句版本历史id
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取报表id
     *
     * @return 报表id
     */
    public Integer getReportId() {
        return this.reportId;
    }

    /**
     * 设置报表id
     *
     * @param reportId
     */
    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    /**
     * 获取报表SQL语句
     *
     * @return 报表SQL语句
     */
    public String getSqlText() {
        return this.sqlText;
    }

    /**
     * 设置报表SQL语句
     *
     * @param sqlText
     */
    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    /**
     * 获取报表SQL语句当前版本创建者
     *
     * @return 报表SQL语句当前版本创建者
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * 设置报表SQL语句当前版本创建者
     *
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 获取报表SQL语句版本历史说明
     *
     * @return 报表SQL语句版本历史说明
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * 设置报表SQL语句版本历史说明
     *
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 获取报表SQL语句版本历史创建时间
     *
     * @return 报表SQL语句版本历史创建时间
     */
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getCreateTime() {
        return createTime == null ? Calendar.getInstance().getTime() : createTime;
    }

    /**
     * 设置报表SQL语句版本历史创建时间
     *
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取报表SQL语句版本历史修改时间
     *
     * @return 报表SQL语句版本历史修改时间
     */
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getUpdateTime() {
        return updateTime == null ? Calendar.getInstance().getTime() : updateTime;
    }

    /**
     * 设置报表SQL语句版本历史修改时间
     *
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
