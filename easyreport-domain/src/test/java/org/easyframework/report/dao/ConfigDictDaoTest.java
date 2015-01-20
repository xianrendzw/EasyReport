package org.easyframework.report.dao;

import java.util.List;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.easyreport.dao.ConfigDictDao;
import com.easyreport.data.util.SpringContextUtils;
import com.easyreport.po.ConfigDictPo;

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
