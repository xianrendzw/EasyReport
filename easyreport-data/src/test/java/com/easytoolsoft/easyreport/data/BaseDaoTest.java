package com.easytoolsoft.easyreport.data;

import com.easytoolsoft.easyreport.data.common.dao.ICrudDao;
import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
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
        Po exceptPo = this.getPo(999);
        this.dao.insert(exceptPo);
    }

    @Test
    public void batchInsertTest() {
        this.dao.batchInsert(this.getRecords());
        List<Po> list = this.dao.selectIn(this.getRecords());
        Integer count = list == null ? 0 : list.size();
        Assert.assertThat(count, IsEqual.equalTo(10));
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
        Integer count = this.dao.countByExample(this.getCountExample());
        Assert.assertThat(count, IsEqual.equalTo(1));
    }

    @Test
    public void updateByIdTest() {
        Integer id = this.getId();
        this.dao.updateById(this.getPo(id));
    }

    @Test
    public void updateByExampleTest() {
        Po expectPo = this.getPo(this.getId());
        this.dao.updateByExample(expectPo, this.getUpdateExample());
        Po actualPo = this.dao.selectOneByExample(this.getUpdateExample());
        this.assertThat(actualPo, expectPo);
    }

    @Test
    public void batchUpdateTest() {
        this.dao.batchUpdate(this.getRecords());
    }

    @Test
    public void deleteByIdTest() {
        Integer id = this.getId();
        this.dao.deleteById(id);
        Po po = this.dao.selectById(id);
        Integer count = po == null ? 0 : 1;
        Assert.assertThat(count, IsEqual.equalTo(0));
    }

    @Test
    public void deleteByExampleTest() {
        this.dao.deleteByExample(this.getDeleteExample());
        List<Po> list = this.dao.selectByExample(this.getDeleteExample());
        Integer count = list == null ? 0 : list.size();
        Assert.assertThat(count, IsEqual.equalTo(0));
    }

    @Test
    public void deleteInTest() {
        List<Po> records = this.getRecords();
        this.dao.deleteIn(records);
        List<Po> list = this.dao.selectIn(records);
        Integer count = list == null ? 0 : list.size();
        Assert.assertThat(count, IsEqual.equalTo(0));
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

    protected abstract void assertThat(Po actualPo, Po expectPo);
}
