package com.easytoolsoft.easyreport.membership.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统模块(easyreport_member_module表)持久化类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Module implements Serializable {
    /**
     * 系统模块标识
     */
    private Integer id;
    /**
     * 系统模块父标识
     */
    private Integer parentId;
    /**
     * 系统模块父标识
     */
    private String name;
    /**
     * 系统模块代号
     */
    private String code;
    /**
     * 系统模块显示图标
     */
    private String icon;
    /**
     * 系统模块对应的页面地址
     */
    private String url;
    /**
     * 从根模块到当前子模块的id路径，id之间用逗号分隔
     */
    private String path;
    /**
     * 是否存在子模块,0否,1 是
     */
    private Byte hasChild;
    /**
     * URL链接类型(0表示系统内部，1表示外部链接，默认 0)
     */
    private Byte linkType;
    /**
     * URL链接的target(_blank,_top等)
     */
    private String target;
    /**
     * URL链接参数
     */
    private String params;
    /**
     * 系统模块在当前父模块下的排序顺序
     */
    private Integer sequence;
    /**
     * 系统模块的状态,1表示启用,0表示禁用,默认为1,其他保留
     */
    private Byte status;
    /**
     * 系统模块备注
     */
    private String comment;
    /**
     * 系统模块记录创建时间
     */
    private Date gmtCreated;
    /**
     * 系统模块记录更新时间戳
     */
    private Date gmtModified;
}
