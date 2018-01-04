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
@ConfigurationProperties(prefix = "easytoolsoft.easyreport.common")
public class CommonProperties {
    private String item1;
    private String item2;
}
