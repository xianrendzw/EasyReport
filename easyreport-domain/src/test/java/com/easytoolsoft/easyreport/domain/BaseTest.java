package com.easytoolsoft.easyreport.domain;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.easytoolsoft.easyreport.data.util.SpringContextUtils;

public class BaseTest {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected final SpringContextUtils springContextUtil = new SpringContextUtils();

	@Before
	public void setUp() {
		ApplicationContext appContext = new FileSystemXmlApplicationContext(
				"target/test-classes/applicationContext.xml");
		springContextUtil.setApplicationContext(appContext);
	}

	@After
	public void tearDown() {
	}
}
