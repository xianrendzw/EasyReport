package com.easytoolsoft.easyreport.data.common.service;

import com.easytoolsoft.easyreport.data.common.dao.ICrudDao;

import java.util.List;

/**
 * 基本增删改查(CRUD)数据访问服务基类
 *
 * @param <Dao>
 * @param <Po>
 * @param <Example>
 */
public abstract class AbstractCrudService<Dao extends ICrudDao<Po, Example>, Po, Example>
        extends AbstractGetService<Dao, Po, Example> implements ICrudService<Po, Example> {
    @Override
    public int add(Po record) {
        return this.dao.insert(record);
    }

    @Override
    public int batchAdd(List<Po> records) {
        return this.dao.batchInsert(records);
    }

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
