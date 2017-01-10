package com.easytoolsoft.easyreport.data.service;

import java.util.List;

public interface IAddService<T> {
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
}
