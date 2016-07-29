package com.easytoolsoft.easyreport.data.common.dao;

import java.util.List;
import java.util.Map;

public interface IDeleteDao<T> {
    /**
     * 根据主键删除记录
     *
     * @param id id主键值
     * @return 影响的记录数
     */
    int deleteById(Integer id);

    /**
     * 根据条件删除记录
     *
     * @param params 查询条件参数
     * @return 影响的记录数
     */
    int delete(Map<String, Object> params);

    /**
     * @param records
     * @return
     */
    int deleteIn(List<T> records);
}

