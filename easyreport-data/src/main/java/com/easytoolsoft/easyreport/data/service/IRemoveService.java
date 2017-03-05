package com.easytoolsoft.easyreport.data.service;

import java.util.List;

/**
 * @author tomdeng
 */
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
