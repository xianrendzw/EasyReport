package com.easytoolsoft.easyreport.mybatis.sharding.data;

import java.util.List;

import com.easytoolsoft.easyreport.mybatis.pager.PageInfo;
import com.easytoolsoft.easyreport.mybatis.sharding.ShardTable;
import org.apache.ibatis.annotations.Param;

/**
 * @param <T> Po
 * @param <U> Example
 * @param <K> Key字段数据类型(Integer,Long,String等)
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface SelectRepository<T, U, K> {
    /**
     * 通过主键找出一条数据
     *
     * @param id         主键id值
     * @param shardTable 分表对象
     * @return
     */
    T selectById(@Param("id") K id, @Param("shardTable") ShardTable shardTable);

    /**
     * 根据条件查询零条及多条数据
     *
     * @param example    查询条件参数
     * @param shardTable 分表对象
     * @return 记录列表
     */
    List<T> selectByExample(@Param("example") U example, @Param("shardTable") ShardTable shardTable);

    /**
     * 根据条件查询一条数据
     *
     * @param example    查询条件参数
     * @param shardTable 分表对象
     * @return 分页记录列表
     */
    T selectOneByExample(@Param("example") U example, @Param("shardTable") ShardTable shardTable);

    /**
     * @param records    pojo记录集
     * @param shardTable 分表对象
     * @return
     */
    List<T> selectIn(@Param("records") List<T> records, @Param("shardTable") ShardTable shardTable);

    /**
     * 获取当前分页查询的总记录数
     *
     * @param pager      分页
     * @param example    查询条件参数
     * @param shardTable 分表对象
     * @return 总记录数
     */
    int countByPager(@Param("pager") PageInfo pager, @Param("example") U example,
                     @Param("shardTable") ShardTable shardTable);

    /**
     * 分页查询
     *
     * @param pager      分页对象
     * @param example    查询条件参数
     * @param shardTable 分表对象
     * @return 分页记录列表
     */
    List<T> selectByPager(@Param("pager") PageInfo pager, @Param("example") U example,
                          @Param("shardTable") ShardTable shardTable);

    /**
     * 根据条件获取查询的总记录数
     *
     * @param example    查询条件参数
     * @param shardTable 分表对象
     * @return 总记录数
     */
    int countByExample(@Param("example") U example, @Param("shardTable") ShardTable shardTable);
}
