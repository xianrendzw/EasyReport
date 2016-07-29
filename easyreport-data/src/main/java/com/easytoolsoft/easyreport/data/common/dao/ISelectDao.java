package com.easytoolsoft.easyreport.data.common.dao;

import java.util.List;
import java.util.Map;


public interface ISelectDao<T> {
    /**
     * 通过主键找出一条数据
     *
     * @param id 主键id值
     * @return
     */
    T selectById(Integer id);

    /**
     * 根据条件查询零条及多条数据
     *
     * @param params 查询条件参数
     * @return 记录列表
     */
    List<T> select(Map<String, Object> params);

    /**
     * 根据条件查询一条数据
     *
     * @param params 查询条件参数
     * @return 分页记录列表
     */
    T selectOne(Map<String, Object> params);

    /**
     * @param records
     * @return
     */
    List<T> selectIn(List<T> records);

    /**
     * 获取当前分页查询的总记录数
     *
     * @param params 查询条件参数
     * @return 总记录数
     */
    int countByPager(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询条件参数
     * @return 分页记录列表
     */
    List<T> selectByPager(Map<String, Object> params);

    /**
     * 根据条件获取查询的总记录数
     *
     * @param params 查询条件参数
     * @return 总记录数
     */
    int count(Map<String, Object> params);
}
