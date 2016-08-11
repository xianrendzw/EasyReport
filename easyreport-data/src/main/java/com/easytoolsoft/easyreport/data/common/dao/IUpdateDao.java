package com.easytoolsoft.easyreport.data.common.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @param <T> Po
 * @param <U> Example
 */
public interface IUpdateDao<T, U> {
    /**
     * 根据主键更新用户信息
     *
     * @param record
     * @return 影响的记录数
     */
    int updateById(T record);

    /**
     * 根据条件更新数据
     *
     * @param record
     * @param example
     * @return 影响的记录数
     */
    int updateByExample(@Param("record") T record, @Param("example") U example);

    /**
     * @param records
     * @return 影响的记录数
     */
    int batchUpdate(List<T> records);
}
