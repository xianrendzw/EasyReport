package com.easytoolsoft.easyreport.web.config.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

/**
 * ajax请求跨域服务端配置
 *
 * @author zhiwei.deng
 * @date 2017-05-27
 **/
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "easyreport.cors")
@PropertySource("classpath:conf/properties/config.properties")
public class CorsConfig {
    private String allowOrigin;
    private String allowMethod;
    private String allowHeader;
    private String exposeHeader;
    private Boolean allowCredentials;
    private Long maxAge;
    private String pathPattern;

    public List<String> getAllowOrigins() {
        return this.asList(this.allowOrigin);
    }

    public List<String> getAllowMethods() {
        return this.asList(this.allowMethod);
    }

    public List<String> getAllowHeaders() {
        return this.asList(this.allowHeader);
    }

    public List<String> getExposeHeaders() {
        return this.asList(this.exposeHeader);
    }

    private List<String> asList(String str) {
        if (StringUtils.isNotBlank(str)) {
            return Arrays.asList(StringUtils.split(str, ','));
        }
        return new ArrayList<>(0);
    }
}

