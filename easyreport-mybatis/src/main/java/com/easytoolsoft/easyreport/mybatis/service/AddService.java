package com.easytoolsoft.easyreport.mybatis.service;

import java.util.List;

/**
 * @param <T> Po
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface AddService<T> {
    /**
     * @param record
     * @return
     */
    int add(T record);

    /**
     * @param records
     * @return
     */
    int batchAdd(List<T> records);

    /**
     * 使用mysql on duplicate key 语句插入与修改
     *
     * @param records
     * @return
     */
    int batchAddOnDuplicateKey(List<T> records);
}
