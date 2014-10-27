package org.easyframework.report.service;

import java.util.List;

import org.easyframework.report.data.BaseTest;
import org.easyframework.report.data.util.SpringContextUtils;
import org.easyframework.report.po.ConfigDictPo;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
