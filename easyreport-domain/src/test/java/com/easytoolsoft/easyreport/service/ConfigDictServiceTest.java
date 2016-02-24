package com.easytoolsoft.easyreport.service;

import com.easytoolsoft.easyreport.data.util.SpringContextUtils;
import com.easytoolsoft.easyreport.domain.BaseTest;
import com.easytoolsoft.easyreport.po.ConfigDictPo;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ConfigDictServiceTest extends BaseTest {
    private ConfigDictService configDictService;

    @Before
    public void init() {
        this.configDictService = SpringContextUtils.getBean(ConfigDictService.class);
    }

    @Test
    public void testGetAll() {
        List<ConfigDictPo> list = this.configDictService.getAll();
        Assert.assertThat(10, IsEqual.equalTo(list.size()));
    }
}
