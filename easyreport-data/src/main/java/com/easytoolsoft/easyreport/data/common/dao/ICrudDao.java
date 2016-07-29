package com.easytoolsoft.easyreport.data.common.dao;

/**
 * 基本增删改查(CRUD)数据访问接口
 *
 * @param <T>
 */
public interface ICrudDao<T> extends
        IInsertDao<T>,
        IDeleteDao<T>,
        IUpdateDao<T>,
        ISelectDao<T> {
}
