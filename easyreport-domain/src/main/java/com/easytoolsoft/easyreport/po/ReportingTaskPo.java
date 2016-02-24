package com.easytoolsoft.easyreport.po;

import com.easytoolsoft.easyreport.common.serializer.CustomDateTimeSerializer;
import com.easytoolsoft.easyreport.data.annotations.Column;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * 报表任务信息表持久类
 */
public class ReportingTaskPo implements Serializable {
    /**
     * 列名id,报表任务ID
     */
    public final static String Id = "id";
    /**
     * 列名report_ids,报表ID列表
     */
    public final static String ReportIds = "report_ids";
    /**
     * 列名peroid,报表任务执行频率(取值：y每年|M每月|w每周|d每天|h每小时|m每分|s每秒)
     */
    public final static String Peroid = "peroid";
    /**
     * 列名interval,报表任务执行间隔
     */
    public final static String Interval = "interval";
    /**
     * 列名time,报表任务执行时间,指定任务什么时间执行
     */
    public final static String Time = "time";
    /**
     * 列名comment,报表任务说明
     */
    public final static String Comment = "comment";
    /**
     * 列名create_time,报表任务记录创建时间
     */
    public final static String CreateTime = "create_time";
    /**
     * 列名update_time,报表任务记录修改时间
     */
    public final static String UpdateTime = "update_time";
    private static final long serialVersionUID = -6522318674324444247L;
    /**
     * 实体reporting_task名称
     */
    public static String EntityName = "reporting_task";
    @Column(name = "id", isIgnored = true, isPrimaryKey = true)
    private Integer id;
    @Column(name = "report_ids")
    private String reportIds;
    @Column(name = "peroid")
    private String peroid;
    @Column(name = "interval")
    private Integer interval;
    @Column(name = "time")
    private String time;
    @Column(name = "comment")
    private String comment;
    @Column(name = "create_time")
    private Date createTime = Calendar.getInstance().getTime();
    @Column(name = "update_time")
    private Date updateTime = Calendar.getInstance().getTime();

    /**
     * 获取报表任务ID
     *
     * @return 报表任务ID
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置报表任务ID
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取报表ID列表
     *
     * @return 报表ID列表
     */
    public String getReportIds() {
        return this.reportIds;
    }

    /**
     * 设置报表ID列表
     *
     * @param reportIds
     */
    public void setReportIds(String reportIds) {
        this.reportIds = reportIds;
    }

    /**
     * 获取报表任务执行频率(取值：y每年|M每月|w每周|d每天|h每小时|m每分|s每秒)
     *
     * @return 报表任务执行频率(取值：y每年|M每月|w每周|d每天|h每小时|m每分|s每秒)
     */
    public String getPeroid() {
        return this.peroid;
    }

    /**
     * 设置报表任务执行频率(取值：y每年|M每月|w每周|d每天|h每小时|m每分|s每秒)
     *
     * @param peroid
     */
    public void setPeroid(String peroid) {
        this.peroid = peroid;
    }

    /**
     * 获取报表任务执行间隔
     *
     * @return 报表任务执行间隔
     */
    public Integer getInterval() {
        return this.interval;
    }

    /**
     * 设置报表任务执行间隔
     *
     * @param interval
     */
    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    /**
     * 获取报表任务执行时间,指定任务什么时间执行
     *
     * @return 报表任务执行时间, 指定任务什么时间执行
     */
    public String getTime() {
        return this.time;
    }

    /**
     * 设置报表任务执行时间,指定任务什么时间执行
     *
     * @param time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 获取报表任务说明
     *
     * @return 报表任务说明
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * 设置报表任务说明
     *
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 获取报表任务记录创建时间
     *
     * @return 报表任务记录创建时间
     */
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getCreateTime() {
        return createTime == null ? Calendar.getInstance().getTime() : createTime;
    }

    /**
     * 设置报表任务记录创建时间
     *
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取报表任务记录修改时间
     *
     * @return 报表任务记录修改时间
     */
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getUpdateTime() {
        return updateTime == null ? Calendar.getInstance().getTime() : updateTime;
    }

    /**
     * 设置报表任务记录修改时间
     *
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
