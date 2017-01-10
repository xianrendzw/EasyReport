package com.easytoolsoft.easyreport.domain.metadata.po;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 报表历史记录(ezrpt_meta_report_history表)持久化类
 *
 * @author Tom Deng
 */
@SuppressWarnings("serial")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportHistory implements Serializable {
    /**
     * 报表历史记录id
     */
    private Integer id;
    /**
     * 报表ID
     */
    private Integer reportId;
    /**
     *
     */
    private String uid;
    /**
     * 报表分类id
     */
    private Integer categoryId;
    /**
     * 数据源ID
     */
    private Integer dsId;
    /**
     * 报表名称
     */
    private String name;
    /**
     * 报表SQL语句
     */
    private String sqlText;
    /**
     * 报表列集合元数据(JSON格式)
     */
    private String metaColumns;
    /**
     * 查询条件列属性集合(JSON格式)
     */
    private String queryParams;
    /**
     * 报表配置选项(JSON格式)
     */
    private String options;
    /**
     * 报表状态（1表示锁定，0表示编辑)
     */
    private Integer status;
    /**
     * 报表节点在其父节点中的顺序
     */
    private Integer sequence;
    /**
     * 说明备注
     */
    private String comment;
    /**
     * 创建用户
     */
    private String author;
    /**
     * 记录创建时间
     */
    private Date gmtCreated;
    /**
     * 记录修改时间
     */
    private Date gmtModified;
}
