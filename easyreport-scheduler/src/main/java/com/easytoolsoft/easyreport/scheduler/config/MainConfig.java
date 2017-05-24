package com.easytoolsoft.easyreport.scheduler.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Tom Deng
 * @date 2017-04-10
 **/
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.easytoolsoft.easyreport.meta"
})
public class MainConfig {
}
