package com.easytoolsoft.easyreport.data.common.dao;

import java.util.List;

/**
 * @param <T>
 */
public interface IInsertDao<T> {
    /**
     * 插入一条数据，忽略record中的ID
     *
     * @param record
     * @return 影响的记录数
     */
    int insert(T record);

    /**
     * @param records
     * @return 影响的记录数
     */
    int batchInsert(List<T> records);
}
