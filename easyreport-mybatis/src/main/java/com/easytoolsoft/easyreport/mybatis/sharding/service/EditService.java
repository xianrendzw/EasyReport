package com.easytoolsoft.easyreport.mybatis.sharding.service;

import java.util.List;

import com.easytoolsoft.easyreport.mybatis.sharding.ShardTable;

/**
 * @param <T> Po
 * @param <U> Example
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface EditService<T, U> {
    /**
     * 根据主键更新用户信息
     *
     * @param record     pojo记录
     * @param shardTable 分表对象
     * @return 影响的记录数
     */
    int editById(T record, ShardTable shardTable);

    /**
     * 根据条件更新数据
     *
     * @param record     pojo记录集
     * @param example    where条件对象
     * @param shardTable 分表对象
     * @return 影响的记录数
     */
    int editByExample(T record, U example, ShardTable shardTable);

    /**
     * @param records    pojo记录集
     * @param shardTable 分表对象
     * @return 影响的记录数
     */
    int batchEdit(List<T> records, ShardTable shardTable);
}
