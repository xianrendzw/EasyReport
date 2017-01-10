package com.easytoolsoft.easyreport.data.service;

import com.easytoolsoft.easyreport.data.helper.PageInfo;

import java.util.List;

public interface IGetService<T, U> {
    /**
     * @param example
     * @return
     */
    boolean exists(U example);

    /**
     * 通过主键找出一条数据
     *
     * @param id 主键id值
     * @return
     */
    T getById(Integer id);

    /**
     * 根据条件查询零条及多条数据
     *
     * @param example 查询条件参数
     * @return 记录列表
     */
    List<T> getByExample(U example);

    /**
     * 根据条件查询所有记录
     *
     * @return 记录列表
     */
    List<T> getAll();

    /**
     * 根据条件查询一条数据
     *
     * @param example 查询条件参数
     * @return 分页记录列表
     */
    T getOneByExample(U example);

    /**
     * select in() 查询
     *
     * @param records
     * @return
     */
    List<T> getIn(List<T> records);

    /**
     * 分页查询
     *
     * @param pageInfo 分页参数
     * @return 分页记录列表
     */
    List<T> getByPage(PageInfo pageInfo);

    /**
     * 分页查询
     *
     * @param pageInfo  分页参数
     * @param fieldName where 筛选字段名
     * @param keyword   where 筛选字段模糊匹配关键字
     * @return 分页记录列表
     */
    List<T> getByPage(PageInfo pageInfo, String fieldName, String keyword);

    /**
     * 分页查询
     *
     * @param pageInfo 分页参数
     * @param example  where条件参数
     * @return 分页记录列表
     */
    List<T> getByPage(PageInfo pageInfo, U example);
}
