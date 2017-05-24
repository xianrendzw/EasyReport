package com.easytoolsoft.easyreport.meta.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 报表分类(_rpt_category表)持久化类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Category implements Serializable {
    /**
     * 报表ID
     */
    private Integer id;
    /**
     * 父分类
     */
    private Integer parentId;
    /**
     * 名称
     */
    private String name;
    /**
     * 树型结构路径从根id到当前id的路径
     */
    private String path;
    /**
     * 是否为子类别1为是，0为否
     */
    private Byte hasChild;
    /**
     * 状态（1表示启用，0表示禁用，默认为0)
     */
    private Integer status;
    /**
     * 节点在其父节点中的顺序
     */
    private Integer sequence;
    /**
     * 说明备注
     */
    private String comment;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改时间
     */
    private Date gmtModified;
}
