package com.easytoolsoft.easyreport.mybatis.sharding.data;

import java.util.List;

import com.easytoolsoft.easyreport.mybatis.sharding.ShardTable;
import org.apache.ibatis.annotations.Param;

/**
 * @param <T> Po
 * @param <U> Example
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface UpdateRepository<T, U> {
    /**
     * 根据主键更新用户信息
     *
     * @param record     pojo记录
     * @param shardTable 分表对象
     * @return 影响的记录数
     */
    int updateById(@Param("record") T record, @Param("shardTable") ShardTable shardTable);

    /**
     * 根据条件更新数据
     *
     * @param record     pojo记录
     * @param example
     * @param shardTable 分表对象
     * @return 影响的记录数
     */
    int updateByExample(@Param("record") T record, @Param("example") U example,
                        @Param("shardTable") ShardTable shardTable);

    /**
     * @param records    pojo记录集
     * @param shardTable 分表对象
     * @return 影响的记录数
     */
    int batchUpdate(@Param("records") List<T> records, @Param("shardTable") ShardTable shardTable);
}
