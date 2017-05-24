package com.easytoolsoft.easyreport.mybatis.data;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * @param <T>
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface InsertRepository<T> {
    /**
     * 插入一条数据，忽略record中的ID
     *
     * @param record
     * @return 影响的记录数
     */
    int insert(@Param("record") T record);

    /**
     * @param records
     * @return 影响的记录数
     */
    int batchInsert(@Param("records") List<T> records);

    /**
     * 使用mysql on duplicate key 语句插入与修改
     *
     * @param records pojo记录集
     * @return 影响的记录数
     */
    int batchInsertOnDuplicateKey(@Param("records") List<T> records);
}
