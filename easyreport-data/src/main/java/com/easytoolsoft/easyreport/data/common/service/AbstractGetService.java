package com.easytoolsoft.easyreport.data.common.service;

import com.easytoolsoft.easyreport.data.common.dao.ISelectDao;
import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @param <Dao>
 * @param <Po>
 * @param <Example>
 */
public abstract class AbstractGetService<Dao extends ISelectDao<Po, Example>, Po, Example>
        implements IGetService<Po, Example> {
    @Autowired
    protected Dao dao;

    @Override
    public boolean exists(Example example) {
        return this.dao.countByExample(example) > 0;
    }

    @Override
    public Po getById(Integer id) {
        return this.dao.selectById(id);
    }

    @Override
    public List<Po> getByExample(Example example) {
        return this.dao.selectByExample(example);
    }

    @Override
    public List<Po> getAll() {
        return this.dao.selectByExample(null);
    }

    @Override
    public Po getOneByExample(Example example) {
        return this.dao.selectOneByExample(example);
    }

    @Override
    public List<Po> getIn(List<Po> records) {
        return this.dao.selectIn(records);
    }

    @Override
    public List<Po> getByPage(PageInfo pageInfo) {
        return this.getByPage(pageInfo, null, null);
    }

    @Override
    public List<Po> getByPage(PageInfo pageInfo, String fieldName, String keyword) {
        return this.getByPage(pageInfo, this.getPageExample(fieldName, keyword));
    }

    @Override
    public List<Po> getByPage(PageInfo pageInfo, Example example) {
        pageInfo.setTotals(this.dao.countByPager(pageInfo, example));
        return this.dao.selectByPager(pageInfo, example);
    }

    protected abstract Example getPageExample(String fieldName, String keyword);
}
