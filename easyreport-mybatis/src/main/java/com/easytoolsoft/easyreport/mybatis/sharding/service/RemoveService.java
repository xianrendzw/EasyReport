package com.easytoolsoft.easyreport.mybatis.sharding.service;

import java.util.List;

import com.easytoolsoft.easyreport.mybatis.sharding.ShardTable;

/**
 * @param <T> Po
 * @param <U> Example
 * @param <K> key字段数据类型(Integer,Long,String等)
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface RemoveService<T, U, K> {
    /**
     * @param id         id
     * @param shardTable 分表对象
     * @return 影响的记录数
     */
    int removeById(K id, ShardTable shardTable);

    /**
     * @param example    pojo记录
     * @param shardTable 分表对象
     * @return 影响的记录数
     */
    int removeByExample(U example, ShardTable shardTable);

    /**
     * @param records    pojo记录集
     * @param shardTable 分表对象
     * @return 影响的记录数
     */
    int removeIn(List<T> records, ShardTable shardTable);
}
