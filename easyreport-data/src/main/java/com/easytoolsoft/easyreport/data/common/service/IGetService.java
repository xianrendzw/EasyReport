package com.easytoolsoft.easyreport.data.common.service;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;

import java.util.List;
import java.util.Map;

public interface IGetService<T> {
    /**
     * 根据条件查询判断记录是否存在
     *
     * @param params
     * @return true|false
     */
    boolean exists(Map<String, Object> params);

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
     * @param params 查询条件参数
     * @return 记录列表
     */
    List<T> get(Map<String, Object> params);

    /**
     * 根据条件查询所有记录
     *
     * @return 记录列表
     */
    List<T> getAll();

    /**
     * 根据条件查询一条数据
     *
     * @param params 查询条件参数
     * @return 分页记录列表
     */
    T getOne(Map<String, Object> params);

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
     * @param pageInfo  分页参数
     * @param fieldName where 筛选字段名
     * @param keyword   where 筛选字段模糊匹配关键字
     * @param where     where条件参数
     * @return 分页记录列表
     */
    List<T> getByPage(PageInfo pageInfo, String fieldName, String keyword, Map<String, Object> where);
}
