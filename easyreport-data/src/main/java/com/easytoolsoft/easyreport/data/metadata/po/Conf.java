package com.easytoolsoft.easyreport.data.metadata.po;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 报表配置项(ezrpt_meta_conf表)持久化类
 *
 * @author Tom Deng
 */
@SuppressWarnings("serial")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Conf implements Serializable {
    /**
     * 数据源ID
     */
    private Integer id;
    /**
     * 父ID
     */
    private Integer parentId;
    /**
     * 名称
     */
    private String name;
    /**
     * 配置key
     */
    private String key;
    /**
     * 配置值
     */
    private String value;
    /**
     * 显示顺序
     */
    private Integer sequence;
    /**
     * 配置说明
     */
    private String comment;
    /**
     * 记录创建时间
     */
    private Date gmtCreated;
    /**
     * 记录修改时间
     */
    private Date gmtModified;
    /**
     * 是否有子配置项
     */
    private boolean hasChild;
}
