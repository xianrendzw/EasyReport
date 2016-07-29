package com.easytoolsoft.easyreport.data.common.service;

import com.easytoolsoft.easyreport.data.common.dao.IDeleteDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public abstract class AbstractRemoveService<Dao extends IDeleteDao<Po>, Po> implements IRemoveService<Po> {
    @Autowired
    protected Dao dao;

    @Override
    public int removeById(Integer id) {
        return this.dao.deleteById(id);
    }

    @Override
    public int remove(Map<String, Object> params) {
        return this.dao.delete(params);
    }

    @Override
    public int removeIn(List<Po> records) {
        return this.dao.deleteIn(records);
    }
}
