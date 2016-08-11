package com.easytoolsoft.easyreport.data.common.service;

import com.easytoolsoft.easyreport.data.common.dao.IInsertDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @param <Dao>
 * @param <Po>
 */
public abstract class AbstractAddService<Dao extends IInsertDao<Po>, Po> implements IAddService<Po> {
    @Autowired
    protected Dao dao;

    @Override
    public int add(Po record) {
        return this.dao.insert(record);
    }

    @Override
    public int batchAdd(List<Po> records) {
        return this.dao.batchInsert(records);
    }
}
