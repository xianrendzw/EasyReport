package com.easytoolsoft.easyreport.data;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/config/spring/spring-test.xml"})
//@Transactional
public abstract class BaseTest {
}
