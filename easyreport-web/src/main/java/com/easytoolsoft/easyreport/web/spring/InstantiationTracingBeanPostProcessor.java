package com.easytoolsoft.easyreport.web.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.easytoolsoft.easyreport.domain.schedule.service.QuartzManager;

@Component("BeanDefineConfigue") 
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private QuartzManager quartz;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("spring容易初始化完毕================================================888"); 
		quartz.reScheduleJob();
	}
}
