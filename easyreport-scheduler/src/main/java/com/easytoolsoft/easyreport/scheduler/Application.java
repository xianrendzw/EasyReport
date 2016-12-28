package com.easytoolsoft.easyreport.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class Application {

	@Autowired
	private QuartzManager quartz;
	
    public static void main(String[] args) {
    	ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:config/easyreport/spring/spring-main.xml");
    	Application service = appContext.getBean(Application.class);
    	service.quartz.reScheduleJob();
        SpringApplication.run(Application.class, args);
    }
}