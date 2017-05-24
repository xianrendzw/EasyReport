package com.easytoolsoft.easyreport.web.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Tom Deng
 * @date 2017-04-11
 **/
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "easytoolsoft.easyreport.env")
@PropertySource("classpath:conf/properties/env.properties")
public class EnvProperties {
    private String appName;
    private String name;
    private String version;
}
