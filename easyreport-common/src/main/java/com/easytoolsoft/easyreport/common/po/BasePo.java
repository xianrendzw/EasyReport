package com.easytoolsoft.easyreport.common.po;

import java.util.Date;

/**
 * PO类公共字段
 *
 * @author Tom Deng
 */
public abstract class BasePo {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 记录创建时间
     */
    private Date gmtCreated;
    /**
     * 记录修改时间
     */
    private Date gmtModified;
}
