package com.easytoolsoft.easyreport.mybatis.service;

/**
 * 基本增删改查(CRUD)数据访问服务接口
 *
 * @param <T> Po
 * @param <U> Example
 * @param <K> key字段数据类型(Integer,Long,String等)
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface CrudService<T, U, K> extends
    AddService<T>,
    RemoveService<T, U, K>,
    EditService<T, U>,
    GetService<T, U, K> {
}
