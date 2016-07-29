package com.easytoolsoft.easyreport.data.common.service;

import com.easytoolsoft.easyreport.data.common.dao.ICrudDao;

import java.util.List;
import java.util.Map;

/**
 * 基本增删改查(CRUD)数据访问服务基类
 *
 * @param <Dao> crud数据访问类
 * @param <Po>  pojo类
 */
public abstract class AbstractCrudService<Dao extends ICrudDao<Po>, Po> extends AbstractGetService<Dao, Po>
        implements ICrudService<Po> {
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
    public int edit(Map<String, Object> params) {
        return this.dao.update(params);
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
    public int remove(Map<String, Object> params) {
        return this.dao.delete(params);
    }

    @Override
    public int removeIn(List<Po> records) {
        return this.dao.deleteIn(records);
    }
}
