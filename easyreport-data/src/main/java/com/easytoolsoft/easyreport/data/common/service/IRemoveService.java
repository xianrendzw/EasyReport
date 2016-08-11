package com.easytoolsoft.easyreport.data.common.service;

import java.util.List;

public interface IRemoveService<T, U> {
    /**
     * @param id
     * @return
     */
    int removeById(Integer id);

    /**
     * @param example
     * @return
     */
    int removeByExample(U example);

    /**
     * @param records
     * @return
     */
    int removeIn(List<T> records);
}
