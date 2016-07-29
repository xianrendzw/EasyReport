package com.easytoolsoft.easyreport.data.common.service;


import java.util.List;
import java.util.Map;

public interface IEditService<T> {
    /**
     * 根据主键更新用户信息
     *
     * @param record
     * @return 影响的记录数
     */
    int editById(T record);

    /**
     * 根据条件更新数据
     *
     * @param params
     * @return 影响的记录数
     */
    int edit(Map<String, Object> params);

    /**
     * @param records
     * @return 影响的记录数
     */
    int batchEdit(List<T> records);
}
