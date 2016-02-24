package com.easytoolsoft.easyreport.dao;

import com.easytoolsoft.easyreport.data.util.SpringContextUtils;
import com.easytoolsoft.easyreport.po.ConfigDictPo;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ConfigDictDaoTest {
    private ConfigDictDao configDictDao;

    @Before
    public void init() {
        this.configDictDao = SpringContextUtils.getBean(ConfigDictDao.class);
    }

    @Test
    public void testGetAll() {
        List<ConfigDictPo> list = this.configDictDao.query();
        Assert.assertThat(10, IsEqual.equalTo(list.size()));
    }
}
