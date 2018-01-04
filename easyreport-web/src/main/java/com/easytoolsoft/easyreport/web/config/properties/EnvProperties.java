package com.easytoolsoft.easyreport.web.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tom Deng
 * @date 2017-04-11
 **/
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "easytoolsoft.easyreport.env")
public class EnvProperties {
    private String appName;
    private String name;
    private String version;
}
