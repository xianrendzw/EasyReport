package com.easytoolsoft.easyreport.data.common.service;

import com.easytoolsoft.easyreport.data.common.dao.IUpdateDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public abstract class AbstractEditService<Dao extends IUpdateDao<Po>, Po> implements IEditService<Po> {
    @Autowired
    protected Dao dao;

    @Override
    public int editById(Po record) {
        return this.dao.updateById(record);
    }

    @Override
    public int edit(Map<String, Object> params) {
        return this.dao.update(params);
    }

    @Override
    public int batchEdit(List<Po> records) {
        return this.dao.batchUpdate(records);
    }
}
