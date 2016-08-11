package com.easytoolsoft.easyreport.data.common.service;

import com.easytoolsoft.easyreport.data.common.dao.IDeleteDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @param <Dao>
 * @param <Po>
 * @param <Example>
 */
public abstract class AbstractRemoveService<Dao extends IDeleteDao<Po, Example>, Po, Example>
        implements IRemoveService<Po, Example> {
    @Autowired
    protected Dao dao;

    @Override
    public int removeById(Integer id) {
        return this.dao.deleteById(id);
    }

    @Override
    public int removeByExample(Example example) {
        return this.dao.deleteByExample(example);
    }

    @Override
    public int removeIn(List<Po> records) {
        return this.dao.deleteIn(records);
    }
}
