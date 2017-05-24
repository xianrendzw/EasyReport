package com.easytoolsoft.easyreport.mybatis.sharding.service;

import java.util.List;

import com.easytoolsoft.easyreport.mybatis.pager.PageInfo;
import com.easytoolsoft.easyreport.mybatis.sharding.ShardTable;

/**
 * @param <T> Po
 * @param <U> Example
 * @param <K> key字段数据类型(Integer,Long,String等)
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface GetService<T, U, K> {
    /**
     * @param example
     * @return
     */
    boolean exists(U example, ShardTable shardTable);

    /**
     * 通过主键找出一条数据
     *
     * @param id         主键id值
     * @param shardTable 分表对象
     * @return 当前id对象的记录
     */
    T getById(K id, ShardTable shardTable);

    /**
     * 根据条件查询零条及多条数据
     *
     * @param example    查询条件参数
     * @param shardTable 分表对象
     * @return 记录列表
     */
    List<T> getByExample(U example, ShardTable shardTable);

    /**
     * 根据条件查询所有记录
     *
     * @param shardTable 分表对象
     * @return 记录列表
     */
    List<T> getAll(ShardTable shardTable);

    /**
     * 根据条件查询一条数据
     *
     * @param example    查询条件参数
     * @param shardTable 分表对象
     * @return 分页记录列表
     */
    T getOneByExample(U example, ShardTable shardTable);

    /**
     * select in() 查询
     *
     * @param records
     * @param shardTable 分表对象
     * @return 记录列表
     */
    List<T> getIn(List<T> records, ShardTable shardTable);

    /**
     * 分页查询
     *
     * @param pageInfo   分页参数
     * @param shardTable 分表对象
     * @return 分页记录列表
     */
    List<T> getByPage(PageInfo pageInfo, ShardTable shardTable);

    /**
     * 分页查询
     *
     * @param pageInfo   分页参数
     * @param fieldName  where 筛选字段名
     * @param keyword    where 筛选字段模糊匹配关键字
     * @param shardTable 分表对象
     * @return 分页记录列表
     */
    List<T> getByPage(PageInfo pageInfo, String fieldName, String keyword, ShardTable shardTable);

    /**
     * 分页查询
     *
     * @param pageInfo   分页参数
     * @param example    where条件参数
     * @param shardTable 分表对象
     * @return 分页记录列表
     */
    List<T> getByPage(PageInfo pageInfo, U example, ShardTable shardTable);
}
