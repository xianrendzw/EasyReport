package com.easytoolsoft.easyreport.data.common.service;

import java.util.List;
import java.util.Map;

public interface IRemoveService<T> {
    /**
     * @param id
     * @return
     */
    int removeById(Integer id);

    /**
     * @param params
     * @return
     */
    int remove(Map<String, Object> params);

    /**
     * @param records
     * @return
     */
    int removeIn(List<T> records);
}
