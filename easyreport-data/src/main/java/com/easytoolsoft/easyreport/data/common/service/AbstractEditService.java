package com.easytoolsoft.easyreport.data.common.service;

import com.easytoolsoft.easyreport.data.common.dao.IUpdateDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @param <Dao>
 * @param <Po>
 * @param <Example>
 */
public abstract class AbstractEditService<Dao extends IUpdateDao<Po, Example>, Po, Example>
        implements IEditService<Po, Example> {
    @Autowired
    protected Dao dao;

    @Override
    public int editById(Po record) {
        return this.dao.updateById(record);
    }

    @Override
    public int editByExample(Po record, Example example) {
        return this.dao.updateByExample(record, example);
    }

    @Override
    public int batchEdit(List<Po> records) {
        return this.dao.batchUpdate(records);
    }
}
