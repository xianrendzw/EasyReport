package com.easytoolsoft.easyreport.meta.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 报表任务(_rpt_task)持久化类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Task implements Serializable {
    /**
     * 报表任务ID
     */
    private Integer id;
    /**
     * 报表ID列表
     */
    private String reportIds;
    /**
     * 报表任务调度crontab表达式
     */
    private String cronExpr;
    /**
     * 任务类型,1为邮件任务,2为手机短信任务,其他保留.默认为1
     */
    private Byte type;
    /**
     * 配置选项(JSON格式)
     */
    private String options;
    /**
     * 任务内容模板
     */
    private String template;
    /**
     * 报表任务说明
     */
    private String comment;
    /**
     * 报表任务记录创建时间
     */
    private Date gmtCreated;
    /**
     * 报表任务记录修改时间
     */
    private Date gmtModified;
}
