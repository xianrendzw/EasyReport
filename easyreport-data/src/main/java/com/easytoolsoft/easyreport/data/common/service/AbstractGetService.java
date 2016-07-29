package com.easytoolsoft.easyreport.data.common.service;

import com.easytoolsoft.easyreport.data.common.dao.ISelectDao;
import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbstractGetService<Dao extends ISelectDao<Po>, Po> implements IGetService<Po> {
    @Autowired
    protected Dao dao;

    @Override
    public boolean exists(Map<String, Object> params) {
        return this.dao.count(params) > 0;
    }

    @Override
    public Po getById(Integer id) {
        return this.dao.selectById(id);
    }

    @Override
    public List<Po> get(Map<String, Object> params) {
        return this.dao.select(params);
    }

    @Override
    public List<Po> getAll() {
        return this.dao.select(new HashMap<>(0));
    }

    @Override
    public Po getOne(Map<String, Object> params) {
        return this.dao.selectOne(params);
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
        return this.getByPage(pageInfo, fieldName, keyword, new HashMap<>(2));
    }

    @Override
    public List<Po> getByPage(PageInfo pageInfo, String fieldName, String keyword, Map<String, Object> where) {
        if (where == null) {
            where = new HashMap<>(5);
        }
        where.put("fieldName", fieldName);
        where.put("keyword", keyword);

        Map<String, Object> params = new HashMap<>(2);
        params.put("pager", pageInfo);
        params.put("where", where);
        pageInfo.setTotals(this.dao.countByPager(params));
        return this.dao.selectByPager(params);
    }
}
