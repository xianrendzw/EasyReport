package com.easytoolsoft.easyreport.data;

import com.easytoolsoft.easyreport.data.common.dao.ICrudDao;
import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class BaseDaoTest<Dao extends ICrudDao<Po, Example>, Po, Example> extends BaseTest {
    @Autowired
    protected Dao dao;

    @Before
    public void setup() {
        this.dao.deleteIn(this.getRecords());
        this.dao.deleteById(this.getId());
    }

    @Test
    public void insertTest() {
        this.dao.insert(this.getPo(999));
    }

    @Test
    public void batchInsertTest() {
        this.dao.batchInsert(this.getRecords());
    }

    @Test
    public void deleteByIdTest() {
        this.dao.deleteById(this.getId());
    }

    @Test
    public void deleteByExampleTest() {
        this.dao.deleteByExample(this.getDeleteExample());
    }

    @Test
    public void deleteInTest() {
        this.dao.deleteIn(this.getRecords());
    }

    @Test
    public void updateByIdTest() {
        this.dao.updateById(this.getPo(this.getId()));
    }

    @Test
    public void updateByExampleTest() {
        this.dao.updateByExample(this.getPo(this.getId()), this.getUpdateExample());
    }

    @Test
    public void batchUpdateTest() {
        this.dao.batchUpdate(this.getRecords());
    }

    @Test
    public void selectByIdTest() {
        this.dao.selectById(this.getId());
    }

    @Test
    public void selectByExampleTest() {
        this.dao.selectByExample(this.getSelectExample());
    }

    @Test
    public void selectOneByExampleTest() {
        this.dao.selectOneByExample(this.getSelectExample());
    }

    @Test
    public void selectInTest() {
        this.dao.selectIn(this.getRecords());
    }

    @Test
    public void countByPagerTest() {
        this.dao.countByPager(new PageInfo(), this.getSelectByPageExample());
    }

    @Test
    public void selectByPagerTest() {
        this.dao.selectByPager(new PageInfo(), this.getSelectByPageExample());
    }

    @Test
    public void countByExampleTest() {
        this.dao.countByExample(this.getCountExample());
    }

    protected List<Po> getRecords() {
        List<Po> records = new ArrayList<>(10);
        for (int i = 1000; i < 1010; i++) {
            records.add(this.getPo(i));
        }
        return records;
    }

    protected Example getDeleteExample() {
        return this.getExample();
    }

    protected Example getSelectExample() {
        return this.getExample();
    }

    protected Example getUpdateExample() {
        return this.getExample();
    }

    protected Example getSelectByPageExample() {
        return this.getExample();
    }

    protected Example getCountExample() {
        return this.getExample();
    }

    protected abstract Integer getId();

    protected abstract Po getPo(Integer id);

    protected abstract Example getExample();
}
