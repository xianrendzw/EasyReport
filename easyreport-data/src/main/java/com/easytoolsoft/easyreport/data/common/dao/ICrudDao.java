package com.easytoolsoft.easyreport.data.common.dao;

/**
 * 基本增删改查(CRUD)数据访问接口
 *
 * @param <T> Po
 * @param <U> Example
 */
public interface ICrudDao<T, U> extends
        IInsertDao<T>,
        IDeleteDao<T, U>,
        IUpdateDao<T, U>,
        ISelectDao<T, U> {
}
