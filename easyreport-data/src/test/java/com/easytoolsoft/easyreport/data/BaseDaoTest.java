package com.easytoolsoft.easyreport.data;

import com.easytoolsoft.easyreport.data.common.dao.ICrudDao;
import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class BaseDaoTest<Dao extends ICrudDao<Po, Example>, Po, Example> extends BaseTest {
    @Autowired
    protected Dao dao;

    @Test
    public void insertTest() {
        dao.insert(this.getPo(999));
    }

    @Test
    public void batchInsertTest() {
        dao.batchInsert(this.getRecords());
    }

    @Test
    public void deleteByIdTest() {
        dao.deleteById(1);
    }

    @Test
    public void deleteByExampleTest() {
        dao.deleteByExample(this.getDeleteExample());
    }

    @Test
    public void deleteInTest() {
        dao.deleteIn(this.getRecords());
    }

    @Test
    public void updateByIdTest() {
        dao.updateById(this.getPo(999));
    }

    @Test
    public void updateByExampleTest() {
        dao.updateByExample(this.getPo(999), this.getUpdateExample());
    }

    @Test
    public void batchUpdateTest() {
        dao.batchUpdate(this.getRecords());
    }

    @Test
    public void selectByIdTest() {
        dao.selectById(1);
    }

    @Test
    public void selectByExampleTest() {
        dao.selectByExample(this.getSelectExample());
    }

    @Test
    public void selectOneByExampleTest() {
        dao.selectOneByExample(this.getSelectExample());
    }

    @Test
    public void selectInTest() {
        dao.selectIn(this.getRecords());
    }

    @Test
    public void countByPagerTest() {
        dao.countByPager(new PageInfo(), this.getSelectByPageExample());
    }

    @Test
    public void selectByPagerTest() {
        dao.selectByPager(new PageInfo(), this.getSelectByPageExample());
    }

    @Test
    public void countByExampleTest() {
        dao.countByExample(this.getCountExample());
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

    protected abstract Po getPo(Integer id);

    protected abstract Example getExample();
}
