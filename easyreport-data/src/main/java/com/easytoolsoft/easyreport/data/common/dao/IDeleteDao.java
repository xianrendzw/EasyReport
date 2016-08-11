package com.easytoolsoft.easyreport.data.common.dao;

import java.util.List;

/**
 * @param <T> Po
 * @param <U> Example
 */
public interface IDeleteDao<T, U> {
    /**
     * 根据主键删除记录
     *
     * @param id id主键值
     * @return 影响的记录数
     */
    int deleteById(Integer id);

    /**
     * 根据条件删除记录
     *
     * @param example 查询Example条件参数
     * @return 影响的记录数
     */
    int deleteByExample(U example);

    /**
     * @param records
     * @return
     */
    int deleteIn(List<T> records);
}

