package com.easytoolsoft.easyreport.web.config.properties;

import javax.validation.constraints.NotNull;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

/**
 * JSON Web Token 配置项类
 *
 * @author zhiwei.deng
 * @date 2017-05-24
 **/
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "easyreport.security.jwt")
@PropertySource("classpath:conf/properties/config.properties")
public class JwtConfig {
    private String header;
    private String secret;
    @NotNull
    private Long expiration;
}
