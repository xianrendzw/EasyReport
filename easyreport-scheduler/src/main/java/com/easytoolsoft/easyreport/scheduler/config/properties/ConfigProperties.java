package com.easytoolsoft.easyreport.scheduler.config.properties;

import javax.validation.Valid;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

/**
 * 自定义配置类
 *
 * @author Tom Deng
 * @date 2017-04-19
 **/
@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = "easytoolsoft.easyreport")
@PropertySource("classpath:conf/properties/config.properties")
public class ConfigProperties {
    @Valid
    private final Shiro shiro = new Shiro();

    public static class Shiro {
        @NotEmpty
        private String filters;

        public String getFilters() {
            return this.filters;
        }

        public void setFilters(final String filters) {
            this.filters = filters;
        }
    }
}
