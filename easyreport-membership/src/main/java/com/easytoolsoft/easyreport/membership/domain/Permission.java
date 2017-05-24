package com.easytoolsoft.easyreport.membership.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统权限(easyreport_member_permission表)持久化类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Permission implements Serializable {
    /**
     * 系统操作标识
     */
    private Integer id;
    /**
     * 系统模块标识
     */
    private Integer moduleId;
    /**
     * 系统操作名称
     */
    private String name;
    /**
     * 系统操作唯一代号
     */
    private String code;
    /**
     * 系统操作的排序顺序
     */
    private Integer sequence;
    /**
     * 系统操作备注
     */
    private String comment;
    /**
     * 系统操作记录创建时间
     */
    private Date gmtCreated;
    /**
     * 系统操作记录更新时间戳
     */
    private Date gmtModified;
    /**
     * 系统操作所属模块树路径
     */
    private String path;
}
